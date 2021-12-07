// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antivanish.util;

public class VanishedEntry
{
    private final String name;
    private final long time;
    
    public VanishedEntry(final String name) {
        this.name = name;
        this.time = System.currentTimeMillis();
    }
    
    public String getName() {
        return this.name;
    }
    
    public long getTime() {
        return this.time;
    }
}
