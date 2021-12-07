//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import java.util.function.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.inventory.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.init.*;

public class InventoryUtil implements Globals
{
    public static final ItemStack ILLEGAL_STACK;
    
    public static void switchTo(final int slot) {
        if (InventoryUtil.mc.player.inventory.currentItem != slot && slot > -1 && slot < 9) {
            InventoryUtil.mc.player.inventory.currentItem = slot;
            syncItem();
        }
    }
    
    public static void switchToBypass(final int slot) {
        if (InventoryUtil.mc.player.inventory.currentItem != slot && slot > -1 && slot < 9) {
            Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
                final int lastSlot = InventoryUtil.mc.player.inventory.currentItem;
                final int targetSlot = hotbarToInventory(slot);
                final int currentSlot = hotbarToInventory(lastSlot);
                InventoryUtil.mc.playerController.windowClick(0, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
                InventoryUtil.mc.playerController.windowClick(0, currentSlot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
                InventoryUtil.mc.playerController.windowClick(0, targetSlot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
            });
        }
    }
    
    public static void switchToBypassAlt(final int slot) {
        if (slot != -1) {
            Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> InventoryUtil.mc.playerController.windowClick(0, slot, InventoryUtil.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)InventoryUtil.mc.player));
        }
    }
    
    public static void bypassSwitch(final int slot) {
        if (slot >= 0) {
            InventoryUtil.mc.playerController.pickItem(slot);
        }
    }
    
    public static void illegalSync() {
        if (InventoryUtil.mc.player != null) {
            PacketUtil.click(0, 0, 0, ClickType.PICKUP, InventoryUtil.ILLEGAL_STACK, (short)0);
        }
    }
    
    public static int findHotbarBlock(final Block block, final Block... optional) {
        return findInHotbar(s -> ItemUtil.areSame(s, block), (Iterable<Predicate<ItemStack>>)CollectionUtil.convert(optional, o -> s -> ItemUtil.areSame(s, o)));
    }
    
    public static int findHotbarItem(final Item item, final Item... optional) {
        return findInHotbar(s -> ItemUtil.areSame(s, item), (Iterable<Predicate<ItemStack>>)CollectionUtil.convert(optional, o -> s -> ItemUtil.areSame(s, o)));
    }
    
    public static int findInHotbar(final Predicate<ItemStack> condition) {
        return findInHotbar(condition, true);
    }
    
    public static int findInHotbar(final Predicate<ItemStack> condition, final boolean offhand) {
        if (offhand && condition.test(InventoryUtil.mc.player.getHeldItemOffhand())) {
            return -2;
        }
        int result = -1;
        for (int i = 8; i > -1 && (!condition.test(InventoryUtil.mc.player.inventory.getStackInSlot(i)) || InventoryUtil.mc.player.inventory.currentItem != (result = i)); --i) {}
        return result;
    }
    
    public static int findInInventory(final Predicate<ItemStack> condition, final boolean xCarry) {
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = (ItemStack)InventoryUtil.mc.player.inventoryContainer.getInventory().get(i);
            if (condition.test(stack)) {
                return i;
            }
        }
        if (xCarry) {
            for (int i = 1; i < 5; ++i) {
                final ItemStack stack = (ItemStack)InventoryUtil.mc.player.inventoryContainer.getInventory().get(i);
                if (condition.test(stack)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static int findInCraftingTable(final Container container, final Predicate<ItemStack> condition) {
        for (int i = 11; i < 47; ++i) {
            final ItemStack stack = (ItemStack)container.getInventory().get(i);
            if (condition.test(stack)) {
                return i;
            }
        }
        return -1;
    }
    
    public static int findInHotbar(final Predicate<ItemStack> condition, final Iterable<Predicate<ItemStack>> optional) {
        int result = findInHotbar(condition);
        if (result == -1) {
            for (final Predicate<ItemStack> opt : optional) {
                result = findInHotbar(opt);
                if (result != -1) {
                    break;
                }
            }
        }
        return result;
    }
    
    public static int findBlock(final Block block, final boolean xCarry) {
        if (ItemUtil.areSame(InventoryUtil.mc.player.inventory.getItemStack(), block)) {
            return -2;
        }
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = (ItemStack)InventoryUtil.mc.player.inventoryContainer.getInventory().get(i);
            if (ItemUtil.areSame(stack, block)) {
                return i;
            }
        }
        if (xCarry) {
            for (int i = 1; i < 5; ++i) {
                final ItemStack stack = (ItemStack)InventoryUtil.mc.player.inventoryContainer.getInventory().get(i);
                if (ItemUtil.areSame(stack, block)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static int findItem(final Item item, final Container container) {
        for (int i = 0; i < container.getInventory().size(); ++i) {
            final ItemStack stack = (ItemStack)container.getInventory().get(i);
            if (stack.getItem() == item) {
                return i;
            }
        }
        return -1;
    }
    
    public static int findItem(final Item item, final boolean xCarry) {
        return findItem(item, xCarry, Collections.emptySet());
    }
    
    public static int findItem(final Item item, final boolean xCarry, final Set<Integer> ignore) {
        if (InventoryUtil.mc.player.inventory.getItemStack().getItem() == item && !ignore.contains(-2)) {
            return -2;
        }
        for (int i = 9; i < 45; ++i) {
            if (!ignore.contains(i)) {
                if (get(i).getItem() == item) {
                    return i;
                }
            }
        }
        if (xCarry) {
            for (int i = 1; i < 5; ++i) {
                if (!ignore.contains(i)) {
                    if (get(i).getItem() == item) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static int getCount(final Item item) {
        int result = 0;
        for (int i = 0; i < 46; ++i) {
            final ItemStack stack = (ItemStack)InventoryUtil.mc.player.inventoryContainer.getInventory().get(i);
            if (stack.getItem() == item) {
                result += stack.func_190916_E();
            }
        }
        if (InventoryUtil.mc.player.inventory.getItemStack().getItem() == item) {
            result += InventoryUtil.mc.player.inventory.getItemStack().func_190916_E();
        }
        return result;
    }
    
    public static boolean isHoldingServer(final Item item) {
        final ItemStack offHand = InventoryUtil.mc.player.getHeldItemOffhand();
        if (ItemUtil.areSame(offHand, item)) {
            return true;
        }
        final ItemStack mainHand = InventoryUtil.mc.player.getHeldItemMainhand();
        if (ItemUtil.areSame(mainHand, item)) {
            final int current = InventoryUtil.mc.player.inventory.currentItem;
            final int server = getServerItem();
            return server == current;
        }
        return false;
    }
    
    public static boolean isHolding(final Class<?> clazz) {
        return clazz.isAssignableFrom(InventoryUtil.mc.player.getHeldItemMainhand().getItem().getClass()) || clazz.isAssignableFrom(InventoryUtil.mc.player.getHeldItemOffhand().getItem().getClass());
    }
    
    public static boolean isHolding(final Item item) {
        return isHolding((EntityLivingBase)InventoryUtil.mc.player, item);
    }
    
    public static boolean isHolding(final Block block) {
        return isHolding((EntityLivingBase)InventoryUtil.mc.player, block);
    }
    
    public static boolean isHolding(final EntityLivingBase entity, final Item item) {
        final ItemStack mainHand = entity.getHeldItemMainhand();
        final ItemStack offHand = entity.getHeldItemOffhand();
        return ItemUtil.areSame(mainHand, item) || ItemUtil.areSame(offHand, item);
    }
    
    public static boolean isHolding(final EntityLivingBase entity, final Block block) {
        final ItemStack mainHand = entity.getHeldItemMainhand();
        final ItemStack offHand = entity.getHeldItemOffhand();
        return ItemUtil.areSame(mainHand, block) || ItemUtil.areSame(offHand, block);
    }
    
    public static EnumHand getHand(final int slot) {
        return (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
    }
    
    public static EnumHand getHand(final Item item) {
        return (InventoryUtil.mc.player.getHeldItemMainhand().getItem() == item) ? EnumHand.MAIN_HAND : ((InventoryUtil.mc.player.getHeldItemOffhand().getItem() == item) ? EnumHand.OFF_HAND : null);
    }
    
    public static boolean validScreen() {
        return !(InventoryUtil.mc.currentScreen instanceof GuiContainer) || InventoryUtil.mc.currentScreen instanceof GuiInventory;
    }
    
    public static int getServerItem() {
        return ((IPlayerControllerMP)InventoryUtil.mc.playerController).getItem();
    }
    
    public static void syncItem() {
        ((IPlayerControllerMP)InventoryUtil.mc.playerController).syncItem();
    }
    
    public static void click(final int slot) {
        InventoryUtil.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.player);
    }
    
    public static ItemStack get(final int slot) {
        if (slot == -2) {
            return InventoryUtil.mc.player.inventory.getItemStack();
        }
        return (ItemStack)InventoryUtil.mc.player.inventoryContainer.getInventory().get(slot);
    }
    
    public static NonNullList<ItemStack> getInventory() {
        return (NonNullList<ItemStack>)InventoryUtil.mc.player.inventoryContainer.getInventory();
    }
    
    public static void put(final int slot, final ItemStack stack) {
        if (slot == -2) {
            InventoryUtil.mc.player.inventory.setItemStack(stack);
        }
        InventoryUtil.mc.player.inventoryContainer.putStackInSlot(slot, stack);
    }
    
    public static int findEmptyHotbarSlot() {
        int result = -1;
        for (int i = 8; i > -1; --i) {
            final ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (stack.func_190926_b() || stack.getItem() == Items.field_190931_a) {
                result = i;
            }
        }
        return result;
    }
    
    public static int hotbarToInventory(final int slot) {
        if (slot == -2) {
            return 45;
        }
        if (slot > -1 && slot < 9) {
            return 36 + slot;
        }
        return slot;
    }
    
    public static boolean canStack(final ItemStack inSlot, final ItemStack stack) {
        return inSlot.func_190926_b() || (inSlot.getItem() == stack.getItem() && inSlot.getMaxStackSize() > 1 && (!inSlot.getHasSubtypes() || inSlot.getMetadata() == stack.getMetadata()) && ItemStack.areItemStackTagsEqual(inSlot, stack));
    }
    
    public static boolean equals(final ItemStack stack1, final ItemStack stack2) {
        if (stack1 == null) {
            return stack2 == null;
        }
        if (stack2 == null) {
            return false;
        }
        final boolean empty1 = stack1.func_190926_b();
        final boolean empty2 = stack2.func_190926_b();
        return empty1 == empty2 && stack1.getDisplayName().equals(stack2.getDisplayName()) && stack1.getItem() == stack1.getItem() && stack1.getHasSubtypes() == stack2.getHasSubtypes() && stack1.getMetadata() == stack2.getMetadata() && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
    
    public static void clickLocked(final int slot, final int to, final Item inSlot, final Item inTo) {
        Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
            if ((slot == -1 || get(slot).getItem() == inSlot) && get(to).getItem() == inTo) {
                final boolean multi = slot >= 0;
                if (multi) {
                    Managers.NCP.startMultiClick();
                    click(slot);
                }
                click(to);
                if (multi) {
                    Managers.NCP.releaseMultiClick();
                }
            }
        });
    }
    
    static {
        ILLEGAL_STACK = new ItemStack(Item.getItemFromBlock(Blocks.BEDROCK));
    }
}
