package com.boilingcauldron.datagen;

import com.boilingcauldron.BoilingCauldron;
import com.boilingcauldron.block.ModBlocks;
import com.boilingcauldron.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // 合成配方：8 竹子围 1 泥土
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BAMBOO_ROOT)
            .pattern("BBB")
            .pattern("BDB")
            .pattern("BBB")
            .input('B', Items.BAMBOO)
            .input('D', Items.DIRT)
            .criterion("has_bamboo", RecipeProvider.conditionsFromItem(Items.BAMBOO))
            .offerTo(exporter, BoilingCauldron.id("bamboo_root"));

        // 无序合成：原版荷叶 + 任意花朵类物品 → 盛花荷叶
        ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.BLOOMING_LOTUS_LEAF)
            .input(Blocks.LILY_PAD)
            .input(ItemTags.FLOWERS)
            .criterion("has_lily_pad", RecipeProvider.conditionsFromItem(Blocks.LILY_PAD))
            .offerTo(exporter, BoilingCauldron.id("blooming_lotus_leaf"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.Ginger_Seeds)
                .input(ModItems.Ginger)
                .criterion("has_ginger", RecipeProvider.conditionsFromItem(ModItems.Ginger))
                .offerTo(exporter, BoilingCauldron.id("ginger"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.Garlic_Seeds)
                .input(ModItems.Garlic)
                .criterion("has_garlic", RecipeProvider.conditionsFromItem(ModItems.Garlic))
                .offerTo(exporter, BoilingCauldron.id("garlic"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.Gong_Cai_Seeds)
                .input(ModItems.Gong_Cai)
                .criterion("has_gong", RecipeProvider.conditionsFromItem(ModItems.Gong_Cai))
                .offerTo(exporter, BoilingCauldron.id("gong_cai"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC,ModItems.Sichuan_Pepper_Seeds)
                .input(ModItems.Sichuan_Pepper)
                .criterion("has_pepper", RecipeProvider.conditionsFromItem(ModItems.Sichuan_Pepper))
                .offerTo(exporter, BoilingCauldron.id("pepper"));
    }
}
