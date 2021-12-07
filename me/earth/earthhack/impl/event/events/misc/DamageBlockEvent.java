// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class DamageBlockEvent extends Event
{
    private final BlockPos pos;
    private final EnumFacing facing;
    private float damage;
    private int delay;
    
    public DamageBlockEvent(final BlockPos pos, final EnumFacing facing, final float damage, final int delay) {
        this.pos = pos;
        this.facing = facing;
        this.damage = damage;
        this.delay = delay;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
    
    public float getDamage() {
        return this.damage;
    }
    
    public void setDamage(final float damage) {
        this.damage = damage;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public void setDelay(final int delay) {
        this.delay = delay;
    }
}
