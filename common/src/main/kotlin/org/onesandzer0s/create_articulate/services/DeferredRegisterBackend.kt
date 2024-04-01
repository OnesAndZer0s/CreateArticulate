package org.onesandzer0s.create_articulate.services

import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import org.onesandzer0s.create_articulate.registry.DeferredRegister

interface DeferredRegisterBackend {
    fun <T> makeDeferredRegister(
            id: String,
            registry: ResourceKey<Registry<T>>
    ): DeferredRegister<T>
}
