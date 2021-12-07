//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerInput extends ModuleListener<Phase, MovementInputEvent>
{
    public ListenerInput(final Phase module) {
        super(module, (Class<? super Object>)MovementInputEvent.class);
    }
    
    public void invoke(final MovementInputEvent event) {
        if (((Phase)this.module).autoSneak.getValue()) {
            event.getInput().sneak = (!((Phase)this.module).requireForward.getValue() || ListenerInput.mc.gameSettings.keyBindForward.isKeyDown());
        }
    }
}
