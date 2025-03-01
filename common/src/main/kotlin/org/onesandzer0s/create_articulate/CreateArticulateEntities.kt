package org.onesandzer0s.create_articulate

import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.EntityRenderers
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.Level
import org.onesandzer0s.create_articulate.registry.DeferredRegister
import org.onesandzer0s.create_articulate.registry.RegistrySupplier

private typealias EFactory<T> = (EntityType<T>, Level) -> T

private typealias RFactory<T> = EntityRendererProvider<T>

private data class ToRegEntityRenderer<T : Entity>(
        val type: RegistrySupplier<EntityType<T>>,
        val renderer: RFactory<in T>
) {
    fun register() = EntityRenderers.register(type.get(), renderer)
}

object CreateArticulateEntities {
    private val ENTITIES =
            DeferredRegister.create(CreateArticulateMod.MOD_ID, Registries.ENTITY_TYPE)
    private val ENTITY_RENDERERS = mutableListOf<ToRegEntityRenderer<*>>()

    // val SEAT = ::SeatEntity category MobCategory.MISC byName "seat" registerRenderer
    // ::EmptyRenderer

    fun register() {
        ENTITIES.applyAll()
    }

    private infix fun <T : Entity> EFactory<T>.category(category: MobCategory) =
            EntityType.Builder.of(this, category)

    private infix fun <T : Entity> EntityType.Builder<T>.configure(
            run: EntityType.Builder<T>.() -> Unit
    ) = this.apply { run(this) }

    private infix fun <T : Entity> RegistrySupplier<EntityType<T>>.registerRenderer(
            factory: RFactory<in T>
    ) = this.apply { ENTITY_RENDERERS += ToRegEntityRenderer(this, factory) }

    private infix fun <T : Entity> EntityType.Builder<T>.byName(name: String) =
            ENTITIES.register(name) { this.build(name) }

    @JvmStatic fun registerRenderers() = ENTITY_RENDERERS.forEach { it.register() }
}
