//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.*;
import net.minecraft.entity.player.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.event.bus.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.util.math.*;

public class CombatManager extends SubscriberImpl implements Globals
{
    private final Map<EntityPlayer, PopCounter> pops;
    
    public CombatManager() {
        this.pops = new ConcurrentHashMap<EntityPlayer, PopCounter>();
        this.listeners.add(new EventListener<DeathEvent>(DeathEvent.class, Integer.MIN_VALUE) {
            @Override
            public void invoke(final DeathEvent event) {
                CombatManager.this.onDeath((Entity)event.getEntity());
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketEntityStatus>>(PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketEntityStatus.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketEntityStatus> event) {
                switch (event.getPacket().getOpCode()) {
                    case 3: {
                        Globals.mc.addScheduledTask(() -> CombatManager.this.onDeath((Globals.mc.world == null) ? null : ((SPacketEntityStatus)event.getPacket()).getEntity((World)Globals.mc.world)));
                        break;
                    }
                    case 35: {
                        Globals.mc.addScheduledTask(() -> CombatManager.this.onTotemPop((SPacketEntityStatus)event.getPacket()));
                        break;
                    }
                }
            }
        });
    }
    
    public void reset() {
        this.pops.clear();
    }
    
    public int getPops(final Entity player) {
        if (player instanceof EntityPlayer) {
            final PopCounter popCounter = this.pops.get(player);
            if (popCounter != null) {
                return popCounter.getPops();
            }
        }
        return 0;
    }
    
    public long lastPop(final Entity player) {
        if (player instanceof EntityPlayer) {
            final PopCounter popCounter = this.pops.get(player);
            if (popCounter != null) {
                return popCounter.lastPop();
            }
        }
        return 2147483647L;
    }
    
    private void onTotemPop(final SPacketEntityStatus packet) {
        final Entity player = packet.getEntity((World)CombatManager.mc.world);
        if (player instanceof EntityPlayer) {
            this.pops.computeIfAbsent((EntityPlayer)player, v -> new PopCounter()).pop();
            final TotemPopEvent totemPopEvent = new TotemPopEvent((EntityPlayer)player);
            Bus.EVENT_BUS.post(totemPopEvent);
        }
    }
    
    private void onDeath(final Entity entity) {
        if (entity instanceof EntityPlayer) {
            this.pops.remove(entity);
        }
    }
    
    private static class PopCounter
    {
        private final StopWatch timer;
        private int pops;
        
        private PopCounter() {
            this.timer = new StopWatch();
        }
        
        public int getPops() {
            return this.pops;
        }
        
        public void pop() {
            this.timer.reset();
            ++this.pops;
        }
        
        public void reset() {
            this.pops = 0;
        }
        
        public long lastPop() {
            return this.timer.getTime();
        }
    }
}
