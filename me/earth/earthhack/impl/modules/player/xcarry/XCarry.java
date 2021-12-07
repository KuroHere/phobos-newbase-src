//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.xcarry;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class XCarry extends Module
{
    public XCarry() {
        super("XCarry", Category.Player);
        this.listeners.add(new ListenerCloseWindow(this));
        this.setData(new SimpleData(this, "Allows you to store items in your crafting inventory and drag slot."));
    }
    
    @Override
    protected void onDisable() {
        if (XCarry.mc.player != null) {
            XCarry.mc.player.connection.sendPacket((Packet)new CPacketCloseWindow(XCarry.mc.player.inventoryContainer.windowId));
        }
    }
}
