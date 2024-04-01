package org.onesandzer0s.create_articulate

import org.valkyrienskies.core.impl.config.VSConfigClass

object CreateArticulateMod {
    const val MOD_ID = "create_articulate"

    @JvmStatic
    fun init() {
        CreateArticulateBlocks.register()
        CreateArticulateBlockEntities.register()
        CreateArticulateItems.register()
        CreateArticulateScreens.register()
        CreateArticulateEntities.register()
        CreateArticulateWeights.register()
        VSConfigClass.registerConfig("create_articulate", CreateArticulateConfig::class.java)
    }

    @JvmStatic
    fun initClient() {
        CreateArticulateClientScreens.register()
    }
}
