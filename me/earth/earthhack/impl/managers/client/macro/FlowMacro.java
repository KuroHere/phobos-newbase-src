// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client.macro;

import me.earth.earthhack.api.util.bind.*;

public class FlowMacro extends Macro
{
    public FlowMacro(final String name, final Bind bind, final Macro... macros) {
        super(name, bind, MacroUtil.concatenateCommands(macros));
    }
    
    @Override
    public MacroType getType() {
        return MacroType.FLOW;
    }
}
