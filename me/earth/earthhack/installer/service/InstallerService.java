// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.service;

import me.earth.earthhack.installer.version.*;
import me.earth.earthhack.installer.main.*;
import java.net.*;
import java.io.*;
import me.earth.earthhack.installer.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.impl.managers.config.util.*;

public class InstallerService
{
    public static final String VANILLA = "earthhack/vanilla/1.12.2/";
    public static final String FORGE = "earthhack/forge/1.12.2/";
    public static final String JAR = "forge-1.12.2.jar";
    public static final String VJAR = "vanilla-1.12.2.jar";
    private final Srg2NotchService remapper;
    
    public InstallerService() {
        this.remapper = new Srg2NotchService();
    }
    
    public void install(final MinecraftFiles files, final Version version) throws IOException {
        final boolean hasForge = VersionUtil.hasForge(version);
        final boolean hasEarth = VersionUtil.hasEarthhack(version);
        this.update(files, hasForge);
        InstallerUtil.installLibs(version.getJson());
        if (!hasEarth) {
            InstallerUtil.installEarthhack(version.getJson(), hasForge);
            this.write(version);
        }
    }
    
    public void update(final MinecraftFiles files, final boolean forge) throws IOException {
        String libPath;
        if (forge) {
            libPath = files.getLibraries() + "earthhack/forge/1.12.2/" + "forge-1.12.2.jar";
        }
        else {
            libPath = files.getLibraries() + "earthhack/vanilla/1.12.2/" + "vanilla-1.12.2.jar";
        }
        final URL us = Main.class.getProtectionDomain().getCodeSource().getLocation();
        final URL target = new URL("file:/" + libPath);
        new File(target.getFile()).getParentFile().mkdirs();
        if (forge) {
            if (!InstallerGlobals.hasInstalledForge()) {
                StreamUtil.copy(us, target);
                InstallerGlobals.setForge(true);
            }
        }
        else if (!InstallerGlobals.hasInstalledVanilla()) {
            this.remapper.remap(us, target);
            InstallerGlobals.setVanilla(true);
        }
    }
    
    public void uninstall(final Version version) throws IOException {
        InstallerUtil.uninstallEarthhack(version.getJson());
        InstallerUtil.uninstallLibs(version.getJson());
        this.write(version);
    }
    
    public void write(final Version version) throws IOException {
        JsonPathWriter.write(version.getFile().toPath(), version.getJson());
    }
}
