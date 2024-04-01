package org.onesandzer0s.create_articulate.registry

interface RegistrySupplier<T> {

    val name: String
    fun get(): T
}
