//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autoarmor.modes;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.inventory.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.util.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.*;
import me.earth.earthhack.impl.util.minecraft.*;
import java.util.*;
import net.minecraft.item.*;

public enum ArmorMode implements Globals
{
    Blast {
        @Override
        public Map<EntityEquipmentSlot, Integer> setup(final boolean xCarry, final boolean curse, final boolean prio, final float threshold) {
            boolean wearingBlast = false;
            final Set<EntityEquipmentSlot> cursed = new HashSet<EntityEquipmentSlot>(6);
            final List<EntityEquipmentSlot> empty = new ArrayList<EntityEquipmentSlot>(4);
            for (int i = 5; i < 9; ++i) {
                final ItemStack stack = InventoryUtil.get(i);
                if (!stack.func_190926_b()) {
                    if (stack.getItem() instanceof ItemArmor) {
                        final int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, stack);
                        if (lvl > 0) {
                            wearingBlast = true;
                        }
                    }
                    else {
                        empty.add(AutoArmor.fromSlot(i));
                    }
                    if (EnchantmentHelper.func_190938_b(stack)) {
                        cursed.add(AutoArmor.fromSlot(i));
                    }
                }
                else {
                    empty.add(AutoArmor.fromSlot(i));
                }
            }
            if (wearingBlast && empty.isEmpty()) {
                return new HashMap<EntityEquipmentSlot, Integer>(1, 1.0f);
            }
            final Map<EntityEquipmentSlot, LevelStack> map = new HashMap<EntityEquipmentSlot, LevelStack>(6);
            final Map<EntityEquipmentSlot, LevelStack> blast = new HashMap<EntityEquipmentSlot, LevelStack>(6);
            for (int j = 8; j < 45; ++j) {
                if (j == 5) {
                    j = 9;
                }
                final ItemStack stack2 = ArmorMode.getStack(j);
                if (!stack2.func_190926_b() && stack2.getItem() instanceof ItemArmor && AutoArmor.curseCheck(stack2, curse)) {
                    final float d = (float)DamageUtil.getDamage(stack2);
                    final ItemArmor armor = (ItemArmor)stack2.getItem();
                    final EntityEquipmentSlot type = armor.getEquipmentSlot();
                    final int blastLvL = EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, stack2);
                    if (blastLvL != 0) {
                        compute(stack2, blast, type, j, blastLvL, d, prio, threshold);
                    }
                    int lvl2 = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack2);
                    if (blastLvL != 0) {
                        if (lvl2 < 4) {
                            continue;
                        }
                        lvl2 += blastLvL;
                    }
                    compute(stack2, map, type, j, lvl2, d, prio, threshold);
                }
                if (j == 8 && xCarry) {
                    j = 0;
                }
            }
            final Map<EntityEquipmentSlot, Integer> result = new HashMap<EntityEquipmentSlot, Integer>(6);
            if (wearingBlast) {
                for (final EntityEquipmentSlot slot : empty) {
                    if (map.get(slot) == null) {
                        final LevelStack e = blast.get(slot);
                        if (e == null) {
                            continue;
                        }
                        map.put(slot, e);
                    }
                }
                map.keySet().retainAll(empty);
                map.forEach((key, value) -> {
                    final Integer n = result.put(key, value.getSlot());
                    return;
                });
            }
            else {
                boolean foundBlast = false;
                final List<EntityEquipmentSlot> both = new ArrayList<EntityEquipmentSlot>(4);
                for (final EntityEquipmentSlot slot2 : empty) {
                    final LevelStack b = blast.get(slot2);
                    final LevelStack p = map.get(slot2);
                    if (b == null && p != null) {
                        result.put(slot2, p.getSlot());
                    }
                    else if (b != null && p == null) {
                        foundBlast = true;
                        result.put(slot2, b.getSlot());
                    }
                    else {
                        if (b == null) {
                            continue;
                        }
                        both.add(slot2);
                    }
                }
                for (final EntityEquipmentSlot b2 : both) {
                    if (foundBlast) {
                        result.put(b2, map.get(b2).getSlot());
                    }
                    else {
                        foundBlast = true;
                        result.put(b2, blast.get(b2).getSlot());
                    }
                }
                if (!foundBlast && !blast.isEmpty()) {
                    final LevelStack e;
                    final Optional<Map.Entry<EntityEquipmentSlot, LevelStack>> first = blast.entrySet().stream().filter(e -> !cursed.contains(e.getKey())).findFirst();
                    first.ifPresent(e -> {
                        final Integer n2 = result.put(e.getKey(), e.getValue().getSlot());
                        return;
                    });
                }
            }
            return result;
        }
    }, 
    Protection {
        @Override
        public Map<EntityEquipmentSlot, Integer> setup(final boolean xCarry, final boolean curse, final boolean prio, final float threshold) {
            final List<EntityEquipmentSlot> semi = new ArrayList<EntityEquipmentSlot>(4);
            final List<EntityEquipmentSlot> empty = new ArrayList<EntityEquipmentSlot>(4);
            for (int i = 4; i < 9; ++i) {
                final ItemStack stack = InventoryUtil.get(i);
                final EntityEquipmentSlot slot = AutoArmor.fromSlot(i);
                if (!stack.func_190926_b()) {
                    if (!EnchantmentHelper.func_190938_b(stack)) {
                        if (stack.getItem() instanceof ItemArmor) {
                            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack) == 0) {
                                semi.add(slot);
                            }
                        }
                        else {
                            empty.add(slot);
                        }
                    }
                }
                else {
                    empty.add(slot);
                }
            }
            if (empty.isEmpty()) {
                return new HashMap<EntityEquipmentSlot, Integer>(0);
            }
            final Map<EntityEquipmentSlot, LevelStack> map = new HashMap<EntityEquipmentSlot, LevelStack>(6);
            for (int j = 8; j < 45; ++j) {
                if (j == 5) {
                    j = 9;
                }
                final ItemStack stack2 = ArmorMode.getStack(j);
                if (!stack2.func_190926_b() && stack2.getItem() instanceof ItemArmor && AutoArmor.curseCheck(stack2, curse)) {
                    final float d = (float)DamageUtil.getDamage(stack2);
                    final ItemArmor armor = (ItemArmor)stack2.getItem();
                    final EntityEquipmentSlot type = armor.getEquipmentSlot();
                    int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack2);
                    if (lvl >= 4) {
                        lvl += EnchantmentHelper.getEnchantmentLevel(Enchantments.BLAST_PROTECTION, stack2);
                    }
                    compute(stack2, map, type, j, lvl, d, prio, threshold);
                }
                if (j == 8 && xCarry) {
                    j = 0;
                }
            }
            for (final EntityEquipmentSlot s : semi) {
                final LevelStack entry = map.get(s);
                if (entry != null && entry.getLevel() > 0) {
                    empty.add(s);
                }
            }
            map.keySet().retainAll(empty);
            final Map<EntityEquipmentSlot, Integer> result = new HashMap<EntityEquipmentSlot, Integer>(6);
            map.forEach((key, value) -> {
                final Integer n = result.put(key, value.getSlot());
                return;
            });
            return result;
        }
    }, 
    Elytra {
        @Override
        public Map<EntityEquipmentSlot, Integer> setup(final boolean xCarry, final boolean curse, final boolean prio, final float threshold) {
            final Map<EntityEquipmentSlot, Integer> map = ArmorMode$3.Blast.setup(xCarry, curse, prio, threshold);
            int bestDura = 0;
            int bestElytra = -1;
            final ItemStack elytra = InventoryUtil.get(6);
            if (!elytra.func_190926_b() && (elytra.getItem() instanceof ItemElytra || EnchantmentHelper.func_190938_b(elytra))) {
                map.remove(EntityEquipmentSlot.CHEST);
                return map;
            }
            for (int i = 8; i < 45; ++i) {
                if (i == 5) {
                    i = 9;
                }
                final ItemStack stack = ArmorMode.getStack(i);
                if (!stack.func_190926_b() && stack.getItem() instanceof ItemElytra && AutoArmor.curseCheck(stack, curse)) {
                    final int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack) + 1;
                    final int dura = DamageUtil.getDamage(stack) * lvl;
                    if (bestElytra == -1 || (!prio && dura > bestDura) || (prio && dura > threshold && dura < bestDura)) {
                        bestElytra = i;
                        bestDura = dura;
                    }
                }
                if (i == 8 && xCarry) {
                    i = 0;
                }
            }
            if (bestElytra != -1) {
                map.put(EntityEquipmentSlot.CHEST, bestElytra);
            }
            return map;
        }
    };
    
    public abstract Map<EntityEquipmentSlot, Integer> setup(final boolean p0, final boolean p1, final boolean p2, final float p3);
    
    public static ItemStack getStack(final int slot) {
        if (slot == 8) {
            return ArmorMode.mc.player.inventory.getItemStack();
        }
        return InventoryUtil.get(slot);
    }
    
    private static void compute(final ItemStack stack, final Map<EntityEquipmentSlot, LevelStack> map, final EntityEquipmentSlot type, final int slot, final int level, final float damage, final boolean prio, final float threshold) {
        map.compute(type, (k, v) -> {
            if (v == null || !v.isBetter(damage, threshold, level, prio)) {
                return new LevelStack(stack, damage, slot, level);
            }
            else {
                return v;
            }
        });
    }
}
