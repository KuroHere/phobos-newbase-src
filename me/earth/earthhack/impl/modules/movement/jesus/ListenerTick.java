//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.jesus;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.movement.jesus.mode.*;
import me.earth.earthhack.impl.util.math.position.*;

final class ListenerTick extends ModuleListener<Jesus, TickEvent>
{
    public ListenerTick(final Jesus module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (((Jesus)this.module).timer.passed(800L) && ListenerTick.mc.player != null && ((Jesus)this.module).mode.getValue() == JesusMode.Solid) {
            if (ListenerTick.mc.player.fallDistance > 3.0f) {
                return;
            }
            if ((ListenerTick.mc.player.isInLava() || ListenerTick.mc.player.isInWater()) && !ListenerTick.mc.player.isSneaking()) {
                ListenerTick.mc.player.motionY = 0.1;
                return;
            }
            if (PositionUtil.inLiquid() && !ListenerTick.mc.player.isSneaking()) {
                ListenerTick.mc.player.motionY = 0.1;
            }
        }
    }
}
