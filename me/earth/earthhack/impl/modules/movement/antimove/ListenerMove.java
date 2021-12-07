//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.antimove;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;
import me.earth.earthhack.impl.modules.movement.antimove.modes.*;

final class ListenerMove extends ModuleListener<NoMove, MoveEvent>
{
    public ListenerMove(final NoMove module) {
        super(module, (Class<? super Object>)MoveEvent.class, -1000);
    }
    
    public void invoke(final MoveEvent event) {
        if (((NoMove)this.module).mode.getValue() == StaticMode.Stop) {
            ListenerMove.mc.player.setVelocity(0.0, 0.0, 0.0);
            event.setX(0.0);
            event.setY(0.0);
            event.setZ(0.0);
        }
    }
}
