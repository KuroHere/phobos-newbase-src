// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.macro;

import java.util.*;

public class MacroUtil
{
    public static String[] concatenateCommands(final Macro... macros) {
        final List<String> commands = new ArrayList<String>();
        for (final Macro macro : macros) {
            commands.addAll(Arrays.asList(macro.getCommands()));
        }
        return commands.toArray(new String[0]);
    }
}
