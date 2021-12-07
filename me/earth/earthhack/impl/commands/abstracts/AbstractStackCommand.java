//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.abstracts;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.text.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.impl.commands.gui.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.server.integrated.*;
import java.util.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.*;

public abstract class AbstractStackCommand extends Command implements Globals
{
    protected String stackName;
    
    public AbstractStackCommand(final String name, final String stackName) {
        this(new String[][] { { name } }, stackName);
    }
    
    public AbstractStackCommand(final String[][] args, final String stackName) {
        super(args);
        this.stackName = stackName;
    }
    
    protected abstract ItemStack getStack(final String[] p0);
    
    @Override
    public void execute(final String[] args) {
        if (AbstractStackCommand.mc.player == null) {
            ChatUtil.sendMessage("§cYou need to be ingame for this command.");
            return;
        }
        final boolean ghost = !AbstractStackCommand.mc.player.isCreative();
        boolean hotbar = true;
        int slot = InventoryUtil.findHotbarBlock(Blocks.AIR, new Block[0]);
        if (slot == -1) {
            hotbar = false;
            slot = findBlockNoDrag(Blocks.AIR);
            if (slot == -1) {
                Scheduler.getInstance().schedule(() -> AbstractStackCommand.mc.displayGuiScreen((GuiScreen)new YesNoNonPausing((result, id) -> {
                    AbstractStackCommand.mc.displayGuiScreen((GuiScreen)null);
                    if (result) {
                        this.setSlot(args, AbstractStackCommand.mc.player.inventory.currentItem, true, ghost);
                    }
                }, "§cYour inventory is full.", "Should your MainHand Slot be replaced?", 1337)));
                return;
            }
        }
        this.setSlot(args, slot, hotbar, ghost);
    }
    
    private void setSlot(final String[] args, int slot, final boolean hotbar, boolean ghost) {
        if (AbstractStackCommand.mc.player == null) {
            return;
        }
        final ItemStack stack = this.getStack(args);
        if (stack == null) {
            ChatUtil.sendMessage("<" + this.getName() + ">" + "§c" + " An error occurred.");
            return;
        }
        if (hotbar) {
            slot = InventoryUtil.hotbarToInventory(slot);
        }
        AbstractStackCommand.mc.player.inventoryContainer.putStackInSlot(slot, stack);
        if (AbstractStackCommand.mc.player.isCreative()) {
            AbstractStackCommand.mc.player.connection.sendPacket((Packet)new CPacketCreativeInventoryAction(slot, stack));
        }
        else if (AbstractStackCommand.mc.isSingleplayer()) {
            final EntityPlayerMP player = Objects.requireNonNull(AbstractStackCommand.mc.getIntegratedServer()).getPlayerList().getPlayerByUUID(AbstractStackCommand.mc.player.getUniqueID());
            if (player != null) {
                player.inventoryContainer.putStackInSlot(slot, stack);
                ghost = false;
            }
        }
        ChatUtil.sendMessage("§aGave you a " + (ghost ? "§c(ghost) " : "") + "§a" + this.stackName + ". It's in your " + "§f" + ((slot == 45) ? "Offhand" : (hotbar ? "Hotbar" : "Inventory")) + "§a" + ".");
    }
    
    public static int findBlockNoDrag(final Block block) {
        for (int i = 9; i < 45; ++i) {
            final ItemStack stack = (ItemStack)AbstractStackCommand.mc.player.inventoryContainer.getInventory().get(i);
            if (ItemUtil.areSame(stack, block)) {
                return i;
            }
        }
        return -1;
    }
}
