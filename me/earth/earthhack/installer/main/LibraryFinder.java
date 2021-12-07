// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.main;

import java.io.*;
import java.net.*;
import java.util.*;

public class LibraryFinder
{
    private static final Map<String, URL> LIBRARIES;
    
    public List<Library> findLibraries(final MinecraftFiles files) {
        final List<Library> result = new ArrayList<Library>(LibraryFinder.LIBRARIES.size());
        for (final Map.Entry<String, URL> lib : LibraryFinder.LIBRARIES.entrySet()) {
            final String path = files.getLibraries() + lib.getKey();
            final boolean exists = new File(path).exists();
            final URL url = toUrl("file:/" + path);
            result.add(new Library(url, lib.getValue(), !exists));
        }
        return result;
    }
    
    private static URL toUrl(final String s) {
        try {
            return new URL(s);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    
    static {
        LIBRARIES = new HashMap<String, URL>(3);
        final String asm_lib = "org/ow2/asm/asm-debug-all/5.2/asm-debug-all-5.2.jar";
        final URL asm_url = toUrl("https://repo1.maven.org/maven2/" + asm_lib);
        LibraryFinder.LIBRARIES.put(asm_lib, asm_url);
        final String gson_lib = "com/google/code/gson/gson/2.8.0/gson-2.8.0.jar";
        final URL gson_url = toUrl("https://libraries.minecraft.net/" + gson_lib);
        LibraryFinder.LIBRARIES.put(gson_lib, gson_url);
    }
}
