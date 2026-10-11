package com.boilingcauldron.datagen;

import com.boilingcauldron.block.ModBlocks;
import com.boilingcauldron.block.ModCropBlocks;
import com.boilingcauldron.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class Model extends FabricModelProvider {
    public Model(FabricDataOutput output) {super(output);}

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // 作物方块模型：registerCrop 需要 Block, age Property, ages 数组
        blockStateModelGenerator.registerCrop(ModBlocks.GINGER_CROP, ModCropBlocks.AGE, new int[]{0,1,2,3});
        blockStateModelGenerator.registerCrop(ModBlocks.GARLIC_CROP, ModCropBlocks.AGE, new int[]{0,1,2,3});
        blockStateModelGenerator.registerCrop(ModBlocks.GONG_CAI_CROP, ModCropBlocks.AGE, new int[]{0,1,2,3});
        // 花椒也使用相同的 AGE_3 属性
        blockStateModelGenerator.registerCrop(ModBlocks.SICHUAN_PEPPER_CROP, ModCropBlocks.AGE, new int[]{0,1,2,3});
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.Ginger, Models.GENERATED);
        itemModelGenerator.register(ModItems.Garlic, Models.GENERATED);
        itemModelGenerator.register(ModItems.Gong_Cai, Models.GENERATED);
        itemModelGenerator.register(ModItems.Winter_Bamboo_Shoots, Models.GENERATED);
        itemModelGenerator.register(ModItems.Lotus_Root, Models.GENERATED);
        itemModelGenerator.register(ModItems.Sichuan_Pepper, Models.GENERATED);
        itemModelGenerator.register(ModItems.Bamboo_Fungus, Models.GENERATED);
        // 种子为 BlockItem(SeedItem)，自动从方块模型生成物品模型，无需手动注册
    }
}
