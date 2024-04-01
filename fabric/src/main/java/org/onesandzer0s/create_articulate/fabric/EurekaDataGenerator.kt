package org.onesandzer0s.create_articulate.fabric

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class CreateArticulateDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack: FabricDataGenerator.Pack = fabricDataGenerator.createPack()
        pack.addProvider { output, registriesFuture ->
            CreateArticulateBlockTagsProvider(output, registriesFuture)
        }
    }
}
