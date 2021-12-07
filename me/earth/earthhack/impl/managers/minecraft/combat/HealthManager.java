// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;

public class HealthManager extends SubscriberImpl implements Globals
{
    private volatile float lastAbsorption;
    private volatile float lastHealth;
    
    public HealthManager() {
        this.lastAbsorption = -1.0f;
        this.lastHealth = -1.0f;
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketUpdateHealth.class, e -> {
            final SPacketUpdateHealth packet = (SPacketUpdateHealth)e.getPacket();
        }));
    }
    
    public float getLastHealth() {
        return this.lastHealth;
    }
}
