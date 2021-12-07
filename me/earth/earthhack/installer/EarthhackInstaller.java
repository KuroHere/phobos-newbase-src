// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer;

import me.earth.earthhack.installer.service.*;
import me.earth.earthhack.installer.main.*;
import javax.swing.*;
import java.util.*;
import me.earth.earthhack.installer.version.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.installer.gui.*;

public class EarthhackInstaller implements Installer
{
    private final MinecraftFiles files;
    private final InstallerFrame gui;
    private InstallerService service;
    
    public EarthhackInstaller() {
        this.files = new MinecraftFiles();
        this.gui = new InstallerFrame();
    }
    
    public void launch(final LibraryClassLoader classLoader, final String[] args) {
        SwingUtilities.invokeLater(this.gui::display);
        this.wrapErrorGui(() -> {
            this.files.findFiles(args);
            final LibraryFinder libraryFinder = new LibraryFinder();
            libraryFinder.findLibraries(this.files).iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final Library library = iterator.next();
                classLoader.installLibrary(library);
            }
            this.service = new InstallerService();
            this.refreshVersions();
        });
    }
    
    @Override
    public boolean refreshVersions() {
        return this.wrapErrorGui(() -> {
            final VersionFinder versionFinder = new VersionFinder();
            final List<Version> versions = versionFinder.findVersions(this.files);
            this.gui.schedule(new VersionPanel(this, versions));
        });
    }
    
    @Override
    public boolean install(final Version version) {
        return this.wrapErrorGui(() -> {
            this.service.install(this.files, version);
            this.refreshVersions();
        });
    }
    
    @Override
    public boolean uninstall(final Version version) {
        return this.wrapErrorGui(() -> {
            this.service.uninstall(version);
            this.refreshVersions();
        });
    }
    
    @Override
    public boolean update(final boolean forge) {
        return this.wrapErrorGui(() -> {
            this.service.update(this.files, forge);
            this.refreshVersions();
        });
    }
    
    private boolean wrapErrorGui(final SafeRunnable runnable) {
        try {
            runnable.runSafely();
            return false;
        }
        catch (Throwable throwable) {
            this.gui.schedule(new ErrorPanel(throwable));
            throwable.printStackTrace();
            return true;
        }
    }
}
