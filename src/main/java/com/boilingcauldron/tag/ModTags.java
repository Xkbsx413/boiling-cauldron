package com.boilingcauldron.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * 森罗厨房相关的物品标签。
 */
public final class ModTags {

    /**
     * 森罗厨房的液体油脂标签，用于给炒锅抹油。
     */
    public static final TagKey<Item> KALEIDOSCOPE_OIL = TagKey.of(
        RegistryKeys.ITEM,
        new Identifier("kaleidoscope_cookery", "oil")
    );

    /**
     * 森罗辣椒作物标签，红辣椒和青辣椒都可以作为“森罗辣椒”使用。
     */
    public static final TagKey<Item> KALEIDOSCOPE_CHILI = TagKey.of(
        RegistryKeys.ITEM,
        new Identifier("c", "crops/chilipepper")
    );

    /**
     * 原版蘑菇物品标签：红蘑菇和棕色蘑菇。
     */
    public static final TagKey<Item> MUSHROOMS = TagKey.of(
        RegistryKeys.ITEM,
        new Identifier("boiling-cauldron", "mushrooms")
    );

    /**
     * 森罗炒锅允许放入的配料标签。
     */
    public static final TagKey<Item> KALEIDOSCOPE_POT_INGREDIENT = TagKey.of(
        RegistryKeys.ITEM,
        new Identifier("kaleidoscope_cookery", "pot_ingredient")
    );

    private ModTags() {
    }
}
