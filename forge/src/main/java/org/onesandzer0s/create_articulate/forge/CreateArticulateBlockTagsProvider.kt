package org.onesandzer0s.create_articulate.forge

import java.util.concurrent.CompletableFuture
import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.tags.BlockTags
import net.minecraftforge.common.data.BlockTagsProvider
import net.minecraftforge.common.data.ExistingFileHelper
import org.onesandzer0s.create_articulate.CreateArticulateBlocks

class CreateArticulateBlockTagsProvider(
        output: PackOutput,
        lookupProvider: CompletableFuture<HolderLookup.Provider>,
        modId: String,
        existingFileHelper: ExistingFileHelper?
) : BlockTagsProvider(output, lookupProvider, modId, existingFileHelper) {
    override fun addTags(arg: HolderLookup.Provider) {
        tag(BlockTags.WOOL)
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

        tag(BlockTags.PLANKS)
                .add(CreateArticulateBlocks.OAK_SHIP_HELM.get())
                .add(CreateArticulateBlocks.SPRUCE_SHIP_HELM.get())
                .add(CreateArticulateBlocks.BIRCH_SHIP_HELM.get())
                .add(CreateArticulateBlocks.JUNGLE_SHIP_HELM.get())
                .add(CreateArticulateBlocks.ACACIA_SHIP_HELM.get())
                .add(CreateArticulateBlocks.DARK_OAK_SHIP_HELM.get())
                .add(CreateArticulateBlocks.CRIMSON_SHIP_HELM.get())
                .add(CreateArticulateBlocks.WARPED_SHIP_HELM.get())

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(CreateArticulateBlocks.OAK_SHIP_HELM.get())
                .add(CreateArticulateBlocks.SPRUCE_SHIP_HELM.get())
                .add(CreateArticulateBlocks.BIRCH_SHIP_HELM.get())
                .add(CreateArticulateBlocks.JUNGLE_SHIP_HELM.get())
                .add(CreateArticulateBlocks.ACACIA_SHIP_HELM.get())
                .add(CreateArticulateBlocks.DARK_OAK_SHIP_HELM.get())
                .add(CreateArticulateBlocks.CRIMSON_SHIP_HELM.get())
                .add(CreateArticulateBlocks.WARPED_SHIP_HELM.get())

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(CreateArticulateBlocks.ANCHOR.get())
                .add(CreateArticulateBlocks.ENGINE.get())
                .add(CreateArticulateBlocks.BALLAST.get())
    }
}
