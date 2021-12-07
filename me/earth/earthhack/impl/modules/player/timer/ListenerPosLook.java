// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.timer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

final class ListenerPosLook extends ModuleListener<Timer, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerPosLook(final Timer module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        ((Timer)this.module).packets = 0;
        ((Timer)this.module).sent = 0;
        ((Timer)this.module).pSpeed = 1.0f;
    }
}
