//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.antimove;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.modules.movement.antimove.modes.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

final class ListenerMotion extends ModuleListener<NoMove, MotionUpdateEvent>
{
    public ListenerMotion(final NoMove module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE && ((NoMove)this.module).mode.getValue() == StaticMode.Roof) {
            ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(ListenerMotion.mc.player.posX, 10000.0, ListenerMotion.mc.player.posZ, ListenerMotion.mc.player.onGround));
            ((NoMove)this.module).disable();
        }
    }
}
