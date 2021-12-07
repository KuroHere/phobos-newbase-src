//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.blink;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerPosLook extends ModuleListener<Blink, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPosLook(final Blink module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        if (((Blink)this.module).lagDisable.getValue()) {
            ListenerPosLook.mc.addScheduledTask((Blink)this.module::disable);
        }
    }
}
