// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.abstracts;

import me.earth.earthhack.api.command.*;

public abstract class AbstractTextCommand extends Command
{
    public AbstractTextCommand(final String name) {
        super(new String[][] { { name }, { "message" } });
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args.length > 1) {
            return PossibleInputs.empty();
        }
        return super.getPossibleInputs(args);
    }
    
    @Override
    public Completer onTabComplete(final Completer completer) {
        if (completer.getArgs().length > 1 || completer.getArgs()[0].equalsIgnoreCase(this.getName())) {
            completer.setMcComplete(true);
            return completer;
        }
        return super.onTabComplete(completer);
    }
}
