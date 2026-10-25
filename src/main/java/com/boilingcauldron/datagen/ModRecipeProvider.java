package com.boilingcauldron.datagen;

import com.boilingcauldron.BoilingCauldron;
import com.boilingcauldron.block.ModBlocks;
import com.boilingcauldron.item.ModItems;
import com.boilingcauldron.tag.ModTags;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder.PotRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

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

        // 森罗炒锅配方：底料
        // 配方使用森罗油脂块作为材料；炒锅开炒前仍需先抹 1 个液体油脂
        Item oilBlock = Registries.ITEM.get(new Identifier("kaleidoscope_cookery", "oil_block"));
        if (oilBlock == Items.AIR) {
            throw new IllegalStateException("Missing item: kaleidoscope_cookery:oil_block");
        }

        // 菌菇底料：大蒜 + 竹荪 + 原版蘑菇标签 x2 + 油脂块
        PotRecipeBuilder.builder()
            .addInput(ModItems.Garlic, ModItems.Bamboo_Fungus, ModTags.MUSHROOMS, ModTags.MUSHROOMS, oilBlock)
            .setResult(ModItems.Mushroom_Base)
            .offerTo(exporter, BoilingCauldron.id("pot/mushroom_base"));

        // 麻辣底料：花椒 + 森罗辣椒 + 大蒜 + 油脂块
        PotRecipeBuilder.builder()
            .addInput(ModItems.Sichuan_Pepper, ModTags.KALEIDOSCOPE_CHILI, ModItems.Garlic, oilBlock)
            .setResult(ModItems.Mala_Base)
            .offerTo(exporter, BoilingCauldron.id("pot/mala_base"));

        // 藤椒底料：花椒 x2 + 大蒜 + 油脂块
        PotRecipeBuilder.builder()
            .addInput(ModItems.Sichuan_Pepper, ModItems.Sichuan_Pepper, ModItems.Garlic, oilBlock)
            .setResult(ModItems.Rattan_Pepper_Base)
            .offerTo(exporter, BoilingCauldron.id("pot/rattan_pepper_base"));
    }
}
