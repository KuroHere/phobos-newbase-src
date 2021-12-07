// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc;

import java.net.*;

public class URLUtil
{
    public static URL toUrl(final String url) {
        try {
            return new URL(url);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static URL toUrl(final URI uri) {
        try {
            return uri.toURL();
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
