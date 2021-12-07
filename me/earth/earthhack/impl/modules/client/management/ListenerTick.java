//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.management;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.media.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerTick extends ModuleListener<Management, TickEvent>
{
    private static final ModuleCache<Media> MEDIA;
    
    public ListenerTick(final Management module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (((Management)this.module).friend.getValue() && ((Management)this.module).lastProfile != null && !((Management)this.module).lastProfile.equals((Object)ListenerTick.mc.getSession().getProfile())) {
            ((Management)this.module).lastProfile = ListenerTick.mc.getSession().getProfile();
            Managers.FRIENDS.add(((Management)this.module).lastProfile.getName(), ((Management)this.module).lastProfile.getId());
            ListenerTick.MEDIA.computeIfPresent(Media::reload);
        }
    }
    
    static {
        MEDIA = Caches.getModule(Media.class);
    }
}
