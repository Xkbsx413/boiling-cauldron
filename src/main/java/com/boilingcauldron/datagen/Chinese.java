package com.boilingcauldron.datagen;

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
        translationBuilder.add("itemgroup.boiling_cauldron", "沸鼎烟火");

    }
}