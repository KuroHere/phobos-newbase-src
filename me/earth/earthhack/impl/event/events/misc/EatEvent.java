// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import net.minecraft.item.*;
import net.minecraft.entity.*;

public class EatEvent
{
    private final ItemStack stack;
    private final EntityLivingBase entity;
    
    public EatEvent(final ItemStack stack, final EntityLivingBase entity) {
        this.stack = stack;
        this.entity = entity;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
}
