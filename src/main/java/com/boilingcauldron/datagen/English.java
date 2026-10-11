package com.boilingcauldron.datagen;

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
        translationBuilder.add("itemgroup.boiling_cauldron", "Boiling Cauldron");

    }
}
