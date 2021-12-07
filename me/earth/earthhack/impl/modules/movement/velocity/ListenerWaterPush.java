//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.velocity;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerWaterPush extends ModuleListener<Velocity, WaterPushEvent>
{
    public ListenerWaterPush(final Velocity module) {
        super(module, (Class<? super Object>)WaterPushEvent.class);
    }
    
    public void invoke(final WaterPushEvent event) {
        if (((Velocity)this.module).water.getValue() && event.getEntity().equals((Object)ListenerWaterPush.mc.player)) {
            event.setCancelled(true);
        }
    }
}
