// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.network;

import net.minecraft.network.*;
import me.earth.earthhack.api.event.events.*;
import java.util.*;
import me.earth.earthhack.impl.util.thread.*;

public class PacketEvent<T extends Packet<? extends INetHandler>> extends Event
{
    private final T packet;
    
    private PacketEvent(final T packet) {
        this.packet = packet;
    }
    
    public T getPacket() {
        return this.packet;
    }
    
    public static class Send<T extends Packet<? extends INetHandler>> extends PacketEvent<T>
    {
        public Send(final T packet) {
            super(packet, null);
        }
    }
    
    public static class NoEvent<T extends Packet<? extends INetHandler>> extends PacketEvent<T>
    {
        private final boolean post;
        
        public NoEvent(final T packet, final boolean post) {
            super(packet, null);
            this.post = post;
        }
        
        public boolean hasPost() {
            return this.post;
        }
    }
    
    public static class Receive<T extends Packet<? extends INetHandler>> extends PacketEvent<T>
    {
        private final Deque<Runnable> postEvents;
        
        public Receive(final T packet) {
            super(packet, null);
            this.postEvents = new ArrayDeque<Runnable>();
        }
        
        public void addPostEvent(final SafeRunnable runnable) {
            this.postEvents.add(runnable);
        }
        
        public Deque<Runnable> getPostEvents() {
            return this.postEvents;
        }
    }
    
    public static class Post<T extends Packet<? extends INetHandler>> extends PacketEvent<T>
    {
        public Post(final T packet) {
            super(packet, null);
        }
    }
}
