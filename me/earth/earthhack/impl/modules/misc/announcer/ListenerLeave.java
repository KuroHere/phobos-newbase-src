// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.misc.announcer.util.*;

final class ListenerLeave extends ModuleListener<Announcer, ConnectionEvent.Leave>
{
    public ListenerLeave(final Announcer module) {
        super(module, (Class<? super Object>)ConnectionEvent.Leave.class);
    }
    
    public void invoke(final ConnectionEvent.Leave event) {
        if (((Announcer)this.module).leave.getValue()) {
            ((Announcer)this.module).addWordAndIncrement(AnnouncementType.Leave, event.getName());
        }
    }
}
