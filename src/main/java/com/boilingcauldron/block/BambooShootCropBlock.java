package com.boilingcauldron.block;

import com.boilingcauldron.item.ModItems;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class BambooShootCropBlock extends CropBlock {

    public static final int MAX_AGE = 5;
    public static final IntProperty AGE = Properties.AGE_5;

    public BambooShootCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.Winter_Bamboo_Shoots;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        // 与原版竹子相同的地基，保证骨粉转化后竹子不会掉落
        return floor.isIn(BlockTags.BAMBOO_PLANTABLE_ON);
    }

    // 不受光照限制，仅要求合适的地基
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return this.canPlantOnTop(world.getBlockState(pos.down()), world, pos);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < MAX_AGE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(2) == 0) {
            int age = state.get(AGE);
            if (age < MAX_AGE) {
                world.setBlockState(pos, state.with(AGE, age + 1), 3);
            }
        }
    }

    // 骨粉：未成熟无效，仅成熟时可用
    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return this.isMature(state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return this.isMature(state);
    }

    // 成熟期用骨粉：变为原版竹子，并按原版竹子骨粉逻辑生长 1-2 节
    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockState bambooState = Blocks.BAMBOO.getDefaultState();
        world.setBlockState(pos, bambooState, 3);
        ((BambooBlock) Blocks.BAMBOO).grow(world, random, pos, bambooState);
    }
}
