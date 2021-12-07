// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.thread;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import java.util.*;

public class ThreadFactoryBuilder
{
    private Boolean daemon;
    private String nameFormat;
    
    public ThreadFactoryBuilder setDaemon(final boolean daemon) {
        this.daemon = daemon;
        return this;
    }
    
    public ThreadFactoryBuilder setNameFormat(final String nameFormat) {
        this.nameFormat = nameFormat;
        return this;
    }
    
    public ThreadFactory build() {
        final Boolean daemon = this.daemon;
        final String nameFormat = this.nameFormat;
        final AtomicLong id = (nameFormat != null) ? new AtomicLong(0L) : null;
        return r -> {
            final Thread thread = Executors.defaultThreadFactory().newThread(r);
            if (daemon != null) {
                thread.setDaemon(daemon);
            }
            if (nameFormat != null) {
                thread.setName(format(nameFormat, id.getAndIncrement()));
            }
            return thread;
        };
    }
    
    private static String format(final String format, final Object... args) {
        return String.format(Locale.ROOT, format, args);
    }
}
