// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.lookup;

import java.util.*;

public abstract class LookUp
{
    protected String name;
    protected UUID uuid;
    protected Map<Date, String> names;
    protected Type type;
    
    public LookUp(final Type type, final String name) {
        this.type = type;
        this.name = name;
    }
    
    public LookUp(final Type type, final UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }
    
    public abstract void onSuccess();
    
    public abstract void onFailure();
    
    public enum Type
    {
        NAME, 
        UUID, 
        HISTORY;
    }
}
