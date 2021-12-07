// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.pistonaura.util;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;

public class PistonData implements Globals
{
    private final BlockPos crystalPos;
    private final EntityPlayer target;
    private final BlockPos startPos;
    private final EnumFacing facing;
    private PistonStage[] order;
    private BlockPos redstonePos;
    private BlockPos pistonPos;
    private boolean valid;
    private boolean multi;
    
    public PistonData(final EntityPlayer target, final BlockPos crystalPos, final EnumFacing facing) {
        this.crystalPos = crystalPos;
        this.target = target;
        this.startPos = PositionUtil.getPosition((Entity)target);
        this.facing = facing;
    }
    
    public boolean isValid() {
        return this.valid && this.order != null && EntityUtil.isValid((Entity)this.target, 9.0) && this.startPos.equals((Object)PositionUtil.getPosition((Entity)this.target));
    }
    
    public BlockPos getStartPos() {
        return this.startPos;
    }
    
    public BlockPos getCrystalPos() {
        return this.crystalPos;
    }
    
    public EntityPlayer getTarget() {
        return this.target;
    }
    
    public EnumFacing getFacing() {
        return this.facing;
    }
    
    public BlockPos getRedstonePos() {
        return this.redstonePos;
    }
    
    public void setRedstonePos(final BlockPos redstonePos) {
        this.redstonePos = redstonePos;
    }
    
    public BlockPos getPistonPos() {
        return this.pistonPos;
    }
    
    public void setPistonPos(final BlockPos pistonPos) {
        this.pistonPos = pistonPos;
    }
    
    public void setValid(final boolean valid) {
        this.valid = valid;
    }
    
    public PistonStage[] getOrder() {
        return this.order;
    }
    
    public void setOrder(final PistonStage[] order) {
        this.order = order;
    }
    
    public boolean isMulti() {
        return this.multi;
    }
    
    public void setMulti(final boolean multi) {
        this.multi = multi;
    }
}
