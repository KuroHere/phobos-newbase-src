//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.entitycontrol;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.core.ducks.entity.*;

final class ListenerTick extends ModuleListener<EntityControl, TickEvent>
{
    public ListenerTick(final EntityControl module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (event.isSafe() && ((EntityControl)this.module).jumpHeight.getValue() > 0.0) {
            ((IEntityPlayerSP)ListenerTick.mc.player).setHorseJumpPower(1.0f);
        }
    }
}
