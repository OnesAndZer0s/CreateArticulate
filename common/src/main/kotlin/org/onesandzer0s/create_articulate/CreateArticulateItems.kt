package org.onesandzer0s.create_articulate

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import org.onesandzer0s.create_articulate.registry.DeferredRegister

@Suppress("unused")
object CreateArticulateItems {
    internal val ITEMS = DeferredRegister.create(CreateArticulateMod.MOD_ID, Registries.ITEM)
    val TAB: ResourceKey<CreativeModeTab> =
            ResourceKey.create(
                    Registries.CREATIVE_MODE_TAB,
                    ResourceLocation(CreateArticulateMod.MOD_ID, "create_articulate_tab")
            )

    fun register() {
        CreateArticulateBlocks.registerItems(ITEMS)
        ITEMS.applyAll()
    }

    private infix fun Item.byName(name: String) = ITEMS.register(name) { this }
}
