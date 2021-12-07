// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

public class SimpleEntry implements IConnectionEntry
{
    private final String name;
    private final int id;
    
    public SimpleEntry(final String name, final int id) {
        this.name = name;
        this.id = id;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
}
