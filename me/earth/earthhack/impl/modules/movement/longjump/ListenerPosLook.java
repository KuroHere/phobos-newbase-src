//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.longjump;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerPosLook extends ModuleListener<LongJump, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPosLook(final LongJump module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        if (((LongJump)this.module).noKick.getValue()) {
            ListenerPosLook.mc.addScheduledTask((LongJump)this.module::disable);
        }
        ((LongJump)this.module).speed = 0.0;
        ((LongJump)this.module).stage = 0;
        ((LongJump)this.module).airTicks = 0;
        ((LongJump)this.module).groundTicks = 0;
    }
}
