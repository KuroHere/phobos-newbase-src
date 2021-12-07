//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.util;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.util.math.path.*;
import java.util.*;
import net.minecraft.block.*;

public class PositionData extends BasePath implements Globals, Comparable<PositionData>
{
    private final List<EntityPlayer> forced;
    private final Set<EntityPlayer> antiTotems;
    private EntityPlayer target;
    private EntityPlayer facePlace;
    private IBlockState state;
    private float selfDamage;
    private float damage;
    private boolean obby;
    private boolean obbyValid;
    private boolean blocked;
    private boolean liquidValid;
    private boolean liquid;
    private float minDiff;
    
    public PositionData(final BlockPos pos, final int blocks) {
        this(pos, blocks, new HashSet<EntityPlayer>());
    }
    
    public PositionData(final BlockPos pos, final int blocks, final Set<EntityPlayer> antiTotems) {
        super((Entity)RotationUtil.getRotationPlayer(), pos, blocks);
        this.forced = new ArrayList<EntityPlayer>();
        this.antiTotems = antiTotems;
        this.minDiff = Float.MAX_VALUE;
    }
    
    public boolean usesObby() {
        return this.obby;
    }
    
    public boolean isObbyValid() {
        return this.obbyValid;
    }
    
    public float getMaxDamage() {
        return this.damage;
    }
    
    public void setDamage(final float damage) {
        this.damage = damage;
    }
    
    public float getSelfDamage() {
        return this.selfDamage;
    }
    
    public void setSelfDamage(final float selfDamage) {
        this.selfDamage = selfDamage;
    }
    
    public EntityPlayer getTarget() {
        return this.target;
    }
    
    public void setTarget(final EntityPlayer target) {
        this.target = target;
    }
    
    public EntityPlayer getFacePlacer() {
        return this.facePlace;
    }
    
    public void setFacePlacer(final EntityPlayer facePlace) {
        this.facePlace = facePlace;
    }
    
    public Set<EntityPlayer> getAntiTotems() {
        return this.antiTotems;
    }
    
    public void addAntiTotem(final EntityPlayer player) {
        this.antiTotems.add(player);
    }
    
    public boolean isBlocked() {
        return this.blocked;
    }
    
    public float getMinDiff() {
        return this.minDiff;
    }
    
    public void setMinDiff(final float minDiff) {
        this.minDiff = minDiff;
    }
    
    public boolean isForce() {
        return !this.forced.isEmpty();
    }
    
    public void addForcePlayer(final EntityPlayer player) {
        this.forced.add(player);
    }
    
    public List<EntityPlayer> getForced() {
        return this.forced;
    }
    
    public boolean isLiquidValid() {
        return this.liquidValid;
    }
    
    public boolean isLiquid() {
        return this.liquid;
    }
    
    @Override
    public int compareTo(final PositionData o) {
        if (Math.abs(o.damage - this.damage) >= 1.0f) {
            return Float.compare(o.damage, this.damage);
        }
        if (this.usesObby() && o.usesObby()) {
            return Integer.compare(this.getPath().length, o.getPath().length) + Float.compare(this.selfDamage, o.selfDamage);
        }
        return Float.compare(this.selfDamage, o.getSelfDamage());
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof PositionData && ((PositionData)o).getPos().equals((Object)this.getPos());
    }
    
    @Override
    public int hashCode() {
        return this.getPos().hashCode();
    }
    
    public static PositionData create(final BlockPos pos, final boolean obby, final int helpingBlocks, final boolean newVer, final boolean newVerEntities, final int deathTime, final List<Entity> entities, final boolean lava, final boolean water, final boolean lavaItems) {
        final PositionData data = new PositionData(pos, helpingBlocks);
        data.state = PositionData.mc.world.getBlockState(pos);
        if (data.state.getBlock() != Blocks.BEDROCK && data.state.getBlock() != Blocks.OBSIDIAN) {
            if (!obby || !data.state.getMaterial().isReplaceable() || checkEntities(data, pos, entities, 0, true, true, false)) {
                return data;
            }
            data.obby = true;
        }
        final BlockPos up = pos.up();
        final IBlockState upState = PositionData.mc.world.getBlockState(up);
        if (upState.getBlock() != Blocks.AIR) {
            if (!checkLiquid(upState.getBlock(), water, lava)) {
                return data;
            }
            data.liquid = true;
        }
        final IBlockState upUpState;
        if (!newVer && (upUpState = PositionData.mc.world.getBlockState(up.up())).getBlock() != Blocks.AIR) {
            if (!checkLiquid(upUpState.getBlock(), water, lava)) {
                return data;
            }
            data.liquid = true;
        }
        final boolean checkLavaItems = lavaItems && upState.getMaterial() == Material.LAVA;
        if (checkEntities(data, up, entities, deathTime, false, false, checkLavaItems) || (!newVerEntities && checkEntities(data, up.up(), entities, deathTime, false, false, checkLavaItems))) {
            return data;
        }
        if (data.obby) {
            if (data.liquid) {
                data.liquidValid = true;
            }
            data.obbyValid = true;
            return data;
        }
        if (data.liquid) {
            data.liquidValid = true;
            return data;
        }
        data.setValid(true);
        return data;
    }
    
    private static boolean checkEntities(final PositionData data, final BlockPos pos, final List<Entity> entities, final int deathTime, final boolean dead, final boolean spawning, final boolean lavaItems) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos);
        for (final Entity entity : entities) {
            if (entity != null && (!spawning || entity.preventEntitySpawning) && (!dead || !EntityUtil.isDead(entity))) {
                if (!entity.getEntityBoundingBox().intersectsWith(bb)) {
                    continue;
                }
                if (lavaItems && entity instanceof EntityItem) {
                    continue;
                }
                if (!(entity instanceof EntityEnderCrystal)) {
                    return true;
                }
                if (!dead) {
                    if (EntityUtil.isDead(entity)) {
                        if (Managers.SET_DEAD.passedDeathTime(entity, deathTime)) {
                            continue;
                        }
                        if (((IEntity)entity).getPseudoTime().passed(deathTime)) {
                            continue;
                        }
                        return true;
                    }
                    else {
                        data.blocked = true;
                    }
                }
                data.getBlockingEntities().add(new BlockingEntity(entity, pos));
            }
        }
        return false;
    }
    
    private static boolean checkLiquid(final Block block, final boolean water, final boolean lava) {
        return (water && (block == Blocks.WATER || block == Blocks.FLOWING_WATER)) || (lava && (block == Blocks.LAVA || block == Blocks.FLOWING_LAVA));
    }
}
