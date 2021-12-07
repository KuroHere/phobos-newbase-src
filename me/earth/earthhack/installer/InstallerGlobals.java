// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer;

import java.util.concurrent.atomic.*;

public class InstallerGlobals
{
    private static final AtomicBoolean VANILLA;
    private static final AtomicBoolean FORGE;
    
    public static void setVanilla(final boolean vanilla) {
        InstallerGlobals.VANILLA.set(vanilla);
    }
    
    public static void setForge(final boolean forge) {
        InstallerGlobals.FORGE.set(forge);
    }
    
    public static boolean hasInstalledVanilla() {
        return InstallerGlobals.VANILLA.get();
    }
    
    public static boolean hasInstalledForge() {
        return InstallerGlobals.FORGE.get();
    }
    
    static {
        VANILLA = new AtomicBoolean();
        FORGE = new AtomicBoolean();
    }
}
