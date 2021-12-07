// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.thread;

@FunctionalInterface
public interface SafeRunnable extends Runnable
{
    void runSafely() throws Throwable;
    
    default void run() {
        try {
            this.runSafely();
        }
        catch (Throwable t) {
            this.handle(t);
        }
    }
    
    default void handle(final Throwable t) {
        t.printStackTrace();
    }
}
