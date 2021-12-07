// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.disabling;

public interface IDisablingModule
{
    void onShutDown();
    
    void onDisconnect();
    
    void onDeath();
}
