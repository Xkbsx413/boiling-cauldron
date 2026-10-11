package com.boilingcauldron.block;

import com.boilingcauldron.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class SichuanPepperCropBlock extends CropBlock {
    public SichuanPepperCropBlock(Settings settings) {super(settings);}
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = Properties.AGE_3;

    private ItemConvertible seedsItem;

    public void setSeedsItem(ItemConvertible seedsItem) {
        this.seedsItem = seedsItem;
    }

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 8.0, 13.0),
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0),
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 14.0, 14.0),
            Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0)
    };

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return this.seedsItem;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[state.get(AGE)];
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return blockState.isOf(Blocks.FARMLAND) || blockState.isOf(Blocks.DIRT) || blockState.isOf(Blocks.GRASS_BLOCK) || blockState.isOf(Blocks.SAND);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int age = state.get(AGE);
        boolean mature = age == MAX_AGE;

        if (mature) {
            // 右键采集：掉落 1-2 个花椒，回退到第二阶段
            if (!world.isClient) {
                dropStack(world, pos, new ItemStack(ModItems.Sichuan_Pepper, world.getRandom().nextBetween(1, 2)));
                world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 1.0F);

                // 回退到第二阶段
                BlockState newState = state.with(AGE, 1);
                world.setBlockState(pos, newState, 3);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, newState));
            }
            return ActionResult.success(world.isClient);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        // 掉落由战利品表处理，不再硬编码
        super.onBreak(world, pos, state, player);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < MAX_AGE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos.up(), 0) >= 9 && random.nextInt(2) == 0) {
            int age = state.get(AGE);
            if (age < MAX_AGE) {
                world.setBlockState(pos, state.with(AGE, age + 1), 3);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}

