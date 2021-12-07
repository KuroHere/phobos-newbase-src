// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.hidden;

import me.earth.earthhack.impl.commands.abstracts.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.managers.client.macro.*;

public class HMacroFlowCommand extends AbstractMultiMacroCommand<FlowMacro>
{
    public HMacroFlowCommand() {
        super(new String[][] { { "flow" }, { "macro" }, { "macro" }, { "..." } }, "FlowMacro", "Please specify 2 or more macros that should flow into each other.");
    }
    
    @Override
    protected FlowMacro getMacro(final String name, final Bind bind, final Macro... macros) {
        return new FlowMacro(name, bind, macros);
    }
}
