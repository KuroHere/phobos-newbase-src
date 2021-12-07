//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.core.ducks.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;

final class ListenerTick extends ModuleListener<PingBypass, TickEvent>
{
    public ListenerTick(final PingBypass module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (((PingBypass)this.module).timer.passed(((PingBypass)this.module).pings.getValue() * 1000)) {
            final NetHandlerPlayClient connection = ListenerTick.mc.getConnection();
            if (connection != null) {
                final CPacketClickWindow container = new CPacketClickWindow(1, -1337, 1, ClickType.PICKUP, ItemStack.field_190927_a, ((IContainer)ListenerTick.mc.player.openContainer).getTransactionID());
                final CPacketKeepAlive alive = new CPacketKeepAlive(-1337L);
                ((PingBypass)this.module).startTime = System.currentTimeMillis();
                ((PingBypass)this.module).handled = false;
                connection.sendPacket((Packet)container);
                connection.sendPacket((Packet)alive);
            }
            ((PingBypass)this.module).timer.reset();
        }
    }
}
