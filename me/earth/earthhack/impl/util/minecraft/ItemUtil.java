//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class ItemUtil
{
    public static boolean isThrowable(final Item item) {
        return item instanceof ItemEnderPearl || item instanceof ItemExpBottle || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemFishingRod;
    }
    
    public static boolean areSame(final Block block1, final Block block2) {
        return Block.getIdFromBlock(block1) == Block.getIdFromBlock(block2);
    }
    
    public static boolean areSame(final Item item1, final Item item2) {
        return Item.getIdFromItem(item1) == Item.getIdFromItem(item2);
    }
    
    public static boolean areSame(final Block block, final Item item) {
        return item instanceof ItemBlock && areSame(block, ((ItemBlock)item).getBlock());
    }
    
    public static boolean areSame(final ItemStack stack, final Block block) {
        return stack != null && ((block == Blocks.AIR && stack.func_190926_b()) || areSame(block, stack.getItem()));
    }
    
    public static boolean areSame(final ItemStack stack, final Item item) {
        return stack != null && areSame(stack.getItem(), item);
    }
}
