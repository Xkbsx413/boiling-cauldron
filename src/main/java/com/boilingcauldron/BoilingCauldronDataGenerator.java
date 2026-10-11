package com.boilingcauldron;

import com.boilingcauldron.datagen.Chinese;
import com.boilingcauldron.datagen.English;
import com.boilingcauldron.datagen.Model;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BoilingCauldronDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(Model::new);
		pack.addProvider(Chinese::new);
		pack.addProvider(English::new);

	}
}
