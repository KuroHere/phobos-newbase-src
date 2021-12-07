// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.listeners;

import net.minecraft.network.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.bus.api.*;

public class PostSendListener<P extends Packet<?>> extends LambdaListener<PacketEvent.Post<P>>
{
    public PostSendListener(final Class<P> target, final Invoker<PacketEvent.Post<P>> invoker) {
        this(target, 10, invoker);
    }
    
    public PostSendListener(final Class<P> target, final int priority, final Invoker<PacketEvent.Post<P>> invoker) {
        super(PacketEvent.Post.class, priority, target, invoker);
    }
}
