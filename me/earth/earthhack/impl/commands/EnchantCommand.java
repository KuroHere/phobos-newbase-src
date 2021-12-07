//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.util.text.*;
import net.minecraft.enchantment.*;
import me.earth.earthhack.impl.commands.util.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.server.integrated.*;
import java.util.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import net.minecraft.util.text.translation.*;

public class EnchantCommand extends Command implements Globals
{
    public EnchantCommand() {
        super(new String[][] { { "enchant" }, { "level" }, { "enchantment" } });
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("§cPlease specify a level!");
            return;
        }
        if (EnchantCommand.mc.player == null) {
            ChatUtil.sendMessage("§cYou need to be ingame to use this command!");
            return;
        }
        short level;
        try {
            level = (short)Integer.parseInt(args[1]);
        }
        catch (Exception e) {
            ChatUtil.sendMessage("§cCould not parse level §f" + args[1] + "§c" + "!");
            return;
        }
        final ItemStack stack = EnchantCommand.mc.player.inventory.getCurrentItem();
        if (stack.func_190926_b()) {
            ChatUtil.sendMessage("§cYou need to be holding an item for this command!");
            return;
        }
        if (args.length <= 2) {
            for (final Enchantment enchantment : Enchantment.REGISTRY) {
                if (!enchantment.func_190936_d()) {
                    stack.addEnchantment(enchantment, (int)level);
                }
            }
            this.setStack(stack);
            return;
        }
        final String conc = CommandUtil.concatenate(args, 2);
        Enchantment enchantment = getEnchantment(conc);
        if (enchantment == null) {
            ChatUtil.sendMessage("§cCould find Enchantment §f" + conc + "§c" + "!");
            return;
        }
        stack.addEnchantment(enchantment, (int)level);
        this.setStack(stack);
    }
    
    private void setStack(final ItemStack stack) {
        final int slot = EnchantCommand.mc.player.inventory.currentItem + 36;
        if (EnchantCommand.mc.player.isCreative()) {
            EnchantCommand.mc.player.connection.sendPacket((Packet)new CPacketCreativeInventoryAction(slot, stack));
        }
        else if (EnchantCommand.mc.isSingleplayer()) {
            final EntityPlayerMP player = Objects.requireNonNull(EnchantCommand.mc.getIntegratedServer()).getPlayerList().getPlayerByUUID(EnchantCommand.mc.player.getUniqueID());
            if (player != null) {
                player.inventoryContainer.putStackInSlot(slot, stack);
            }
        }
        else {
            ChatUtil.sendMessage("§cNot Creative and not Singleplayer: Enchantments are §bghost §cenchantments!");
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length <= 1) {
            return super.getPossibleInputs(args);
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        if (args.length == 2) {
            inputs.setRest(" <enchantment>");
            if (args[1].isEmpty()) {
                return inputs.setRest("<level>");
            }
            return inputs;
        }
        else {
            if (args[2].isEmpty()) {
                return inputs.setRest("<enchantment>");
            }
            final String conc = CommandUtil.concatenate(args, 2);
            final String s = getEnchantmentStartingWith(conc);
            if (s != null) {
                inputs.setCompletion(TextUtil.substring(s, conc.length()));
            }
            return inputs;
        }
    }
    
    public static String getEnchantmentStartingWith(final String prefix) {
        final Enchantment enchantment = getEnchantment(prefix);
        if (enchantment != null) {
            return I18n.translateToLocal(enchantment.getName());
        }
        return null;
    }
    
    public static Enchantment getEnchantment(String prefix) {
        prefix = prefix.toLowerCase();
        for (final Enchantment enchantment : Enchantment.REGISTRY) {
            final String s = I18n.translateToLocal(enchantment.getName());
            if (s.toLowerCase().startsWith(prefix)) {
                return enchantment;
            }
        }
        return null;
    }
}
