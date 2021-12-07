// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.packetfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.modules.movement.packetfly.util.*;
import java.util.concurrent.*;
import java.util.*;

final class ListenerTick extends ModuleListener<PacketFly, ListenerTick>
{
    public ListenerTick(final PacketFly module) {
        super(module, (Class<? super Object>)ListenerTick.class);
    }
    
    public void invoke(final ListenerTick event) {
        ((PacketFly)this.module).posLooks.entrySet().removeIf(entry -> System.currentTimeMillis() - entry.getValue().getTime() > TimeUnit.SECONDS.toMillis(30L));
    }
}
