package org.onesandzer0s.create_articulate.gui.shiphelm

import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.phys.BlockHitResult
import org.onesandzer0s.create_articulate.CreateArticulateConfig
import org.onesandzer0s.create_articulate.CreateArticulateMod
import org.valkyrienskies.mod.common.getShipManagingPos

class ShipHelmScreen(handler: ShipHelmScreenMenu, playerInventory: Inventory, text: Component) :
        AbstractContainerScreen<ShipHelmScreenMenu>(handler, playerInventory, text) {

    private lateinit var assembleButton: ShipHelmButton
    private lateinit var alignButton: ShipHelmButton
    private lateinit var disassembleButton: ShipHelmButton

    private val pos = (Minecraft.getInstance().hitResult as? BlockHitResult)?.blockPos

    init {
        titleLabelX = 120
    }

    override fun init() {
        super.init()
        val x = (width - imageWidth) / 2
        val y = (height - imageHeight) / 2

        assembleButton =
                addRenderableWidget(
                        ShipHelmButton(x + BUTTON_1_X, y + BUTTON_1_Y, ASSEMBLE_TEXT, font) {
                            minecraft?.gameMode?.handleInventoryButtonClick(menu.containerId, 0)
                        }
                )

        alignButton =
                addRenderableWidget(
                        ShipHelmButton(x + BUTTON_2_X, y + BUTTON_2_Y, ALIGN_TEXT, font) {
                            minecraft?.gameMode?.handleInventoryButtonClick(menu.containerId, 1)
                        }
                )

        disassembleButton =
                addRenderableWidget(
                        ShipHelmButton(x + BUTTON_3_X, y + BUTTON_3_Y, TODO_TEXT, font) {
                            minecraft?.gameMode?.handleInventoryButtonClick(menu.containerId, 3)
                        }
                )

        disassembleButton.active = CreateArticulateConfig.SERVER.allowDisassembly
        updateButtons()
    }

    private fun updateButtons() {
        val level = Minecraft.getInstance().level ?: return
        val isLookingAtShip = level.getShipManagingPos(pos ?: return) != null
        assembleButton.active = !isLookingAtShip
        disassembleButton.active = CreateArticulateConfig.SERVER.allowDisassembly && isLookingAtShip
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTicks: Float, mouseX: Int, mouseY: Int) {
        updateButtons()

        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        val x = (width - imageWidth) / 2
        val y = (height - imageHeight) / 2
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight)
    }

    override fun renderLabels(guiGraphics: GuiGraphics, i: Int, j: Int) {
        guiGraphics.drawString(font, title, titleLabelX, titleLabelY, 0x404040, false)

        if (this.menu.aligning) {
            alignButton.message = ALIGNING_TEXT
            alignButton.active = false
        } else {
            alignButton.message = ALIGN_TEXT
            alignButton.active = true
        }

        // TODO render stats
    }

    // mojank doesn't check mouse release for their widgets for some reason
    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        isDragging = false
        if (getChildAt(mouseX, mouseY).filter { it.mouseReleased(mouseX, mouseY, button) }.isPresent
        )
                return true

        return super.mouseReleased(mouseX, mouseY, button)
    }

    companion object { // TEXTURE DATA
        internal val TEXTURE =
                ResourceLocation(CreateArticulateMod.MOD_ID, "textures/gui/ship_helm.png")

        private const val BUTTON_1_X = 10
        private const val BUTTON_1_Y = 73
        private const val BUTTON_2_X = 10
        private const val BUTTON_2_Y = 103
        private const val BUTTON_3_X = 10
        private const val BUTTON_3_Y = 133

        private val ASSEMBLE_TEXT = Component.translatable("gui.create_articulate.assemble")
        private val DISSEMBLE_TEXT = Component.translatable("gui.create_articulate.disassemble")
        private val ALIGN_TEXT = Component.translatable("gui.create_articulate.align")
        private val ALIGNING_TEXT = Component.translatable("gui.create_articulate.aligning")
        private val TODO_TEXT = Component.translatable("gui.create_articulate.todo")
    }
}
