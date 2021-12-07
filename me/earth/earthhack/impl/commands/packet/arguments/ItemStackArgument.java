//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.impl.commands.util.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import me.earth.earthhack.api.command.*;

public class ItemStackArgument extends AbstractArgument<ItemStack>
{
    private static final ItemArgument ITEM_ARGUMENT;
    
    public ItemStackArgument() {
        super(ItemStack.class);
    }
    
    @Override
    public ItemStack fromString(final String argument) throws ArgParseException {
        final String[] args = argument.split(",");
        if (args.length == 0) {
            throw new ArgParseException("ItemStack was empty?");
        }
        final Item item = ItemStackArgument.ITEM_ARGUMENT.fromString(args[0]);
        int size = new ItemStack(item).getMaxStackSize();
        if (args.length > 1) {
            try {
                size = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException e) {
                throw new ArgParseException("Could not parse " + argument + " as ItemStack size!");
            }
        }
        int meta = 0;
        if (args.length > 2) {
            try {
                meta = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException e2) {
                throw new ArgParseException("Could not parse " + argument + " as ItemStack meta!");
            }
        }
        final ItemStack stack = new ItemStack(item, size, meta);
        if (args.length > 3) {
            final String conc = CommandUtil.concatenate(args, 3);
            try {
                stack.setTagCompound(JsonToNBT.getTagFromJson(conc));
            }
            catch (NBTException nbtexception) {
                throw new ArgParseException("Could not parse ItemStack NBT from " + conc + "!");
            }
        }
        return stack;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty()) {
            return inputs.setRest("<ItemStack:Item,Size,Meta,NBT>");
        }
        final String[] args = argument.split(",");
        if (args.length == 1) {
            if (args[0].isEmpty()) {
                return inputs.setRest("Item,Size,Meta,NBT");
            }
            inputs = ItemStackArgument.ITEM_ARGUMENT.getPossibleInputs(args[0]);
            inputs.setCompletion(inputs.getCompletion() + ",");
            inputs.setRest(",Size,Meta,NBT");
        }
        if (args.length == 2) {
            return inputs.setCompletion(",").setRest(",Meta,NBT");
        }
        if (args.length == 3) {
            return inputs.setCompletion(",").setRest("NBT");
        }
        return inputs;
    }
    
    static {
        ITEM_ARGUMENT = new ItemArgument();
    }
}
