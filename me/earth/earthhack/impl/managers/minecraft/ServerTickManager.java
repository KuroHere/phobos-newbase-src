//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.event.bus.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.managers.*;
import java.util.*;

public class ServerTickManager extends SubscriberImpl implements Globals
{
    private int serverTicks;
    private Map<BlockPos, Long> timeMap;
    private final Timer serverTickTimer;
    private boolean flag;
    private boolean initialized;
    private final ArrayDeque<Integer> spawnObjectTimes;
    private int averageSpawnObjectTime;
    
    public ServerTickManager() {
        this.timeMap = new HashMap<BlockPos, Long>();
        this.serverTickTimer = new Timer();
        this.flag = true;
        this.initialized = false;
        this.spawnObjectTimes = new ArrayDeque<Integer>();
        this.listeners.add(new EventListener<WorldClientEvent>(WorldClientEvent.class) {
            @Override
            public void invoke(final WorldClientEvent event) {
                if (event.getClient().isRemote) {
                    ServerTickManager.this.reset();
                }
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketSpawnObject>>(PacketEvent.Receive.class, Integer.MAX_VALUE, SPacketSpawnObject.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
                if (Globals.mc.world != null && Globals.mc.world.isRemote) {
                    ServerTickManager.this.onSpawnObject();
                }
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketTimeUpdate>>(PacketEvent.Receive.class, Integer.MAX_VALUE, SPacketTimeUpdate.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketTimeUpdate> event) {
                if (Globals.mc.world != null && Globals.mc.world.isRemote) {
                    ServerTickManager.this.reset();
                }
            }
        });
        this.listeners.add(new EventListener<DisconnectEvent>(DisconnectEvent.class) {
            @Override
            public void invoke(final DisconnectEvent event) {
                ServerTickManager.this.initialized = false;
            }
        });
    }
    
    public int getTickTime() {
        if (this.serverTickTimer.getTime() < 50L) {
            return (int)this.serverTickTimer.getTime();
        }
        return (int)(this.serverTickTimer.getTime() % this.getServerTickLengthMS());
    }
    
    public int getTickTimeAdjusted() {
        final int time = this.getTickTime() + ServerUtil.getPingNoPingSpoof() / 2;
        if (time < this.getServerTickLengthMS()) {
            return time;
        }
        return time % this.getServerTickLengthMS();
    }
    
    public int getTickTimeAdjustedForServerPackets() {
        final int time = this.getTickTime() - ServerUtil.getPingNoPingSpoof() / 2;
        if (time < this.getServerTickLengthMS() && time > 0) {
            return time;
        }
        if (time < 0) {
            return time + this.getServerTickLengthMS();
        }
        return time % this.getServerTickLengthMS();
    }
    
    public void reset() {
        this.serverTickTimer.reset();
        this.serverTickTimer.adjust(ServerUtil.getPingNoPingSpoof() / 2);
        this.initialized = true;
    }
    
    public int getServerTickLengthMS() {
        if (Managers.TPS.getTps() == 0.0f) {
            return 50;
        }
        return (int)(50.0f * (20.0f / Managers.TPS.getTps()));
    }
    
    public void onSpawnObject() {
        final int time = this.getTickTimeAdjustedForServerPackets();
        if (this.spawnObjectTimes.size() > 10) {
            this.spawnObjectTimes.poll();
        }
        this.spawnObjectTimes.add(time);
        int totalTime = 0;
        for (final int spawnTime : this.spawnObjectTimes) {
            totalTime += spawnTime;
        }
        this.averageSpawnObjectTime = totalTime / this.spawnObjectTimes.size();
    }
    
    public int normalize(int toNormalize) {
        while (toNormalize < 0) {
            toNormalize += this.getServerTickLengthMS();
        }
        while (toNormalize > this.getServerTickLengthMS()) {
            toNormalize -= this.getServerTickLengthMS();
        }
        return toNormalize;
    }
    
    public boolean valid(final int currentTime, final int minTime, final int maxTime) {
        if (minTime > maxTime) {
            return currentTime >= minTime || currentTime <= maxTime;
        }
        return currentTime >= minTime && currentTime <= maxTime;
    }
    
    public int getSpawnTime() {
        return this.averageSpawnObjectTime;
    }
}
