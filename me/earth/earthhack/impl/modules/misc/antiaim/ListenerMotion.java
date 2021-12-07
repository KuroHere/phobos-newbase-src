//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antiaim;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import java.util.*;
import me.earth.earthhack.api.event.events.*;
import java.util.concurrent.*;

final class ListenerMotion extends ModuleListener<AntiAim, MotionUpdateEvent>
{
    private static final Random RANDOM;
    private int skip;
    
    public ListenerMotion(final AntiAim module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, 2147482647);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.POST || ((AntiAim)this.module).dontRotate()) {
            return;
        }
        if (((AntiAim)this.module).sneak.getValue() && !ListenerMotion.mc.gameSettings.keyBindSneak.isKeyDown() && ((AntiAim)this.module).timer.passed(((AntiAim)this.module).sneakDelay.getValue())) {
            ListenerMotion.mc.player.setSneaking(!ListenerMotion.mc.player.isSneaking());
            ((AntiAim)this.module).timer.reset();
        }
        if (((AntiAim)this.module).skip.getValue() != 1 && this.skip++ % ((AntiAim)this.module).skip.getValue() == 0) {
            event.setYaw(((AntiAim)this.module).lastYaw);
            event.setPitch(((AntiAim)this.module).lastPitch);
            return;
        }
        switch (((AntiAim)this.module).mode.getValue()) {
            case Random: {
                ((AntiAim)this.module).lastYaw = (float)ThreadLocalRandom.current().nextDouble(-180.0, 180.0);
                ((AntiAim)this.module).lastPitch = -90.0f + ListenerMotion.RANDOM.nextFloat() * 180.0f;
                break;
            }
            case Spin: {
                ((AntiAim)this.module).lastYaw = (((AntiAim)this.module).lastYaw + ((AntiAim)this.module).hSpeed.getValue()) % 360.0f;
                ((AntiAim)this.module).lastPitch += ((AntiAim)this.module).vSpeed.getValue();
                break;
            }
            case Down: {
                ((AntiAim)this.module).lastYaw = event.getYaw();
                ((AntiAim)this.module).lastPitch = 90.0f;
                break;
            }
            case Headbang: {
                ((AntiAim)this.module).lastYaw = event.getYaw();
                ((AntiAim)this.module).lastPitch += ((AntiAim)this.module).vSpeed.getValue();
                break;
            }
            case Horizontal: {
                ((AntiAim)this.module).lastPitch = event.getPitch();
                ((AntiAim)this.module).lastYaw = (((AntiAim)this.module).lastYaw + ((AntiAim)this.module).hSpeed.getValue()) % 360.0f;
                break;
            }
            case Constant: {
                event.setYaw(((AntiAim)this.module).yaw.getValue());
                event.setPitch(((AntiAim)this.module).pitch.getValue());
                return;
            }
        }
        if (((AntiAim)this.module).lastPitch > 90.0f && ((AntiAim)this.module).lastPitch != event.getPitch()) {
            ((AntiAim)this.module).lastPitch = -90.0f;
        }
        event.setYaw(((AntiAim)this.module).lastYaw);
        event.setPitch(((AntiAim)this.module).lastPitch);
    }
    
    static {
        RANDOM = new Random();
    }
}
