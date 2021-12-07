//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.breadcrumbs;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerDeath extends ModuleListener<BreadCrumbs, DeathEvent>
{
    public ListenerDeath(final BreadCrumbs module) {
        super(module, (Class<? super Object>)DeathEvent.class);
    }
    
    public void invoke(final DeathEvent event) {
        if (((BreadCrumbs)this.module).clearD.getValue() && event.getEntity() != null && event.getEntity().equals((Object)ListenerDeath.mc.player)) {
            ListenerDeath.mc.addScheduledTask(((BreadCrumbs)this.module).positions::clear);
        }
    }
}
