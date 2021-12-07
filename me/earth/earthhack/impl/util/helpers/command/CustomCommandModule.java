// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.command;

import me.earth.earthhack.api.command.*;

public interface CustomCommandModule
{
    public static final String[] DEFAULT_ARGS = new String[0];
    
    default boolean execute(final String[] args) {
        return false;
    }
    
    default boolean getInput(final String[] args, final PossibleInputs inputs) {
        return false;
    }
    
    default CustomCompleterResult complete(final Completer completer) {
        return CustomCompleterResult.PASS;
    }
    
    default String[] getArgs() {
        return CustomCommandModule.DEFAULT_ARGS;
    }
}
