package com.boilingcauldron.datagen;

import com.boilingcauldron.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class Model extends FabricModelProvider {
    public Model(FabricDataOutput output) {super(output);}

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.Ginger, Models.GENERATED);
        itemModelGenerator.register(ModItems.Garlic, Models.GENERATED);
        itemModelGenerator.register(ModItems.Gong_Cai, Models.GENERATED);
        itemModelGenerator.register(ModItems.Winter_Bamboo_Shoots, Models.GENERATED);
        itemModelGenerator.register(ModItems.Lotus_Root, Models.GENERATED);
        itemModelGenerator.register(ModItems.Sichuan_Pepper, Models.GENERATED);
        itemModelGenerator.register(ModItems.Bamboo_Fungus, Models.GENERATED);

    }
}
