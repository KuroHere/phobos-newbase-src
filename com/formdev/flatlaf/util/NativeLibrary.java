// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.util;

import java.net.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.nio.file.*;

public class NativeLibrary
{
    private static final String DELETE_SUFFIX = ".delete";
    private static boolean deletedTemporary;
    private final boolean loaded;
    
    public NativeLibrary(final String libraryName, final ClassLoader classLoader, final boolean supported) {
        this.loaded = (supported && loadLibraryFromJar(libraryName, classLoader));
    }
    
    public boolean isLoaded() {
        return this.loaded;
    }
    
    private static boolean loadLibraryFromJar(String libraryName, final ClassLoader classLoader) {
        libraryName = decorateLibraryName(libraryName);
        final URL libraryUrl = (classLoader != null) ? classLoader.getResource(libraryName) : NativeLibrary.class.getResource("/" + libraryName);
        if (libraryUrl == null) {
            log("Library '" + libraryName + "' not found", null);
            return false;
        }
        File tempFile = null;
        try {
            if ("file".equals(libraryUrl.getProtocol())) {
                final File libraryFile = new File(libraryUrl.getPath());
                if (libraryFile.isFile()) {
                    System.load(libraryFile.getCanonicalPath());
                    return true;
                }
            }
            final Path tempPath = createTempFile(libraryName);
            tempFile = tempPath.toFile();
            try (final InputStream in = libraryUrl.openStream()) {
                Files.copy(in, tempPath, StandardCopyOption.REPLACE_EXISTING);
            }
            System.load(tempFile.getCanonicalPath());
            deleteOrMarkForDeletion(tempFile);
            return true;
        }
        catch (Throwable ex) {
            log(null, ex);
            if (tempFile != null) {
                deleteOrMarkForDeletion(tempFile);
            }
            return false;
        }
    }
    
    private static String decorateLibraryName(final String libraryName) {
        if (SystemInfo.isWindows) {
            return libraryName.concat(".dll");
        }
        final String suffix = SystemInfo.isMacOS ? ".dylib" : ".so";
        final int sep = libraryName.lastIndexOf(47);
        return (sep >= 0) ? (libraryName.substring(0, sep + 1) + "lib" + libraryName.substring(sep + 1) + suffix) : ("lib" + libraryName + suffix);
    }
    
    private static void log(final String msg, final Throwable thrown) {
        LoggingFacade.INSTANCE.logSevere(msg, thrown);
    }
    
    private static Path createTempFile(final String libraryName) throws IOException {
        final int sep = libraryName.lastIndexOf(47);
        final String name = (sep >= 0) ? libraryName.substring(sep + 1) : libraryName;
        final int dot = name.lastIndexOf(46);
        final String prefix = ((dot >= 0) ? name.substring(0, dot) : name) + '-';
        final String suffix = (dot >= 0) ? name.substring(dot) : "";
        final Path tempDir = getTempDir();
        final long nanoTime = System.nanoTime();
        int i = 0;
        while (true) {
            final String s = prefix + Long.toUnsignedString(nanoTime) + i + suffix;
            try {
                return Files.createFile(tempDir.resolve(s), (FileAttribute<?>[])new FileAttribute[0]);
            }
            catch (FileAlreadyExistsException ex) {
                ++i;
            }
        }
    }
    
    private static Path getTempDir() throws IOException {
        String tmpdir = System.getProperty("java.io.tmpdir");
        if (SystemInfo.isWindows) {
            tmpdir += "\\flatlaf.temp";
        }
        final Path tempDir = Paths.get(tmpdir, new String[0]);
        Files.createDirectories(tempDir, (FileAttribute<?>[])new FileAttribute[0]);
        if (SystemInfo.isWindows) {
            deleteTemporaryFiles(tempDir);
        }
        return tempDir;
    }
    
    private static void deleteTemporaryFiles(final Path tempDir) {
        if (NativeLibrary.deletedTemporary) {
            return;
        }
        NativeLibrary.deletedTemporary = true;
        final File[] markerFiles = tempDir.toFile().listFiles((dir, name) -> name.endsWith(".delete"));
        if (markerFiles == null) {
            return;
        }
        for (final File markerFile : markerFiles) {
            final File toDeleteFile = new File(markerFile.getParent(), StringUtils.removeTrailing(markerFile.getName(), ".delete"));
            if (!toDeleteFile.exists() || toDeleteFile.delete()) {
                markerFile.delete();
            }
        }
    }
    
    private static void deleteOrMarkForDeletion(final File file) {
        if (file.delete()) {
            return;
        }
        try {
            final File markFile = new File(file.getParent(), file.getName() + ".delete");
            markFile.createNewFile();
        }
        catch (IOException ex) {}
    }
}
