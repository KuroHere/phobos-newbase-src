// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.exception;

import me.earth.earthhack.impl.util.exception.*;

public class ArgParseException extends NoStackTraceException
{
    public ArgParseException(final String message) {
        super(message);
    }
    
    public static long tryLong(final String string, final String message) throws ArgParseException {
        try {
            return Long.parseLong(string);
        }
        catch (NumberFormatException e) {
            throw new ArgParseException("Couldn't parse " + message + ": " + string + "!");
        }
    }
    
    public static double tryDouble(final String string, final String message) throws ArgParseException {
        try {
            return Double.parseDouble(string);
        }
        catch (NumberFormatException e) {
            throw new ArgParseException("Couldn't parse " + message + ": " + string + "!");
        }
    }
}
