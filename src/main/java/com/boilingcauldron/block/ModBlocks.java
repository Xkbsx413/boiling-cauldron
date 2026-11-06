package com.boilingcauldron.block;

import com.boilingcauldron.BoilingCauldron;
import com.boilingcauldron.item.ModItems;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.PlaceableOnWaterItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

public class ModBlocks {

    // 统一注册方法（注册方块和对应的 BlockItem，用于普通方块）
    private static Block register(String id, Block block) {
        return register(id, block, BlockItem::new);
    }

    // 统一注册方法（自定义物品工厂，用于需要特殊放置逻辑的方块，如盛花荷叶）
    private static Block register(String id, Block block, BiFunction<Block, Item.Settings, Item> itemFactory) {
        Registry.register(
            Registries.ITEM,
            RegistryKey.of(Registries.ITEM.getKey(), new Identifier(BoilingCauldron.MOD_ID, id)),
            itemFactory.apply(block, new Item.Settings())
        );
        return Registry.register(
            Registries.BLOCK,
            RegistryKey.of(Registries.BLOCK.getKey(), new Identifier(BoilingCauldron.MOD_ID, id)),
            block
        );
    }

    // 作物方块注册方法（不注册 BlockItem，避免翻译键冲突，玩家通过种子种植）
    private static Block registerCrop(String id, Block block) {
        return Registry.register(
            Registries.BLOCK,
            RegistryKey.of(Registries.BLOCK.getKey(), new Identifier(BoilingCauldron.MOD_ID, id)),
            block
        );
    }

    // 作物方块设置（可复用）
    private static AbstractBlock.Settings cropSettings() {
        return AbstractBlock.Settings.create()
            .noCollision()
            .ticksRandomly()
            .breakInstantly()
            .pistonBehavior(PistonBehavior.DESTROY)
            .nonOpaque();
    }

    // 作物方块注册（不注册 BlockItem，避免翻译键冲突）
    public static final Block GINGER_CROP = registerCrop(
        "ginger_crop",
        new ModCropBlocks(cropSettings())
    );

    public static final Block GARLIC_CROP = registerCrop(
        "garlic_crop",
        new ModCropBlocks(cropSettings())
    );

    public static final Block GONG_CAI_CROP = registerCrop(
        "gong_cai_crop",
        new ModCropBlocks(cropSettings())
    );

    public static final Block SICHUAN_PEPPER_CROP = registerCrop(
        "sichuan_pepper_crop",
        new SichuanPepperCropBlock(cropSettings())
    );

    public static final Block BAMBOO_SHOOT_CROP = registerCrop(
        "bamboo_shoot_crop",
        new BambooShootCropBlock(cropSettings())
    );

    public static final Block BAMBOO_ROOT = register(
        "bamboo_root",
        new BambooRootBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.DIRT_BROWN)
            .strength(0.5f)
            .sounds(BlockSoundGroup.GRAVEL))
    );

    public static final Block BAMBOO_FUNGUS_COLONY = register(
        "bamboo_fungus_colony",
        new BambooFungusColonyBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.WHITE)
            .breakInstantly()
            .noCollision()
            .nonOpaque()
            .sounds(BlockSoundGroup.FUNGUS)
            .pistonBehavior(PistonBehavior.DESTROY))
    );

    // 盛花荷叶：仿原版荷叶，只能贴水放置；由世界自然生成或合成获得
    // 物品使用原版 PlaceableOnWaterItem（与原版荷叶同款放置逻辑：对准水源右键，放在水面上方）
    public static final Block BLOOMING_LOTUS_LEAF = register(
        "blooming_lotus_leaf",
        new BloomingLotusLeafBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.GREEN)
            .strength(0.0f)
            .sounds(BlockSoundGroup.LILY_PAD)
            .nonOpaque()),
        PlaceableOnWaterItem::new
    );

    // 藏藕泥块：由盛花荷叶把下方泥土/泥巴/沙子/砂砾转化而来，挖开可得莲藕
    public static final Block LOTUS_MUD = register(
        "lotus_mud",
        new LotusMudBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.DIRT_BROWN)
            .strength(0.5f)
            .sounds(BlockSoundGroup.GRAVEL))
    );

    // 钵钵鸡锅：16 格冷泡容器，右键打开 GUI
    public static final Block BOBO_CHICKEN_POT = register(
        "bobo_chicken_pot",
        new BoboChickenPotBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.DARK_RED)
            .strength(1.5f)
            .sounds(BlockSoundGroup.DECORATED_POT)
            .requiresTool()
            .nonOpaque())
    );

    // 统一初始化入口（在 ModInitializer 中调用）
    public static void registerBlocks() {
        // 触发静态初始化
        CompostingChanceRegistry.INSTANCE.add(BAMBOO_FUNGUS_COLONY, 0.65F);
    }

    // 注册完种子后回填种子引用（在 ModItems.registerSeeds() 中调用）
    public static void setCropSeeds() {
        ((ModCropBlocks) GINGER_CROP).setSeedsItem(ModItems.Ginger_Seeds);
        ((ModCropBlocks) GARLIC_CROP).setSeedsItem(ModItems.Garlic_Seeds);
        ((ModCropBlocks) GONG_CAI_CROP).setSeedsItem(ModItems.Gong_Cai_Seeds);
        ((SichuanPepperCropBlock) SICHUAN_PEPPER_CROP).setSeedsItem(ModItems.Sichuan_Pepper_Seeds);
    }
}