// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread;

import java.util.concurrent.*;
import me.earth.earthhack.impl.util.thread.*;

public interface GlobalExecutor
{
    public static final ExecutorService EXECUTOR = ThreadUtil.newDaemonCachedThreadPool();
    public static final ExecutorService FIXED_EXECUTOR = ThreadUtil.newFixedThreadPool((int)(Runtime.getRuntime().availableProcessors() / 1.5));
}
