package com.boilingcauldron.item;

import com.boilingcauldron.BoilingCauldron;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup BOILING_CAULDRON_GROUP = FabricItemGroup.builder()
            .displayName(Text.translatable("itemgroup.boiling_cauldron"))
            .icon(() -> new ItemStack(ModItems.Gong_Cai))
            .entries((displayContext, entries) -> {
                entries.add(ModItems.Ginger);
               // entries.add(ModItems.Ginger_Seeds);
                entries.add(ModItems.Garlic);
                //entries.add(ModItems.Garlic_Seeds);
                entries.add(ModItems.Gong_Cai);
                //entries.add(ModItems.Gong_Cai_Seeds);
                entries.add(ModItems.Winter_Bamboo_Shoots);
                entries.add(ModItems.Bamboo_Fungus);
               // entries.add(ModItems.Bamboo_Fungus_Block);
                entries.add(ModItems.Lotus_Root);
               // entries.add(ModItems.Lotus_Root_Seeds);
                entries.add(ModItems.Sichuan_Pepper);
               // entries.add(ModItems.Sichuan_Pepper_Seeds);

            })
            .build();


    public static void RegisterGroups() {
        Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(BoilingCauldron.MOD_ID, "boiling_cauldron_group"),
                BOILING_CAULDRON_GROUP
        );
    }
}

