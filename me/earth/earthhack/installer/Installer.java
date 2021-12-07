// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer;

import me.earth.earthhack.installer.version.*;

public interface Installer
{
    boolean refreshVersions();
    
    boolean install(final Version p0);
    
    boolean uninstall(final Version p0);
    
    boolean update(final boolean p0);
}
