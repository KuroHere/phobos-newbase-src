//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.boatfly;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;

final class ListenerGameLoop extends ModuleListener<BoatFly, GameLoopEvent>
{
    public ListenerGameLoop(final BoatFly module) {
        super(module, (Class<? super Object>)GameLoopEvent.class);
    }
    
    public void invoke(final GameLoopEvent event) {
        if (ListenerGameLoop.mc.player == null) {
            return;
        }
        final Entity riding = ListenerGameLoop.mc.player.getRidingEntity();
        if (riding == null) {
            return;
        }
        if (ListenerGameLoop.mc.player.equals((Object)riding.getControllingPassenger())) {
            riding.motionY = (ListenerGameLoop.mc.player.movementInput.jump ? ((BoatFly)this.module).upSpeed.getValue() : (KeyBoardUtil.isKeyDown(((BoatFly)this.module).downBind) ? (-((BoatFly)this.module).downSpeed.getValue()) : ((double)(float)((BoatFly)this.module).glide.getValue())));
            if (((BoatFly)this.module).fixYaw.getValue()) {
                riding.rotationYaw = ListenerGameLoop.mc.player.rotationYaw;
            }
        }
    }
}
