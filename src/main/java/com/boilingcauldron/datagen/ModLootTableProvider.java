package com.boilingcauldron.datagen;

import com.boilingcauldron.block.ModBlocks;
import com.boilingcauldron.block.ModCropBlocks;
import com.boilingcauldron.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

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
        var matureCondition = BlockStatePropertyLootCondition.builder(cropBlock)
            .properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(ModCropBlocks.AGE, ModCropBlocks.MAX_AGE))
            .build();

        var notMatureCondition = net.minecraft.loot.condition.InvertedLootCondition.builder(
            BlockStatePropertyLootCondition.builder(cropBlock)
                .properties(net.minecraft.predicate.StatePredicate.Builder.create().exactMatch(ModCropBlocks.AGE, ModCropBlocks.MAX_AGE))
        );

        this.addDrop(cropBlock, LootTable.builder()
            // 成熟池：age=MAX_AGE 时掉落 1-2 个花椒果实 + 1-3 个种子
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
}
