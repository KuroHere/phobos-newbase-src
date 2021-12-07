// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.fastswim;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;

public class ListenerSetback extends ModuleListener<FastSwim, PacketEvent.Receive<SPacketPlayerPosLook>>
{
    public ListenerSetback(final FastSwim module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketPlayerPosLook.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketPlayerPosLook> event) {
        ((FastSwim)this.module).waterSpeed = ((FastSwim)this.module).hWater.getValue();
        ((FastSwim)this.module).lavaSpeed = ((FastSwim)this.module).hLava.getValue();
    }
}
