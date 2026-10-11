package com.boilingcauldron.item;

import com.boilingcauldron.BoilingCauldron;
import com.boilingcauldron.block.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {
    // 食物物品
    public static final Item Ginger = registerItem("ginger", new Item(new Item.Settings().food(ModFoodComponents.Ginger))); // 生姜
    public static final Item Garlic = registerItem("garlic", new Item(new Item.Settings().food(ModFoodComponents.Garlic))); // 大蒜
    public static final Item Gong_Cai = registerItem("gong_cai", new Item(new Item.Settings().food(ModFoodComponents.Gong_Cai))); // 贡菜
    public static final Item Winter_Bamboo_Shoots = registerItem("winter_bamboo_shoots", new Item(new Item.Settings().food(ModFoodComponents.Winter_Bamboo_Shoots))); // 冬笋
    public static final Item Lotus_Root = registerItem("lotus_root", new Item(new Item.Settings().food(ModFoodComponents.Lotus_Root))); // 莲藕
    public static final Item Sichuan_Pepper = registerItem("sichuan_pepper", new Item(new Item.Settings().food(ModFoodComponents.Sichuan_Pepper))); // 花椒
    public static final Item Bamboo_Fungus = registerItem("bamboo_fungus", new Item(new Item.Settings().food(ModFoodComponents.Bamboo_Fungus))); // 竹荪（物品：可食用，不可种植）

    // 种子物品（SeedItem 继承 BlockItem，指定自定义翻译键避免冲突）
    public static final Item Ginger_Seeds = registerSeed("ginger_seeds", ModBlocks.GINGER_CROP, "item.boiling-cauldron.ginger_seeds");
    public static final Item Garlic_Seeds = registerSeed("garlic_seeds", ModBlocks.GARLIC_CROP, "item.boiling-cauldron.garlic_seeds");
    public static final Item Gong_Cai_Seeds = registerSeed("gong_cai_seeds", ModBlocks.GONG_CAI_CROP, "item.boiling-cauldron.gong_cai_seeds");
    public static final Item Sichuan_Pepper_Seeds = registerSeed("sichuan_pepper_seeds", ModBlocks.SICHUAN_PEPPER_CROP, "item.boiling-cauldron.sichuan_pepper_seeds");

    public static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier(BoilingCauldron.MOD_ID, id)), item);
    }

    // 注册种子：创建指向作物方块的 SeedItem，指定自定义翻译键
    public static Item registerSeed(String id, net.minecraft.block.Block cropBlock, String translationKey) {
        return registerItem(id, new SeedItem(cropBlock, new Item.Settings(), translationKey));
    }

    public static void registerItems() {
        // 种子注册完成后，回填作物方块的种子引用
        ModBlocks.setCropSeeds();
    }
}

