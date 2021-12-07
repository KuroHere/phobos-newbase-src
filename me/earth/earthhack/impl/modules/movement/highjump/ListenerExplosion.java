//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.highjump;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerExplosion extends ModuleListener<HighJump, PacketEvent.Receive<SPacketExplosion>>
{
    public ListenerExplosion(final HighJump module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketExplosion.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketExplosion> event) {
        if (((HighJump)this.module).explosions.getValue()) {
            final double y = event.getPacket().getMotionY();
            ((HighJump)this.module).addVelocity(y);
        }
    }
}
