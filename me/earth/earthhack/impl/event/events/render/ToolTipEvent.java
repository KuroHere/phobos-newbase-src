// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.render;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.item.*;

public class ToolTipEvent extends Event
{
    private final ItemStack stack;
    private final int x;
    private final int y;
    
    public ToolTipEvent(final ItemStack stack, final int x, final int y) {
        this.stack = stack;
        this.x = x;
        this.y = y;
    }
    
    public ItemStack getStack() {
        return this.stack;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public static class Post extends ToolTipEvent
    {
        public Post(final ItemStack stack, final int x, final int y) {
            super(stack, x, y);
        }
    }
}
