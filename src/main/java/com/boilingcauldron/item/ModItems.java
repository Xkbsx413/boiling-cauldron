package com.boilingcauldron.item;

import com.boilingcauldron.BoilingCauldron;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item Ginger = registerItem("ginger", new Item(new Item.Settings().food(ModFoodComponents.Ginger))); // 生姜
    public static final Item Garlic = registerItem("garlic", new Item(new Item.Settings().food(ModFoodComponents.Garlic))); // 大蒜
    public static final Item Gong_Cai = registerItem("gong_cai", new Item(new Item.Settings().food(ModFoodComponents.Gong_Cai))); // 贡菜
    public static final Item Winter_Bamboo_Shoots = registerItem("winter_bamboo_shoots", new Item(new Item.Settings().food(ModFoodComponents.Winter_Bamboo_Shoots))); // 冬笋
    public static final Item Lotus_Root = registerItem("lotus_root", new Item(new Item.Settings().food(ModFoodComponents.Lotus_Root))); // 莲藕
    public static final Item Sichuan_Pepper = registerItem("sichuan_pepper", new Item(new Item.Settings().food(ModFoodComponents.Sichuan_Pepper))); // 花椒
    public static final Item Bamboo_Fungus = registerItem("bamboo_fungus", new Item(new Item.Settings().food(ModFoodComponents.Bamboo_Fungus))); // 竹荪（物品：可食用，不可种植）

    public static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier(BoilingCauldron.MOD_ID, id)), item);
    }

    public static void registerItems() {
    }
}

