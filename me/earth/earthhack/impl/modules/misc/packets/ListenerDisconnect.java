// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.misc.packets.util.*;

final class ListenerDisconnect extends ModuleListener<Packets, DisconnectEvent>
{
    public ListenerDisconnect(final Packets module) {
        super(module, (Class<? super Object>)DisconnectEvent.class);
    }
    
    public void invoke(final DisconnectEvent event) {
        ((Packets)this.module).bookCrash.setValue(BookCrashMode.None);
        ((Packets)this.module).offhandCrashes.setValue(0);
    }
}
