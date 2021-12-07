//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine.util;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.managers.*;
import java.util.*;

public class BigConstellation implements IConstellation, Globals
{
    private final IBlockStateHelper helper;
    private final IAutomine automine;
    private final BlockPos[] positions;
    private final IBlockState[] states;
    private final EntityPlayer target;
    private int blockStateChanges;
    private boolean valid;
    
    public BigConstellation(final IAutomine automine, final BlockPos[] positions, final IBlockState[] states, final EntityPlayer target) {
        this.automine = automine;
        this.positions = positions;
        this.states = states;
        this.target = target;
        this.valid = true;
        (this.helper = new BlockStateHelper()).addBlockState(positions[0], Blocks.OBSIDIAN.getDefaultState());
        for (int i = 1; i < positions.length; ++i) {
            this.helper.addAir(positions[i]);
        }
    }
    
    @Override
    public void update(final IAutomine automine) {
        this.valid = false;
        BlockPos attackPos = null;
        for (int i = 0; i < this.states.length; ++i) {
            this.states[i] = BigConstellation.mc.world.getBlockState(this.positions[i]);
            if (i == 0 && (this.states[0].getBlock() == Blocks.OBSIDIAN || this.states[0].getBlock() == Blocks.BEDROCK || this.states[0].getMaterial().isReplaceable())) {
                if (this.positions[0].equals((Object)automine.getCurrent())) {
                    automine.setCurrent(null);
                }
            }
            else {
                if (i != 0 && this.states[i].getBlock() == Blocks.OBSIDIAN && !automine.shouldMineObby()) {
                    return;
                }
                if (this.states[i].getBlock() != Blocks.AIR) {
                    if (!MineUtil.canBreak(this.states[i], this.positions[i])) {
                        return;
                    }
                    attackPos = this.positions[i];
                    this.valid = true;
                }
                else if (this.positions[i].equals((Object)automine.getCurrent())) {
                    automine.setCurrent(null);
                }
            }
        }
        if (!this.valid) {
            return;
        }
        for (int i = 1; i < this.positions.length; ++i) {
            for (final Entity entity : BigConstellation.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(this.positions[i]))) {
                if (entity != null && !(entity instanceof EntityItem) && !EntityUtil.isDead(entity)) {
                    this.valid = false;
                    return;
                }
            }
            if (automine.getNewVEntities()) {
                break;
            }
        }
        final BlockPos pos = this.positions[0];
        if (this.states[0].getBlock() != Blocks.OBSIDIAN && this.states[0].getBlock() != Blocks.BEDROCK) {
            for (final Entity entity : BigConstellation.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
                if (entity != null && !EntityUtil.isDead(entity) && entity.preventEntitySpawning && !(entity instanceof EntityItem)) {
                    this.valid = false;
                    return;
                }
            }
        }
        if (RotationUtil.getRotationPlayer().getDistanceSq((double)(pos.getX() + 0.5f), (double)(pos.getY() + 1), (double)(pos.getZ() + 0.5f)) >= automine.getBreakTrace() && !RayTraceUtil.canBeSeen(new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() + 1), (double)(pos.getZ() + 0.5f)), (Entity)RotationUtil.getRotationPlayer())) {
            this.valid = false;
            return;
        }
        final float self = DamageUtil.calculate(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, RotationUtil.getRotationPlayer().getEntityBoundingBox(), (EntityLivingBase)RotationUtil.getRotationPlayer(), (IBlockAccess)this.helper, true);
        if (!automine.isSuicide() && self > automine.getMaxSelfDmg()) {
            this.valid = false;
            return;
        }
        if (this.target == null) {
            for (final EntityPlayer player : BigConstellation.mc.world.playerEntities) {
                if (player != null && !EntityUtil.isDead((Entity)player) && !Managers.FRIENDS.contains(player)) {
                    if (player.getDistanceSq(pos) > 144.0) {
                        continue;
                    }
                    final float d = DamageUtil.calculate(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, player.getEntityBoundingBox(), (EntityLivingBase)player, (IBlockAccess)this.helper, true);
                    if (d >= automine.getMinDmg()) {
                        if (automine.getCurrent() == null) {
                            automine.attackPos(attackPos);
                        }
                        return;
                    }
                    continue;
                }
            }
        }
        else if (!EntityUtil.isDead((Entity)this.target) && DamageUtil.calculate(pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, this.target.getEntityBoundingBox(), (EntityLivingBase)this.target, (IBlockAccess)this.helper, true) >= automine.getMinDmg()) {
            if (automine.getCurrent() == null) {
                automine.attackPos(attackPos);
            }
            return;
        }
        this.valid = false;
    }
    
    @Override
    public boolean isAffected(final BlockPos pos, final IBlockState state) {
        for (final BlockPos position : this.positions) {
            if (position.equals((Object)pos)) {
                if (position.equals((Object)this.automine.getCurrent())) {
                    this.automine.setCurrent(null);
                }
                ++this.blockStateChanges;
                return false;
            }
        }
        return false;
    }
    
    @Override
    public boolean isValid(final IBlockAccess world, final boolean checkPlayerState) {
        return this.blockStateChanges < this.positions.length * 2.25 && this.valid;
    }
    
    @Override
    public boolean cantBeImproved() {
        return !this.automine.canBigCalcsBeImproved();
    }
}
