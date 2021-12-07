// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tooltips.util;

import net.minecraft.item.*;

public class TimeStack
{
    private final ItemStack stack;
    private final long time;
    
    public TimeStack(final ItemStack stack, final long time) {
        this.stack = stack;
        this.time = time;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public long getTime() {
        return this.time;
    }
}
