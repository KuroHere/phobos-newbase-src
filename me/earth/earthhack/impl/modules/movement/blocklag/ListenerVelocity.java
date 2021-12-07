//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.blocklag;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.entity.*;

final class ListenerVelocity extends ModuleListener<BlockLag, PacketEvent.Receive<SPacketEntityVelocity>>
{
    public ListenerVelocity(final BlockLag module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEntityVelocity.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEntityVelocity> event) {
        if (!((BlockLag)this.module).scaleVelocity.getValue()) {
            return;
        }
        final EntityPlayerSP playerSP = ListenerVelocity.mc.player;
        if (playerSP != null && event.getPacket().getEntityID() == playerSP.getEntityId()) {
            ((BlockLag)this.module).motionY = event.getPacket().getMotionY() / 8000.0;
            ((BlockLag)this.module).scaleTimer.reset();
        }
    }
}
