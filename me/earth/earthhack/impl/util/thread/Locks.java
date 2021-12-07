// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.thread;

import java.util.concurrent.locks.*;

public class Locks
{
    public static final Lock PLACE_SWITCH_LOCK;
    public static final Lock WINDOW_CLICK_LOCK;
    
    public static void acquire(final Lock lock, final Runnable runnable) {
        try {
            lock.lock();
            runnable.run();
        }
        finally {
            lock.unlock();
        }
    }
    
    public static Runnable wrap(final Lock lock, final Runnable runnable) {
        return () -> acquire(lock, runnable);
    }
    
    static {
        PLACE_SWITCH_LOCK = new ReentrantLock();
        WINDOW_CLICK_LOCK = new ReentrantLock();
    }
}
