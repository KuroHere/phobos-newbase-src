//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;

final class ListenerDeath extends ModuleListener<Packets, DeathEvent>
{
    public ListenerDeath(final Packets module) {
        super(module, (Class<? super Object>)DeathEvent.class, Integer.MAX_VALUE);
    }
    
    public void invoke(final DeathEvent event) {
        if (((Packets)this.module).fastDeath.getValue() && !event.getEntity().equals((Object)ListenerDeath.mc.player)) {
            Managers.SET_DEAD.setDead((Entity)event.getEntity());
        }
    }
}
