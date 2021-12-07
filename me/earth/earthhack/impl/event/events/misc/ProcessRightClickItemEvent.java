// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import net.minecraft.item.*;

public class ProcessRightClickItemEvent
{
    private final ItemStack itemStack;
    
    public ProcessRightClickItemEvent(final ItemStack stack) {
        this.itemStack = stack;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
