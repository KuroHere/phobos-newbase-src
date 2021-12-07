//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.logoutspots;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.render.logoutspots.util.*;
import me.earth.earthhack.impl.modules.render.logoutspots.mode.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;

final class ListenerJoin extends ModuleListener<LogoutSpots, ConnectionEvent.Join>
{
    public ListenerJoin(final LogoutSpots module) {
        super(module, (Class<? super Object>)ConnectionEvent.Join.class);
    }
    
    public void invoke(final ConnectionEvent.Join event) {
        if (event.getName().equals(ListenerJoin.mc.getSession().getProfile().getName())) {
            return;
        }
        final LogoutSpot spot = ((LogoutSpots)this.module).spots.remove(event.getUuid());
        if (((LogoutSpots)this.module).message.getValue() != MessageMode.None) {
            String text;
            if (spot != null) {
                final Vec3d pos = spot.rounded();
                text = "§e" + event.getName() + "§c" + " is back at: " + pos.xCoord + "x, " + pos.yCoord + "y, " + pos.zCoord + "z!";
            }
            else {
                final EntityPlayer player = event.getPlayer();
                if (player != null) {
                    text = "§e" + player.getName() + "§a" + " just joined at: %sx, %sy, %sz!";
                    text = String.format(text, MathUtil.round(player.posX, 1), MathUtil.round(player.posY, 1), MathUtil.round(player.posZ, 1));
                }
                else {
                    if (((LogoutSpots)this.module).message.getValue() == MessageMode.Render) {
                        return;
                    }
                    text = "§e" + event.getName() + "§a" + " just joined.";
                }
            }
            Managers.CHAT.sendDeleteMessageScheduled(text, event.getUuid().toString(), 2000);
        }
    }
}
