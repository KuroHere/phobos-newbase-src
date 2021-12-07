//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.timer;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.util.network.*;

public class PhysicsManager extends SubscriberImpl implements Globals
{
    private final StopWatch timer;
    private boolean blocking;
    private int delay;
    private int times;
    
    public PhysicsManager() {
        this.timer = new StopWatch();
        this.listeners.add(new EventListener<GameLoopEvent>(GameLoopEvent.class) {
            @Override
            public void invoke(final GameLoopEvent event) {
                if (Globals.mc.player == null) {
                    PhysicsManager.this.times = 0;
                    return;
                }
                if (PhysicsManager.this.times > 0 && PhysicsManager.this.timer.passed(PhysicsManager.this.delay)) {
                    PhysicsManager.this.blocking = true;
                    while (PhysicsManager.this.times > 0) {
                        PhysicsManager.this.invokePhysics();
                        if (PhysicsManager.this.delay != 0) {
                            break;
                        }
                        PhysicsManager.this.times--;
                    }
                    PhysicsManager.this.blocking = false;
                    PhysicsManager.this.timer.reset();
                }
            }
        });
        this.listeners.add(new EventListener<DisconnectEvent>(DisconnectEvent.class) {
            @Override
            public void invoke(final DisconnectEvent event) {
                PhysicsManager.this.times = 0;
            }
        });
        this.listeners.add(new EventListener<WorldClientEvent.Load>(WorldClientEvent.Load.class) {
            @Override
            public void invoke(final WorldClientEvent.Load event) {
                PhysicsManager.this.times = 0;
            }
        });
    }
    
    public void invokePhysics(final int times, final int delay) {
        if (!this.blocking) {
            this.times = times;
            this.delay = delay;
        }
    }
    
    public void invokePhysics() {
        PhysicsUtil.runPhysicsTick();
    }
}
