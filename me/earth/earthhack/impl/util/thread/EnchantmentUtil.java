//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.thread;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import java.util.*;
import net.minecraft.nbt.*;

public class EnchantmentUtil
{
    public static int getEnchantmentModifierDamage(final Iterable<ItemStack> stacks, final DamageSource source) {
        int modifier = 0;
        for (final ItemStack stack : stacks) {
            if (!stack.func_190926_b()) {
                final NBTTagList nbttaglist = stack.getEnchantmentTagList();
                for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                    final int id = nbttaglist.getCompoundTagAt(i).getShort("id");
                    final int lvl = nbttaglist.getCompoundTagAt(i).getShort("lvl");
                    final Enchantment ench = Enchantment.getEnchantmentByID(id);
                    if (ench != null) {
                        modifier += ench.calcModifierDamage(lvl, source);
                    }
                }
            }
        }
        return modifier;
    }
    
    public static void addEnchantment(final ItemStack stack, final int id, final int level) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (!stack.getTagCompound().hasKey("ench", 9)) {
            stack.getTagCompound().setTag("ench", (NBTBase)new NBTTagList());
        }
        final NBTTagList nbttaglist = stack.getTagCompound().getTagList("ench", 10);
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setShort("id", (short)id);
        nbttagcompound.setShort("lvl", (short)level);
        nbttaglist.appendTag((NBTBase)nbttagcompound);
    }
}
