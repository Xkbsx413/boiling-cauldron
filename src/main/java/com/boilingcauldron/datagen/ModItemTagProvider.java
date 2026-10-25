package com.boilingcauldron.datagen;

import com.boilingcauldron.item.ModItems;
import com.boilingcauldron.tag.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

/**
 * 物品标签数据生成：把本模组材料、原版蘑菇和森罗油脂块加入炒锅配料标签。
 */
public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        // 森罗油脂块默认不是食物，也不在炒锅配料标签中，需要手动加入才能作为底料材料放入锅中
        Item oilBlock = Registries.ITEM.get(new Identifier("kaleidoscope_cookery", "oil_block"));
        if (oilBlock == Items.AIR) {
            throw new IllegalStateException("Missing item: kaleidoscope_cookery:oil_block");
        }
        // 原版蘑菇标签：红蘑菇和棕色蘑菇，供菌菇底料配方使用
        getOrCreateTagBuilder(ModTags.MUSHROOMS)
            .add(Items.RED_MUSHROOM, Items.BROWN_MUSHROOM);

        // 大蒜、竹荪、花椒作为底料材料；油脂块替代液体油脂参与底料配方
        getOrCreateTagBuilder(ModTags.KALEIDOSCOPE_POT_INGREDIENT)
            .add(ModItems.Garlic, ModItems.Bamboo_Fungus, ModItems.Sichuan_Pepper, oilBlock)
            .addTag(ModTags.MUSHROOMS);
    }
}
