//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.breadcrumbs;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

final class ListenerLogout extends ModuleListener<BreadCrumbs, DisconnectEvent>
{
    public ListenerLogout(final BreadCrumbs module) {
        super(module, (Class<? super Object>)DisconnectEvent.class);
    }
    
    public void invoke(final DisconnectEvent event) {
        if (((BreadCrumbs)this.module).clearL.getValue()) {
            ListenerLogout.mc.addScheduledTask(((BreadCrumbs)this.module).positions::clear);
        }
    }
}
