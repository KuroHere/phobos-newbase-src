//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.aimbot;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.item.*;

final class ListenerGameLoop extends ModuleListener<AimBot, GameLoopEvent>
{
    public ListenerGameLoop(final AimBot module) {
        super(module, (Class<? super Object>)GameLoopEvent.class);
    }
    
    public void invoke(final GameLoopEvent event) {
        if (((AimBot)this.module).target != null && !((AimBot)this.module).silent.getValue() && ListenerGameLoop.mc.player.getActiveItemStack().getItem() instanceof ItemBow) {
            ListenerGameLoop.mc.player.rotationYaw = ((AimBot)this.module).yaw;
            ListenerGameLoop.mc.player.rotationPitch = ((AimBot)this.module).pitch;
        }
    }
}
