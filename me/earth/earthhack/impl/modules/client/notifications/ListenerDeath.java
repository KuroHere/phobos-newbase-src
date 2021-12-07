// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.notifications;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;

final class ListenerDeath extends ModuleListener<Notifications, DeathEvent>
{
    public ListenerDeath(final Notifications module) {
        super(module, (Class<? super Object>)DeathEvent.class);
    }
    
    public void invoke(final DeathEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            final int pops = Managers.COMBAT.getPops((Entity)event.getEntity());
            if (pops > 0) {
                ((Notifications)this.module).onDeath((Entity)event.getEntity(), Managers.COMBAT.getPops((Entity)event.getEntity()));
            }
        }
    }
}
