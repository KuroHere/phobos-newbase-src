// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

public interface IHost extends ICloseable
{
    IConnectionManager getConnectionManager();
    
    int getPort();
}
