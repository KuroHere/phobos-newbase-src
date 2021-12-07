//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.packetfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.core.mixins.network.server.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.movement.packetfly.util.*;
import me.earth.earthhack.impl.util.network.*;

final class ListenerPosLook extends ModuleListener<PacketFly, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPosLook(final PacketFly module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        final ISPacketPlayerPosLook packet = event.getPacket();
        if (ListenerPosLook.mc.player.isEntityAlive() && ((PacketFly)this.module).mode.getValue() != Mode.Setback && ((PacketFly)this.module).mode.getValue() != Mode.Slow && !(ListenerPosLook.mc.currentScreen instanceof GuiDownloadTerrain) && ListenerPosLook.mc.world.isBlockLoaded(new BlockPos((Entity)ListenerPosLook.mc.player), false)) {
            final TimeVec vec = ((PacketFly)this.module).posLooks.remove(packet.getTeleportId());
            if (vec != null && vec.xCoord == packet.getX() && vec.yCoord == packet.getY() && vec.zCoord == packet.getZ()) {
                event.setCancelled(true);
                return;
            }
        }
        ((PacketFly)this.module).teleportID.set(packet.getTeleportId());
        if (((PacketFly)this.module).answer.getValue()) {
            event.setCancelled(true);
            ListenerPosLook.mc.addScheduledTask(() -> PacketUtil.handlePosLook((SPacketPlayerPosLook)event.getPacket(), (Entity)ListenerPosLook.mc.player, true, false));
            return;
        }
        packet.setYaw(ListenerPosLook.mc.player.rotationYaw);
        packet.setPitch(ListenerPosLook.mc.player.rotationPitch);
    }
}
