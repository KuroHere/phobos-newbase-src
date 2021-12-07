//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.logoutspots;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.render.logoutspots.mode.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.render.logoutspots.util.*;
import net.minecraft.entity.player.*;

final class ListenerLeave extends ModuleListener<LogoutSpots, ConnectionEvent.Leave>
{
    public ListenerLeave(final LogoutSpots module) {
        super(module, (Class<? super Object>)ConnectionEvent.Leave.class);
    }
    
    public void invoke(final ConnectionEvent.Leave event) {
        final EntityPlayer player = event.getPlayer();
        if (((LogoutSpots)this.module).message.getValue() != MessageMode.None) {
            String text = null;
            if (player != null) {
                text = String.format("§e" + player.getName() + "§c" + " just logged out, at: %sx, %sy, %sz.", MathUtil.round(player.posX, 1), MathUtil.round(player.posY, 1), MathUtil.round(player.posZ, 1));
            }
            else if (((LogoutSpots)this.module).message.getValue() != MessageMode.Render) {
                text = "§e" + event.getName() + "§c" + " just logged out.";
            }
            if (text != null) {
                Managers.CHAT.sendDeleteMessageScheduled(text, event.getUuid().toString(), 2000);
            }
        }
        if (player != null && (((LogoutSpots)this.module).friends.getValue() || !Managers.FRIENDS.contains(player))) {
            final LogoutSpot spot = new LogoutSpot(player);
            ((LogoutSpots)this.module).spots.put(player.getUniqueID(), spot);
        }
    }
}
