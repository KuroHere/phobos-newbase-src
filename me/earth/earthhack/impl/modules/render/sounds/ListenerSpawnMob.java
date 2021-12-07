//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.sounds;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

final class ListenerSpawnMob extends ModuleListener<Sounds, PacketEvent.Receive<SPacketSpawnMob>>
{
    public ListenerSpawnMob(final Sounds module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnMob.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnMob> event) {
        final Entity player = (Entity)ListenerSpawnMob.mc.player;
        if (player != null && ((Sounds)this.module).slimes.getValue()) {
            final SPacketSpawnMob p = event.getPacket();
            if (p.getEntityType() == 55 && p.getY() <= 40.0 && !ListenerSpawnMob.mc.world.getBiome(player.getPosition()).getBiomeName().toLowerCase().contains("swamp")) {
                final BlockPos pos = new BlockPos(p.getX(), p.getY(), p.getZ());
                ((Sounds)this.module).log("Slime: " + ListenerSpawnMob.mc.world.getChunkFromBlockCoords(pos).xPosition + "-ChunkX, " + ListenerSpawnMob.mc.world.getChunkFromBlockCoords(pos).zPosition + "-ChunkZ.");
            }
        }
    }
}
