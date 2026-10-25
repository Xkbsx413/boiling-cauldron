package com.boilingcauldron.block.entity;

import com.boilingcauldron.block.BloomingLotusLeafBlock;
import com.boilingcauldron.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.ArrayList;
import java.util.List;

public class BloomingLotusLeafBlockEntity extends BlockEntity {

    private static final int GROWTH_INTERVAL = 1200;
    private static final int CONVERSION_INTERVAL = 600;
    private static final int MAX_GROWTH = 4;
    private static final int MIN_SCAN_DEPTH = 2;
    private static final int MAX_SCAN_DEPTH = 4;
    private static final int DIRTY_INTERVAL = 20;
    private static final int DATA_VERSION = 1;

    private int growth;
    private int growthTicks;
    private int conversionTicks;

    public BloomingLotusLeafBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BLOOMING_LOTUS_LEAF, pos, state);
    }

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, BloomingLotusLeafBlockEntity blockEntity) {
        if (world.isClient) {
            return;
        }
        if (!(world instanceof ServerWorld serverWorld)) {
            return;
        }

        blockEntity.syncGrowthState(world, pos);

        if (blockEntity.growth >= MAX_GROWTH) {
            blockEntity.growthTicks = 0;
            blockEntity.conversionTicks++;
            if (blockEntity.conversionTicks >= CONVERSION_INTERVAL) {
                blockEntity.conversionTicks = 0;
                blockEntity.tryConvert(serverWorld, pos);
            }
        } else {
            blockEntity.conversionTicks = 0;
            blockEntity.growthTicks++;
            if (blockEntity.growthTicks >= GROWTH_INTERVAL) {
                blockEntity.growthTicks = 0;
                blockEntity.growth++;
                blockEntity.markDirty();
                blockEntity.syncGrowthState(world, pos);
                if (blockEntity.growth >= MAX_GROWTH) {
                    blockEntity.conversionTicks = 0;
                }
            }
        }

        if (blockEntity.growthTicks % DIRTY_INTERVAL == 0 || blockEntity.conversionTicks % DIRTY_INTERVAL == 0) {
            blockEntity.markDirty();
        }
    }

    public boolean useBoneMeal(ServerWorld world, Random random) {
        if (growth < MAX_GROWTH) {
            growth++;
            if (growth >= MAX_GROWTH) {
                growthTicks = 0;
                conversionTicks = 0;
            }
            markDirty();
            syncGrowthState(world, pos);
            return false;
        }

        if (trySpread(world, random)) {
            growth = 0;
            growthTicks = 0;
            conversionTicks = 0;
            markDirty();
            syncGrowthState(world, pos);
            return true;
        }

        markDirty();
        return false;
    }

    private void tryConvert(ServerWorld world, BlockPos pos) {
        BlockPos targetPos = findConversionTarget(world, pos);
        if (targetPos == null) {
            markDirty();
            return;
        }

        if (!world.setBlockState(targetPos, ModBlocks.LOTUS_MUD.getDefaultState(), Block.NOTIFY_ALL)) {
            markDirty();
            return;
        }

        growth = 0;
        growthTicks = 0;
        conversionTicks = 0;
        markDirty();
        syncGrowthState(world, pos);
        BoneMealItem.createParticles(world, pos, 15);
    }

    private boolean trySpread(ServerWorld world, Random random) {
        List<BlockPos> candidates = new ArrayList<>();

        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                if (dx == 0 && dz == 0) {
                    continue;
                }
                BlockPos targetPos = pos.add(dx, 0, dz);
                if (canSpreadTo(world, targetPos)) {
                    candidates.add(targetPos);
                }
            }
        }

        if (candidates.isEmpty()) {
            return false;
        }

        BlockPos targetPos = candidates.get(random.nextInt(candidates.size()));
        return world.setBlockState(targetPos, ModBlocks.BLOOMING_LOTUS_LEAF.getDefaultState(), Block.NOTIFY_ALL);
    }

    private static boolean canSpreadTo(WorldView world, BlockPos targetPos) {
        if (isChunkUnloaded(world, targetPos)) {
            return false;
        }
        if (!world.getBlockState(targetPos).isAir()) {
            return false;
        }

        BlockPos supportPos = targetPos.down();
        BlockState supportState = world.getBlockState(supportPos);
        if (!supportState.isOf(Blocks.WATER)) {
            return false;
        }
        if (!world.getFluidState(supportPos).isStill()) {
            return false;
        }

        return ModBlocks.BLOOMING_LOTUS_LEAF.getDefaultState().canPlaceAt(world, targetPos);
    }

    private static BlockPos findConversionTarget(WorldView world, BlockPos pos) {
        for (int dy = MIN_SCAN_DEPTH; dy <= MAX_SCAN_DEPTH; dy++) {
            BlockPos checkPos = pos.down(dy);
            if (isChunkUnloaded(world, checkPos)) {
                return null;
            }

            BlockState state = world.getBlockState(checkPos);
            if (isPassThrough(state)) {
                continue;
            }
            if (isConvertible(state)) {
                return checkPos;
            }
            return null;
        }
        return null;
    }

    private static boolean isChunkUnloaded(WorldView world, BlockPos pos) {
        return !world.isPosLoaded(pos.getX(), pos.getZ());
    }

    private static boolean isPassThrough(BlockState state) {
        if (state.isOf(Blocks.WATER) || state.isOf(Blocks.BUBBLE_COLUMN)) {
            return true;
        }
        if (state.isOf(Blocks.KELP) || state.isOf(Blocks.KELP_PLANT)
            || state.isOf(Blocks.SEAGRASS) || state.isOf(Blocks.TALL_SEAGRASS)) {
            return true;
        }
        if (state.isOf(Blocks.SEA_PICKLE) || state.isOf(Blocks.LILY_PAD)) {
            return true;
        }
        return state.isIn(BlockTags.CORALS)
            || state.isIn(BlockTags.CORAL_BLOCKS)
            || state.isIn(BlockTags.WALL_CORALS);
    }

    private static boolean isConvertible(BlockState state) {
        return state.isIn(BlockTags.DIRT)
            || state.isIn(BlockTags.SAND)
            || state.isOf(Blocks.GRAVEL);
    }

    private void syncGrowthState(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (!state.isOf(ModBlocks.BLOOMING_LOTUS_LEAF)) {
            return;
        }
        if (state.get(BloomingLotusLeafBlock.GROWTH) != growth) {
            world.setBlockState(pos, state.with(BloomingLotusLeafBlock.GROWTH, growth), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("LotusDataVersion", DATA_VERSION);
        nbt.putInt("Growth", growth);
        nbt.putInt("GrowthTicks", growthTicks);
        nbt.putInt("ConversionTicks", conversionTicks);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (nbt.getInt("LotusDataVersion") >= DATA_VERSION) {
            growth = MathHelper.clamp(nbt.getInt("Growth"), 0, MAX_GROWTH);
            growthTicks = MathHelper.clamp(nbt.getInt("GrowthTicks"), 0, GROWTH_INTERVAL);
            conversionTicks = MathHelper.clamp(nbt.getInt("ConversionTicks"), 0, CONVERSION_INTERVAL);

            if (growth >= MAX_GROWTH) {
                growthTicks = 0;
            } else {
                conversionTicks = 0;
            }
        } else {
            growth = 0;
            growthTicks = 0;
            conversionTicks = 0;
        }

        markDirty();
    }
}
