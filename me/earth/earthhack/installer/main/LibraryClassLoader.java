// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.main;

import java.net.*;
import java.nio.channels.*;
import me.earth.earthhack.impl.util.misc.*;
import java.io.*;

public class LibraryClassLoader extends URLClassLoader
{
    public LibraryClassLoader(final ClassLoader parent, final URL... urls) {
        super(urls, parent);
    }
    
    public void installLibrary(final Library library) throws Exception {
        if (library.needsDownload()) {
            new File(library.getUrl().getFile()).getParentFile().mkdirs();
            try (final ReadableByteChannel rbc = Channels.newChannel(library.getWeb().openStream());
                 final FileOutputStream fos = new FileOutputStream(library.getUrl().getFile())) {
                fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
            }
        }
        this.addURL(library.getUrl());
    }
    
    public Class<?> findClass_public(final String name) throws ClassNotFoundException {
        return this.findClass(name);
    }
    
    @Override
    protected Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
        if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("sun.") || name.startsWith("me.earth.earthhack.installer.main") || name.startsWith("jdk.")) {
            return super.loadClass(name, resolve);
        }
        final Class<?> alreadyLoaded = this.findLoadedClass(name);
        if (alreadyLoaded != null) {
            return alreadyLoaded;
        }
        try (final InputStream is = this.getResourceAsStream(name.replaceAll("\\.", "/") + ".class")) {
            if (is == null) {
                throw new ClassNotFoundException("Could not find " + name);
            }
            final byte[] bytes = StreamUtil.toByteArray(is);
            final Class<?> clazz = this.defineClass(name, bytes, 0, bytes.length);
            if (resolve) {
                this.resolveClass(clazz);
            }
            return clazz;
        }
        catch (IOException e) {
            throw new ClassNotFoundException("Could not load " + name, e);
        }
    }
}
