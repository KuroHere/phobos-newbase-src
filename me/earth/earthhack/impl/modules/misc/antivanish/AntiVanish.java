// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antivanish;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.modules.misc.antivanish.util.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.module.data.*;
import java.util.*;

public class AntiVanish extends Module
{
    protected final Map<Integer, Future<?>> futures;
    protected final Map<UUID, VanishedEntry> cache;
    protected final AtomicInteger ids;
    protected final StopWatch timer;
    
    public AntiVanish() {
        super("AntiVanish", Category.Misc);
        this.futures = new ConcurrentHashMap<Integer, Future<?>>();
        this.cache = new ConcurrentHashMap<UUID, VanishedEntry>();
        this.ids = new AtomicInteger();
        this.timer = new StopWatch();
        this.listeners.add(new ListenerLatency(this));
        this.setData(new AntiVanishData(this));
    }
    
    @Override
    protected void onDisable() {
        for (final Future<?> future : this.futures.values()) {
            if (future != null) {
                future.cancel(true);
            }
        }
        this.futures.clear();
    }
}
