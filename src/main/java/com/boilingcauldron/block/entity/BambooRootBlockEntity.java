package com.boilingcauldron.block.entity;

import com.boilingcauldron.block.BambooRootBlock;
import com.boilingcauldron.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BambooRootBlockEntity extends BlockEntity {

    public static final int MAX_BAMBOO_COUNT = 5;
    private static final int CHECK_INTERVAL = 1200; // 1 分钟
    private static final float TRIGGER_CHANCE = 0.55f; // 每次尝试基础成功率 55%
    private static final float CROP_CHANCE = 0.55f; // 55% 竹笋作物，45% 竹荪菌落
    private static final float BAMBOO_BONUS_PER_BLOCK = 0.01f; // 检测范围内每格原版竹子：成功率 +1%
    private static final float MAX_BAMBOO_BONUS = 0.20f; // 竹子加成至多 +20%

    private int ticks;
    // 已生成产物的位置，产物被破坏/采收后移除，计数回落并恢复繁殖
    private final List<BlockPos> spawnedPositions = new ArrayList<>();

    public BambooRootBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BAMBOO_ROOT, pos, state);
    }

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, BambooRootBlockEntity blockEntity) {
        if (world.isClient) {
            return;
        }

        // 每 tick 清理已消失的产物并同步计数（实时回落）
        blockEntity.pruneSpawned(world);
        blockEntity.syncCount(world, pos);

        blockEntity.ticks++;
        if (blockEntity.ticks < CHECK_INTERVAL) {
            return;
        }

        blockEntity.ticks = 0;
        blockEntity.tryReproduce(world, pos);
    }

    private void tryReproduce(World world, BlockPos pos) {
        int alive = spawnedPositions.size();
        if (alive >= MAX_BAMBOO_COUNT) {
            return; // 存活产物数量达到上限，停止
        }

        int spawned = 0;
        Random random = world.random;
        // 5×5×8 范围内每格原版竹子 +1%，至多 +20%
        float successChance = TRIGGER_CHANCE + Math.min(countNearbyBamboo(world, pos) * BAMBOO_BONUS_PER_BLOCK, MAX_BAMBOO_BONUS);

        for (int attempt = 0; attempt < 2; attempt++) {
            if (alive + spawned >= MAX_BAMBOO_COUNT) {
                break;
            }
            if (random.nextFloat() >= successChance) {
                continue; // 本次尝试未成功
            }

            BlockPos target = pickSpawnPosition(world, pos);
            if (target == null) {
                continue;
            }

            BlockState product = random.nextFloat() < CROP_CHANCE
                    ? ModBlocks.BAMBOO_SHOOT_CROP.getDefaultState()
                    : ModBlocks.BAMBOO_FUNGUS_COLONY.getDefaultState();

            if (!product.canPlaceAt(world, target)) {
                continue;
            }

            world.setBlockState(target, product, 3);
            spawnedPositions.add(target);
            spawned++;
        }

        if (spawned > 0) {
            syncCount(world, pos);
        }
    }

    private void pruneSpawned(World world) {
        spawnedPositions.removeIf(spawnedPos -> {
            if (isChunkUnloaded(world, spawnedPos)) {
                return false; // 未加载时保留，避免误清
            }

            BlockState state = world.getBlockState(spawnedPos);
            // 被采收/破坏/替换，或竹笋被施肥长成竹子：移出清单，计数 -1；原版竹子不计入
            return !state.isOf(ModBlocks.BAMBOO_SHOOT_CROP) && !state.isOf(ModBlocks.BAMBOO_FUNGUS_COLONY);
        });
    }

    // 将方块状态 bamboo_count 同步为当前存活产物数量
    private void syncCount(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!state.isOf(ModBlocks.BAMBOO_ROOT)) {
            return;
        }

        int clamped = Math.min(spawnedPositions.size(), MAX_BAMBOO_COUNT);
        if (state.get(BambooRootBlock.BAMBOO_COUNT) != clamped) {
            world.setBlockState(pos, state.with(BambooRootBlock.BAMBOO_COUNT, clamped), 3);
        }
    }

    private BlockPos pickSpawnPosition(World world, BlockPos pos) {
        List<BlockPos> candidates = new ArrayList<>();
        BlockPos scanRow = pos.up(); // 上方 1 格

        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                BlockPos floorPos = scanRow.add(dx, 0, dz);
                if (isChunkUnloaded(world, floorPos)) {
                    continue;
                }

                BlockState floor = world.getBlockState(floorPos);
                if (!isSoil(floor)) {
                    continue;
                }

                BlockPos target = floorPos.up(); // 该方块上方一格
                if (!world.getBlockState(target).isAir()) {
                    continue;
                }

                candidates.add(target);
            }
        }

        if (candidates.isEmpty()) {
            return null;
        }

        return candidates.get(world.random.nextInt(candidates.size()));
    }

    private static boolean isSoil(BlockState state) {
        return state.isIn(BlockTags.DIRT) || state.isOf(Blocks.FARMLAND);
    }

    private static boolean isChunkUnloaded(World world, BlockPos pos) {
        return !world.isPosLoaded(pos.getX(), pos.getZ());
    }

    // 统计 5×5×8（水平 ±2，根块所在层向上 8 格）内的原版竹子数量
    private int countNearbyBamboo(World world, BlockPos pos) {
        int count = 0;
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                for (int dy = 0; dy < 8; dy++) {
                    BlockPos checkPos = pos.add(dx, dy, dz);
                    if (isChunkUnloaded(world, checkPos)) {
                        continue;
                    }
                    if (world.getBlockState(checkPos).isOf(Blocks.BAMBOO)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Ticks", ticks);
        long[] positions = new long[spawnedPositions.size()];
        for (int i = 0; i < positions.length; i++) {
            positions[i] = spawnedPositions.get(i).asLong();
        }
        nbt.putLongArray("Spawned", positions);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        ticks = nbt.getInt("Ticks");
        spawnedPositions.clear();
        for (long position : nbt.getLongArray("Spawned")) {
            spawnedPositions.add(BlockPos.fromLong(position));
        }
    }
}
