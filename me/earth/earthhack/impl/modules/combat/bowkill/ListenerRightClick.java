//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bowkill;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.init.*;

final class ListenerRightClick extends ModuleListener<BowKiller, RightClickItemEvent>
{
    public ListenerRightClick(final BowKiller module) {
        super(module, (Class<? super Object>)RightClickItemEvent.class);
    }
    
    public void invoke(final RightClickItemEvent event) {
        if (!ListenerRightClick.mc.player.isCollidedVertically) {
            return;
        }
        if (ListenerRightClick.mc.player.getHeldItem(event.getHand()).getItem() == Items.BOW && ((BowKiller)this.module).blockUnder) {
            ((BowKiller)this.module).cancelling = true;
            ((BowKiller)this.module).needsMessage = true;
        }
    }
}
