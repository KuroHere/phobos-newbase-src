//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import net.minecraft.util.*;
import java.util.*;

public class ItemArgument extends AbstractArgument<Item>
{
    public ItemArgument() {
        super(Item.class);
    }
    
    @Override
    public Item fromString(final String argument) throws ArgParseException {
        Item item = ItemAddingModule.getItemStartingWith(argument, i -> true);
        if (item != null) {
            return item;
        }
        item = Item.getByNameOrId(argument);
        if (item == null) {
            throw new ArgParseException("Could not parse Item from " + argument + ".");
        }
        return item;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String arg) {
        if (arg == null || arg.isEmpty()) {
            return PossibleInputs.empty().setRest("<item>");
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        final String s = ItemAddingModule.getItemStartingWithDefault(arg, i -> true);
        if (s != null) {
            return inputs.setCompletion(TextUtil.substring(s, arg.length()));
        }
        for (final ResourceLocation location : Item.REGISTRY.getKeys()) {
            if (TextUtil.startsWith(location.toString(), arg)) {
                return inputs.setCompletion(TextUtil.substring(location.toString(), arg.length()));
            }
        }
        return PossibleInputs.empty();
    }
}
