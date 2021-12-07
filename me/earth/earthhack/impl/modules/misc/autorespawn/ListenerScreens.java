//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autorespawn;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.text.*;

final class ListenerScreens extends ModuleListener<AutoRespawn, GuiScreenEvent<GuiGameOver>>
{
    public ListenerScreens(final AutoRespawn module) {
        super(module, (Class<? super Object>)GuiScreenEvent.class, GuiGameOver.class);
    }
    
    public void invoke(final GuiScreenEvent<GuiGameOver> event) {
        if (ListenerScreens.mc.player != null) {
            if (((AutoRespawn)this.module).coords.getValue()) {
                ChatUtil.sendMessage("§cYou died at §f" + MathUtil.round(ListenerScreens.mc.player.posX, 2) + "§c" + "x, " + "§f" + MathUtil.round(ListenerScreens.mc.player.posY, 2) + "§c" + "y, " + "§f" + MathUtil.round(ListenerScreens.mc.player.posZ, 2) + "§c" + "z.");
            }
            ListenerScreens.mc.player.respawnPlayer();
            event.setCancelled(true);
        }
    }
}
