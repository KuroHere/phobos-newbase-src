//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoregear;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.nbt.*;
import java.util.*;

final class ListenerTick extends ModuleListener<AutoRegear, TickEvent>
{
    public ListenerTick(final AutoRegear module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (ListenerTick.mc.currentScreen instanceof GuiShulkerBox) {
            for (int i = 0; i < 36; ++i) {
                if (((AutoRegear)this.module).delayTimer.passed(((AutoRegear)this.module).delay.getValue())) {
                    final Setting<?> setting = ((AutoRegear)this.module).getSettingFromSlot(i);
                    if (setting != null) {
                        final int itemId = Integer.parseInt(setting.getName().split(":")[1]);
                        if (itemId != 0) {
                            final Item item = Item.getItemById(itemId);
                            final int shulkerSlot = InventoryUtil.findItem(item, ((GuiShulkerBox)ListenerTick.mc.currentScreen).inventorySlots);
                            final ItemStack stackInSlot = (ItemStack)((GuiContainer)ListenerTick.mc.currentScreen).inventorySlots.getInventory().get(i + 27);
                            if (stackInSlot.getMaxStackSize() != 1 && stackInSlot.getMaxStackSize() != stackInSlot.func_190916_E() && (stackInSlot.getItem() == item || stackInSlot.getItem() == Items.field_190931_a) && shulkerSlot != -1) {
                                if (shulkerSlot <= 26) {
                                    ListenerTick.mc.playerController.windowClick(((GuiContainer)ListenerTick.mc.currentScreen).inventorySlots.windowId, shulkerSlot, 0, ClickType.PICKUP, (EntityPlayer)ListenerTick.mc.player);
                                    ListenerTick.mc.playerController.windowClick(((GuiContainer)ListenerTick.mc.currentScreen).inventorySlots.windowId, i + 27, 0, ClickType.PICKUP, (EntityPlayer)ListenerTick.mc.player);
                                    ListenerTick.mc.playerController.windowClick(((GuiContainer)ListenerTick.mc.currentScreen).inventorySlots.windowId, shulkerSlot, 0, ClickType.PICKUP, (EntityPlayer)ListenerTick.mc.player);
                                    ((AutoRegear)this.module).delayTimer.reset();
                                }
                            }
                        }
                    }
                }
            }
        }
        else if (ListenerTick.mc.currentScreen instanceof GuiChest && ((AutoRegear)this.module).shouldRegear && ((AutoRegear)this.module).grabShulker.getValue() && !((AutoRegear)this.module).hasKit()) {
            int slot = -1;
            ItemStack stack = null;
            for (int j = 0; j < 28; ++j) {
                boolean foundExp = false;
                boolean foundCrystals = false;
                boolean foundGapples = false;
                stack = (ItemStack)((GuiContainer)ListenerTick.mc.currentScreen).inventorySlots.getInventory().get(j);
                if (stack.getItem() instanceof ItemShulkerBox) {
                    final NBTTagCompound tagCompound = stack.getTagCompound();
                    if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
                        final NBTTagCompound blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag");
                        if (blockEntityTag.hasKey("Items", 9)) {
                            final NonNullList<ItemStack> nonNullList = (NonNullList<ItemStack>)NonNullList.func_191197_a(27, (Object)ItemStack.field_190927_a);
                            ItemStackHelper.func_191283_b(blockEntityTag, (NonNullList)nonNullList);
                            for (final ItemStack stack2 : nonNullList) {
                                if (stack2.getItem() == Items.GOLDEN_APPLE) {
                                    foundGapples = true;
                                }
                                else if (stack2.getItem() == Items.EXPERIENCE_BOTTLE) {
                                    foundExp = true;
                                }
                                else {
                                    if (stack2.getItem() != Items.END_CRYSTAL) {
                                        continue;
                                    }
                                    foundCrystals = true;
                                }
                            }
                            if (foundExp && foundGapples && foundCrystals) {
                                slot = j;
                            }
                            else if (!((AutoRegear)this.module).hasKit() && ((AutoRegear)this.module).getShulkerBox() == null) {
                                ((AutoRegear)this.module).shouldRegear = false;
                                ListenerTick.mc.player.closeScreen();
                                return;
                            }
                        }
                    }
                }
            }
            if (slot != -1) {
                final int emptySlot = InventoryUtil.findInInventory(stack -> stack.func_190926_b() || stack.getItem() == Items.field_190931_a, false);
                if (emptySlot != -1) {
                    ListenerTick.mc.playerController.windowClick(((GuiContainer)ListenerTick.mc.currentScreen).inventorySlots.windowId, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)ListenerTick.mc.player);
                }
            }
            ListenerTick.mc.player.closeScreen();
        }
    }
}
