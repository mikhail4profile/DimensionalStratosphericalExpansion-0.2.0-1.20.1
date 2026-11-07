package com.Apothic0n.StratosphericalExpansion.world.dimension;

import com.Apothic0n.StratosphericalExpansion.StratosphericalExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class StratoDimensions {
    public static final ResourceKey<Level> StratoDim =
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(StratosphericalExpansion.MODID, "stratospherical"));
}
