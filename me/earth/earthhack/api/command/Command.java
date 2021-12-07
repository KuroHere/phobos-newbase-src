// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.command;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.util.*;

public abstract class Command implements Nameable
{
    private final String name;
    private final String fullUsage;
    private final String[][] usage;
    private final boolean hidden;
    
    public Command(final String[][] usage) {
        this(usage, false);
    }
    
    public Command(final String[][] usage, final boolean hidden) {
        if (usage == null || usage.length == 0 || usage[0].length != 1) {
            throw new IllegalArgumentException("Usage of command needs to be an 2 dimensional array with a length > 0 and the first entry needs to have a length of 1.");
        }
        this.name = usage[0][0];
        this.usage = usage;
        this.hidden = hidden;
        this.fullUsage = this.concatenateUsage(0);
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public boolean fits(final String[] args) {
        return args[0].length() > 0 && TextUtil.startsWith(this.name, args[0]);
    }
    
    public abstract void execute(final String[] p0);
    
    public PossibleInputs getPossibleInputs(final String[] args) {
        if (args == null || args.length == 0) {
            return PossibleInputs.empty();
        }
        if (args.length == 1) {
            final String completion = TextUtil.substring(this.name, args[0].length());
            final String rest = TextUtil.substring(this.getFullUsage(), this.name.length());
            return new PossibleInputs(completion, rest);
        }
        if (args.length <= this.usage.length) {
            final String last = this.getLast(args);
            final String completion2 = TextUtil.substring(last, args[args.length - 1].length());
            return new PossibleInputs(completion2, this.concatenateUsage(args.length));
        }
        return PossibleInputs.empty();
    }
    
    public Completer onTabComplete(final Completer completer) {
        if (completer.isSame() && completer.getArgs().length <= this.usage.length) {
            String[] args;
            int i;
            for (args = this.usage[completer.getArgs().length - 1], i = 0; i < args.length && !args[i].equalsIgnoreCase(completer.getArgs()[completer.getArgs().length - 1]); ++i) {}
            final String arg = (i >= args.length - 1) ? args[0] : args[i + 1];
            final String newInitial = completer.getInitial().trim().substring(0, completer.getInitial().trim().length() - completer.getArgs()[completer.getArgs().length - 1].length());
            completer.setResult(newInitial + arg);
            return completer;
        }
        final PossibleInputs inputs = this.getPossibleInputs(completer.getArgs());
        if (!inputs.getCompletion().isEmpty()) {
            completer.setResult(completer.getInitial().trim() + inputs.getCompletion());
            return completer;
        }
        completer.setMcComplete(true);
        return completer;
    }
    
    public String[][] getUsage() {
        return this.usage;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public String getFullUsage() {
        return this.fullUsage;
    }
    
    private String getLast(final String[] args) {
        if (args.length <= this.usage.length) {
            final String last = args[args.length - 1];
            final String[] array2;
            final String[] array = array2 = this.usage[args.length - 1];
            for (final String string : array2) {
                if (TextUtil.startsWith(string, last)) {
                    return string;
                }
            }
        }
        return "";
    }
    
    private String concatenateUsage(final int index) {
        if (this.usage.length == 1) {
            return this.name;
        }
        if (index >= this.usage.length) {
            return "";
        }
        final StringBuilder builder = new StringBuilder((index == 0) ? this.name : "");
        for (int j = (index == 0) ? 1 : index; j < this.usage.length; ++j) {
            builder.append(" <");
            for (int i = 0; i < this.usage[j].length; ++i) {
                builder.append(this.usage[j][i]).append("/");
            }
            builder.replace(builder.length() - 1, builder.length(), ">");
        }
        return builder.toString();
    }
}
