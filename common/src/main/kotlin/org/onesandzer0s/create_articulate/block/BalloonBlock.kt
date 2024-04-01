package org.onesandzer0s.create_articulate.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import org.onesandzer0s.create_articulate.CreateArticulateConfig
import org.onesandzer0s.create_articulate.ship.CreateArticulateShipControl
import org.valkyrienskies.core.api.ships.getAttachment
import org.valkyrienskies.mod.common.getShipManagingPos
import org.valkyrienskies.mod.common.getShipObjectManagingPos

class BalloonBlock(properties: Properties) : Block(properties) {

    override fun fallOn(
            level: Level,
            state: BlockState,
            blockPos: BlockPos,
            entity: Entity,
            f: Float
    ) {
        entity.causeFallDamage(f, 0.2f, entity.damageSources().fall())
    }

    override fun onPlace(
            state: BlockState,
            level: Level,
            pos: BlockPos,
            oldState: BlockState,
            isMoving: Boolean
    ) {
        super.onPlace(state, level, pos, oldState, isMoving)

        if (level.isClientSide) return
        level as ServerLevel

        val ship = level.getShipObjectManagingPos(pos) ?: level.getShipManagingPos(pos) ?: return
        CreateArticulateShipControl.getOrCreate(ship).balloons += 1
    }

    override fun destroy(level: LevelAccessor, pos: BlockPos, state: BlockState) {
        super.destroy(level, pos, state)

        if (level.isClientSide) return
        level as ServerLevel

        level.getShipManagingPos(pos)?.getAttachment<CreateArticulateShipControl>()?.let {
            it.balloons -= 1
        }
    }

    override fun onProjectileHit(
            level: Level,
            state: BlockState,
            hit: BlockHitResult,
            projectile: Projectile
    ) {
        if (level.isClientSide) return

        level.destroyBlock(hit.blockPos, false)
        Direction.values().forEach {
            val neighbor = hit.blockPos.relative(it)
            if (level.getBlockState(neighbor).block == this &&
                            level.random.nextFloat() <
                                    CreateArticulateConfig.SERVER.popSideBalloonChance
            ) {
                level.destroyBlock(neighbor, false)
            }
        }
    }
}
