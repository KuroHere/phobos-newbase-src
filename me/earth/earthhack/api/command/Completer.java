// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.command;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.setting.settings.*;

public class Completer
{
    private static String lastCompleted;
    private final String initial;
    private final String[] args;
    private String result;
    private boolean mc_complete;
    
    public Completer(final String initial, final String[] args) {
        this.initial = initial;
        this.args = args;
    }
    
    public Completer setResult(final String result) {
        Completer.lastCompleted = result;
        this.result = result;
        return this;
    }
    
    public String getResult() {
        return (this.result == null) ? this.initial : this.result;
    }
    
    public Completer setMcComplete(final boolean complete) {
        this.mc_complete = complete;
        return this;
    }
    
    public boolean shouldMcComplete() {
        return this.mc_complete;
    }
    
    public String[] getArgs() {
        return this.args;
    }
    
    public String getInitial() {
        return this.initial;
    }
    
    public static String nextValueInSetting(final Setting<?> settingIn, final String previous) {
        if (settingIn instanceof EnumSetting) {
            final Enum<?> next = EnumHelper.next(EnumHelper.fromString((Enum<?>)settingIn.getInitial(), previous));
            return next.name();
        }
        if (settingIn instanceof BooleanSetting) {
            return String.valueOf(!Boolean.parseBoolean(previous));
        }
        return previous;
    }
    
    public String getLastCompleted() {
        return Completer.lastCompleted;
    }
    
    public String getLast() {
        return this.args[this.args.length - 1];
    }
    
    public void setLastCompleted(final String lastCompletedIn) {
        Completer.lastCompleted = lastCompletedIn;
    }
    
    public boolean isSame() {
        return this.initial.equalsIgnoreCase(Completer.lastCompleted);
    }
}
