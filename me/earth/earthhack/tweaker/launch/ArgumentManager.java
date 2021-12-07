// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.tweaker.launch;

public interface ArgumentManager
{
    void loadArguments();
    
    void addArgument(final String p0, final Argument<?> p1);
    
     <T> Argument<T> getArgument(final String p0);
}
