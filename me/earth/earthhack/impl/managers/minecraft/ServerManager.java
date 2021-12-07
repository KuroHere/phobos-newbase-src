// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft;

import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.bus.*;

public class ServerManager extends SubscriberImpl
{
    private final StopWatch timer;
    
    public ServerManager() {
        this.timer = new StopWatch();
        this.listeners.add(new EventListener<PacketEvent.Receive<?>>(PacketEvent.Receive.class) {
            @Override
            public void invoke(final PacketEvent.Receive<?> event) {
                ServerManager.this.timer.reset();
            }
        });
    }
    
    public long lastResponse() {
        return this.timer.getTime();
    }
}
