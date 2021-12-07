//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autocraft;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;

final class ListenerTick extends ModuleListener<AutoCraft, TickEvent>
{
    public ListenerTick(final AutoCraft module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (((AutoCraft)this.module).currentTask != null) {
            System.out.println("current task:" + ((AutoCraft)this.module).currentTask.getRecipe().getRecipeOutput().getItem().getRegistryName());
        }
        if (((AutoCraft)this.module).lastTask != null) {
            System.out.println("last task:" + ((AutoCraft)this.module).lastTask.getRecipe().getRecipeOutput().getItem().getRegistryName());
        }
        if (((AutoCraft)this.module).delayTimer.passed(((AutoCraft)this.module).delay.getValue()) && ((AutoCraft)this.module).currentTask == null) {
            ((AutoCraft)this.module).currentTask = ((AutoCraft)this.module).dequeue();
        }
        if (((AutoCraft)this.module).currentTask != null && ((AutoCraft)this.module).currentTask.isInTable() && !(ListenerTick.mc.currentScreen instanceof GuiCrafting)) {
            if (((AutoCraft)this.module).getCraftingTable() == null && InventoryUtil.findBlock(Blocks.CRAFTING_TABLE, false) == -1 && ((AutoCraft)this.module).craftTable.getValue()) {
                ((AutoCraft)this.module).lastTask = ((AutoCraft)this.module).currentTask;
                ((AutoCraft)this.module).currentTask = new AutoCraft.CraftTask("crafting_table", 1);
            }
            ((AutoCraft)this.module).shouldTable = true;
            return;
        }
        if (((AutoCraft)this.module).lastTask != null && InventoryUtil.findBlock(Blocks.CRAFTING_TABLE, false) != -1) {
            ((AutoCraft)this.module).currentTask = ((AutoCraft)this.module).lastTask;
            ((AutoCraft)this.module).lastTask = null;
        }
        if (((AutoCraft)this.module).clickDelay.getValue() != 0 && ((AutoCraft)this.module).clickDelayTimer.passed(((AutoCraft)this.module).clickDelay.getValue()) && ((AutoCraft)this.module).currentTask != null && (!((AutoCraft)this.module).currentTask.isInTable() || ListenerTick.mc.currentScreen instanceof GuiCrafting)) {
            ((AutoCraft)this.module).currentTask.updateSlots();
            int windowId = 0;
            if (((AutoCraft)this.module).currentTask.isInTable()) {
                assert ListenerTick.mc.currentScreen != null;
                windowId = ((GuiContainer)ListenerTick.mc.currentScreen).inventorySlots.windowId;
            }
            if (((AutoCraft)this.module).currentTask.step < ((AutoCraft)this.module).currentTask.getSlotToSlotMap().size()) {
                final AutoCraft.SlotEntry entry = ((AutoCraft)this.module).currentTask.getSlotToSlotMap().get(((AutoCraft)this.module).currentTask.getStep());
                System.out.println("inventory slot:" + entry.getInventorySlot());
                System.out.println("gui slot:" + entry.getGuiSlot());
                ListenerTick.mc.playerController.windowClick(windowId, entry.getInventorySlot(), 0, ClickType.PICKUP, (EntityPlayer)ListenerTick.mc.player);
                for (int i = 0; i < ((AutoCraft)this.module).currentTask.runs; ++i) {
                    ListenerTick.mc.playerController.windowClick(windowId, entry.getGuiSlot(), 1, ClickType.PICKUP, (EntityPlayer)ListenerTick.mc.player);
                }
                ListenerTick.mc.playerController.windowClick(windowId, entry.getInventorySlot(), 0, ClickType.PICKUP, (EntityPlayer)ListenerTick.mc.player);
                final AutoCraft.CraftTask currentTask = ((AutoCraft)this.module).currentTask;
                ++currentTask.step;
            }
            else if (((AutoCraft)this.module).currentTask.step == ((AutoCraft)this.module).currentTask.getSlotToSlotMap().size()) {
                ListenerTick.mc.playerController.windowClick(windowId, 0, 0, ClickType.QUICK_MOVE, (EntityPlayer)ListenerTick.mc.player);
                ((AutoCraft)this.module).currentTask = null;
                ((AutoCraft)this.module).delayTimer.reset();
                if (ListenerTick.mc.currentScreen instanceof GuiCrafting) {
                    ListenerTick.mc.displayGuiScreen((GuiScreen)null);
                }
            }
        }
    }
}
