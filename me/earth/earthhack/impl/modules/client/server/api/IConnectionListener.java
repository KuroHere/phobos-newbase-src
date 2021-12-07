// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

public interface IConnectionListener
{
    void onJoin(final IConnectionManager p0, final IConnection p1);
    
    void onLeave(final IConnectionManager p0, final IConnection p1);
}
