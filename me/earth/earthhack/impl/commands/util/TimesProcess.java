// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.util;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;
import java.util.*;

public class TimesProcess
{
    private final AtomicBoolean valid;
    private final List<Future<?>> futures;
    
    public TimesProcess(final int size) {
        this.valid = new AtomicBoolean(true);
        this.futures = new ArrayList<Future<?>>(size);
    }
    
    public void addFuture(final Future<?> future) {
        this.futures.add(future);
    }
    
    public void clear() {
        for (final Future<?> future : this.futures) {
            future.cancel(true);
        }
    }
    
    public void setValid(final boolean valid) {
        this.valid.set(valid);
    }
    
    public boolean isValid() {
        return this.valid.get();
    }
}
