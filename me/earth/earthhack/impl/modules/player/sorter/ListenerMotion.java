//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.sorter;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.*;
import me.earth.earthhack.impl.modules.player.cleaner.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerMotion extends ModuleListener<Sorter, MotionUpdateEvent>
{
    private static final ModuleCache<AutoArmor> AUTO_ARMOR;
    private static final ModuleCache<Cleaner> CLEANER;
    
    public ListenerMotion(final Sorter module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class, 999999);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (!((Sorter)this.module).timer.passed(((Sorter)this.module).delay.getValue()) || !((Sorter)this.module).sort.getValue() || event.getStage() != Stage.PRE || !Managers.NCP.getClickTimer().passed(((Sorter)this.module).globalDelay.getValue()) || ListenerMotion.mc.player.isCreative() || !InventoryUtil.validScreen() || ListenerMotion.AUTO_ARMOR.returnIfPresent(AutoArmor::isActive, false) || (!((Sorter)this.module).sortInInv.getValue() && ListenerMotion.mc.currentScreen instanceof GuiInventory) || (!((Sorter)this.module).sortInLoot.getValue() && !ListenerMotion.mc.world.getEntitiesWithinAABB((Class)EntityItem.class, RotationUtil.getRotationPlayer().getEntityBoundingBox()).isEmpty()) || (ListenerMotion.CLEANER.isEnabled() && !ListenerMotion.CLEANER.get().getTimer().passed(ListenerMotion.CLEANER.get().getDelay() * 3L))) {
            return;
        }
        final InventoryLayout layout = ((Sorter)this.module).current;
        if (layout == null) {
            return;
        }
        Item fallbackItem = null;
        Item otherFallbackItem = null;
        int fallback = -1;
        int otherFallback = -1;
        boolean emptyFallback = false;
        final Set<Item> missing = new HashSet<Item>();
        for (int i = 44; i > 8; --i) {
            final ItemStack s = InventoryUtil.get(i);
            final Item shouldBeHere = layout.getItem(i);
            if (shouldBeHere != s.getItem() && shouldBeHere != Items.field_190931_a) {
                if (!missing.contains(shouldBeHere)) {
                    final int slot = this.getSlot(shouldBeHere, s.getItem(), i, missing, layout);
                    if (slot == -2) {
                        return;
                    }
                    if (slot != -1 && (fallback == -1 || (!emptyFallback && s.func_190926_b()))) {
                        fallback = slot;
                        otherFallback = i;
                        emptyFallback = s.func_190926_b();
                        fallbackItem = InventoryUtil.get(i).getItem();
                        otherFallbackItem = shouldBeHere;
                    }
                }
            }
        }
        if (fallback != -1) {
            this.click(fallback, otherFallback, fallbackItem, otherFallbackItem);
            ((Sorter)this.module).timer.reset();
        }
    }
    
    private int getSlot(final Item shouldBeHere, final Item inSlot, final int slot, final Set<Item> missing, final InventoryLayout layout) {
        int result = -1;
        for (int i = 44; i > 8; --i) {
            if (i != slot) {
                final Item item = InventoryUtil.get(i).getItem();
                if (item == shouldBeHere) {
                    result = i;
                    final Item shouldBeThere = layout.getItem(i);
                    if (shouldBeThere == inSlot) {
                        this.click(i, slot, item, inSlot);
                        ((Sorter)this.module).timer.reset();
                        return -2;
                    }
                }
            }
        }
        if (result == -1) {
            missing.add(shouldBeHere);
        }
        return result;
    }
    
    private void click(final int from, final int to, final Item inSlot, final Item inToSlot) {
        Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
            final Item slotItem = InventoryUtil.get(from).getItem();
            final Item toItem = InventoryUtil.get(to).getItem();
            if (slotItem == inSlot && inToSlot == toItem) {
                InventoryUtil.click(from);
                InventoryUtil.click(to);
                InventoryUtil.click(from);
                ((Sorter)this.module).timer.reset();
            }
        });
    }
    
    static {
        AUTO_ARMOR = Caches.getModule(AutoArmor.class);
        CLEANER = Caches.getModule(Cleaner.class);
    }
}
