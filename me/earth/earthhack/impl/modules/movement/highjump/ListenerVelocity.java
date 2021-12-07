//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.highjump;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.entity.*;

final class ListenerVelocity extends ModuleListener<HighJump, PacketEvent.Receive<SPacketEntityVelocity>>
{
    public ListenerVelocity(final HighJump module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityVelocity.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityVelocity> event) {
        final EntityPlayerSP player = ListenerVelocity.mc.player;
        final SPacketEntityVelocity packet = event.getPacket();
        if (((HighJump)this.module).velocity.getValue() && player != null && player.getEntityId() == packet.getEntityID()) {
            final double y = packet.getMotionY() / 8000.0;
            ((HighJump)this.module).addVelocity(y);
        }
    }
}
