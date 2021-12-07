// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.installer.service;

import java.net.*;
import me.earth.earthhack.installer.srg2notch.*;
import java.util.jar.*;
import java.util.*;
import java.util.zip.*;
import me.earth.earthhack.impl.util.misc.*;
import java.io.*;

public class Srg2NotchService
{
    private final ASMRemapper remapper;
    
    public Srg2NotchService() {
        this.remapper = new ASMRemapper();
    }
    
    public void remap(final URL from, final URL to) throws IOException {
        final Mapping mapping = Mapping.fromResource("mappings/mappings.csv");
        final JarFile jar = new JarFile(from.getFile());
        try (final FileOutputStream fos = new FileOutputStream(to.getFile());
             final JarOutputStream jos = new JarOutputStream(fos)) {
            final Enumeration<JarEntry> e = jar.entries();
            while (e.hasMoreElements()) {
                final JarEntry next = e.nextElement();
                this.handleEntry(next, jos, jar, mapping);
            }
        }
    }
    
    protected void handleEntry(final JarEntry entry, final JarOutputStream jos, final JarFile jar, final Mapping mapping) throws IOException {
        try (final InputStream is = jar.getInputStream(entry)) {
            jos.putNextEntry(new JarEntry(entry.getName()));
            if (entry.getName().endsWith(".class")) {
                final byte[] bytes = StreamUtil.toByteArray(is);
                jos.write(this.remapper.transform(bytes, mapping));
            }
            else {
                StreamUtil.copy(is, jos);
            }
            jos.flush();
            jos.closeEntry();
        }
    }
}
