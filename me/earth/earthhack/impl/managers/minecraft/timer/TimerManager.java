//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.timer;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.timer.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.core.mixins.util.*;
import me.earth.earthhack.impl.core.ducks.*;
import me.earth.earthhack.impl.modules.*;

public class TimerManager extends SubscriberImpl implements Globals
{
    private static final ModuleCache<Timer> MODULE;
    private float speed;
    
    public TimerManager() {
        this.listeners.add(new EventListener<TickEvent>(TickEvent.class) {
            @Override
            public void invoke(final TickEvent event) {
                if (Globals.mc.player == null) {
                    TimerManager.this.reset();
                }
                else {
                    TimerManager.this.update();
                }
            }
        });
    }
    
    private void update() {
        if (TimerManager.MODULE.isEnabled()) {
            ((ITimer)((IMinecraft)TimerManager.mc).getTimer()).setTickLength(50.0f / TimerManager.MODULE.get().getSpeed());
        }
        else {
            ((ITimer)((IMinecraft)TimerManager.mc).getTimer()).setTickLength(50.0f / this.speed);
        }
    }
    
    public void setTimer(final float speed) {
        this.speed = ((speed <= 0.0f) ? 0.1f : speed);
    }
    
    public float getSpeed() {
        return this.speed;
    }
    
    public void reset() {
        this.speed = 1.0f;
    }
    
    static {
        MODULE = Caches.getModule(Timer.class);
    }
}
