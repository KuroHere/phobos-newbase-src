//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.cleaner;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.client.multiplayer.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerMotion extends ModuleListener<Cleaner, MotionUpdateEvent>
{
    private static final ModuleCache<AutoArmor> AUTO_ARMOR;
    
    public ListenerMotion(final Cleaner module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, 1000000);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if ((((Cleaner)this.module).action == null && !((Cleaner)this.module).timer.passed(((Cleaner)this.module).delay.getValue())) || !Managers.NCP.getClickTimer().passed(((Cleaner)this.module).globalDelay.getValue()) || ListenerMotion.mc.player.isCreative() || !InventoryUtil.validScreen() || ListenerMotion.AUTO_ARMOR.returnIfPresent(AutoArmor::isActive, false) || (!((Cleaner)this.module).inInventory.getValue() && ListenerMotion.mc.currentScreen instanceof GuiInventory) || (!((Cleaner)this.module).cleanInLoot.getValue() && !ListenerMotion.mc.world.getEntitiesWithinAABB((Class)EntityItem.class, RotationUtil.getRotationPlayer().getEntityBoundingBox()).isEmpty() && (!((Cleaner)this.module).cleanWithFull.getValue() || !this.isInvFull()))) {
            return;
        }
        if (event.getStage() == Stage.PRE) {
            if ((((Cleaner)this.module).stack.getValue() && this.stack()) || (((Cleaner)this.module).xCarry.getValue() && this.doXCarry())) {
                return;
            }
            final Map<Item, ItemToDrop> items = new HashMap<Item, ItemToDrop>();
            final boolean prio = ((Cleaner)this.module).prioHotbar.getValue();
            Item drag = null;
            final ItemStack draggedStack = ListenerMotion.mc.player.inventory.getItemStack();
            if (this.check(draggedStack, -2, items)) {
                drag = draggedStack.getItem();
            }
            else if (!draggedStack.func_190926_b()) {
                return;
            }
            int i = prio ? 44 : 9;
            while (true) {
                if (prio) {
                    if (i <= 8) {
                        break;
                    }
                }
                else if (i > 44) {
                    break;
                }
                final ItemStack stack = InventoryUtil.get(i);
                this.check(stack, i, items);
                i = (prio ? (--i) : (++i));
            }
            WindowClick action = null;
            if (drag != null) {
                final ItemToDrop dragged = items.get(drag);
                if (dragged != null && dragged.shouldDrop()) {
                    action = new WindowClick(-999, ItemStack.field_190927_a, ListenerMotion.mc.player.inventory.getItemStack());
                }
            }
            else {
                for (final ItemToDrop toDrop : items.values()) {
                    if (toDrop.shouldDrop()) {
                        final int s = toDrop.getSlot();
                        action = new WindowClick(-1, ItemStack.field_190927_a, s, InventoryUtil.get(s), -1, p -> p.windowClick(0, s, 1, ClickType.THROW, (EntityPlayer)ListenerMotion.mc.player));
                        break;
                    }
                }
            }
            if (action != null) {
                if (((Cleaner)this.module).rotate.getValue()) {
                    if (MovementUtil.isMoving()) {
                        event.setYaw(event.getYaw() - 180.0f);
                    }
                    else {
                        event.setYaw(this.getYaw(event.getYaw()));
                    }
                    event.setPitch(-5.0f);
                    ((Cleaner)this.module).action = action;
                }
                else {
                    ((Cleaner)this.module).action = action;
                    ((Cleaner)this.module).runAction();
                }
            }
        }
        else {
            ((Cleaner)this.module).runAction();
        }
    }
    
    private boolean stack() {
        final ItemStack drag = ListenerMotion.mc.player.inventory.getItemStack();
        if (drag.func_190926_b()) {
            final Map<Item, SettingMap> pref = new HashMap<Item, SettingMap>();
            final Map<Item, Map<Integer, Integer>> corresponding = new HashMap<Item, Map<Integer, Integer>>();
            for (int i = 44; i > 8; --i) {
                final ItemStack stack = InventoryUtil.get(i);
                if (!stack.func_190926_b()) {
                    final Item item = stack.getItem();
                    Map<Integer, Integer> corr = corresponding.get(item);
                    if (corr != null) {
                        if (stack.func_190916_E() < stack.getMaxStackSize()) {
                            if (!corr.containsKey(stack.func_190916_E())) {
                                corr.put(i, stack.func_190916_E());
                            }
                        }
                    }
                    else {
                        SettingMap map = pref.get(item);
                        if (map == null) {
                            final Setting<Integer> setting = this.getSetting(stack);
                            if (setting == null) {
                                corr = new HashMap<Integer, Integer>();
                                if (stack.func_190916_E() != stack.getMaxStackSize()) {
                                    corr.put(i, stack.func_190916_E());
                                }
                                corresponding.put(item, corr);
                                continue;
                            }
                            map = new SettingMap(setting, new HashMap<Integer, Integer>());
                            pref.put(stack.getItem(), map);
                        }
                        map.getMap().put(i, stack.func_190916_E());
                    }
                }
            }
            final Map<Integer, Map.Entry<Integer, Integer>> best = new TreeMap<Integer, Map.Entry<Integer, Integer>>();
            for (final Map.Entry<Item, SettingMap> entry : pref.entrySet()) {
                final SettingMap map2 = entry.getValue();
                if (map2.getMap().size() >= 2) {
                    if (map2.getSetting().getValue() == 0) {
                        continue;
                    }
                    final ItemStack deprec = new ItemStack((Item)entry.getKey());
                    final int max = map2.getSetting().getValue() * deprec.getMaxStackSize();
                    int s = 0;
                    int total = 0;
                    int fullStacks = 0;
                    for (final int stackCount : map2.getMap().values()) {
                        if (stackCount == deprec.getMaxStackSize()) {
                            ++fullStacks;
                        }
                        total += stackCount;
                        ++s;
                    }
                    final boolean smart = ((Cleaner)this.module).smartStack.getValue();
                    if (total > max && !smart) {
                        continue;
                    }
                    if (fullStacks >= map2.getSetting().getValue()) {
                        continue;
                    }
                    final int m = map2.getSetting().getValue();
                    final Map<Integer, Integer> sMap = CollectionUtil.sortByValue(map2.getMap());
                    if (this.findBest(sMap, entry.getKey(), best, smart, s, m)) {
                        return true;
                    }
                    continue;
                }
            }
            Map.Entry<Integer, Integer> b = best.values().stream().findFirst().orElse(null);
            if (b != null) {
                this.click(b.getValue(), b.getKey());
                return true;
            }
            for (final Map.Entry<Item, Map<Integer, Integer>> entry2 : corresponding.entrySet()) {
                final Map<Integer, Integer> map3 = entry2.getValue();
                if (map3.size() < 2) {
                    continue;
                }
                final Map<Integer, Integer> sort = CollectionUtil.sortByValue(map3);
                if (this.findBest(sort, entry2.getKey(), best, false, 0, 0)) {
                    return true;
                }
            }
            b = best.values().stream().findFirst().orElse(null);
            if (b != null) {
                this.click(b.getValue(), b.getKey());
                return true;
            }
        }
        else if (((Cleaner)this.module).stackDrag.getValue()) {
            final Setting<Integer> setting2 = this.getSetting(drag);
            if (setting2 != null && setting2.getValue() == 0) {
                return false;
            }
            for (int j = 44; j > 8; --j) {
                final ItemStack stack2 = InventoryUtil.get(j);
                if (InventoryUtil.canStack(stack2, drag) && stack2.func_190916_E() + drag.func_190916_E() <= stack2.getMaxStackSize()) {
                    final int finalI = j;
                    final Item item = stack2.getItem();
                    Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
                        if (InventoryUtil.get(finalI).getItem() == item) {
                            InventoryUtil.click(finalI);
                        }
                        return;
                    });
                    ((Cleaner)this.module).timer.reset();
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean findBest(final Map<Integer, Integer> map, final Item item, final Map<Integer, Map.Entry<Integer, Integer>> best, final boolean smart, final int stacks, final int max) {
        final Set<Integer> checked = new HashSet<Integer>((int)(map.size() * 1.5));
        for (final Map.Entry<Integer, Integer> inner : map.entrySet()) {
            checked.add(inner.getKey());
            for (final Map.Entry<Integer, Integer> sec : map.entrySet()) {
                if (checked.contains(sec.getKey())) {
                    continue;
                }
                int diff = item.getItemStackLimit();
                if (inner.getValue() == diff) {
                    continue;
                }
                if (sec.getValue() == diff) {
                    continue;
                }
                diff -= inner.getValue() + sec.getValue();
                if (diff < 0) {
                    if (!smart) {
                        continue;
                    }
                    if (stacks <= max) {
                        continue;
                    }
                }
                final int i = Math.max(inner.getKey(), sec.getKey());
                final int j = Math.min(inner.getKey(), sec.getKey());
                final ItemStack stack1 = InventoryUtil.get(i);
                final ItemStack stack2 = InventoryUtil.get(j);
                if (!InventoryUtil.canStack(stack1, stack2)) {
                    continue;
                }
                if (diff == 0) {
                    this.click(j, i);
                    return true;
                }
                best.put(diff, new AbstractMap.SimpleEntry<Integer, Integer>(i, j));
            }
        }
        return false;
    }
    
    private boolean doXCarry() {
        final int xCarry = this.getEmptyXCarry();
        final ItemStack drag = ListenerMotion.mc.player.inventory.getItemStack();
        if (xCarry == -1 || (!drag.func_190926_b() && !((Cleaner)this.module).dragCarry.getValue()) || this.getSetting(drag) != null) {
            return false;
        }
        int stacks = 0;
        final Set<Item> invalid = new HashSet<Item>();
        final Map<Item, List<SlotCount>> slots = new HashMap<Item, List<SlotCount>>();
        for (int i = 44; i > 8; --i) {
            final ItemStack stack = InventoryUtil.get(i);
            if (!stack.func_190926_b()) {
                ++stacks;
                if (!invalid.contains(stack.getItem())) {
                    final Setting<Integer> setting = this.getSetting(stack);
                    if (setting == null) {
                        slots.computeIfAbsent(stack.getItem(), v -> new ArrayList()).add(new SlotCount(stack.func_190916_E(), i));
                    }
                    else {
                        invalid.add(stack.getItem());
                    }
                }
            }
        }
        if (stacks < ((Cleaner)this.module).xCarryStacks.getValue()) {
            return false;
        }
        if (drag.func_190926_b()) {
            int best = -1;
            int bestSize = 0;
            for (final Map.Entry<Item, List<SlotCount>> entry : slots.entrySet()) {
                final int size = entry.getValue().size();
                final ItemStack deprec = new ItemStack((Item)entry.getKey());
                if (size >= ((Cleaner)this.module).minXcarry.getValue() && size > bestSize) {
                    for (final SlotCount count : entry.getValue()) {
                        if (count.getSlot() < 36 && count.getCount() == deprec.getMaxStackSize()) {
                            best = count.getSlot();
                            bestSize = size;
                        }
                    }
                }
            }
            if (best != -1) {
                this.click(best, xCarry);
                return true;
            }
        }
        else {
            final List<SlotCount> counts = slots.get(drag.getItem());
            if ((counts == null && ((Cleaner)this.module).minXcarry.getValue() == 0) || (counts != null && counts.size() >= ((Cleaner)this.module).minXcarry.getValue())) {
                final Item item = InventoryUtil.get(xCarry).getItem();
                Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
                    if (InventoryUtil.get(xCarry).getItem() == item) {
                        InventoryUtil.click(xCarry);
                    }
                    return;
                });
                ((Cleaner)this.module).timer.reset();
                return true;
            }
        }
        return false;
    }
    
    private int getEmptyXCarry() {
        for (int i = 1; i < 5; ++i) {
            final ItemStack stack = InventoryUtil.get(i);
            if (stack.func_190926_b() || stack.getItem() == Items.field_190931_a) {
                return i;
            }
        }
        return -1;
    }
    
    private void click(final int first, final int second) {
        final Item firstItem = InventoryUtil.get(first).getItem();
        final Item secondItem = InventoryUtil.get(second).getItem();
        Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
            if (InventoryUtil.get(first).getItem() != firstItem || InventoryUtil.get(second).getItem() != secondItem) {
                return;
            }
            else {
                Managers.NCP.startMultiClick();
                InventoryUtil.click(first);
                InventoryUtil.click(second);
                Managers.NCP.releaseMultiClick();
                return;
            }
        });
        ((Cleaner)this.module).timer.reset();
    }
    
    private Setting<Integer> getSetting(final ItemStack stack) {
        if (!stack.func_190926_b() && !((Cleaner)this.module).isStackValid(stack)) {
            final Item item = stack.getItem();
            return ((Cleaner)this.module).getSetting(item.getItemStackDisplayName(stack), RemovingInteger.class);
        }
        return null;
    }
    
    private boolean check(final ItemStack stack, final int i, final Map<Item, ItemToDrop> items) {
        if (!stack.func_190926_b() && !((Cleaner)this.module).isStackValid(stack)) {
            final Item item = stack.getItem();
            final Setting<Integer> setting = ((Cleaner)this.module).getSetting(item.getItemStackDisplayName(stack), RemovingInteger.class);
            items.computeIfAbsent(item, v -> new ItemToDrop(setting)).addSlot(i, stack.func_190916_E());
            return true;
        }
        return false;
    }
    
    private float getYaw(final float yaw) {
        int same = 0;
        int bestCount = 0;
        EnumFacing bestFacing = null;
        final BlockPos pos = PositionUtil.getPosition().up();
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            int count = 0;
            BlockPos current = pos;
            for (int i = 0; i < 5; ++i) {
                final BlockPos offset = current.offset(facing);
                if (ListenerMotion.mc.world.getBlockState(offset).getMaterial().blocksMovement()) {
                    break;
                }
                ++count;
                current = offset;
            }
            if (count == bestCount || bestFacing == null) {
                ++same;
            }
            if (count > bestCount) {
                bestCount = count;
                bestFacing = facing;
            }
        }
        if (bestFacing == null || same == 4) {
            return yaw - 180.0f;
        }
        return bestFacing.getHorizontalAngle();
    }
    
    private boolean isInvFull() {
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = InventoryUtil.get(i);
            if (stack.func_190926_b()) {
                return false;
            }
            if (stack.func_190916_E() != stack.getMaxStackSize() && ((Cleaner)this.module).sizeCheck.getValue()) {
                for (final EntityItem entity : ListenerMotion.mc.world.getEntitiesWithinAABB((Class)EntityItem.class, RotationUtil.getRotationPlayer().getEntityBoundingBox())) {
                    if (InventoryUtil.canStack(stack, entity.getEntityItem())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    static {
        AUTO_ARMOR = Caches.getModule(AutoArmor.class);
    }
}
