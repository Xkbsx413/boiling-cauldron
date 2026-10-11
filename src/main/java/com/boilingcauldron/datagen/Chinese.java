package com.boilingcauldron.datagen;

import com.boilingcauldron.block.ModBlocks;
import com.boilingcauldron.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class Chinese extends FabricLanguageProvider {
    public Chinese(FabricDataOutput dataOutput) {super(dataOutput,"zh_cn");}

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.Ginger, "生姜");
        translationBuilder.add(ModItems.Garlic, "大蒜");
        translationBuilder.add(ModItems.Gong_Cai, "贡菜");
        translationBuilder.add(ModItems.Winter_Bamboo_Shoots, "冬笋");
        translationBuilder.add(ModItems.Lotus_Root, "莲藕");
        translationBuilder.add(ModItems.Sichuan_Pepper, "花椒");
        translationBuilder.add(ModItems.Bamboo_Fungus, "竹荪");
        
        // 种子翻译
        translationBuilder.add(ModItems.Ginger_Seeds, "生姜种子");
        translationBuilder.add(ModItems.Garlic_Seeds, "大蒜种子");
        translationBuilder.add(ModItems.Gong_Cai_Seeds, "贡菜种子");
        translationBuilder.add(ModItems.Sichuan_Pepper_Seeds, "花椒种子");
        
        // 作物方块翻译
        translationBuilder.add(ModBlocks.GINGER_CROP, "生姜作物");
        translationBuilder.add(ModBlocks.GARLIC_CROP, "大蒜作物");
        translationBuilder.add(ModBlocks.GONG_CAI_CROP, "贡菜作物");
        translationBuilder.add(ModBlocks.SICHUAN_PEPPER_CROP, "花椒作物");
        
        translationBuilder.add("itemgroup.boiling_cauldron", "沸鼎烟火");
    }
}