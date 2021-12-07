// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.util;

import me.earth.earthhack.api.util.interfaces.*;

public class IdentifiedNameable implements Nameable
{
    private final String lower;
    private final String name;
    
    public IdentifiedNameable(final String name) {
        this.lower = name.toLowerCase();
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public int hashCode() {
        return this.lower.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof IdentifiedNameable && ((IdentifiedNameable)o).lower.equals(this.lower);
    }
}
