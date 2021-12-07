// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.exception;

public class NoStackTraceException extends Exception
{
    public NoStackTraceException(final String message) {
        super(message);
        this.setStackTrace(new StackTraceElement[0]);
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        this.setStackTrace(new StackTraceElement[0]);
        return this;
    }
}
