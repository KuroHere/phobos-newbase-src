// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math;

public class StopWatch implements Passable
{
    private long time;
    
    public boolean passed(final double ms) {
        return System.currentTimeMillis() - this.time >= ms;
    }
    
    @Override
    public boolean passed(final long ms) {
        return System.currentTimeMillis() - this.time >= ms;
    }
    
    public StopWatch reset() {
        this.time = System.currentTimeMillis();
        return this;
    }
    
    public long getTime() {
        return System.currentTimeMillis() - this.time;
    }
    
    public void setTime(final long ns) {
        this.time = ns;
    }
    
    public void adjust(final int time) {
        this.time += time;
    }
}
