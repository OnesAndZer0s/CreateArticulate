package org.onesandzer0s.create_articulate

import net.minecraft.Util
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.util.datafix.fixes.References
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import org.onesandzer0s.create_articulate.blockentity.EngineBlockEntity
import org.onesandzer0s.create_articulate.blockentity.ShipHelmBlockEntity
import org.onesandzer0s.create_articulate.registry.DeferredRegister
import org.onesandzer0s.create_articulate.registry.RegistrySupplier

@Suppress("unused")
object CreateArticulateBlockEntities {
    private val BLOCKENTITIES =
            DeferredRegister.create(CreateArticulateMod.MOD_ID, Registries.BLOCK_ENTITY_TYPE)

    val SHIP_HELM =
            setOf(
                    CreateArticulateBlocks.OAK_SHIP_HELM,
                    CreateArticulateBlocks.SPRUCE_SHIP_HELM,
                    CreateArticulateBlocks.BIRCH_SHIP_HELM,
                    CreateArticulateBlocks.JUNGLE_SHIP_HELM,
                    CreateArticulateBlocks.ACACIA_SHIP_HELM,
                    CreateArticulateBlocks.DARK_OAK_SHIP_HELM,
                    CreateArticulateBlocks.CRIMSON_SHIP_HELM,
                    CreateArticulateBlocks.WARPED_SHIP_HELM
            ) withBE ::ShipHelmBlockEntity byName "ship_helm"

    val ENGINE = CreateArticulateBlocks.ENGINE withBE ::EngineBlockEntity byName "engine"

    fun register() {
        BLOCKENTITIES.applyAll()
    }

    private infix fun <T : BlockEntity> Set<RegistrySupplier<out Block>>.withBE(
            blockEntity: (BlockPos, BlockState) -> T
    ) = Pair(this, blockEntity)

    private infix fun <T : BlockEntity> RegistrySupplier<out Block>.withBE(
            blockEntity: (BlockPos, BlockState) -> T
    ) = Pair(setOf(this), blockEntity)

    private infix fun <T : BlockEntity> Block.withBE(blockEntity: (BlockPos, BlockState) -> T) =
            Pair(this, blockEntity)
    private infix fun <T : BlockEntity> Pair<
            Set<RegistrySupplier<out Block>>, (BlockPos, BlockState) -> T>.byName(
            name: String
    ): RegistrySupplier<BlockEntityType<T>> =
            BLOCKENTITIES.register(name) {
                val type = Util.fetchChoiceType(References.BLOCK_ENTITY, name)

                BlockEntityType.Builder.of(this.second, *this.first.map { it.get() }.toTypedArray())
                        .build(type)
            }
}
