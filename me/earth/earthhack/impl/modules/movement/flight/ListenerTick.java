//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.flight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.movement.flight.mode.*;

final class ListenerTick extends ModuleListener<Flight, TickEvent>
{
    public ListenerTick(final Flight module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (event.isSafe() && ((Flight)this.module).mode.getValue() == FlightMode.AAC && ListenerTick.mc.player.hurtTime == 10.0f) {
            ListenerTick.mc.player.motionY = ((Flight)this.module).aacY.getValue();
        }
    }
}
