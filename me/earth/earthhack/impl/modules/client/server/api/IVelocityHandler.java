// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.api;

public interface IVelocityHandler
{
    void onVelocity(final double p0, final double p1, final double p2);
    
    double getLastX();
    
    double getLastY();
    
    double getLastZ();
}
