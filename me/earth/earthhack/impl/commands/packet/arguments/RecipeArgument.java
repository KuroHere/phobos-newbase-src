// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.item.crafting.*;
import me.earth.earthhack.api.command.*;

public class RecipeArgument extends AbstractArgument<IRecipe>
{
    public RecipeArgument() {
        super(IRecipe.class);
    }
    
    @Override
    public IRecipe fromString(final String argument) throws ArgParseException {
        int id;
        try {
            id = Integer.parseInt(argument);
        }
        catch (Exception e) {
            throw new ArgParseException("Could not parse Recipe-ID to integer: " + argument + "!");
        }
        final IRecipe recipe = CraftingManager.func_193374_a(id);
        if (recipe == null) {
            throw new ArgParseException("No Recipe found for id: " + id + "!");
        }
        return recipe;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return new PossibleInputs("", "<Recipe-ID>");
        }
        return PossibleInputs.empty();
    }
}
