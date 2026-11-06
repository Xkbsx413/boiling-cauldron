package com.boilingcauldron.block.entity;

import com.boilingcauldron.BoilingCauldron;
import com.boilingcauldron.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities {

    public static final BlockEntityType<BambooRootBlockEntity> BAMBOO_ROOT = Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        BoilingCauldron.id("bamboo_root"),
        FabricBlockEntityTypeBuilder.create(BambooRootBlockEntity::new, ModBlocks.BAMBOO_ROOT).build()
    );

    public static final BlockEntityType<BloomingLotusLeafBlockEntity> BLOOMING_LOTUS_LEAF = Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        BoilingCauldron.id("blooming_lotus_leaf"),
        FabricBlockEntityTypeBuilder.create(BloomingLotusLeafBlockEntity::new, ModBlocks.BLOOMING_LOTUS_LEAF).build()
    );

    public static final BlockEntityType<BoboChickenPotBlockEntity> BOBO_CHICKEN_POT = Registry.register(
        Registries.BLOCK_ENTITY_TYPE,
        BoilingCauldron.id("bobo_chicken_pot"),
        FabricBlockEntityTypeBuilder.create(BoboChickenPotBlockEntity::new, ModBlocks.BOBO_CHICKEN_POT).build()
    );

    public static void registerBlockEntities() {
    }
}
