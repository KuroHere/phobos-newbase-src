// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc;

import java.util.concurrent.atomic.*;
import java.util.function.*;

public class SkippingCounter
{
    private final AtomicInteger counter;
    private final Predicate<Integer> skip;
    private final int initial;
    
    public SkippingCounter(final int initial, final Predicate<Integer> skip) {
        this.counter = new AtomicInteger(initial);
        this.initial = initial;
        this.skip = skip;
    }
    
    public int get() {
        return this.counter.get();
    }
    
    public int next() {
        int result;
        do {
            result = this.counter.incrementAndGet();
        } while (!this.skip.test(result));
        return result;
    }
    
    public void reset() {
        this.counter.set(this.initial);
    }
}
