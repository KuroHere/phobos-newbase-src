//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.exptweaks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import me.earth.earthhack.api.util.bind.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.minecraft.*;

final class ListenerMiddleClick extends ModuleListener<ExpTweaks, ClickMiddleEvent>
{
    public ListenerMiddleClick(final ExpTweaks module) {
        super(module, (Class<? super Object>)ClickMiddleEvent.class);
    }
    
    public void invoke(final ClickMiddleEvent event) {
        if (((ExpTweaks)this.module).middleClickExp.getValue() && ((ExpTweaks)this.module).mceBind.getValue().getKey() == -1 && !event.isModuleCancelled() && !event.isCancelled() && !((ExpTweaks)this.module).isWasting()) {
            final int slot = InventoryUtil.findHotbarItem(Items.EXPERIENCE_BOTTLE, new Item[0]);
            if (slot != -1 && slot != -2 && slot != ListenerMiddleClick.mc.player.inventory.currentItem) {
                event.setCancelled(true);
            }
        }
    }
}
