//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.replenish;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;

final class ListenerDeath extends ModuleListener<Replenish, DeathEvent>
{
    public ListenerDeath(final Replenish module) {
        super(module, (Class<? super Object>)DeathEvent.class);
    }
    
    public void invoke(final DeathEvent event) {
        if (event.getEntity().equals((Object)ListenerDeath.mc.player)) {
            ((Replenish)this.module).clear();
        }
    }
}
