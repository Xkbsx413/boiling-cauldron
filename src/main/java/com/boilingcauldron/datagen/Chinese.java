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
        translationBuilder.add(ModItems.Bobo_Chicken, "钵钵鸡");
        translationBuilder.add(ModBlocks.BOBO_CHICKEN_POT, "钵钵鸡锅");
        // 钵钵鸡成品名称与提示
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.mushroom", "菌香的钵钵鸡");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.mala", "辣辣的钵钵鸡");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.rattan_pepper", "藤椒的钵钵鸡");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.mushroom_mala", "菌香辣辣的钵钵鸡");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.mushroom_rattan_pepper", "菌香藤椒的钵钵鸡");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.mala_rattan_pepper", "辣辣藤椒的钵钵鸡");
        translationBuilder.add("item.boiling-cauldron.bobo_chicken.three_flavors", "三味钵钵鸡");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.original", "原料：%s");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.flavors", "口味：%s");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.separator", "、");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.flavor.mushroom", "菌香");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.flavor.mala", "辣辣");
        translationBuilder.add("tooltip.boiling-cauldron.bobo_chicken.flavor.rattan_pepper", "藤椒");
        // 钵钵鸡锅 GUI
        translationBuilder.add("gui.boiling-cauldron.bobo_chicken_pot.empty", "空锅");
        translationBuilder.add("gui.boiling-cauldron.bobo_chicken_pot.water", "清水");
        translationBuilder.add("gui.boiling-cauldron.bobo_chicken_pot.base", "%s · 剩余 %s 次");
        // 底料翻译
        translationBuilder.add(ModItems.Mushroom_Base, "菌菇底料");
        translationBuilder.add(ModItems.Mala_Base, "麻辣底料");
        translationBuilder.add(ModItems.Rattan_Pepper_Base, "藤椒底料");
        
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
        translationBuilder.add(ModBlocks.BAMBOO_SHOOT_CROP, "冬笋作物");
        translationBuilder.add(ModBlocks.BAMBOO_ROOT, "竹根殖块");
        translationBuilder.add(ModBlocks.BAMBOO_FUNGUS_COLONY, "竹荪菌落");
        translationBuilder.add(ModBlocks.BLOOMING_LOTUS_LEAF, "盛花荷叶");
        translationBuilder.add(ModBlocks.LOTUS_MUD, "藏藕泥块");
        
        translationBuilder.add("itemgroup.boiling_cauldron", "沸鼎烟火");
    }
}