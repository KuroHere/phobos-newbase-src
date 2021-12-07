// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.misc;

import me.earth.earthhack.api.event.events.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class ClickBlockEvent extends Event
{
    private final BlockPos pos;
    private final EnumFacing facing;
    
    public ClickBlockEvent(final BlockPos pos, final EnumFacing facing) {
        this.pos = pos;
        this.facing = facing;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
    
    public static class Right extends ClickBlockEvent
    {
        private final Vec3d vec;
        private final EnumHand hand;
        
        public Right(final BlockPos pos, final EnumFacing facing, final Vec3d vec, final EnumHand hand) {
            super(pos, facing);
            this.vec = vec;
            this.hand = hand;
        }
        
        public EnumHand getHand() {
            return this.hand;
        }
        
        public Vec3d getVec() {
            return this.vec;
        }
    }
}
