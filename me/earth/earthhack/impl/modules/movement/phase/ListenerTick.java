//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.movement.phase.mode.*;

public class ListenerTick extends ModuleListener<Phase, TickEvent>
{
    public ListenerTick(final Phase module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (((Phase)this.module).mode.getValue() != PhaseMode.ConstantiamNew || ListenerTick.mc.player.isCollidedHorizontally) {}
    }
}
