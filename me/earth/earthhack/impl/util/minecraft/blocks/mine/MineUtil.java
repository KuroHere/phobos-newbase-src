//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.blocks.mine;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.core.ducks.block.*;
import me.earth.earthhack.impl.core.mixins.item.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.enchantment.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.misc.*;
import net.minecraft.init.*;
import java.util.*;

public class MineUtil implements Globals
{
    public static boolean canHarvestBlock(final BlockPos pos, final ItemStack stack) {
        IBlockState state = MineUtil.mc.world.getBlockState(pos);
        state = state.getActualState((IBlockAccess)MineUtil.mc.world, pos);
        final Block block = state.getBlock();
        if (state.getMaterial().isToolNotRequired()) {
            return true;
        }
        if (stack.func_190926_b()) {
            return stack.canHarvestBlock(state);
        }
        final String tool = ((IBlock)block).getHarvestToolNonForge(state);
        if (tool == null) {
            return stack.canHarvestBlock(state);
        }
        int toolLevel = -1;
        if (stack.getItem() instanceof IItemTool) {
            String toolClass = null;
            if (stack.getItem() instanceof ItemPickaxe) {
                toolClass = "pickaxe";
            }
            else if (stack.getItem() instanceof ItemAxe) {
                toolClass = "axe";
            }
            else if (stack.getItem() instanceof ItemSpade) {
                toolClass = "shovel";
            }
            if (tool.equals(toolClass)) {
                toolLevel = ((IItemTool)stack.getItem()).getToolMaterial().getHarvestLevel();
            }
        }
        if (toolLevel < 0) {
            return stack.canHarvestBlock(state);
        }
        return toolLevel >= ((IBlock)block).getHarvestLevelNonForge(state);
    }
    
    public static int findBestTool(final BlockPos pos) {
        return findBestTool(pos, MineUtil.mc.world.getBlockState(pos));
    }
    
    public static int findBestTool(final BlockPos pos, final IBlockState state) {
        int result = MineUtil.mc.player.inventory.currentItem;
        if (state.getBlockHardness((World)MineUtil.mc.world, pos) > 0.0f) {
            double speed = getSpeed(state, MineUtil.mc.player.getHeldItemMainhand());
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = MineUtil.mc.player.inventory.getStackInSlot(i);
                final double stackSpeed = getSpeed(state, stack);
                if (stackSpeed > speed) {
                    speed = stackSpeed;
                    result = i;
                }
            }
        }
        return result;
    }
    
    public static double getSpeed(final IBlockState state, final ItemStack stack) {
        final double str = stack.getStrVsBlock(state);
        final int effect = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
        return Math.max(str + ((str > 1.0) ? (effect * effect + 1.0) : 0.0), 0.0);
    }
    
    public static float getDamage(final ItemStack stack, final BlockPos pos, final boolean onGround) {
        final IBlockState state = MineUtil.mc.world.getBlockState(pos);
        return getDamage(state, stack, pos, onGround);
    }
    
    public static float getDamage(final IBlockState state, final ItemStack stack, final BlockPos pos, final boolean onGround) {
        return getDigSpeed(stack, state, onGround) / (state.getBlockHardness((World)MineUtil.mc.world, pos) * (canHarvestBlock(pos, stack) ? 30 : 100));
    }
    
    private static float getDigSpeed(final ItemStack stack, final IBlockState state, final boolean onGround) {
        float digSpeed = 1.0f;
        if (!stack.func_190926_b()) {
            digSpeed *= stack.getStrVsBlock(state);
        }
        if (digSpeed > 1.0f) {
            final int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            if (i > 0 && !stack.func_190926_b()) {
                digSpeed += i * i + 1;
            }
        }
        if (MineUtil.mc.player.isPotionActive(MobEffects.HASTE)) {
            digSpeed *= 1.0f + (MineUtil.mc.player.getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1) * 0.2f;
        }
        if (MineUtil.mc.player.isPotionActive(MobEffects.MINING_FATIGUE)) {
            float miningFatigue = 0.0f;
            switch (MineUtil.mc.player.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) {
                case 0: {
                    miningFatigue = 0.3f;
                    break;
                }
                case 1: {
                    miningFatigue = 0.09f;
                    break;
                }
                case 2: {
                    miningFatigue = 0.0027f;
                    break;
                }
                default: {
                    miningFatigue = 8.1E-4f;
                    break;
                }
            }
            digSpeed *= miningFatigue;
        }
        if (MineUtil.mc.player.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier((EntityLivingBase)MineUtil.mc.player)) {
            digSpeed /= 5.0f;
        }
        if (onGround && !MineUtil.mc.player.onGround) {
            digSpeed /= 5.0f;
        }
        return (digSpeed < 0.0f) ? 0.0f : digSpeed;
    }
    
    public static boolean canBreak(final BlockPos pos) {
        return canBreak(MineUtil.mc.world.getBlockState(pos), pos);
    }
    
    public static boolean canBreak(final IBlockState state, final BlockPos pos) {
        return state.getBlockHardness((World)MineUtil.mc.world, pos) != -1.0f || state.getMaterial().isLiquid();
    }
    
    private static void setupHarvestLevels() {
        Set<Block> blocks = ReflectionUtil.getField(ItemPickaxe.class, (Object)null, 0);
        for (final Block block : blocks) {
            ((IBlock)block).setHarvestLevelNonForge("pickaxe", 0);
        }
        blocks = ReflectionUtil.getField(ItemSpade.class, (Object)null, 0);
        for (final Block block : blocks) {
            ((IBlock)block).setHarvestLevelNonForge("shovel", 0);
        }
        blocks = ReflectionUtil.getField(ItemAxe.class, (Object)null, 0);
        for (final Block block : blocks) {
            ((IBlock)block).setHarvestLevelNonForge("axe", 0);
        }
        ((IBlock)Blocks.OBSIDIAN).setHarvestLevelNonForge("pickaxe", 3);
        ((IBlock)Blocks.ENCHANTING_TABLE).setHarvestLevelNonForge("pickaxe", 0);
        final Block[] array;
        final Block[] oreBlocks = array = new Block[] { Blocks.EMERALD_ORE, Blocks.EMERALD_BLOCK, Blocks.DIAMOND_ORE, Blocks.DIAMOND_BLOCK, Blocks.GOLD_ORE, Blocks.GOLD_BLOCK, Blocks.REDSTONE_ORE, Blocks.LIT_REDSTONE_ORE };
        for (final Block block2 : array) {
            ((IBlock)block2).setHarvestLevelNonForge("pickaxe", 2);
        }
        ((IBlock)Blocks.IRON_ORE).setHarvestLevelNonForge("pickaxe", 1);
        ((IBlock)Blocks.IRON_BLOCK).setHarvestLevelNonForge("pickaxe", 1);
        ((IBlock)Blocks.LAPIS_ORE).setHarvestLevelNonForge("pickaxe", 1);
        ((IBlock)Blocks.LAPIS_BLOCK).setHarvestLevelNonForge("pickaxe", 1);
        ((IBlock)Blocks.QUARTZ_ORE).setHarvestLevelNonForge("pickaxe", 0);
    }
    
    static {
        setupHarvestLevels();
    }
}
