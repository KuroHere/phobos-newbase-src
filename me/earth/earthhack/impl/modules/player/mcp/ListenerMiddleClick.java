//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.mcp;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.keyboard.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.managers.*;

final class ListenerMiddleClick extends ModuleListener<MiddleClickPearl, ClickMiddleEvent>
{
    protected ListenerMiddleClick(final MiddleClickPearl module) {
        super(module, (Class<? super Object>)ClickMiddleEvent.class, 11);
    }
    
    public void invoke(final ClickMiddleEvent event) {
        if (!event.isModuleCancelled() && !event.isCancelled()) {
            if (InventoryUtil.findHotbarItem(Items.ENDER_PEARL, new Item[0]) == -1) {
                return;
            }
            if (!((MiddleClickPearl)this.module).prioritizeMCF()) {
                if (((MiddleClickPearl)this.module).cancelBlock.getValue()) {
                    event.setCancelled(true);
                }
            }
            else {
                if (!((MiddleClickPearl)this.module).cancelMCF.getValue()) {
                    return;
                }
                event.setModuleCancelled(true);
            }
            ((MiddleClickPearl)this.module).runnable = (() -> {
                final int slot = InventoryUtil.findHotbarItem(Items.ENDER_PEARL, new Item[0]);
                if (slot == -1) {
                    return;
                }
                else {
                    Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                        final int lastSlot = ListenerMiddleClick.mc.player.inventory.currentItem;
                        InventoryUtil.switchTo(slot);
                        ListenerMiddleClick.mc.playerController.processRightClick((EntityPlayer)ListenerMiddleClick.mc.player, (World)ListenerMiddleClick.mc.world, InventoryUtil.getHand(slot));
                        InventoryUtil.switchTo(lastSlot);
                    });
                    return;
                }
            });
            if (Managers.ROTATION.getServerPitch() == ListenerMiddleClick.mc.player.rotationPitch && Managers.ROTATION.getServerYaw() == ListenerMiddleClick.mc.player.rotationYaw) {
                ((MiddleClickPearl)this.module).runnable.run();
                ((MiddleClickPearl)this.module).runnable = null;
            }
        }
    }
}
