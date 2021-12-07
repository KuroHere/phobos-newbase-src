// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.addable;

import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;

public abstract class LoadableModule extends AddableModule
{
    public LoadableModule(final String name, final Category category, final String command, final String descriptor) {
        super(name, category, command, descriptor);
    }
    
    protected abstract void load(final String p0, final boolean p1);
    
    protected abstract String getLoadableStartingWith(final String p0);
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length > 1 && args[1].equalsIgnoreCase("load")) {
            if (args.length == 2) {
                this.load(null, true);
            }
            else {
                this.load(args[2], false);
            }
            return true;
        }
        return super.execute(args);
    }
    
    @Override
    public boolean getInput(final String[] args, final PossibleInputs inputs) {
        if (args == null) {
            return false;
        }
        if (args.length == 1) {
            final String name = this.getName();
            inputs.setCompletion(TextUtil.substring(name, args[0].length())).setRest(" <add/del/load/setting> <value>");
            return true;
        }
        if (args.length > 1 && "load".startsWith(args[1].toLowerCase())) {
            if (args.length == 2) {
                inputs.setCompletion(TextUtil.substring("load", args[1].length())).setRest(" <" + this.descriptor + ">");
            }
            else {
                final String s = this.getLoadableStartingWith(args[2]);
                if (s == null) {
                    inputs.setCompletion("").setRest("");
                }
                else {
                    inputs.setCompletion(TextUtil.substring(s, args[2].length())).setRest("");
                }
            }
            return true;
        }
        return super.getInput(args, inputs);
    }
}
