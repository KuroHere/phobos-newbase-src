// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.listeners;

import net.minecraft.network.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.bus.api.*;

public class ReceiveListener<P extends Packet<?>> extends LambdaListener<PacketEvent.Receive<P>>
{
    public ReceiveListener(final Class<P> target, final Invoker<PacketEvent.Receive<P>> invoker) {
        this(target, 10, invoker);
    }
    
    public ReceiveListener(final Class<P> target, final int priority, final Invoker<PacketEvent.Receive<P>> invoker) {
        super(PacketEvent.Receive.class, priority, target, invoker);
    }
}
