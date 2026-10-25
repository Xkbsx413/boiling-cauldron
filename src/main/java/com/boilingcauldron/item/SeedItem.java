package com.boilingcauldron.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SeedItem extends BlockItem {
    private final String translationKey;

    public SeedItem(Block block, Item.Settings settings, String translationKey) {
        super(block, settings);
        this.translationKey = translationKey;
    }

    @Override
    public String getTranslationKey() {
        return this.translationKey;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return this.translationKey;
    }
}