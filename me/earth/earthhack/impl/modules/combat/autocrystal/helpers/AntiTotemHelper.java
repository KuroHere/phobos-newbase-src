// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;

public class AntiTotemHelper
{
    private final Setting<Float> health;
    private EntityPlayer target;
    private BlockPos targetPos;
    
    public AntiTotemHelper(final Setting<Float> health) {
        this.health = health;
    }
    
    public boolean isDoublePoppable(final EntityPlayer player) {
        return Managers.COMBAT.lastPop((Entity)player) > 500L && EntityUtil.getHealth((EntityLivingBase)player) <= this.health.getValue();
    }
    
    public BlockPos getTargetPos() {
        return this.targetPos;
    }
    
    public void setTargetPos(final BlockPos targetPos) {
        this.targetPos = targetPos;
    }
    
    public EntityPlayer getTarget() {
        return this.target;
    }
    
    public void setTarget(final EntityPlayer target) {
        this.target = target;
    }
}
