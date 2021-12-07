// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client;

import me.earth.earthhack.installer.service.*;
import me.earth.earthhack.impl.core.*;
import me.earth.earthhack.impl.util.misc.*;
import java.util.*;
import java.net.*;
import me.earth.earthhack.installer.srg2notch.*;
import java.util.zip.*;
import java.util.jar.*;
import java.io.*;

public class PluginRemapper extends Srg2NotchService
{
    public File[] remap(final Collection<File> files) throws IOException, URISyntaxException {
        final File[] remapped = new File[files.size()];
        int index = 0;
        for (final File file : files) {
            Core.LOGGER.info("Remapping: " + file.getName());
            final URL url = URLUtil.toUrl(file.toURI());
            String toURL = url.toString();
            toURL = toURL.substring(0, toURL.length() - 4) + "-vanilla.jar";
            final URL to = URLUtil.toUrl(toURL);
            this.remap(url, to);
            final File remappedFile = new File(to.toURI());
            remapped[index++] = remappedFile;
        }
        return remapped;
    }
    
    @Override
    protected void handleEntry(final JarEntry entry, final JarOutputStream jos, final JarFile jar, final Mapping mapping) throws IOException {
        if (entry.getName().equals("META-INF/MANIFEST.MF")) {
            try (final InputStream is = jar.getInputStream(entry)) {
                jos.putNextEntry(new JarEntry(entry.getName()));
                final Manifest manifest = new Manifest(is);
                final Attributes attr = manifest.getMainAttributes();
                attr.put(new Attributes.Name("3arthh4ckVanilla"), "true");
                manifest.write(jos);
                jos.flush();
                jos.closeEntry();
            }
            return;
        }
        super.handleEntry(entry, jos, jar, mapping);
    }
}
