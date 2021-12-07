//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.norender;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerAnimation extends ModuleListener<NoRender, PacketEvent.Receive<SPacketAnimation>>
{
    public ListenerAnimation(final NoRender module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketAnimation.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketAnimation> event) {
        if (((NoRender)this.module).critParticles.getValue() && (event.getPacket().getAnimationType() == 4 || event.getPacket().getAnimationType() == 5)) {
            event.setCancelled(true);
        }
    }
}
