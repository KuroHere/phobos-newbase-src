// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.command;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.command.*;

public abstract class AddableCommandModule extends Module implements CustomCommandModule
{
    private static final String[] ARGS;
    
    public AddableCommandModule(final String name, final Category category) {
        super(name, category);
    }
    
    public abstract void add(final String p0);
    
    public abstract void del(final String p0);
    
    public abstract PossibleInputs getSettingInput(final String p0, final String[] p1);
    
    public void add(final String[] args) {
        this.add(CommandUtil.concatenate(args, 2));
    }
    
    public void del(final String[] args) {
        this.del(CommandUtil.concatenate(args, 2));
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length <= 1) {
            return false;
        }
        final boolean add = args[1].equalsIgnoreCase("add");
        if (!add && !args[1].equalsIgnoreCase("del")) {
            return false;
        }
        if (args.length == 2) {
            ChatUtil.sendMessage("§cPlease specify what to add/delete!");
            return true;
        }
        if (add) {
            this.add(args);
        }
        else {
            this.del(args);
        }
        return true;
    }
    
    @Override
    public boolean getInput(final String[] args, final PossibleInputs inputs) {
        if (args == null || args.length < 1) {
            return false;
        }
        if (args.length == 1) {
            final String name = this.getName();
            inputs.setCompletion(TextUtil.substring(name, args[0].length())).setRest(" <add/del/setting> <value>");
            return true;
        }
        if (!args[0].equalsIgnoreCase(this.getName())) {
            return false;
        }
        final String lower = args[1].toLowerCase();
        if ("add".startsWith(lower) || "del".startsWith(lower)) {
            final String conc = (args.length == 2) ? args[1] : CommandUtil.concatenate(args, 1);
            final String[] sub = new String[args.length - 1];
            System.arraycopy(args, 1, sub, 0, args.length - 1);
            final PossibleInputs si = this.getSettingInput(conc, sub);
            inputs.setCompletion(si.getCompletion()).setRest(si.getRest());
            return true;
        }
        return false;
    }
    
    @Override
    public CustomCompleterResult complete(final Completer completer) {
        if (!completer.isSame() && completer.getArgs().length == 2) {
            return CustomCompleterResult.SUPER;
        }
        return CustomCompleterResult.PASS;
    }
    
    @Override
    public String[] getArgs() {
        return AddableCommandModule.ARGS;
    }
    
    static {
        ARGS = new String[] { "add", "del" };
    }
}
