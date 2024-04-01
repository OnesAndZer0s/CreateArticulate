package org.onesandzer0s.create_articulate.forge

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.client.event.ModelEvent
import org.onesandzer0s.create_articulate.CreateArticulateBlockEntities
import org.onesandzer0s.create_articulate.CreateArticulateMod
import org.onesandzer0s.create_articulate.block.WoodType
import org.onesandzer0s.create_articulate.blockentity.renderer.ShipHelmBlockEntityRenderer
import org.onesandzer0s.create_articulate.blockentity.renderer.WheelModels
import thedarkcolour.kotlinforforge.forge.MOD_BUS

object CreateArticulateModForgeClient {
    private var happendClientSetup = false

    fun registerClient() {
        MOD_BUS.addListener { event: ModelEvent.BakingCompleted -> clientSetup(event) }
        MOD_BUS.addListener { event: ModelEvent.RegisterAdditional -> onModelRegistry(event) }
        MOD_BUS.addListener { event: EntityRenderersEvent.RegisterRenderers ->
            entityRenderers(event)
        }
    }

    fun clientSetup(event: ModelEvent.BakingCompleted) {
        if (happendClientSetup) {
            return
        }
        happendClientSetup = true
        CreateArticulateMod.initClient()
        WheelModels.setModelGetter { woodType: WoodType ->
            event.modelBakery.bakedTopLevelModels.getOrDefault(
                    ResourceLocation(
                            CreateArticulateMod.MOD_ID,
                            "block/" + woodType.resourceName + "_ship_helm_wheel"
                    ),
                    Minecraft.getInstance().modelManager.missingModel
            )
        }
    }

    fun entityRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerBlockEntityRenderer(CreateArticulateBlockEntities.SHIP_HELM.get()) {
                ctx: BlockEntityRendererProvider.Context ->
            ShipHelmBlockEntityRenderer(ctx)
        }
    }

    fun onModelRegistry(event: ModelEvent.RegisterAdditional) {
        for (woodType in WoodType.values()) {
            event.register(
                    ResourceLocation(
                            CreateArticulateMod.MOD_ID,
                            "block/" + woodType.resourceName + "_ship_helm_wheel"
                    )
            )
        }
    }
}
