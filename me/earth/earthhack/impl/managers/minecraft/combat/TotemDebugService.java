//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.core.mixins.network.server.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.event.listeners.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.tweaker.launch.*;
import me.earth.earthhack.impl.event.events.network.*;

public class TotemDebugService extends SubscriberImpl implements Globals
{
    private volatile long time;
    
    public TotemDebugService() {
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketEntityStatus.class, e -> {
            final EntityPlayer player = (EntityPlayer)TotemDebugService.mc.player;
            final ISPacketEntityStatus packet = (ISPacketEntityStatus)e.getPacket();
            if (player != null && packet.getLogicOpcode() == 35 && packet.getEntityId() == player.getEntityId()) {
                final long t = System.currentTimeMillis();
                Earthhack.getLogger().info("Pop, last pop: " + (t - this.time) + "ms");
                this.time = t;
            }
            return;
        }));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketUpdateHealth.class, e -> {
            if (((SPacketUpdateHealth)e.getPacket()).getHealth() <= 0.0f) {
                final long t3 = System.currentTimeMillis();
                Earthhack.getLogger().info("Death, last pop: " + (t3 - this.time) + "ms");
                this.time = t3;
            }
        }));
    }
    
    public static void trySubscribe(final EventBus eventBus) {
        final Argument<Boolean> a = DevArguments.getInstance().getArgument("totems");
        if (a == null || a.getValue()) {
            Earthhack.getLogger().info("TotemDebugger loaded.");
            eventBus.subscribe(new TotemDebugService());
        }
    }
}
