//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.highjump;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerInput extends ModuleListener<HighJump, MovementInputEvent>
{
    public ListenerInput(final HighJump module) {
        super(module, (Class<? super Object>)MovementInputEvent.class);
    }
    
    public void invoke(final MovementInputEvent event) {
        if (((HighJump)this.module).onlySpecial.getValue() && (((HighJump)this.module).explosions.getValue() || ((HighJump)this.module).velocity.getValue()) && ((HighJump)this.module).cancelJump.getValue() && ((HighJump)this.module).motionY < ((HighJump)this.module).minY.getValue()) {
            event.getInput().jump = false;
        }
    }
}
