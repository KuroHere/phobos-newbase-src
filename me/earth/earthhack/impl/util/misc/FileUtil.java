// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc;

import java.nio.file.attribute.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.nio.file.*;
import java.net.*;

public class FileUtil
{
    public static Path getDirectory(final Path parent, final String... paths) {
        if (paths.length < 1) {
            return parent;
        }
        final Path dir = lookupPath(parent, paths);
        createDirectory(dir);
        return dir;
    }
    
    public static Path lookupPath(final Path root, final String... paths) {
        return Paths.get(root.toString(), paths);
    }
    
    public static boolean createDirectory(final File file) {
        boolean created = true;
        if (!file.exists()) {
            created = file.mkdir();
        }
        return created;
    }
    
    public static void createDirectory(final Path dir) {
        try {
            if (!Files.isDirectory(dir, new LinkOption[0])) {
                if (Files.exists(dir, new LinkOption[0])) {
                    Files.delete(dir);
                }
                Files.createDirectories(dir, (FileAttribute<?>[])new FileAttribute[0]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<String> readFile(final String file, final boolean write, final Iterable<String> data) {
        try {
            final Path path = Paths.get(file, new String[0]);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            if (write) {
                writeFile(file, data);
            }
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    public static void writeFile(final String file, final Iterable<String> data) {
        final Path path = Paths.get(file, new String[0]);
        try {
            Files.write(path, data, StandardCharsets.UTF_8, Files.exists(path, new LinkOption[0]) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    
    public static Path getPath(final String name, final String... more) throws IOException {
        final Path path = Paths.get(name, more);
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createFile(path, (FileAttribute<?>[])new FileAttribute[0]);
        }
        return path;
    }
    
    public static void openWebLink(final URI url) throws Throwable {
        final Class<?> clazz = Class.forName("java.awt.Desktop");
        final Object object = clazz.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
        clazz.getMethod("browse", URI.class).invoke(object, url);
    }
}
