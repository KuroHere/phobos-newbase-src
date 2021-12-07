//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.jesus;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.movement.*;

final class ListenerLiquidJump extends ModuleListener<Jesus, LiquidJumpEvent>
{
    public ListenerLiquidJump(final Jesus module) {
        super(module, (Class<? super Object>)LiquidJumpEvent.class);
    }
    
    public void invoke(final LiquidJumpEvent event) {
        if (ListenerLiquidJump.mc.player != null && ListenerLiquidJump.mc.player.equals((Object)event.getEntity()) && (ListenerLiquidJump.mc.player.isInWater() || ListenerLiquidJump.mc.player.isInLava()) && (ListenerLiquidJump.mc.player.motionY == 0.1 || ListenerLiquidJump.mc.player.motionY == 0.5)) {
            event.setCancelled(true);
        }
    }
}
