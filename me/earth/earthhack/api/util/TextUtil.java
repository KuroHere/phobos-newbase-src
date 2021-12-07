// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.util;

public class TextUtil
{
    public static boolean startsWith(final String string, final String prefix) {
        return string != null && prefix != null && string.toLowerCase().startsWith(prefix.toLowerCase());
    }
    
    public static String substring(final String string, final int beginIndex) {
        if (string != null && beginIndex <= string.length()) {
            return string.substring(Math.max(beginIndex, 0));
        }
        return "";
    }
    
    public static String substring(final String string, final int beginIndex, final int endIndex) {
        if (string != null && beginIndex <= string.length() && endIndex > 0 && endIndex >= beginIndex) {
            return string.substring(Math.max(0, beginIndex), Math.min(endIndex, string.length()));
        }
        return "";
    }
    
    public static String get32BitString(final int value) {
        final StringBuilder r = new StringBuilder(Integer.toHexString(value));
        while (r.length() < 8) {
            r.insert(0, 0);
        }
        return r.toString().toUpperCase();
    }
    
    public static String capitalize(final String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
