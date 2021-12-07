// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread;

import me.earth.earthhack.impl.util.thread.*;
import java.util.concurrent.*;

public class ThreadManager implements GlobalExecutor
{
    public Future<?> submit(final SafeRunnable runnable) {
        return this.submitRunnable(runnable);
    }
    
    public Future<?> submitRunnable(final Runnable runnable) {
        return ThreadManager.EXECUTOR.submit(runnable);
    }
    
    public void shutDown() {
        ThreadManager.EXECUTOR.shutdown();
    }
}
