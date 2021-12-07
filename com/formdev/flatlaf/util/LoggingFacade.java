// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.util;

public interface LoggingFacade
{
    public static final LoggingFacade INSTANCE = new LoggingFacadeImpl();
    
    void logSevere(final String p0, final Throwable p1);
    
    void logConfig(final String p0, final Throwable p1);
}
