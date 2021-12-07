//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

final class ListenerDismount extends ModuleListener<BoatFly, PacketEvent.Receive<SPacketSetPassengers>>
{
    public ListenerDismount(final BoatFly module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSetPassengers.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSetPassengers> event) {
        final EntityPlayer player = (EntityPlayer)ListenerDismount.mc.player;
        if (player == null) {
            return;
        }
        final Entity riding = ListenerDismount.mc.player.getRidingEntity();
        if (riding != null && event.getPacket().getEntityId() == riding.getEntityId() && ((BoatFly)this.module).remount.getValue()) {
            event.setCancelled(true);
            if (((BoatFly)this.module).schedule.getValue()) {
                ListenerDismount.mc.addScheduledTask(() -> this.remove((SPacketSetPassengers)event.getPacket(), (Entity)player, riding));
            }
            else {
                this.remove(event.getPacket(), (Entity)player, riding);
            }
        }
    }
    
    private void remove(final SPacketSetPassengers packet, final Entity player, final Entity riding) {
        for (final int id : packet.getPassengerIds()) {
            if (id == player.getEntityId()) {
                if (((BoatFly)this.module).remountPackets.getValue()) {
                    ((BoatFly)this.module).sendPackets(riding);
                }
            }
            else {
                try {
                    final Entity entity = ListenerDismount.mc.world.getEntityByID(id);
                    if (entity != null) {
                        entity.dismountRidingEntity();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
