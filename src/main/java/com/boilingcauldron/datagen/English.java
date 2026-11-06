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
        translationBuilder.add(ModItems.Bobo_Chicken, "Bobo Chicken");
        translationBuilder.add(ModBlocks.BOBO_CHICKEN_POT, "Bobo Chicken Pot");
        // 钵钵鸡成品名称与提示
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.mushroom", "Mushroom Bobo Chicken");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.mala", "Mala Bobo Chicken");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.rattan_pepper", "Rattan Pepper Bobo Chicken");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.mushroom_mala", "Mushroom-Mala Bobo Chicken");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.mushroom_rattan_pepper", "Mushroom-Rattan Pepper Bobo Chicken");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.mala_rattan_pepper", "Mala-Rattan Pepper Bobo Chicken");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.three_flavors", "Three-Flavor Bobo Chicken");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.original", "Original: %s");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.flavors", "Flavors: %s");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.separator", ", ");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.flavor.mushroom", "Mushroom");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.flavor.mala", "Mala");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.flavor.rattan_pepper", "Rattan Pepper");
        // 钵钵鸡锅 GUI
        translationBuilder.add("gui.boiling-cauldron.bobo_chicken_pot.empty", "Empty");
        translationBuilder.add("gui.boiling-cauldron.bobo_chicken_pot.water", "Water");
        translationBuilder.add("gui.boiling-cauldron.bobo_chicken_pot.base", "%s · %s uses left");
        // 底料翻译
        translationBuilder.add(ModItems.Mushroom_Base, "Mushroom Base");
        translationBuilder.add(ModItems.Mala_Base, "Mala Base");
        translationBuilder.add(ModItems.Rattan_Pepper_Base, "Rattan Pepper Base");
        
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
        translationBuilder.add(ModBlocks.BAMBOO_SHOOT_CROP, "Winter Bamboo Shoot Crop");
        translationBuilder.add(ModBlocks.BAMBOO_ROOT, "Bamboo Root");
        translationBuilder.add(ModBlocks.BAMBOO_FUNGUS_COLONY, "Bamboo Fungus Colony");
        translationBuilder.add(ModBlocks.BLOOMING_LOTUS_LEAF, "Blooming Lotus Leaf");
        translationBuilder.add(ModBlocks.LOTUS_MUD, "Lotus Mud");
        
        translationBuilder.add("itemgroup.boiling_cauldron", "Boiling Cauldron");
    }
}
