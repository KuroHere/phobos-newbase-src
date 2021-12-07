//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autoarmor;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.xcarry.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.util.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.modes.*;
import net.minecraft.inventory.*;
import java.util.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerTick extends ModuleListener<AutoArmor, TickEvent>
{
    private static final ModuleCache<XCarry> XCARRY;
    
    public ListenerTick(final AutoArmor module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (!event.isSafe() || this.checkDesync()) {
            ((AutoArmor)this.module).putBackClick = null;
            return;
        }
        ((AutoArmor)this.module).stackSet = false;
        ((AutoArmor)this.module).queuedSlots.clear();
        ((AutoArmor)this.module).windowClicks.clear();
        ((AutoArmor)this.module).desyncMap.entrySet().removeIf(e -> System.currentTimeMillis() - e.getValue().getTimeStamp() > ((AutoArmor)this.module).removeTime.getValue());
        if (InventoryUtil.validScreen()) {
            if (((AutoArmor)this.module).canAutoMend()) {
                ((AutoArmor)this.module).queuedSlots.add(-2);
                final List<DamageStack> stacks = new ArrayList<DamageStack>(4);
                for (int i = 5; i < 9; ++i) {
                    final ItemStack stack = ListenerTick.mc.player.inventoryContainer.getSlot(i).getStack();
                    if (!stack.func_190926_b()) {
                        final float percent = DamageUtil.getPercent(stack);
                        if (percent > (int)((AutoArmor)this.module).damages[i - 5].getValue()) {
                            stacks.add(new DamageStack(stack, percent, i));
                        }
                    }
                }
                stacks.sort(DamageStack::compareTo);
                final ItemStack setStack = ((AutoArmor)this.module).setStack();
                if (setStack == null) {
                    return;
                }
                ItemStack drag = setStack;
                for (final DamageStack stack2 : stacks) {
                    final ItemStack sStack = stack2.getStack();
                    final int slot = AutoArmor.findItem(Items.field_190931_a, ListenerTick.XCARRY.isEnabled(), ((AutoArmor)this.module).queuedSlots);
                    if (slot == -1) {
                        if (((AutoArmor)this.module).dragTakeOff.getValue() && (((AutoArmor)this.module).stackSet || ListenerTick.mc.player.inventory.getItemStack().func_190926_b())) {
                            ((AutoArmor)this.module).queueClick(stack2.getSlot(), sStack, drag, -1);
                        }
                        return;
                    }
                    if (slot == -2) {
                        continue;
                    }
                    ((AutoArmor)this.module).queueClick(stack2.getSlot(), sStack, drag, slot).setDoubleClick(((AutoArmor)this.module).doubleClicks.getValue());
                    drag = sStack;
                    final ItemStack inSlot = InventoryUtil.get(slot);
                    ((AutoArmor)this.module).queueClick(slot, inSlot, drag);
                    ((AutoArmor)this.module).queuedSlots.add(slot);
                    drag = inSlot;
                }
            }
            else {
                final Map<EntityEquipmentSlot, Integer> map = ((AutoArmor)this.module).mode.getValue().setup(ListenerTick.XCARRY.isEnabled(), !((AutoArmor)this.module).curse.getValue(), ((AutoArmor)this.module).prioLow.getValue(), ((AutoArmor)this.module).prioThreshold.getValue());
                int last = -1;
                ItemStack drag = ListenerTick.mc.player.inventory.getItemStack();
                for (final Map.Entry<EntityEquipmentSlot, Integer> entry : map.entrySet()) {
                    if (entry.getValue() == 8) {
                        final int slot2 = AutoArmor.fromEquipment(entry.getKey());
                        if (slot2 != -1 && slot2 != 45) {
                            final ItemStack inSlot2 = InventoryUtil.get(slot2);
                            ((AutoArmor)this.module).queueClick(slot2, inSlot2, drag);
                            drag = inSlot2;
                            last = slot2;
                        }
                        map.remove(entry.getKey());
                        break;
                    }
                }
                for (final Map.Entry<EntityEquipmentSlot, Integer> entry : map.entrySet()) {
                    final int slot2 = AutoArmor.fromEquipment(entry.getKey());
                    if (slot2 != -1 && slot2 != 45 && entry.getValue() != null) {
                        final int j = entry.getValue();
                        ItemStack inSlot = InventoryUtil.get(j);
                        ((AutoArmor)this.module).queueClick(j, inSlot, drag).setDoubleClick(((AutoArmor)this.module).doubleClicks.getValue());
                        if (!drag.func_190926_b()) {
                            ((AutoArmor)this.module).queuedSlots.add(j);
                        }
                        drag = inSlot;
                        inSlot = InventoryUtil.get(slot2);
                        ((AutoArmor)this.module).queueClick(slot2, inSlot, drag);
                        drag = inSlot;
                        last = slot2;
                    }
                }
                if (((AutoArmor)this.module).putBack.getValue()) {
                    if (last != -1) {
                        final ItemStack stack3 = InventoryUtil.get(last);
                        if (!stack3.func_190926_b()) {
                            ((AutoArmor)this.module).queuedSlots.add(-2);
                            final int air = AutoArmor.findItem(Items.field_190931_a, ListenerTick.XCARRY.isEnabled(), ((AutoArmor)this.module).queuedSlots);
                            if (air != -1) {
                                final ItemStack inSlot3 = InventoryUtil.get(air);
                                (((AutoArmor)this.module).putBackClick = ((AutoArmor)this.module).queueClick(air, inSlot3, drag)).addPost(() -> ((AutoArmor)this.module).putBackClick = null);
                            }
                        }
                    }
                    else if (((AutoArmor)this.module).putBackClick != null && ((AutoArmor)this.module).putBackClick.isValid()) {
                        ((AutoArmor)this.module).queueClick(((AutoArmor)this.module).putBackClick);
                    }
                    else {
                        ((AutoArmor)this.module).putBackClick = null;
                    }
                }
            }
        }
        ((AutoArmor)this.module).runClick();
    }
    
    private boolean checkDesync() {
        if (((AutoArmor)this.module).noDesync.getValue() && InventoryUtil.validScreen() && ((AutoArmor)this.module).timer.passed(((AutoArmor)this.module).checkDelay.getValue()) && ((AutoArmor)this.module).desyncTimer.passed(((AutoArmor)this.module).desyncDelay.getValue()) && ((AutoArmor)this.module).propertyTimer.passed(((AutoArmor)this.module).propertyDelay.getValue())) {
            int bestSlot = -1;
            int clientValue = 0;
            boolean foundType = false;
            final int armorValue = ListenerTick.mc.player.getTotalArmorValue();
            for (int i = 5; i < 9; ++i) {
                final ItemStack stack = ListenerTick.mc.player.inventoryContainer.getSlot(i).getStack();
                if (stack.func_190926_b() && !foundType) {
                    bestSlot = i;
                    if (((AutoArmor)this.module).lastType == AutoArmor.fromSlot(i)) {
                        foundType = true;
                    }
                }
                else if (stack.getItem() instanceof ItemArmor) {
                    final ItemArmor itemArmor = (ItemArmor)stack.getItem();
                    clientValue += itemArmor.damageReduceAmount;
                }
            }
            if (clientValue != armorValue && ((AutoArmor)this.module).timer.passed(((AutoArmor)this.module).delay.getValue())) {
                if (((AutoArmor)this.module).illegalSync.getValue()) {
                    ModuleUtil.sendMessage((Module)this.module, "§cDesync!");
                    InventoryUtil.illegalSync();
                }
                else if (bestSlot != -1 && AutoArmor.getSlot(ListenerTick.mc.player.inventory.getItemStack()) == AutoArmor.fromSlot(bestSlot)) {
                    ModuleUtil.sendMessage((Module)this.module, "§cDesync! (Code: " + bestSlot + ")");
                    final Item j = InventoryUtil.get(bestSlot).getItem();
                    InventoryUtil.clickLocked(bestSlot, bestSlot, j, j);
                }
                else {
                    ModuleUtil.sendMessage((Module)this.module, "§cDesync!");
                    final Item j = InventoryUtil.get(20).getItem();
                    InventoryUtil.clickLocked(20, 20, j, j);
                }
                ((AutoArmor)this.module).resetTimer();
                ((AutoArmor)this.module).desyncTimer.reset();
                return true;
            }
        }
        return false;
    }
    
    static {
        XCARRY = Caches.getModule(XCarry.class);
    }
}
