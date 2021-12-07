// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

public class SimpleServerList implements IServerList
{
    private IConnectionEntry[] entries;
    
    public SimpleServerList() {
        this.entries = new IConnectionEntry[0];
    }
    
    @Override
    public IConnectionEntry[] get() {
        return this.entries;
    }
    
    @Override
    public void set(final IConnectionEntry[] entries) {
        this.entries = entries;
    }
}
