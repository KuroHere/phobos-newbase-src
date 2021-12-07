// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.hidden;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.managers.client.macro.*;

public class HMacroCombineCommand extends AbstractMultiMacroCommand<CombinedMacro>
{
    public HMacroCombineCommand() {
        super(new String[][] { { "combine" }, { "macro" }, { "macro" }, { "..." } }, "CombinedMacro", "Please specify 2 or more macros to combine.");
    }
    
    @Override
    protected CombinedMacro getMacro(final String name, final Bind bind, final Macro... macros) {
        return new CombinedMacro(name, bind, macros);
    }
}
