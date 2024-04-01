package org.onesandzer0s.create_articulate.fabric

import java.util.concurrent.CompletableFuture
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import org.onesandzer0s.create_articulate.CreateArticulateBlocks

class CreateArticulateBlockTagsProvider(
        output: FabricDataOutput,
        registriesFuture: CompletableFuture<HolderLookup.Provider>
) : FabricTagProvider.BlockTagProvider(output, registriesFuture) {
    override fun addTags(arg: HolderLookup.Provider) {
        getOrCreateTagBuilder(BlockTags.WOOL)
                .add(CreateArticulateBlocks.BALLOON.get())
                .add(CreateArticulateBlocks.WHITE_BALLOON.get())
                .add(CreateArticulateBlocks.LIGHT_GRAY_BALLOON.get())
                .add(CreateArticulateBlocks.GRAY_BALLOON.get())
                .add(CreateArticulateBlocks.BLACK_BALLOON.get())
                .add(CreateArticulateBlocks.RED_BALLOON.get())
                .add(CreateArticulateBlocks.ORANGE_BALLOON.get())
                .add(CreateArticulateBlocks.YELLOW_BALLOON.get())
                .add(CreateArticulateBlocks.LIME_BALLOON.get())
                .add(CreateArticulateBlocks.GREEN_BALLOON.get())
                .add(CreateArticulateBlocks.LIGHT_BLUE_BALLOON.get())
                .add(CreateArticulateBlocks.CYAN_BALLOON.get())
                .add(CreateArticulateBlocks.BLUE_BALLOON.get())
                .add(CreateArticulateBlocks.PURPLE_BALLOON.get())
                .add(CreateArticulateBlocks.MAGENTA_BALLOON.get())
                .add(CreateArticulateBlocks.PINK_BALLOON.get())
                .add(CreateArticulateBlocks.BROWN_BALLOON.get())
                .add(CreateArticulateBlocks.FLOATER.get())

        getOrCreateTagBuilder(BlockTags.PLANKS)
                .add(CreateArticulateBlocks.OAK_SHIP_HELM.get())
                .add(CreateArticulateBlocks.SPRUCE_SHIP_HELM.get())
                .add(CreateArticulateBlocks.BIRCH_SHIP_HELM.get())
                .add(CreateArticulateBlocks.JUNGLE_SHIP_HELM.get())
                .add(CreateArticulateBlocks.ACACIA_SHIP_HELM.get())
                .add(CreateArticulateBlocks.DARK_OAK_SHIP_HELM.get())
                .add(CreateArticulateBlocks.CRIMSON_SHIP_HELM.get())
                .add(CreateArticulateBlocks.WARPED_SHIP_HELM.get())

        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
                .add(CreateArticulateBlocks.OAK_SHIP_HELM.get())
                .add(CreateArticulateBlocks.SPRUCE_SHIP_HELM.get())
                .add(CreateArticulateBlocks.BIRCH_SHIP_HELM.get())
                .add(CreateArticulateBlocks.JUNGLE_SHIP_HELM.get())
                .add(CreateArticulateBlocks.ACACIA_SHIP_HELM.get())
                .add(CreateArticulateBlocks.DARK_OAK_SHIP_HELM.get())
                .add(CreateArticulateBlocks.CRIMSON_SHIP_HELM.get())
                .add(CreateArticulateBlocks.WARPED_SHIP_HELM.get())

        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(CreateArticulateBlocks.ANCHOR.get())
                .add(CreateArticulateBlocks.ENGINE.get())
                .add(CreateArticulateBlocks.BALLAST.get())
    }
}
