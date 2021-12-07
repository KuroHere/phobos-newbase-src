//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;

final class ListenerPostVehicleMove extends ModuleListener<BoatFly, PacketEvent.Post<CPacketVehicleMove>>
{
    public ListenerPostVehicleMove(final BoatFly module) {
        super(module, (Class<? super Object>)PacketEvent.Post.class, CPacketVehicleMove.class);
    }
    
    public void invoke(final PacketEvent.Post<CPacketVehicleMove> event) {
        final Entity riding = ListenerPostVehicleMove.mc.player.getRidingEntity();
        if (riding != null && !((BoatFly)this.module).packetSet.contains(event.getPacket()) && ((BoatFly)this.module).bypass.getValue() && ((BoatFly)this.module).postBypass.getValue() && ((BoatFly)this.module).tickCount++ >= ((BoatFly)this.module).ticks.getValue()) {
            for (int i = 0; i <= ((BoatFly)this.module).packets.getValue(); ++i) {
                ((BoatFly)this.module).sendPackets(riding);
            }
            ((BoatFly)this.module).tickCount = 0;
        }
    }
}
