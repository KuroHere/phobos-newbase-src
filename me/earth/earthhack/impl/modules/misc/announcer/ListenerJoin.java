//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.misc.announcer.util.*;

final class ListenerJoin extends ModuleListener<Announcer, ConnectionEvent.Join>
{
    public ListenerJoin(final Announcer module) {
        super(module, (Class<? super Object>)ConnectionEvent.Join.class);
    }
    
    public void invoke(final ConnectionEvent.Join event) {
        if (((Announcer)this.module).join.getValue() && !event.getName().equals(ListenerJoin.mc.getSession().getProfile().getName())) {
            ((Announcer)this.module).addWordAndIncrement(AnnouncementType.Join, event.getName());
        }
    }
}
