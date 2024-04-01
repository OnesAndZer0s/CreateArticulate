package org.onesandzer0s.create_articulate.registry

import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import org.onesandzer0s.create_articulate.CreateArticulateBlocks
import org.onesandzer0s.create_articulate.CreateArticulateItems

object CreativeTabs {
    fun create(): CreativeModeTab {
        return CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .title(Component.translatable("itemGroup.create_articulate"))
                .icon { ItemStack(CreateArticulateBlocks.OAK_SHIP_HELM.get().asItem()) }
                .displayItems { _, output ->
                    CreateArticulateItems.ITEMS.forEach { output.accept(it.get()) }
                }
                .build()
    }
}
