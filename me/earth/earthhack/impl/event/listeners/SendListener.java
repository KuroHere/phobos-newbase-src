// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.listeners;

import net.minecraft.network.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.bus.api.*;

public class SendListener<P extends Packet<?>> extends LambdaListener<PacketEvent.Send<P>>
{
    public SendListener(final Class<P> target, final Invoker<PacketEvent.Send<P>> invoker) {
        this(target, 10, invoker);
    }
    
    public SendListener(final Class<P> target, final int priority, final Invoker<PacketEvent.Send<P>> invoker) {
        super(PacketEvent.Send.class, priority, target, invoker);
    }
}
