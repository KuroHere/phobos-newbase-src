//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.noslowdown;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.core.ducks.entity.*;

final class ListenerMove extends ModuleListener<NoSlowDown, MoveEvent>
{
    public ListenerMove(final NoSlowDown module) {
        super(module, (Class<? super Object>)MoveEvent.class);
    }
    
    public void invoke(final MoveEvent event) {
        if (Managers.NCP.passed(250) && ((IEntity)ListenerMove.mc.player).inWeb()) {
            if (ListenerMove.mc.player.onGround) {
                event.setX(event.getX() * ((NoSlowDown)this.module).websXZ.getValue());
                event.setZ(event.getZ() * ((NoSlowDown)this.module).websXZ.getValue());
            }
            else if (ListenerMove.mc.player.movementInput.sneak || !((NoSlowDown)this.module).sneak.getValue()) {
                event.setY(event.getY() * ((NoSlowDown)this.module).websY.getValue());
            }
        }
    }
}
