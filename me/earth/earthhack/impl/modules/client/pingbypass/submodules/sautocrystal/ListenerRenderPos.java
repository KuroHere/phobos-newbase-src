//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.*;

final class ListenerRenderPos extends ModuleListener<ServerAutoCrystal, PacketEvent.Receive<SPacketSpawnExperienceOrb>>
{
    public ListenerRenderPos(final ServerAutoCrystal module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MAX_VALUE, SPacketSpawnExperienceOrb.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnExperienceOrb> event) {
        final SPacketSpawnExperienceOrb packet = event.getPacket();
        if (packet.getEntityID() == -1337) {
            ListenerRenderPos.mc.addScheduledTask(() -> {
                if (packet.getXPValue() == -1337) {
                    ((ServerAutoCrystal)this.module).renderPos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
                }
                else if (packet.getXPValue() == -1338 && ListenerRenderPos.mc.player != null) {
                    Swing.Client.swing(EnumHand.MAIN_HAND);
                }
                return;
            });
            event.setCancelled(true);
        }
    }
}
