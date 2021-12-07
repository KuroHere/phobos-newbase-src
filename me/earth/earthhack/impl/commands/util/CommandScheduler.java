// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.util;

import java.util.concurrent.*;
import me.earth.earthhack.impl.util.thread.*;

public interface CommandScheduler
{
    public static final ScheduledExecutorService SCHEDULER = ThreadUtil.newDaemonScheduledExecutor("Command");
}
