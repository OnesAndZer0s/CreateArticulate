package org.onesandzer0s.create_articulate.blockentity

import net.minecraft.commands.arguments.EntityAnchorArgument
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction.Axis
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.world.level.block.state.properties.Half
import org.joml.Vector3d
import org.joml.Vector3dc
import org.onesandzer0s.create_articulate.CreateArticulateBlockEntities
import org.onesandzer0s.create_articulate.CreateArticulateConfig
import org.onesandzer0s.create_articulate.block.ShipHelmBlock
import org.onesandzer0s.create_articulate.gui.shiphelm.ShipHelmScreenMenu
import org.onesandzer0s.create_articulate.ship.CreateArticulateShipControl
import org.onesandzer0s.create_articulate.util.ShipAssembler
import org.valkyrienskies.core.api.ships.ServerShip
import org.valkyrienskies.core.api.ships.getAttachment
import org.valkyrienskies.core.impl.util.logger
import org.valkyrienskies.mod.common.ValkyrienSkiesMod
import org.valkyrienskies.mod.common.entity.ShipMountingEntity
import org.valkyrienskies.mod.common.getShipObjectManagingPos
import org.valkyrienskies.mod.common.util.toDoubles
import org.valkyrienskies.mod.common.util.toJOMLD

class ShipHelmBlockEntity(pos: BlockPos, state: BlockState) :
        BlockEntity(CreateArticulateBlockEntities.SHIP_HELM.get(), pos, state), MenuProvider {

    private val ship: ServerShip?
        get() = (level as ServerLevel).getShipObjectManagingPos(this.blockPos)
    private val control: CreateArticulateShipControl?
        get() = ship?.getAttachment(CreateArticulateShipControl::class.java)
    private val seats = mutableListOf<ShipMountingEntity>()
    val assembled
        get() = ship != null
    val aligning
        get() = control?.aligning ?: false
    private var shouldDisassembleWhenPossible = false

    override fun createMenu(
            id: Int,
            playerInventory: Inventory,
            player: Player
    ): AbstractContainerMenu {
        return ShipHelmScreenMenu(id, playerInventory, this)
    }

    override fun getDisplayName(): Component {
        return Component.translatable("gui.create_articulate.ship_helm")
    }

    // Needs to get called server-side
    fun spawnSeat(blockPos: BlockPos, state: BlockState, level: ServerLevel): ShipMountingEntity {
        val newPos = blockPos.relative(state.getValue(HorizontalDirectionalBlock.FACING))
        val newState = level.getBlockState(newPos)
        val newShape = newState.getShape(level, newPos)
        val newBlock = newState.block
        var height = 0.5
        if (!newState.isAir) {
            height =
                    if (newBlock is StairBlock &&
                                    (!newState.hasProperty(StairBlock.HALF) ||
                                            newState.getValue(StairBlock.HALF) == Half.BOTTOM)
                    )
                            0.5 // Valid StairBlock
                    else newShape.max(Axis.Y)
        }
        val entity =
                ValkyrienSkiesMod.SHIP_MOUNTING_ENTITY_TYPE.create(level)!!.apply {
                    val seatEntityPos: Vector3dc =
                            Vector3d(newPos.x + .5, (newPos.y - .5) + height, newPos.z + .5)
                    moveTo(seatEntityPos.x(), seatEntityPos.y(), seatEntityPos.z())

                    lookAt(
                            EntityAnchorArgument.Anchor.EYES,
                            state.getValue(HORIZONTAL_FACING).normal.toDoubles().add(position())
                    )

                    isController = true
                }

        level.addFreshEntityWithPassengers(entity)
        return entity
    }

    fun startRiding(
            player: Player,
            force: Boolean,
            blockPos: BlockPos,
            state: BlockState,
            level: ServerLevel
    ): Boolean {

        for (i in seats.size - 1 downTo 0) {
            if (!seats[i].isVehicle) {
                seats[i].kill()
                seats.removeAt(i)
            } else if (!seats[i].isAlive) {
                seats.removeAt(i)
            }
        }

        val seat = spawnSeat(blockPos, blockState, level)
        val ride = player.startRiding(seat, force)

        if (ride) {
            control?.seatedPlayer = player
            seats.add(seat)
        }

        return ride
    }

    fun tick() {
        if (shouldDisassembleWhenPossible &&
                        ship?.getAttachment<CreateArticulateShipControl>()?.canDisassemble == true
        ) {
            this.disassemble()
        }
        control?.ship = ship
    }

    // Needs to get called server-side
    fun assemble(player: Player) {
        val level = level as ServerLevel

        // Check the block state before assembling to avoid creating an empty ship
        val blockState = level.getBlockState(blockPos)
        if (blockState.block !is ShipHelmBlock) return

        val builtShip =
                ShipAssembler.collectBlocks(level, blockPos) {
                    !it.isAir &&
                            !CreateArticulateConfig.SERVER.blockBlacklist.contains(
                                    BuiltInRegistries.BLOCK.getKey(it.block).toString()
                            )
                }

        if (builtShip == null) {
            player.displayClientMessage(
                    Component.translatable(
                            "Ship is too big! Max size is ${CreateArticulateConfig.SERVER.maxShipBlocks} blocks (changable in the config)"
                    ),
                    true
            )
            logger.warn("Failed to assemble ship for ${player.name.string}")
        }
    }

    fun disassemble() {
        val ship = ship ?: return
        val level = level ?: return
        val control = control ?: return

        if (!control.canDisassemble) {
            shouldDisassembleWhenPossible = true
            control.disassembling = true
            control.aligning = true
            return
        }

        val inWorld = ship.shipToWorld.transformPosition(this.blockPos.toJOMLD())

        ShipAssembler.unfillShip(
                level as ServerLevel,
                ship,
                control.aligningTo,
                this.blockPos,
                BlockPos.containing(inWorld.x, inWorld.y, inWorld.z)
        )
        // ship.die() TODO i think we do need this no? or autodetecting on all air

        shouldDisassembleWhenPossible = false
    }

    fun align() {
        val control = control ?: return
        control.aligning = !control.aligning
    }

    override fun setRemoved() {

        if (level?.isClientSide == false) {
            for (i in seats.indices) {
                seats[i].kill()
            }
            seats.clear()
        }

        super.setRemoved()
    }

    fun sit(player: Player, force: Boolean = false): Boolean {
        // If player is already controlling the ship, open the helm menu
        if (!force &&
                        player.vehicle?.type == ValkyrienSkiesMod.SHIP_MOUNTING_ENTITY_TYPE &&
                        seats.contains(player.vehicle as ShipMountingEntity)
        ) {
            player.openMenu(this)
            return true
        }

        // val seat = spawnSeat(blockPos, blockState, level as ServerLevel)
        // control?.seatedPlayer = player
        // return player.startRiding(seat, force)
        return startRiding(player, force, blockPos, blockState, level as ServerLevel)
    }
    private val logger by logger()
}
