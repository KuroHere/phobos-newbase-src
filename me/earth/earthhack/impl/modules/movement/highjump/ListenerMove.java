//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.highjump;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.managers.*;

final class ListenerMove extends ModuleListener<HighJump, MoveEvent>
{
    public ListenerMove(final HighJump module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (!Managers.NCP.passed(((HighJump)this.module).lagTime.getValue()) || !ListenerMove.mc.player.movementInput.jump || (((HighJump)this.module).onGround.getValue() && !ListenerMove.mc.player.onGround)) {
            return;
        }
        if (((HighJump)this.module).explosions.getValue() || ((HighJump)this.module).velocity.getValue()) {
            if (((HighJump)this.module).motionY < ((HighJump)this.module).minY.getValue()) {
                return;
            }
            if (!((HighJump)this.module).timer.passed(((HighJump)this.module).delay.getValue())) {
                ListenerMove.mc.player.motionY = (((HighJump)this.module).constant.getValue() ? ((HighJump)this.module).height.getValue() : (((HighJump)this.module).motionY * ((HighJump)this.module).factor.getValue()));
                if (ListenerMove.mc.player.motionY < 0.41999998688697815) {
                    ListenerMove.mc.player.motionY = 0.41999998688697815;
                }
                event.setY(ListenerMove.mc.player.motionY);
                return;
            }
        }
        if (((HighJump)this.module).onlySpecial.getValue()) {
            return;
        }
        event.setY(ListenerMove.mc.player.motionY = ((HighJump)this.module).height.getValue());
    }
}
