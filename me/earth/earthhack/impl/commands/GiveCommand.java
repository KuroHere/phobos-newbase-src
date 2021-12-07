//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.init.*;

public class GiveCommand extends AbstractStackCommand
{
    private boolean local;
    private int amount;
    private Item item;
    
    public GiveCommand() {
        super(new String[][] { { "give" }, { "amount", "local" }, { "item/block" } }, "");
        this.local = true;
        CommandDescriptions.register(this, "Gives you an Item.");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.sendMessage("Use this command to give yourself an item.");
            return;
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("local")) {
                this.local = !this.local;
                if (this.local) {
                    Managers.CHAT.sendDeleteMessage("§aThe Give command now uses localized names, that means that you can use normal names like §bEnder Chest§a now.", "giveCommand", 3000);
                }
                else {
                    Managers.CHAT.sendDeleteMessage("§aThe Give command now uses ids, that means that you need to use names like §bminecraft:apple§a or ids now.", "giveCommand", 3000);
                }
                return;
            }
            ChatUtil.sendMessage("Please specify an item.");
        }
        else {
            int amount;
            try {
                amount = Integer.parseInt(args[1]);
            }
            catch (Exception e) {
                ChatUtil.sendMessage("§cCould not parse §f" + args[1] + "§c" + " to a number!");
                return;
            }
            final String conc = CommandUtil.concatenate(args, 2);
            if (this.local) {
                this.item = ItemAddingModule.getItemStartingWith(conc, i -> true);
            }
            else {
                this.item = Item.getByNameOrId(conc);
            }
            if (this.item == null) {
                ChatUtil.sendMessage("§cCould not find item §f" + conc + "§c" + "! Give command currently uses " + (this.local ? "localized names." : "ids."));
                return;
            }
            this.amount = amount;
            this.stackName = conc;
            super.execute(args);
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length <= 1) {
            return super.getPossibleInputs(args);
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        if ("local".startsWith(args[1].toLowerCase())) {
            if (args.length > 2) {
                return inputs;
            }
            return inputs.setCompletion(TextUtil.substring("local", args[1].length()));
        }
        else if (args.length == 2) {
            if (args[1].isEmpty()) {
                return inputs.setRest("<amount/local> <item/block>");
            }
            return inputs.setRest(" <item/block>");
        }
        else {
            final String conc = CommandUtil.concatenate(args, 2);
            if (conc.isEmpty()) {
                return inputs.setRest(" <item/block>");
            }
            if (this.local) {
                final String s = ItemAddingModule.getItemStartingWithDefault(conc, i -> true);
                if (s != null) {
                    inputs.setCompletion(TextUtil.substring(s, conc.length()));
                }
            }
            else {
                if (args.length == 3 && Character.isDigit(conc.charAt(0))) {
                    try {
                        final int id = Integer.parseInt(args[2]);
                        final Item item = Item.getItemById(id);
                        if (item != null) {
                            return inputs.setRest(" <" + item.getItemStackDisplayName(new ItemStack(item)) + ">");
                        }
                    }
                    catch (Exception ex) {}
                }
                for (final ResourceLocation location : Item.REGISTRY.getKeys()) {
                    if (TextUtil.startsWith(location.toString(), conc)) {
                        return inputs.setCompletion(TextUtil.substring(location.toString(), conc.length()));
                    }
                }
            }
            return inputs;
        }
    }
    
    @Override
    protected ItemStack getStack(final String[] args) {
        if (this.item == null) {
            final ItemStack stack = new ItemStack(Items.WRITTEN_BOOK);
            stack.setStackDisplayName("§cERROR");
            return stack;
        }
        final ItemStack stack = new ItemStack(this.item);
        stack.func_190920_e(this.amount);
        return stack;
    }
}
