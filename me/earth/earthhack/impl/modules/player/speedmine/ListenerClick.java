//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import me.earth.earthhack.impl.core.ducks.network.*;

final class ListenerClick extends ModuleListener<Speedmine, ClickBlockEvent>
{
    public ListenerClick(final Speedmine module) {
        super(module, (Class<? super Object>)ClickBlockEvent.class);
    }
    
    public void invoke(final ClickBlockEvent event) {
        if (!PlayerUtil.isCreative((EntityPlayer)ListenerClick.mc.player) && (((Speedmine)this.module).noReset.getValue() || ((Speedmine)this.module).mode.getValue() == MineMode.Reset) && ((IPlayerControllerMP)ListenerClick.mc.playerController).getCurBlockDamageMP() > 0.1f) {
            ((IPlayerControllerMP)ListenerClick.mc.playerController).setIsHittingBlock(true);
        }
    }
}
