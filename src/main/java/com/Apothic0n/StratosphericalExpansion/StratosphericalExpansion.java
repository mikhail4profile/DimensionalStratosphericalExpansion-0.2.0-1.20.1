package com.Apothic0n.StratosphericalExpansion;

import com.Apothic0n.StratosphericalExpansion.api.biome.features.StratoFeatureRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StratosphericalExpansion.MODID)
public class StratosphericalExpansion {
    public static final String MODID = "strato";

    public StratosphericalExpansion() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        StratoFeatureRegistry.register(modEventBus);
    }
}
