//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.replenish;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.xcarry.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerTick extends ModuleListener<Replenish, TickEvent>
{
    private static final ModuleCache<XCarry> XCARRY;
    private boolean reset;
    
    public ListenerTick(final Replenish module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (event.isSafe() && !ListenerTick.mc.player.isCreative() && !(ListenerTick.mc.currentScreen instanceof GuiContainer)) {
            this.reset = false;
            if (!((Replenish)this.module).timer.passed(((Replenish)this.module).delay.getValue()) || (!((Replenish)this.module).replenishInLoot.getValue() && !ListenerTick.mc.world.getEntitiesWithinAABB((Class)EntityItem.class, RotationUtil.getRotationPlayer().getEntityBoundingBox()).isEmpty())) {
                return;
            }
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = ListenerTick.mc.player.inventory.getStackInSlot(i);
                final Item iItem = stack.getItem();
                if (((Replenish)this.module).isStackValid(stack) && stack.func_190916_E() <= ((Replenish)this.module).threshold.getValue() && (stack.func_190916_E() < stack.getMaxStackSize() || stack.func_190926_b())) {
                    final ItemStack before = ((Replenish)this.module).hotbar.get(i);
                    if (before != null && ((Replenish)this.module).isStackValid(stack) && !before.func_190926_b() && (before.getItem() == stack.getItem() || stack.func_190926_b())) {
                        int slot = this.findSlot(stack.func_190926_b() ? before : stack, stack.func_190916_E());
                        if (slot != -1) {
                            final boolean drag = slot == -2;
                            boolean diff = false;
                            if (slot > 46) {
                                slot -= 100;
                                if (slot < 1) {
                                    ((Replenish)this.module).hotbar.set(i, stack.copy());
                                    continue;
                                }
                                diff = true;
                            }
                            final int finalI = i + 36;
                            final int finalSlot = slot;
                            final boolean finalDiff = diff;
                            final Item sItem = InventoryUtil.get(slot).getItem();
                            Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
                                if (InventoryUtil.get(finalI).getItem() != iItem || InventoryUtil.get(finalSlot).getItem() != sItem) {
                                    return;
                                }
                                else {
                                    Managers.NCP.startMultiClick();
                                    if (!drag) {
                                        InventoryUtil.click(finalSlot);
                                    }
                                    InventoryUtil.click(finalI);
                                    if (finalDiff && ((Replenish)this.module).putBack.getValue()) {
                                        InventoryUtil.click(finalSlot);
                                    }
                                    Managers.NCP.releaseMultiClick();
                                    return;
                                }
                            });
                            ((Replenish)this.module).timer.reset();
                            if (((Replenish)this.module).delay.getValue() != 0) {
                                break;
                            }
                            continue;
                        }
                    }
                }
                ((Replenish)this.module).hotbar.set(i, stack.copy());
            }
        }
        else if (!this.reset) {
            ((Replenish)this.module).clear();
            this.reset = true;
        }
    }
    
    private int findSlot(final ItemStack current, final int count) {
        int result = -1;
        final int maxDiff = current.getMaxStackSize() - count;
        final int minSize = (current.getMaxStackSize() > ((Replenish)this.module).minSize.getValue()) ? ((Replenish)this.module).minSize.getValue() : current.getMaxStackSize();
        int maxSize = 0;
        int maxIndex = -1;
        int limitSize = 0;
        if (InventoryUtil.canStack(current, ListenerTick.mc.player.inventory.getItemStack())) {
            return -2;
        }
        final boolean xCarry = ListenerTick.XCARRY.isEnabled();
        for (int i = 9; i <= 36; ++i) {
            if (i == 5) {
                break;
            }
            if (i == 36) {
                if (!xCarry) {
                    break;
                }
                i = 1;
            }
            final ItemStack stack = (ItemStack)ListenerTick.mc.player.inventoryContainer.getInventory().get(i);
            if (InventoryUtil.canStack(current, stack)) {
                if (stack.func_190916_E() > maxDiff) {
                    if (stack.func_190916_E() > maxSize) {
                        maxIndex = i;
                        maxSize = stack.func_190916_E();
                    }
                }
                else if (stack.func_190916_E() > limitSize) {
                    result = i;
                    limitSize = stack.func_190916_E();
                }
            }
        }
        if (maxIndex != -1 && (result == -1 || ((ItemStack)ListenerTick.mc.player.inventoryContainer.getInventory().get(result)).func_190916_E() < minSize)) {
            return maxIndex + 100;
        }
        return result;
    }
    
    static {
        XCARRY = Caches.getModule(XCarry.class);
    }
}
