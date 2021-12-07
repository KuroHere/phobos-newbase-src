//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.item.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockArgument extends AbstractArgument<Block>
{
    public BlockArgument() {
        super(Block.class);
    }
    
    @Override
    public Block fromString(final String argument) throws ArgParseException {
        final Item item = ItemAddingModule.getItemStartingWith(argument, i -> i instanceof ItemBlock);
        Block block;
        if (item == null) {
            block = Block.getBlockFromName(argument);
            if (block == null) {
                try {
                    final int id = Integer.parseInt(argument);
                    block = Block.getBlockById(id);
                }
                catch (NumberFormatException e) {
                    block = null;
                }
                if (block == null) {
                    throw new ArgParseException("Couldn't parse Block from " + argument + "!");
                }
            }
        }
        else {
            if (!(item instanceof ItemBlock)) {
                throw new IllegalStateException("Item wasn't ItemBlock!");
            }
            block = ((ItemBlock)item).getBlock();
        }
        return block;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String arg) {
        if (arg == null || arg.isEmpty()) {
            return PossibleInputs.empty().setRest("<block>");
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        final String s = ItemAddingModule.getItemStartingWithDefault(arg, i -> i instanceof ItemBlock);
        if (s != null) {
            return inputs.setCompletion(TextUtil.substring(s, arg.length()));
        }
        for (final ResourceLocation location : Block.REGISTRY.getKeys()) {
            if (TextUtil.startsWith(location.toString(), arg)) {
                return inputs.setCompletion(TextUtil.substring(location.toString(), arg.length()));
            }
        }
        return PossibleInputs.empty();
    }
}
