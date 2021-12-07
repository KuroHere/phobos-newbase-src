// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.abstracts;

import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.commands.util.*;

public abstract class AbstractModuleCommand extends Command
{
    protected final int index;
    
    public AbstractModuleCommand(final String name, final String[][] additionalArgs) {
        this(concatArray(name, additionalArgs), 1);
    }
    
    public AbstractModuleCommand(final String[][] usage, final int index) {
        super(usage);
        if (index < 0) {
            throw new IllegalArgumentException("Index is smaller than 0!");
        }
        this.index = index;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String[] args) {
        final PossibleInputs inputs = super.getPossibleInputs(args);
        if (args.length > this.index) {
            final Module module = this.getModule(args, this.index);
            if (module == null) {
                inputs.setCompletion("").setRest("§c not found.");
            }
            else if (args.length == this.index + 1) {
                inputs.setCompletion(TextUtil.substring(module.getName(), args[this.index].length()));
            }
        }
        return inputs;
    }
    
    protected Module getModule(final String[] args, final int index) {
        if (args.length <= index) {
            return null;
        }
        return CommandUtil.getNameableStartingWith(args[index], Managers.MODULES.getRegistered());
    }
    
    private static String[][] concatArray(final String name, final String[][] args) {
        final String[][] concat = new String[args.length + 2][];
        concat[0] = new String[] { name };
        concat[1] = new String[] { "module" };
        System.arraycopy(args, 0, concat, 2, args.length);
        return concat;
    }
}
