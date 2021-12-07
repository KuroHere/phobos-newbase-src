// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft;

import java.util.*;

public class ClickManager
{
    private final Map<Integer, Queue<Runnable>> clicks;
    
    public ClickManager() {
        this.clicks = new TreeMap<Integer, Queue<Runnable>>();
    }
    
    public void scheduleClick(final Runnable runnable, final int priority) {
    }
    
    public void scheduleClickSynchronized(final Runnable runnable, final int priority) {
        synchronized (this.clicks) {
            this.scheduleClick(runnable, priority);
        }
    }
    
    public void runClick() {
    }
}
