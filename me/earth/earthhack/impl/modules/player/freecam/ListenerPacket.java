// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.freecam;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.player.freecam.mode.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

final class ListenerPacket extends ModuleListener<Freecam, PacketEvent.Send<?>>
{
    public ListenerPacket(final Freecam module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class);
    }
    
    public void invoke(final PacketEvent.Send<?> event) {
        switch (((Freecam)this.module).mode.getValue()) {
            case Cancel: {
                if (event.getPacket() instanceof CPacketPlayer) {
                    event.setCancelled(true);
                    break;
                }
                break;
            }
            case Spanish: {
                final Packet<?> packet = event.getPacket();
                if (!(packet instanceof CPacketUseEntity) && !(packet instanceof CPacketPlayerTryUseItem) && !(packet instanceof CPacketPlayerTryUseItemOnBlock) && !(packet instanceof CPacketPlayer) && !(packet instanceof CPacketVehicleMove) && !(packet instanceof CPacketChatMessage) && !(packet instanceof CPacketKeepAlive)) {
                    event.setCancelled(true);
                    break;
                }
                break;
            }
        }
    }
}
