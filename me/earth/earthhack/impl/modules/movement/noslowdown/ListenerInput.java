//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.noslowdown;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import net.minecraft.util.*;

final class ListenerInput extends ModuleListener<NoSlowDown, MovementInputEvent>
{
    public ListenerInput(final NoSlowDown module) {
        super(module, (Class<? super Object>)MovementInputEvent.class);
    }
    
    public void invoke(final MovementInputEvent event) {
        final MovementInput input = event.getInput();
        if (((NoSlowDown)this.module).items.getValue() && ((NoSlowDown)this.module).input.getValue() && input == ListenerInput.mc.player.movementInput && ListenerInput.mc.player.isHandActive() && !ListenerInput.mc.player.isRiding()) {
            final MovementInput movementInput = input;
            movementInput.moveStrafe /= 0.2f;
            final MovementInput movementInput2 = input;
            movementInput2.field_192832_b /= 0.2f;
        }
    }
}
