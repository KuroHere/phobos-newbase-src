//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.pingspoof;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.network.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.client.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import java.util.concurrent.*;

public class PingSpoof extends Module
{
    private final ScheduledExecutorService service;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> keepAlive;
    protected final Setting<Boolean> transactions;
    protected final Setting<Boolean> resources;
    protected final Queue<Packet<?>> packets;
    protected final Set<Short> transactionIDs;
    
    public PingSpoof() {
        super("PingSpoof", Category.Misc);
        this.delay = this.register(new NumberSetting("Delay", 100, 1, 5000));
        this.keepAlive = this.register(new BooleanSetting("KeepAlive", true));
        this.transactions = this.register(new BooleanSetting("Transactions", false));
        this.resources = this.register(new BooleanSetting("Resources", false));
        this.packets = new ConcurrentLinkedQueue<Packet<?>>();
        this.transactionIDs = new HashSet<Short>();
        this.service = ThreadUtil.newDaemonScheduledExecutor("PingSpoof");
        Bus.EVENT_BUS.register(new EventListener<ShutDownEvent>(ShutDownEvent.class) {
            @Override
            public void invoke(final ShutDownEvent event) {
                PingSpoof.this.service.shutdown();
            }
        });
        this.listeners.add(new ListenerKeepAlive(this));
        this.listeners.add(new ListenerLogout(this));
        this.listeners.add(new ListenerTransaction(this));
        this.listeners.add(new ListenerClickWindow(this));
        this.listeners.add(new ListenerResources(this));
        this.setData(new PingSpoofData(this));
    }
    
    @Override
    protected void onDisable() {
        this.clearPackets(true);
    }
    
    public int getDelay() {
        return this.delay.getValue();
    }
    
    protected void clearPackets(final boolean send) {
        this.transactionIDs.clear();
        CollectionUtil.emptyQueue(this.packets, packet -> {
            if (send) {
                NetworkUtil.sendPacketNoEvent((Packet<?>)packet);
            }
        });
    }
    
    protected void onPacket(final Packet<?> packet) {
        this.packets.add(packet);
        this.service.schedule(() -> {
            if (PingSpoof.mc.player != null) {
                final Packet<?> p = this.packets.poll();
                if (p != null) {
                    NetworkUtil.sendPacketNoEvent(p);
                }
            }
        }, this.delay.getValue(), TimeUnit.MILLISECONDS);
    }
}
