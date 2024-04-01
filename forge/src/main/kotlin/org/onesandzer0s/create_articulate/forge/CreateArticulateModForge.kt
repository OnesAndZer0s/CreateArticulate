package org.onesandzer0s.create_articulate.forge

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.core.registries.Registries
import net.minecraftforge.client.ConfigScreenHandler
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.registries.DeferredRegister
import org.onesandzer0s.create_articulate.CreateArticulateConfig
import org.onesandzer0s.create_articulate.CreateArticulateMod
import org.onesandzer0s.create_articulate.CreateArticulateMod.init
import org.onesandzer0s.create_articulate.registry.CreativeTabs
import org.valkyrienskies.core.impl.config.VSConfigClass.Companion.getRegisteredConfig
import org.valkyrienskies.mod.compat.clothconfig.VSClothConfig.createConfigScreenFor
import thedarkcolour.kotlinforforge.forge.LOADING_CONTEXT
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist

@Mod(CreateArticulateMod.MOD_ID)
class CreateArticulateModForge {
    init {
        runForDist(
                clientTarget = { CreateArticulateModForgeClient.registerClient() },
                serverTarget = {}
        )
        LOADING_CONTEXT.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory::class.java
        ) {
            ConfigScreenHandler.ConfigScreenFactory { _: Minecraft?, parent: Screen? ->
                createConfigScreenFor(
                        parent!!,
                        getRegisteredConfig(CreateArticulateConfig::class.java)
                )
            }
        }
        init()

        val deferredRegister =
                DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateArticulateMod.MOD_ID)
        deferredRegister.register("general") { CreativeTabs.create() }
        deferredRegister.register(getModBus())
    }

    companion object {
        fun getModBus(): IEventBus = MOD_BUS
    }
}
