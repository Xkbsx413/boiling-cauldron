package com.boilingcauldron.datagen;

import com.boilingcauldron.block.ModBlocks;
import com.boilingcauldron.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class English extends FabricLanguageProvider {
    public English(FabricDataOutput dataOutput) {super(dataOutput,"en_us");}

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.Ginger, "Ginger");
        translationBuilder.add(ModItems.Garlic, "Garlic");
        translationBuilder.add(ModItems.Gong_Cai, "Gong Cai");
        translationBuilder.add(ModItems.Winter_Bamboo_Shoots, "Winter Bamboo Shoots");
        translationBuilder.add(ModItems.Lotus_Root, "Lotus Root");
        translationBuilder.add(ModItems.Sichuan_Pepper, "Sichuan Pepper");
        translationBuilder.add(ModItems.Bamboo_Fungus, "Bamboo Fungus");
        
        // 种子翻译
        translationBuilder.add(ModItems.Ginger_Seeds, "Ginger Seeds");
        translationBuilder.add(ModItems.Garlic_Seeds, "Garlic Seeds");
        translationBuilder.add(ModItems.Gong_Cai_Seeds, "Gong Cai Seeds");
        translationBuilder.add(ModItems.Sichuan_Pepper_Seeds, "Sichuan Pepper Seeds");
        
        // 作物方块翻译
        translationBuilder.add(ModBlocks.GINGER_CROP, "Ginger Crop");
        translationBuilder.add(ModBlocks.GARLIC_CROP, "Garlic Crop");
        translationBuilder.add(ModBlocks.GONG_CAI_CROP, "Gong Cai Crop");
        translationBuilder.add(ModBlocks.SICHUAN_PEPPER_CROP, "Sichuan Pepper Crop");
        
        translationBuilder.add("itemgroup.boiling_cauldron", "Boiling Cauldron");
    }
}
