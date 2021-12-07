//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nointeract;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.block.state.*;

final class ListenerInteract extends ModuleListener<NoInteract, ClickBlockEvent.Right>
{
    public ListenerInteract(final NoInteract module) {
        super(module, (Class<? super Object>)ClickBlockEvent.Right.class);
    }
    
    public void invoke(final ClickBlockEvent.Right event) {
        if (((NoInteract)this.module).sneak.getValue() && Managers.ACTION.isSneaking()) {
            return;
        }
        final IBlockState state = ListenerInteract.mc.world.getBlockState(event.getPos());
        if (((NoInteract)this.module).isValid(state.getBlock().getLocalizedName())) {
            event.setCancelled(true);
        }
    }
}
