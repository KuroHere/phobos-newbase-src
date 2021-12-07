// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc;

import java.net.*;
import java.nio.channels.*;
import java.io.*;

public class StreamUtil
{
    public static void copy(final URL from, final URL to) throws IOException {
        try (final ReadableByteChannel rbc = Channels.newChannel(from.openStream());
             final FileOutputStream fos = new FileOutputStream(to.getFile())) {
            fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
        }
    }
    
    public static byte[] toByteArray(final InputStream is) throws IOException {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        copy(is, buffer);
        return buffer.toByteArray();
    }
    
    public static void copy(final InputStream is, final OutputStream os) throws IOException {
        final byte[] bytes = new byte[1024];
        int length;
        while ((length = is.read(bytes)) != -1) {
            os.write(bytes, 0, length);
        }
    }
}
