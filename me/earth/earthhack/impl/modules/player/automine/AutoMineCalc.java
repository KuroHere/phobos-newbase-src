//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine;

import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import me.earth.earthhack.impl.modules.player.automine.util.*;
import java.util.*;

public class AutoMineCalc implements SafeRunnable, Globals
{
    private final IAutomine automine;
    private final List<EntityPlayer> players;
    private final List<Entity> entities;
    private final EntityPlayer target;
    private final float minDamage;
    private final float maxSelf;
    private final double range;
    private final boolean obby;
    private final boolean newVer;
    private final boolean newVEntities;
    private final boolean mineObby;
    private final double breakTrace;
    private final boolean suicide;
    private int mX;
    private int mY;
    private int mZ;
    
    public AutoMineCalc(final IAutomine automine, final List<EntityPlayer> players, final List<Entity> entities, final EntityPlayer target, final float minDamage, final float maxSelf, final double range, final boolean obby, final boolean newVer, final boolean newVEntities, final boolean mineObby, final double breakTrace, final boolean suicide) {
        this.automine = automine;
        this.players = players;
        this.entities = entities;
        this.target = target;
        this.minDamage = minDamage;
        this.maxSelf = maxSelf;
        this.range = range;
        this.obby = obby;
        this.newVer = newVer;
        this.newVEntities = newVEntities;
        this.mineObby = mineObby;
        this.breakTrace = breakTrace;
        this.suicide = suicide;
    }
    
