package com.boilingcauldron;

import com.boilingcauldron.block.ModBlocks;
import com.boilingcauldron.item.ModItemGroups;
import com.boilingcauldron.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoilingCauldron implements ModInitializer {
	public static final String MOD_ID = "boiling-cauldron";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ModBlocks.registerBlocks();   // 先注册作物方块
		ModItems.registerItems();     // 再注册种子 BlockItem 并回填种子引用
		ModItemGroups.RegisterGroups();

	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
