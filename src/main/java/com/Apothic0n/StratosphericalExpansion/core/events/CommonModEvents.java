package com.Apothic0n.StratosphericalExpansion.core.events;

import com.Apothic0n.StratosphericalExpansion.StratosphericalExpansion;
import com.Apothic0n.StratosphericalExpansion.world.dimension.StratoDimensions;
import com.Apothic0n.StratosphericalExpansion.world.dimension.StratoITeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StratosphericalExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonModEvents {
    /** Overworld height at which players are lifted into the Stratosphere. */
    private static final int ASCEND_Y = 420;
    /** Stratosphere height at or below which players fall back to the Overworld. */
    private static final int FALL_Y = -64;

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) {
            return;
        }
        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }
        Level level = player.level();

        if (Level.OVERWORLD.equals(level.dimension())) {
            if (player.blockPosition().getY() >= ASCEND_Y) {
                ServerLevel target = server.getLevel(StratoDimensions.StratoDim);
                if (target != null) {
                    BlockPos pos = StratoITeleporter.acsendFromOverworld(player);
                    player.teleportTo(target, pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
                }
            }
        } else if (StratoDimensions.StratoDim.equals(level.dimension())) {
            if (player.blockPosition().getY() <= FALL_Y) {
                ServerLevel target = server.getLevel(Level.OVERWORLD);
                if (target != null) {
                    BlockPos pos = StratoITeleporter.fallToOverworld(player);
                    player.teleportTo(target, pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
                }
            }
        }
    }
}
