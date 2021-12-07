// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.misc.packets.util.*;

final class ListenerWorldClient extends ModuleListener<Packets, WorldClientEvent.Load>
{
    public ListenerWorldClient(final Packets module) {
        super(module, (Class<? super Object>)WorldClientEvent.Load.class);
    }
    
    public void invoke(final WorldClientEvent.Load event) {
        ((Packets)this.module).bookCrash.setValue(BookCrashMode.None);
        ((Packets)this.module).offhandCrashes.setValue(0);
    }
}
