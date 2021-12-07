//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.creativetab.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import net.minecraft.inventory.*;

final class ListenerSetSlot extends ModuleListener<Packets, PacketEvent.Receive<SPacketSetSlot>>
{
    public ListenerSetSlot(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketSetSlot.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSetSlot> event) {
        if (!((Packets)this.module).fastSetSlot.getValue() || event.isCancelled()) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)ListenerSetSlot.mc.player;
        if (player == null) {
            return;
        }
        final int slot = event.getPacket().getSlot();
        final int id = event.getPacket().getWindowId();
        final ItemStack stack = event.getPacket().getStack();
        if (id == -1) {
            Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> player.inventory.setItemStack(stack));
        }
        else if (id == -2) {
            Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> player.inventory.setInventorySlotContents(slot, stack));
        }
        else {
            Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
                boolean badTab = false;
                final GuiScreen current = ListenerSetSlot.mc.currentScreen;
                if (current instanceof GuiContainerCreative) {
                    final GuiContainerCreative creative = (GuiContainerCreative)current;
                    badTab = (creative.getSelectedTabIndex() != CreativeTabs.INVENTORY.getTabIndex());
                }
                if (id == 0 && slot >= 36 && slot < 45) {
                    if (!stack.func_190926_b()) {
                        final ItemStack inSlot = InventoryUtil.get(slot);
                        if (inSlot.func_190926_b() || inSlot.func_190916_E() < stack.func_190916_E()) {
                            stack.func_190915_d(5);
                        }
                    }
                    player.inventoryContainer.putStackInSlot(slot, stack);
                }
                else {
                    final Container container = player.openContainer;
                    if (id == container.windowId && (id != 0 || !badTab)) {
                        container.putStackInSlot(slot, stack);
                    }
                }
            });
        }
    }
}
