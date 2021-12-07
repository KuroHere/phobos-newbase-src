// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math;

public class Timer
{
    private long startTime;
    
    public Timer() {
        this.startTime = System.currentTimeMillis();
    }
    
    public long getTime() {
        return System.currentTimeMillis() - this.startTime;
    }
    
    public void reset() {
        this.startTime = System.currentTimeMillis();
    }
    
    public void adjust(final int by) {
        this.startTime += by;
    }
}
