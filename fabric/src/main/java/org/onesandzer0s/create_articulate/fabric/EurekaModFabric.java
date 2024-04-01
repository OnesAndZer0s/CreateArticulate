package org.onesandzer0s.create_articulate.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.valkyrienskies.core.impl.config.VSConfigClass;
import org.onesandzer0s.create_articulate.CreateArticulateBlockEntities;
import org.onesandzer0s.create_articulate.CreateArticulateConfig;
import org.onesandzer0s.create_articulate.CreateArticulateItems;
import org.onesandzer0s.create_articulate.CreateArticulateMod;
import org.onesandzer0s.create_articulate.block.WoodType;
import org.onesandzer0s.create_articulate.blockentity.renderer.ShipHelmBlockEntityRenderer;
import org.onesandzer0s.create_articulate.blockentity.renderer.WheelModels;
import org.onesandzer0s.create_articulate.registry.CreativeTabs;
import org.valkyrienskies.mod.compat.clothconfig.VSClothConfig;
import org.valkyrienskies.mod.fabric.common.ValkyrienSkiesModFabric;

public class CreateArticulateModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // force VS2 to load before create_articulate
        new ValkyrienSkiesModFabric().onInitialize();

        CreateArticulateMod.init();

        Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            CreateArticulateItems.INSTANCE.getTAB(),
            CreativeTabs.INSTANCE.create()
        );
    }

    @Environment(EnvType.CLIENT)
    public static class Client implements ClientModInitializer {

        @Override
        public void onInitializeClient() {
            CreateArticulateMod.initClient();
            BlockEntityRenderers.register(
                    CreateArticulateBlockEntities.INSTANCE.getSHIP_HELM().get(),
                    ShipHelmBlockEntityRenderer::new
            );

            ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
                for (final WoodType woodType : WoodType.values()) {
                    out.accept(new ResourceLocation(
                        CreateArticulateMod.MOD_ID,
                        "block/" + woodType.getResourceName() + "_ship_helm_wheel"
                    ));
                }
            });

            WheelModels.INSTANCE.setModelGetter(woodType ->
                BakedModelManagerHelper.getModel(Minecraft.getInstance().getModelManager(),
                    new ResourceLocation(
                            CreateArticulateMod.MOD_ID,
                            "block/" + woodType.getResourceName() + "_ship_helm_wheel"
                    )));
        }
    }

    public static class ModMenu implements ModMenuApi {
        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return (parent) -> VSClothConfig.createConfigScreenFor(
                    parent,
                    VSConfigClass.Companion.getRegisteredConfig(CreateArticulateConfig.class)
            );
        }
    }
}
