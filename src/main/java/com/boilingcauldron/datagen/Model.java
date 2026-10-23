package com.boilingcauldron.datagen;

import com.boilingcauldron.block.BambooRootBlock;
import com.boilingcauldron.block.BambooShootCropBlock;
import com.boilingcauldron.block.BloomingLotusLeafBlock;
import com.boilingcauldron.block.ModBlocks;
import com.boilingcauldron.block.ModCropBlocks;
import com.boilingcauldron.block.SichuanPepperCropBlock;
import com.boilingcauldron.item.ModItems;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;

public class Model extends FabricModelProvider {
    private static final net.minecraft.data.client.Model FLAT_LEAF_MODEL =
        new net.minecraft.data.client.Model(Optional.empty(), Optional.empty(), TextureKey.TEXTURE) {
            @Override
            public JsonObject createJson(Identifier modelId, Map<TextureKey, Identifier> textures) {
                JsonObject json = new JsonObject();
                json.addProperty("ambientocclusion", false);

                JsonObject textureJson = new JsonObject();
                String texture = textures.get(TextureKey.TEXTURE).toString();
                textureJson.addProperty("particle", texture);
                textureJson.addProperty("texture", texture);
                json.add("textures", textureJson);

                JsonObject element = new JsonObject();
                element.add("from", numbers(0, 0.25, 0));
                element.add("to", numbers(16, 0.25, 16));

                JsonObject faces = new JsonObject();
                faces.add("down", face(16, 0));
                faces.add("up", face(0, 16));
                element.add("faces", faces);

                JsonArray elements = new JsonArray();
                elements.add(element);
                json.add("elements", elements);
                return json;
            }
        };

    public Model(FabricDataOutput output) {super(output);}

    private static JsonObject face(int v1, int v2) {
        JsonObject face = new JsonObject();
        face.add("uv", numbers(0, v1, 16, v2));
        face.addProperty("texture", "#texture");
        return face;
    }

    private static JsonArray numbers(Number... values) {
        JsonArray array = new JsonArray();
        for (Number value : values) {
            array.add(new JsonPrimitive(value));
        }
        return array;
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // 作物方块模型：registerCrop 需要 Block, age Property, ages 数组
        blockStateModelGenerator.registerCrop(ModBlocks.GINGER_CROP, ModCropBlocks.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModBlocks.GARLIC_CROP, ModCropBlocks.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModBlocks.GONG_CAI_CROP, ModCropBlocks.AGE, 0, 1, 2, 3);
        // 花椒为独立 5 阶段：age 0-4 对应 stage0-stage4
        blockStateModelGenerator.registerCrop(ModBlocks.SICHUAN_PEPPER_CROP, SichuanPepperCropBlock.AGE, 0, 1, 2, 3, 4);
        // 冬笋为独立 6 阶段：age 0-5 对应 stage0-stage5，使用交叉十字模型（block/cross）
        blockStateModelGenerator.blockStateCollector.accept(
            VariantsBlockStateSupplier.create(ModBlocks.BAMBOO_SHOOT_CROP)
                .coordinate(BlockStateVariantMap.create(BambooShootCropBlock.AGE)
                    .register(age -> {
                        Identifier stageModel = Models.CROSS.upload(ModBlocks.BAMBOO_SHOOT_CROP, "_stage" + age,
                            TextureMap.cross(TextureMap.getSubId(ModBlocks.BAMBOO_SHOOT_CROP, "_stage" + age)), blockStateModelGenerator.modelCollector);
                        return BlockStateVariant.create().put(VariantSettings.MODEL, stageModel);
                    }))
        );
        // 竹荪菌落：十字模型（同时生成方块状态与物品模型）
        blockStateModelGenerator.registerTintableCross(ModBlocks.BAMBOO_FUNGUS_COLONY, BlockStateModelGenerator.TintType.NOT_TINTED);
        // 竹根殖块：bamboo_count 0-5 共用同一方块模型
        Identifier bambooRootModel = Models.CUBE_ALL.upload(ModBlocks.BAMBOO_ROOT, TextureMap.all(TextureMap.getId(ModBlocks.BAMBOO_ROOT)), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(
            VariantsBlockStateSupplier.create(ModBlocks.BAMBOO_ROOT)
                .coordinate(BlockStateVariantMap.create(BambooRootBlock.BAMBOO_COUNT)
                    .register(count -> BlockStateVariant.create().put(VariantSettings.MODEL, bambooRootModel)))
        );
        blockStateModelGenerator.registerParentedItemModel(ModBlocks.BAMBOO_ROOT, bambooRootModel);
        // 藏藕泥块：普通方块模型
        Identifier lotusMudModel = Models.CUBE_ALL.upload(ModBlocks.LOTUS_MUD, TextureMap.all(TextureMap.getId(ModBlocks.LOTUS_MUD)), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(
            VariantsBlockStateSupplier.create(ModBlocks.LOTUS_MUD, BlockStateVariant.create().put(VariantSettings.MODEL, lotusMudModel))
        );
        blockStateModelGenerator.registerParentedItemModel(ModBlocks.LOTUS_MUD, lotusMudModel);
        Identifier[] bloomingLotusLeafModels = new Identifier[5];
        for (int stage = 0; stage < bloomingLotusLeafModels.length; stage++) {
            bloomingLotusLeafModels[stage] = FLAT_LEAF_MODEL.upload(
                ModBlocks.BLOOMING_LOTUS_LEAF,
                "_stage" + stage,
                TextureMap.of(TextureKey.TEXTURE, TextureMap.getSubId(ModBlocks.BLOOMING_LOTUS_LEAF, "_stage" + stage)),
                blockStateModelGenerator.modelCollector
            );
        }
        blockStateModelGenerator.blockStateCollector.accept(
            VariantsBlockStateSupplier.create(ModBlocks.BLOOMING_LOTUS_LEAF)
                .coordinate(BlockStateVariantMap.create(BloomingLotusLeafBlock.GROWTH)
                    .register(growth -> BlockStateVariant.create().put(VariantSettings.MODEL, bloomingLotusLeafModels[growth])))
        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        Models.GENERATED.upload(
            ModelIds.getItemModelId(ModBlocks.BLOOMING_LOTUS_LEAF.asItem()),
            TextureMap.layer0(TextureMap.getSubId(ModBlocks.BLOOMING_LOTUS_LEAF, "_stage0")),
            itemModelGenerator.writer
        );
        itemModelGenerator.register(ModItems.Ginger, Models.GENERATED);
        itemModelGenerator.register(ModItems.Garlic, Models.GENERATED);
        itemModelGenerator.register(ModItems.Gong_Cai, Models.GENERATED);
        itemModelGenerator.register(ModItems.Winter_Bamboo_Shoots, Models.GENERATED);
        itemModelGenerator.register(ModItems.Lotus_Root, Models.GENERATED);
        itemModelGenerator.register(ModItems.Sichuan_Pepper, Models.GENERATED);
        itemModelGenerator.register(ModItems.Bamboo_Fungus, Models.GENERATED);
        // 种子为 BlockItem(SeedItem)，自动从方块模型生成物品模型，无需手动注册
    }
}
