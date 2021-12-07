//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.misc.announcer.util.*;

final class ListenerDeath extends ModuleListener<Announcer, DeathEvent>
{
    public ListenerDeath(final Announcer module) {
        super(module, (Class<? super Object>)DeathEvent.class);
    }
    
    public void invoke(final DeathEvent event) {
        if (((Announcer)this.module).autoEZ.getValue() && ((Announcer)this.module).targets.remove(event.getEntity()) && ListenerDeath.mc.player.getDistanceSqToEntity((Entity)event.getEntity()) <= 144.0) {
            ((Announcer)this.module).announcements.put(AnnouncementType.Death, new Announcement(event.getEntity().getName(), 0));
            ((Announcer)this.module).announcements.put(AnnouncementType.Totems, null);
        }
    }
}
