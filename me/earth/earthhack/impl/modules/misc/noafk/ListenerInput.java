//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.noafk;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerInput extends ModuleListener<NoAFK, MovementInputEvent>
{
    public ListenerInput(final NoAFK module) {
        super(module, (Class<? super Object>)MovementInputEvent.class);
    }
    
    public void invoke(final MovementInputEvent event) {
        if (((NoAFK)this.module).sneak.getValue()) {
            if (((NoAFK)this.module).sneak_timer.passed(2000L)) {
                ((NoAFK)this.module).sneaking = !((NoAFK)this.module).sneaking;
                ((NoAFK)this.module).sneak_timer.reset();
            }
            event.getInput().sneak = ((NoAFK)this.module).sneaking;
        }
    }
}
