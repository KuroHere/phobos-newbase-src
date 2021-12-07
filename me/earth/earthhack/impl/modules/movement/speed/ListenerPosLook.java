//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.speed;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;

final class ListenerPosLook extends ModuleListener<Speed, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPosLook(final Speed module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        if (ListenerPosLook.mc.player != null) {
            ((Speed)this.module).distance = 0.0;
            ((Speed)this.module).speed = 0.0;
        }
        ((Speed)this.module).speed = 0.0;
        ((Speed)this.module).vanillaStage = 0;
        ((Speed)this.module).onGroundStage = 2;
        ((Speed)this.module).ncpStage = 1;
        ((Speed)this.module).gayStage = 1;
        ((Speed)this.module).vStage = 1;
        ((Speed)this.module).bhopStage = 4;
        ((Speed)this.module).stage = 4;
        ((Speed)this.module).lowStage = 4;
        ((Speed)this.module).constStage = 0;
        Managers.TIMER.setTimer(1.0f);
    }
}
