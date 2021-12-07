//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.legswitch;

import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import java.util.*;

final class LegConstellation
{
    public final Map<BlockPos, IBlockState> states;
    public final EntityPlayer player;
    public final BlockPos targetPos;
    public final BlockPos playerPos;
    public final BlockPos firstPos;
    public final BlockPos secondPos;
    public boolean firstNeedsObby;
    public boolean secondNeedsObby;
    public boolean invalid;
    
    public LegConstellation(final EntityPlayer player, final BlockPos targetPos, final BlockPos playerPos, final BlockPos firstPos, final BlockPos secondPos, final Map<BlockPos, IBlockState> states, final boolean firstNeedsObby, final boolean secondNeedsObby) {
        this.player = player;
        this.targetPos = targetPos;
        this.playerPos = playerPos;
        this.firstPos = firstPos;
        this.secondPos = secondPos;
        this.states = states;
        this.firstNeedsObby = firstNeedsObby;
        this.secondNeedsObby = secondNeedsObby;
    }
    
    public boolean isValid(final LegSwitch legSwitch, final EntityPlayer self, final IBlockAccess access) {
        if (this.invalid || EntityUtil.isDead((Entity)this.player)) {
            return false;
        }
        if (!PositionUtil.getPosition((Entity)this.player).equals((Object)this.playerPos) || !access.getBlockState(this.playerPos).getMaterial().isReplaceable()) {
            return false;
        }
        if (!legSwitch.checkPos(this.firstPos) || !legSwitch.checkPos(this.secondPos)) {
            return false;
        }
        for (final Map.Entry<BlockPos, IBlockState> entry : this.states.entrySet()) {
            if (!access.getBlockState((BlockPos)entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        float damage = DamageUtil.calculate(this.firstPos, (EntityLivingBase)self);
        if (damage > EntityUtil.getHealth((EntityLivingBase)self) + 0.5 || damage > legSwitch.maxSelfDamage.getValue()) {
            return false;
        }
        damage = DamageUtil.calculate(this.secondPos, (EntityLivingBase)self);
        return damage <= EntityUtil.getHealth((EntityLivingBase)self) + 0.5 && damage <= legSwitch.maxSelfDamage.getValue();
    }
    
    public void add(final BlockPos pos, final IBlockState state) {
        this.states.put(pos, state);
    }
}