    @Override
    public void runSafely() throws Throwable {
        final BlockPos middle = PositionUtil.getPosition();
        this.mX = middle.getX();
        this.mY = middle.getY();
        this.mZ = middle.getZ();
        final BlockPos.MutableBlockPos mPos = new BlockPos.MutableBlockPos();
        final int intRange = (int)this.range;
        final double rSquare = this.range * this.range;
        final double bSquare = this.breakTrace * this.breakTrace;
        float maxDamage = Float.MIN_VALUE;
        final IBlockStateHelper helper = new BlockStateHelper();
        IConstellation constellation = null;
        for (int x = this.mX - intRange; x <= this.mX + this.range; ++x) {
            for (int z = this.mZ - intRange; z <= this.mZ + this.range; ++z) {
                for (int y = this.mY - intRange; y < this.mY + this.range; ++y) {
                    if (this.dsq(x, y, z) <= rSquare) {
                        if (this.dsq(x + 0.5f, y + 1, z + 0.5f) < bSquare || RayTraceUtil.canBeSeen(new Vec3d((double)(x + 0.5f), y + 2.7, (double)(z + 0.5f)), (Entity)RotationUtil.getRotationPlayer())) {
                            mPos.setPos(x, y, z);
                            final IBlockState state = AutoMineCalc.mc.world.getBlockState((BlockPos)mPos);
                            final boolean isObbyState = state.getBlock() == Blocks.OBSIDIAN || state.getBlock() == Blocks.BEDROCK;
                            if (this.obby || isObbyState) {
                                if (isObbyState || state.getMaterial().isReplaceable() || MineUtil.canBreak(state, (BlockPos)mPos)) {
                                    mPos.setY(y + 1);
                                    final IBlockState upState = AutoMineCalc.mc.world.getBlockState((BlockPos)mPos);
                                    if ((upState.getBlock() == Blocks.AIR || MineUtil.canBreak(upState, (BlockPos)mPos)) && (upState.getBlock() != Blocks.OBSIDIAN || this.mineObby)) {
                                        if (upState.getBlock() == Blocks.AIR || this.dsq(x, y + 1, z) <= rSquare) {
                                            IBlockState upUpState = null;
                                            if (!this.newVer) {
                                                mPos.setY(y + 2);
                                                upUpState = AutoMineCalc.mc.world.getBlockState((BlockPos)mPos);
                                                if ((upUpState.getBlock() != Blocks.AIR && !MineUtil.canBreak(upUpState, (BlockPos)mPos)) || (upUpState.getBlock() == Blocks.OBSIDIAN && !this.mineObby)) {
                                                    continue;
                                                }
                                                if (upUpState.getBlock() != Blocks.AIR && this.dsq(x, y + 2, z) > rSquare) {
                                                    continue;
                                                }
                                            }
                                            boolean bad = false;
                                            for (final Entity entity : this.entities) {
                                                if (entity.preventEntitySpawning && !isObbyState) {
                                                    mPos.setY(y);
                                                    if (BBUtil.intersects(entity.getEntityBoundingBox(), (Vec3i)mPos)) {
                                                        bad = true;
                                                        break;
                                                    }
                                                }
                                                mPos.setY(y + 1);
                                                if (BBUtil.intersects(entity.getEntityBoundingBox(), (Vec3i)mPos)) {
                                                    bad = true;
                                                    break;
                                                }
                                                if (this.newVEntities) {
                                                    continue;
                                                }
                                                mPos.setY(y + 2);
                                                if (BBUtil.intersects(entity.getEntityBoundingBox(), (Vec3i)mPos)) {
                                                    bad = true;
                                                    break;
                                                }
                                            }
                                            if (!bad) {
                                                helper.clearAllStates();
                                                mPos.setY(y);
                                                helper.addBlockState((BlockPos)mPos, Blocks.OBSIDIAN.getDefaultState());
                                                final BlockPos up = new BlockPos(x, y + 1, z);
                                                helper.addAir(up);
                                                BlockPos upUp = null;
                                                if (!this.newVer) {
                                                    upUp = up.up();
                                                    helper.addAir(upUp);
                                                }
                                                final float self = DamageUtil.calculate(x + 0.5f, y + 1, z + 0.5f, RotationUtil.getRotationPlayer().getEntityBoundingBox(), (EntityLivingBase)RotationUtil.getRotationPlayer(), (IBlockAccess)helper, true);
                                                if (this.suicide || self <= this.maxSelf) {
                                                    float damage = Float.MIN_VALUE;
                                                    if (this.target == null) {
                                                        for (final EntityPlayer player : this.players) {
                                                            if (player.getDistanceSq((double)x, (double)y, (double)z) > 144.0) {
                                                                continue;
                                                            }
                                                            final float d = DamageUtil.calculate(x + 0.5f, y + 1, z + 0.5f, player.getEntityBoundingBox(), (EntityLivingBase)player, (IBlockAccess)helper, true);
                                                            if (d > damage && (damage = d) > this.minDamage) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    else {
                                                        damage = DamageUtil.calculate(x + 0.5f, y + 1, z + 0.5f, this.target.getEntityBoundingBox(), (EntityLivingBase)this.target, (IBlockAccess)helper, true);
                                                    }
                                                    if (damage >= this.minDamage && damage >= maxDamage) {
                                                        if (damage >= self) {
                                                            final BlockPos[] positions = new BlockPos[this.newVer ? 2 : 3];
                                                            positions[0] = mPos.toImmutable();
                                                            positions[1] = up;
                                                            if (!this.newVer) {
                                                                positions[2] = upUp;
                                                            }
                                                            final IBlockState[] states = new IBlockState[this.newVer ? 2 : 3];
                                                            states[0] = state;
                                                            states[1] = upState;
                                                            if (!this.newVer) {
                                                                states[2] = upUpState;
                                                            }
                                                            maxDamage = damage;
                                                            constellation = new BigConstellation(this.automine, positions, states, this.target);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (constellation != null) {
            final IConstellation finalConstellation = constellation;
            AutoMineCalc.mc.addScheduledTask(() -> {
                this.automine.setFuture(null);
                this.automine.offer(finalConstellation);
            });
        }
        else {
            AutoMineCalc.mc.addScheduledTask(() -> this.automine.setFuture(null));
        }
    }
    
    private double dsq(final double x, final double y, final double z) {
        return (this.mX - x) * (this.mX - x) + (this.mZ - z) * (this.mZ - z) + (this.mY - y) * (this.mY - y);
    }
}
