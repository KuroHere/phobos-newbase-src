//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.exptweaks;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import org.lwjgl.input.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

final class ListenerMotion extends ModuleListener<ExpTweaks, MotionUpdateEvent>
{
    public ListenerMotion(final ExpTweaks module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, 1000);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            if (((ExpTweaks)this.module).feetExp.getValue() && ((InventoryUtil.isHolding(Items.EXPERIENCE_BOTTLE) && Mouse.isButtonDown(1)) || ((ExpTweaks)this.module).isMiddleClick())) {
                event.setPitch(90.0f);
            }
        }
        else if (((ExpTweaks)this.module).isMiddleClick() && (!((ExpTweaks)this.module).wasteStop.getValue() || !((ExpTweaks)this.module).isWasting()) && (((ExpTweaks)this.module).whileEating.getValue() || !(ListenerMotion.mc.player.getActiveItemStack().getItem() instanceof ItemFood))) {
            final int slot = InventoryUtil.findHotbarItem(Items.EXPERIENCE_BOTTLE, new Item[0]);
            if (slot != -1) {
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                    final int lastSlot = ListenerMotion.mc.player.inventory.currentItem;
                    final boolean silent = ((ExpTweaks)this.module).silent.getValue();
                    if (silent) {
                        ((ExpTweaks)this.module).isMiddleClick = true;
                    }
                    InventoryUtil.switchTo(slot);
                    ListenerMotion.mc.playerController.processRightClick((EntityPlayer)ListenerMotion.mc.player, (World)ListenerMotion.mc.world, InventoryUtil.getHand(slot));
                    if (silent) {
                        InventoryUtil.switchTo(lastSlot);
                        ((ExpTweaks)this.module).isMiddleClick = false;
                        ((ExpTweaks)this.module).lastSlot = -1;
                    }
                    else if (lastSlot != slot) {
                        ((ExpTweaks)this.module).lastSlot = lastSlot;
                    }
                });
            }
            else if (((ExpTweaks)this.module).lastSlot != -1) {
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                    InventoryUtil.switchTo(((ExpTweaks)this.module).lastSlot);
                    ((ExpTweaks)this.module).lastSlot = -1;
                });
            }
        }
        else if (((ExpTweaks)this.module).lastSlot != -1) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                InventoryUtil.switchTo(((ExpTweaks)this.module).lastSlot);
                ((ExpTweaks)this.module).lastSlot = -1;
            });
        }
    }
}
