package com.boilingcauldron.loot;

import com.boilingcauldron.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Set;

/**
 * 向村庄箱子追加种子奖池，解决四种作物在生存模式中没有初始来源的问题。
 */
public final class ModLootTableModifiers {

    private static final Set<Identifier> VILLAGE_HOUSE_CHESTS = Set.of(
        villageChest("village_plains_house"),
        villageChest("village_desert_house"),
        villageChest("village_savanna_house"),
        villageChest("village_snowy_house"),
        villageChest("village_taiga_house")
    );

    private static final float SEASONING_PACK_CHANCE = 0.08F;

    private ModLootTableModifiers() {
    }

    public static void register() {
        Map<Identifier, SeedDrop[]> themeDrops = Map.of(
            villageChest("village_plains_house"), new SeedDrop[]{
                new SeedDrop(ModItems.Garlic_Seeds, 0.15F, 1, 2),
                new SeedDrop(ModItems.Ginger_Seeds, 0.08F, 1, 1)
            },
            villageChest("village_savanna_house"), new SeedDrop[]{
                new SeedDrop(ModItems.Ginger_Seeds, 0.15F, 1, 2),
                new SeedDrop(ModItems.Sichuan_Pepper_Seeds, 0.10F, 1, 1)
            },
            villageChest("village_taiga_house"), new SeedDrop[]{
                new SeedDrop(ModItems.Garlic_Seeds, 0.15F, 1, 2),
                new SeedDrop(ModItems.Gong_Cai_Seeds, 0.08F, 1, 1)
            },
            villageChest("village_desert_house"), new SeedDrop[]{
                new SeedDrop(ModItems.Sichuan_Pepper_Seeds, 0.15F, 1, 2)
            },
            villageChest("village_snowy_house"), new SeedDrop[]{
                new SeedDrop(ModItems.Garlic_Seeds, 0.10F, 1, 2)
            },
            villageChest("village_butcher"), new SeedDrop[]{
                new SeedDrop(ModItems.Ginger_Seeds, 0.10F, 1, 1),
                new SeedDrop(ModItems.Garlic_Seeds, 0.10F, 1, 1)
            },
            villageChest("village_fisher"), new SeedDrop[]{
                new SeedDrop(ModItems.Gong_Cai_Seeds, 0.15F, 1, 2)
            }
        );

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // 尊重数据包对村庄箱子的覆盖，只在原版/模组内置表上追加。
            if (!source.isBuiltin()) {
                return;
            }

            SeedDrop[] drops = themeDrops.get(id);
            if (drops != null) {
                for (SeedDrop drop : drops) {
                    tableBuilder.pool(createSeedPool(drop));
                }
            }

            if (VILLAGE_HOUSE_CHESTS.contains(id)) {
                tableBuilder.pool(createSeasoningPackPool());
            }
        });
    }

    private static LootPool.Builder createSeedPool(SeedDrop drop) {
        return LootPool.builder()
            .rolls(ConstantLootNumberProvider.create(1))
            .with(ItemEntry.builder(drop.item())
                .apply(SetCountLootFunction.builder(
                    UniformLootNumberProvider.create(drop.minCount(), drop.maxCount())
                )))
            .conditionally(RandomChanceLootCondition.builder(drop.chance()));
    }

    /**
     * 所有村庄民居共用的“调料包”奖池，随机产出一种种子。
     */
    private static LootPool.Builder createSeasoningPackPool() {
        return LootPool.builder()
            .rolls(ConstantLootNumberProvider.create(1))
            .conditionally(RandomChanceLootCondition.builder(SEASONING_PACK_CHANCE))
            .with(ItemEntry.builder(ModItems.Garlic_Seeds).weight(30))
            .with(ItemEntry.builder(ModItems.Ginger_Seeds).weight(30))
            .with(ItemEntry.builder(ModItems.Gong_Cai_Seeds).weight(20))
            .with(ItemEntry.builder(ModItems.Sichuan_Pepper_Seeds).weight(20));
    }

    private static Identifier villageChest(String path) {
        return new Identifier("minecraft", "chests/village/" + path);
    }

    private record SeedDrop(Item item, float chance, int minCount, int maxCount) {
    }
}
