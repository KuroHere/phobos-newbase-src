//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.pistonaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import java.util.*;

final class ListenerExplosion extends ModuleListener<PistonAura, PacketEvent.Receive<SPacketExplosion>>
{
    public ListenerExplosion(final PistonAura module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketExplosion.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketExplosion> event) {
        if (!((PistonAura)this.module).explosions.getValue()) {
            return;
        }
        ListenerExplosion.mc.addScheduledTask(() -> {
            if (((PistonAura)this.module).current != null) {
                final SPacketExplosion packet = (SPacketExplosion)event.getPacket();
                final BlockPos pos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
                if (pos.equals((Object)((PistonAura)this.module).current.getStartPos().up()) || pos.equals((Object)((PistonAura)this.module).current.getCrystalPos().up())) {
                    ((PistonAura)this.module).current.setValid(false);
                }
                else {
                    packet.getAffectedBlockPositions().iterator();
                    final Iterator iterator;
                    while (iterator.hasNext()) {
                        final BlockPos affected = iterator.next();
                        if (affected.equals((Object)((PistonAura)this.module).current.getPistonPos()) || affected.equals((Object)((PistonAura)this.module).current.getRedstonePos())) {
                            ((PistonAura)this.module).current.setValid(false);
                            break;
                        }
                    }
                }
            }
        });
    }
}
