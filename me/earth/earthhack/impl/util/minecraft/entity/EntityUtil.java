//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.entity;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.managers.*;
import java.util.*;
import java.util.function.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import me.earth.earthhack.impl.util.math.*;

public class EntityUtil implements Globals
{
    public static boolean isDead(final Entity entity) {
        return entity.isDead || ((IEntity)entity).isPseudoDead() || (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0f);
    }
    
    public static float getHealth(final EntityLivingBase base) {
        return base.getHealth() + base.getAbsorptionAmount();
    }
    
    public static float getHealth(final EntityLivingBase base, final boolean absorption) {
        if (absorption) {
            return base.getHealth() + base.getAbsorptionAmount();
        }
        return base.getHealth();
    }
    
    public static EntityPlayer getClosestEnemy() {
        return getClosestEnemy(EntityUtil.mc.world.playerEntities);
    }
    
    public static EntityPlayer getClosestEnemy(final List<EntityPlayer> list) {
        return getClosestEnemy(EntityUtil.mc.player.getPositionVector(), list);
    }
    
    public static EntityPlayer getClosestEnemy(final BlockPos pos, final List<EntityPlayer> list) {
        return getClosestEnemy(pos.getX(), pos.getY(), pos.getZ(), list);
    }
    
    public static EntityPlayer getClosestEnemy(final Vec3d vec3d, final List<EntityPlayer> list) {
        return getClosestEnemy(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, list);
    }
    
    public static EntityPlayer getClosestEnemy(final double x, final double y, final double z, final List<EntityPlayer> players) {
        EntityPlayer closest = null;
        double distance = 3.4028234663852886E38;
        for (final EntityPlayer player : players) {
            if (player != null && !isDead((Entity)player) && !player.equals((Object)EntityUtil.mc.player) && !Managers.FRIENDS.contains(player)) {
                final double dist = player.getDistanceSq(x, y, z);
                if (dist >= distance) {
                    continue;
                }
                closest = player;
                distance = dist;
            }
        }
        return closest;
    }
    
    public static EntityPlayer getClosestEnemy(final double x, final double y, final double z, final double maxRange, final List<EntityPlayer> players) {
        final List<List<EntityPlayer>> split = CollectionUtil.split(players, Managers.ENEMIES::contains);
        return getClosestEnemy(x, y, z, maxRange, split.get(0), split.get(1));
    }
    
    public static EntityPlayer getClosestEnemy(final double x, final double y, final double z, final double maxRange, final List<EntityPlayer> enemies, final List<EntityPlayer> players) {
        final EntityPlayer closestEnemied = getClosestEnemy(x, y, z, enemies);
        if (closestEnemied != null && closestEnemied.getDistanceSq(x, y, z) < MathUtil.square(maxRange)) {
            return closestEnemied;
        }
        return getClosestEnemy(x, y, z, players);
    }
    
    public static boolean isValid(final Entity player, final double range) {
        return player != null && !isDead(player) && EntityUtil.mc.player.getDistanceSqToEntity(player) <= MathUtil.square(range) && !Managers.FRIENDS.contains(player);
    }
}
