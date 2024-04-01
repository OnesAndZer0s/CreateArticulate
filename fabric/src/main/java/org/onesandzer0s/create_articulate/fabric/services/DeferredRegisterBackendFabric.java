package org.onesandzer0s.create_articulate.fabric.services;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;
import org.onesandzer0s.create_articulate.fabric.DeferredRegisterImpl;
import org.onesandzer0s.create_articulate.registry.DeferredRegister;
import org.onesandzer0s.create_articulate.services.DeferredRegisterBackend;

public class DeferredRegisterBackendFabric implements DeferredRegisterBackend {

    @NotNull
    @Override
    public <T> DeferredRegister<T> makeDeferredRegister(
            @NotNull final String id,
            @NotNull final ResourceKey<Registry<T>> registry) {
        return new DeferredRegisterImpl<>(id, registry);
    }
}
