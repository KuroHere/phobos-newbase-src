//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.timer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.modules.player.timer.mode.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.util.network.*;

final class ListenerMotion extends ModuleListener<Timer, MotionUpdateEvent>
{
    private float offset;
    
    public ListenerMotion(final Timer module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, -500000);
        this.offset = 4.0E-4f;
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            if (((Timer)this.module).mode.getValue() == TimerMode.Blink && event.getPitch() - ((IEntityPlayerSP)ListenerMotion.mc.player).getLastReportedPitch() == 0.0 && event.getYaw() - ((IEntityPlayerSP)ListenerMotion.mc.player).getLastReportedYaw() == 0.0) {
                this.offset = -this.offset;
                event.setYaw(event.getYaw() + this.offset);
                event.setPitch(event.getPitch() + this.offset);
            }
        }
        else if (event.getStage() == Stage.POST) {
            if (((Timer)this.module).autoOff.getValue() != 0 && ((Timer)this.module).offTimer.passed(((Timer)this.module).autoOff.getValue())) {
                ((Timer)this.module).disable();
                return;
            }
            if (((Timer)this.module).ticks < ((Timer)this.module).updates.getValue() && ((Timer)this.module).mode.getValue() == TimerMode.Physics) {
                final Timer timer = (Timer)this.module;
                ++timer.ticks;
                PhysicsUtil.runPhysicsTick();
            }
            else {
                ((Timer)this.module).ticks = 0;
            }
        }
    }
}
