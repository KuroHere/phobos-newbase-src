// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft;

import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.event.bus.*;
import java.util.*;

public class TPSManager extends SubscriberImpl
{
    private final ArrayDeque<Float> queue;
    private long time;
    private float tps;
    
    public TPSManager() {
        this.queue = new ArrayDeque<Float>(20);
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketTimeUpdate>>(PacketEvent.Receive.class, SPacketTimeUpdate.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketTimeUpdate> event) {
                if (TPSManager.this.time != 0L) {
                    if (TPSManager.this.queue.size() > 20) {
                        TPSManager.this.queue.poll();
                    }
                    TPSManager.this.queue.add(20.0f * (1000.0f / (System.currentTimeMillis() - TPSManager.this.time)));
                    float factor = 0.0f;
                    for (final Float qTime : TPSManager.this.queue) {
                        factor += Math.max(0.0f, Math.min(20.0f, qTime));
                    }
                    factor /= TPSManager.this.queue.size();
                    TPSManager.this.tps = factor;
                }
                TPSManager.this.time = System.currentTimeMillis();
            }
        });
    }
    
    public float getTps() {
        return this.tps;
    }
    
    public float getFactor() {
        return this.tps / 20.0f;
    }
}
