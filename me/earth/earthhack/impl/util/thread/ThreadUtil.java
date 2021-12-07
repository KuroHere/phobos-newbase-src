// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.thread;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.concurrent.*;

public class ThreadUtil implements Globals
{
    public static final ThreadFactory FACTORY;
    
    public static ScheduledExecutorService newDaemonScheduledExecutor(final String name) {
        final ThreadFactoryBuilder factory = newDaemonThreadFactoryBuilder();
        factory.setNameFormat("3arthh4ck-" + name + "-%d");
        return Executors.newSingleThreadScheduledExecutor(factory.build());
    }
    
    public static ExecutorService newDaemonCachedThreadPool() {
        return Executors.newCachedThreadPool(ThreadUtil.FACTORY);
    }
    
    public static ExecutorService newFixedThreadPool(final int size) {
        final ThreadFactoryBuilder factory = newDaemonThreadFactoryBuilder();
        factory.setNameFormat("3arthh4ck-Fixed-%d");
        return Executors.newFixedThreadPool(Math.max(size, 1), factory.build());
    }
    
    public static ThreadFactoryBuilder newDaemonThreadFactoryBuilder() {
        final ThreadFactoryBuilder factory = new ThreadFactoryBuilder();
        factory.setDaemon(true);
        return factory;
    }
    
    static {
        FACTORY = newDaemonThreadFactoryBuilder().setNameFormat("3arthh4ck-Thread-%d").build();
    }
}
