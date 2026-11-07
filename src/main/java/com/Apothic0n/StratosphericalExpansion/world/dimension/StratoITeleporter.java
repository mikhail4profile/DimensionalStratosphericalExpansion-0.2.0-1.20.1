package com.Apothic0n.StratosphericalExpansion.world.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

/**
 * Computes where a player lands when moving between the Overworld and the Stratosphere.
 * Players keep their X/Z; the vertical position is fixed by the layer they travel through.
 */
public class StratoITeleporter {
    /** Y in the Overworld where players arrive after falling out of the bottom of the Stratosphere. */
    public static final int OVERWORLD_ARRIVAL_Y = 419;

    /** Falling out of the Stratosphere: same X/Z, just above the Overworld build limit. */
    public static BlockPos fallToOverworld(ServerPlayer player) {
        return BlockPos.containing(player.getX(), OVERWORLD_ARRIVAL_Y, player.getZ());
    }

    /** Rising out of the Overworld: same position, now inside the Stratosphere. */
    public static BlockPos acsendFromOverworld(ServerPlayer player) {
        return BlockPos.containing(player.getX(), player.getY(), player.getZ());
    }
}
