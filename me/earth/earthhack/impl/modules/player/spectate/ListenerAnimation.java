//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.spectate;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

final class ListenerAnimation extends ModuleListener<Spectate, PacketEvent.Receive<SPacketAnimation>>
{
    public ListenerAnimation(final Spectate module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketAnimation.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketAnimation> event) {
        event.addPostEvent(() -> {
            final EntityPlayer playerSp = (EntityPlayer)ListenerAnimation.mc.player;
            if (playerSp != null && ((Spectate)this.module).spectating) {
                final EntityPlayer player = ((Spectate)this.module).player;
                final SPacketAnimation packet = (SPacketAnimation)event.getPacket();
                if (player != null && packet.getEntityID() == player.getEntityId()) {
                    if (packet.getAnimationType() == 0) {
                        playerSp.swingArm(EnumHand.MAIN_HAND);
                    }
                    else if (packet.getAnimationType() == 3) {
                        playerSp.swingArm(EnumHand.OFF_HAND);
                    }
                }
            }
        });
    }
}
