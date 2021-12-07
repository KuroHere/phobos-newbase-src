//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.blocktweaks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.core.ducks.network.*;

final class ListenerTick extends ModuleListener<BlockTweaks, TickEvent>
{
    public ListenerTick(final BlockTweaks module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        final IPlayerControllerMP controller = (IPlayerControllerMP)ListenerTick.mc.playerController;
        if (controller != null && controller.getBlockHitDelay() > ((BlockTweaks)this.module).delay.getValue()) {
            controller.setBlockHitDelay(((BlockTweaks)this.module).delay.getValue());
        }
    }
}
