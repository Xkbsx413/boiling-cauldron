package com.boilingcauldron.block;

import com.boilingcauldron.BoilingCauldron;
import com.boilingcauldron.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // 统一注册方法（注册方块和对应的 BlockItem，用于普通方块）
    private static Block register(String id, Block block) {
        registryBlockItems(id, block);
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

    private static void registryBlockItems(String id, Block block) {
        Registry.register(
            Registries.ITEM,
            RegistryKey.of(Registries.ITEM.getKey(), new Identifier(BoilingCauldron.MOD_ID, id)),
            new BlockItem(block, new Item.Settings())
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

    // 统一初始化入口（在 ModInitializer 中调用）
    public static void registerBlocks() {
        // 触发静态初始化
    }

    // 注册完种子后回填种子引用（在 ModItems.registerSeeds() 中调用）
    public static void setCropSeeds() {
        ((ModCropBlocks) GINGER_CROP).setSeedsItem(ModItems.Ginger_Seeds);
        ((ModCropBlocks) GARLIC_CROP).setSeedsItem(ModItems.Garlic_Seeds);
        ((ModCropBlocks) GONG_CAI_CROP).setSeedsItem(ModItems.Gong_Cai_Seeds);
        ((SichuanPepperCropBlock) SICHUAN_PEPPER_CROP).setSeedsItem(ModItems.Sichuan_Pepper_Seeds);
    }
}