//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.movement;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.util.math.*;

public class SpeedManager extends SubscriberImpl implements Globals
{
    private final StopWatch timer;
    private Vec3d last;
    private double speed;
    
    public SpeedManager() {
        this.timer = new StopWatch();
        this.last = new Vec3d(0.0, 0.0, 0.0);
        this.speed = 0.0;
        this.listeners.add(new EventListener<TickEvent>(TickEvent.class) {
            @Override
            public void invoke(final TickEvent event) {
                if (event.isSafe() && SpeedManager.this.timer.passed(40L)) {
                    SpeedManager.this.speed = MathUtil.distance2D(Globals.mc.player.getPositionVector(), SpeedManager.this.last);
                    SpeedManager.this.last = Globals.mc.player.getPositionVector();
                    SpeedManager.this.timer.reset();
                }
            }
        });
    }
    
    public double getSpeed() {
        return this.getSpeedBpS() * 3.6;
    }
    
    public double getSpeedBpS() {
        return this.speed * 20.0;
    }
}
