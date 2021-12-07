//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.arrows;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.item.*;

final class ListenerUseItem extends ModuleListener<Arrows, RightClickItemEvent>
{
    public ListenerUseItem(final Arrows module) {
        super(module, (Class<? super Object>)RightClickItemEvent.class);
    }
    
    public void invoke(final RightClickItemEvent event) {
        if (ListenerUseItem.mc.player.getHeldItem(event.getHand()).getItem() instanceof ItemBow && ((Arrows)this.module).cancelTime.getValue() != 0 && !((Arrows)this.module).timer.passed(((Arrows)this.module).cancelTime.getValue()) && (!((Arrows)this.module).preCycle.getValue() || ((Arrows)this.module).fastCancel.getValue() || !((Arrows)this.module).fast)) {
            event.setCancelled(true);
        }
    }
}
