// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math;

public class GuardTimer implements DiscreteTimer
{
    private final StopWatch guard;
    private final long interval;
    private final long guardDelay;
    private long delay;
    private long time;
    
    public GuardTimer() {
        this(1000L);
    }
    
    public GuardTimer(final long guardDelay) {
        this(guardDelay, 10L);
    }
    
    public GuardTimer(final long guardDelay, final long interval) {
        this.guard = new StopWatch();
        this.guardDelay = guardDelay;
        this.interval = interval;
    }
    
    @Override
    public long getTime() {
        return System.currentTimeMillis() - this.time;
    }
    
    @Override
    public boolean passed(final long ms) {
        return ms == 0L || ms < this.interval || System.currentTimeMillis() - this.time >= ms;
    }
    
    @Override
    public DiscreteTimer reset(final long ms) {
        if (ms <= this.interval || this.delay != ms || this.guard.passed(this.guardDelay)) {
            this.delay = ms;
            this.reset();
        }
        else {
            this.time += ms;
        }
        return this;
    }
    
    public void reset() {
        this.time = System.currentTimeMillis();
        this.guard.reset();
    }
}
