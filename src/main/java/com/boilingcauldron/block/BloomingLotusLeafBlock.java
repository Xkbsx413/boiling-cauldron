package com.boilingcauldron.block;

import com.boilingcauldron.block.entity.BloomingLotusLeafBlockEntity;
import com.boilingcauldron.block.entity.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BloomingLotusLeafBlock extends LilyPadBlock implements BlockEntityProvider {

    public static final IntProperty GROWTH = IntProperty.of("growth", 0, 4);

    public BloomingLotusLeafBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(GROWTH, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GROWTH);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BloomingLotusLeafBlockEntity(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (!stack.isOf(Items.BONE_MEAL)) {
            return ActionResult.PASS;
        }
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        if (!(world.getBlockEntity(pos) instanceof BloomingLotusLeafBlockEntity blockEntity)) {
            return ActionResult.PASS;
        }

        boolean spreadSuccess = blockEntity.useBoneMeal((ServerWorld) world, world.random);
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }
        if (spreadSuccess) {
            BoneMealItem.createParticles(world, pos, 15);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient || type != ModBlockEntities.BLOOMING_LOTUS_LEAF) {
            return null;
        }
        return (BlockEntityTicker<T>) (BlockEntityTicker<BloomingLotusLeafBlockEntity>) BloomingLotusLeafBlockEntity::tick;
    }
}
