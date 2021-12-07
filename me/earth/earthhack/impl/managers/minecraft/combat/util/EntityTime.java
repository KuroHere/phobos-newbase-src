// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat.util;

import java.util.concurrent.atomic.*;
import net.minecraft.entity.*;

public class EntityTime
{
    private final AtomicBoolean valid;
    private final Entity entity;
    public long time;
    
    public EntityTime(final Entity entity) {
        this.valid = new AtomicBoolean(true);
        this.entity = entity;
        this.time = System.currentTimeMillis();
    }
    
    public boolean passed(final long ms) {
        return ms <= 0L || System.currentTimeMillis() - this.time > ms;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public void setValid(final boolean valid) {
        this.valid.set(valid);
    }
    
    public boolean isValid() {
        return this.valid.get();
    }
    
    public void reset() {
        this.time = System.currentTimeMillis();
    }
}
