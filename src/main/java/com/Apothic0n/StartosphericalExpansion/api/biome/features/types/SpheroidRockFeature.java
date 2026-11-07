package com.Apothic0n.StartosphericalExpansion.api.biome.features.types;

import com.Apothic0n.StartosphericalExpansion.api.biome.features.configurations.RockConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SpheroidRockFeature extends Feature<RockConfiguration> {
    public SpheroidRockFeature(Codec<RockConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RockConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        RockConfiguration config = context.config();

        BlockState state = config.blobMaterial.getBlock().defaultBlockState();
        int width = config.getBlobWidth().sample(random);
        int height = config.getBlobHeight().sample(random) + 1;

        // Only place rocks on top of something solid.
        if (level.isEmptyBlock(origin.below())) {
            return false;
        }

        int radius = width;
        for (int y = 0; y < height; y++) {
            if (radius > 0) {
                for (int x = 0; x < radius; x++) {
                    for (int z = 0; z < radius; z++) {
                        // Each successive octant pair is slightly less likely, which roughens the shape.
                        if (random.nextInt(42) + 1 < 42) {
                            set(level, origin.offset(-x, y, z), state);
                            set(level, origin.offset(-x, -y, -z), state);
                            if (random.nextInt(33) + 1 < 33) {
                                set(level, origin.offset(-x, y, -z), state);
                                set(level, origin.offset(-x, -y, z), state);
                                if (random.nextInt(27) + 1 < 27) {
                                    set(level, origin.offset(x, y, z), state);
                                    set(level, origin.offset(x, -y, -z), state);
                                    if (random.nextInt(20) + 1 < 20) {
                                        set(level, origin.offset(x, y, -z), state);
                                        set(level, origin.offset(x, -y, z), state);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // The rock narrows by one block two layers before its top.
            if (y == height - 2) {
                radius--;
            }
        }
        return true;
    }

    private static void set(WorldGenLevel level, BlockPos pos, BlockState state) {
        level.setBlock(pos, state, 2);
    }
}
