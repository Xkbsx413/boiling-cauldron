package com.boilingcauldron.datagen;

import com.boilingcauldron.block.*;
import com.boilingcauldron.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.AnyOfLootCondition;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.tag.ItemTags;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {super(dataOutput);}

    @Override
    public void generate() {
        // 生姜作物：成熟时掉落 1 个生姜 + 1-3 个生姜种子
        addCropLootTable(ModBlocks.GINGER_CROP, ModItems.Ginger, ModItems.Ginger_Seeds);

        // 大蒜作物：成熟时掉落 1 个大蒜 + 1-3 个大蒜种子
        addCropLootTable(ModBlocks.GARLIC_CROP, ModItems.Garlic, ModItems.Garlic_Seeds);

        // 贡菜作物：成熟时掉落 1 个贡菜 + 1-3 个贡菜种子
        addCropLootTable(ModBlocks.GONG_CAI_CROP, ModItems.Gong_Cai, ModItems.Gong_Cai_Seeds);

        // 花椒作物：成熟时掉落 1-2 个花椒果实 + 1-3 个种子；未成熟掉落 0-1 个种子
        addSichuanPepperLootTable();

        // 竹荪菌落：剪刀掉落方块自身，否则掉落 2-3 个竹荪
        addBambooFungusColonyLootTable();

        // 冬笋作物：仅成熟时掉落 1 个冬笋，未成熟无掉落
        addBambooShootCropLootTable();

        // 盛花荷叶：破坏掉落自身
        this.addDrop(ModBlocks.BLOOMING_LOTUS_LEAF);

        // 藏藕泥块：精准采集或锄头掉落自身，否则掉落 1-4 个莲藕
        addLotusMudLootTable();
    }

    private void addCropLootTable(Block cropBlock, net.minecraft.item.Item cropItem, net.minecraft.item.Item seedItem) {
        // 成熟条件：age=MAX_AGE (现在是 3)
        var matureCondition = BlockStatePropertyLootCondition.builder(cropBlock)
            .properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(ModCropBlocks.AGE, ModCropBlocks.MAX_AGE))
            .build();

        // 非成熟条件：反转成熟条件
        var notMatureCondition = net.minecraft.loot.condition.InvertedLootCondition.builder(
            BlockStatePropertyLootCondition.builder(cropBlock)
                .properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(ModCropBlocks.AGE, ModCropBlocks.MAX_AGE))
        );

        this.addDrop(cropBlock, LootTable.builder()
            // 成熟池1：掉落作物 (1 个)
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(cropItem))
                .conditionally(matureCondition)
            )
            // 成熟池2：掉落种子 (1-3 个)
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(seedItem)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)))
                )
                .conditionally(matureCondition)
            )
            // 非成熟池：掉落 0-1 个种子（无作物）
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(seedItem)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f,1.0f)))
                )
                .conditionally(notMatureCondition)
            )
        );
    }

    private void addSichuanPepperLootTable() {
        Block cropBlock = ModBlocks.SICHUAN_PEPPER_CROP;
        // 花椒为独立 5 阶段（AGE_4），必须使用花椒自己的 AGE/MAX_AGE
        var matureCondition = BlockStatePropertyLootCondition.builder(cropBlock)
            .properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(SichuanPepperCropBlock.AGE, SichuanPepperCropBlock.MAX_AGE))
            .build();

        var notMatureCondition = net.minecraft.loot.condition.InvertedLootCondition.builder(
            BlockStatePropertyLootCondition.builder(cropBlock)
                .properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(SichuanPepperCropBlock.AGE, SichuanPepperCropBlock.MAX_AGE))
        );

        this.addDrop(cropBlock, LootTable.builder()
            // 成熟池：age=MAX_AGE(4) 时掉落 1-2 个花椒果实 + 1-3 个种子
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(ModItems.Sichuan_Pepper)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)))
                )
                .with(ItemEntry.builder(ModItems.Sichuan_Pepper_Seeds)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)))
                )
                .conditionally(matureCondition)
            )
            // 非成熟池：age<MAX_AGE 时掉落 0-1 个种子（无果实）
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(ModItems.Sichuan_Pepper_Seeds)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                )
                .conditionally(notMatureCondition)
            )
        );
    }

    private void addBambooShootCropLootTable() {
        Block cropBlock = ModBlocks.BAMBOO_SHOOT_CROP;
        var matureCondition = BlockStatePropertyLootCondition.builder(cropBlock)
            .properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(BambooShootCropBlock.AGE, BambooShootCropBlock.MAX_AGE))
            .build();

        // 只有成熟池：未成熟破坏不掉落任何物品
        this.addDrop(cropBlock, LootTable.builder()
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(ModItems.Winter_Bamboo_Shoots))
                .conditionally(matureCondition)
            )
        );
    }

    private void addBambooFungusColonyLootTable() {
        Block colonyBlock = ModBlocks.BAMBOO_FUNGUS_COLONY;

        // 剪刀条件：仅剪刀破坏时精准采集方块自身
        var shearsCondition = MatchToolLootCondition.builder(
            ItemPredicate.Builder.create().items(Items.SHEARS)
        );

        this.addDrop(colonyBlock, LootTable.builder()
            // 非剪刀池：掉落 2-3 个竹荪
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(ModItems.Bamboo_Fungus)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 3.0f)))
                )
                .conditionally(InvertedLootCondition.builder(shearsCondition))
            )
            // 剪刀池：掉落菌落方块自身
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(colonyBlock))
                .conditionally(shearsCondition)
            )
        );
    }

    private void addLotusMudLootTable() {
        Block mudBlock = ModBlocks.LOTUS_MUD;

        // 精准采集或锄头条件
        var silkTouchCondition = MatchToolLootCondition.builder(
            ItemPredicate.Builder.create().enchantment(
                new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))
            )
        );
        var hoeCondition = MatchToolLootCondition.builder(
            ItemPredicate.Builder.create().tag(ItemTags.HOES)
        );
        var selfDropCondition = AnyOfLootCondition.builder(silkTouchCondition, hoeCondition);

        this.addDrop(mudBlock, LootTable.builder()
            // 无精准采集且非锄头：掉落 1-4 个莲藕，方块消失
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(ModItems.Lotus_Root)
                    .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)))
                )
                .conditionally(InvertedLootCondition.builder(selfDropCondition))
            )
            // 精准采集或锄头：掉落方块自身
            .pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(mudBlock))
                .conditionally(selfDropCondition)
            )
        );
    }
}
