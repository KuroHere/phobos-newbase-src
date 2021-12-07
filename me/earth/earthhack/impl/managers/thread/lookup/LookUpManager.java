// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.lookup;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import me.earth.earthhack.impl.util.thread.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.tweaker.launch.*;

public class LookUpManager implements Globals
{
    private static final long CONNECTION_COOLDOWN;
    private volatile ScheduledExecutorService service;
    private final AtomicLong last;
    
    public LookUpManager() {
        this.last = new AtomicLong();
    }
    
    public Future<?> doLookUp(final LookUp lookUp) {
        switch (lookUp.type) {
            case NAME: {
                if (lookUp.uuid == null) {
                    lookUp.onFailure();
                    break;
                }
                final String name = LookUpUtil.getNameSimple(lookUp.uuid);
                if (name != null) {
                    lookUp.name = name;
                    lookUp.onSuccess();
                    break;
                }
                return this.scheduleLookUp(lookUp);
            }
            case UUID: {
                if (lookUp.name == null) {
                    lookUp.onFailure();
                    break;
                }
                final UUID uuid = LookUpUtil.getUUIDSimple(lookUp.name);
                if (uuid != null) {
                    lookUp.uuid = uuid;
                    lookUp.onSuccess();
                    break;
                }
                return this.scheduleLookUp(lookUp);
            }
            case HISTORY: {
                if (lookUp.name == null) {
                    lookUp.onFailure();
                    break;
                }
                final UUID id = LookUpUtil.getUUIDSimple(lookUp.name);
                if (id != null) {
                    lookUp.uuid = id;
                }
                return this.scheduleLookUp(lookUp);
            }
        }
        return null;
    }
    
    private void doBigLookUp(final LookUp lookUp) {
        switch (lookUp.type) {
            case NAME: {
                final String name = LookUpUtil.getName(lookUp.uuid);
                if (name != null) {
                    lookUp.name = name;
                    lookUp.onSuccess();
                    break;
                }
                lookUp.onFailure();
                break;
            }
            case UUID: {
                final UUID uuid = LookUpUtil.getUUID(lookUp.name);
                if (uuid != null) {
                    lookUp.uuid = uuid;
                    lookUp.onSuccess();
                    break;
                }
                lookUp.onFailure();
                break;
            }
            case HISTORY: {
                UUID id = lookUp.uuid;
                if (id == null) {
                    id = LookUpUtil.getUUID(lookUp.name);
                }
                if (id != null) {
                    lookUp.names = LookUpUtil.getNameHistory(id);
                    lookUp.onSuccess();
                    break;
                }
                lookUp.onFailure();
                break;
            }
        }
    }
    
    private Future<?> scheduleLookUp(final LookUp lookUp) {
        if (this.service == null) {
            synchronized (this) {
                if (this.service == null) {
                    this.service = ThreadUtil.newDaemonScheduledExecutor("LookUp");
                }
            }
        }
        final long t = Math.max(0L, LookUpManager.CONNECTION_COOLDOWN - System.currentTimeMillis() + this.last.getAndSet(System.currentTimeMillis()));
        return this.service.schedule(() -> this.doBigLookUp(lookUp), t, TimeUnit.MILLISECONDS);
    }
    
    static {
        final Argument<Long> a = DevArguments.getInstance().getArgument("connection");
        Earthhack.getLogger().info("Connection Timeout: " + a.getValue());
        CONNECTION_COOLDOWN = a.getValue();
    }
}
