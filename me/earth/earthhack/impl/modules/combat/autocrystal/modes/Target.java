//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.modes;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import java.util.*;

public enum Target implements Globals
{
    Closest {
        @Override
        public EntityPlayer getTarget(final List<EntityPlayer> players, final List<EntityPlayer> enemies, final double maxRange) {
            return EntityUtil.getClosestEnemy(Target$1.mc.player.posX, Target$1.mc.player.posY, Target$1.mc.player.posZ, maxRange, enemies, players);
        }
    }, 
    FOV {
        @Override
        public EntityPlayer getTarget(final List<EntityPlayer> players, final List<EntityPlayer> enemies, final double maxRange) {
            final EntityPlayer enemy = Target.getByFov(enemies, maxRange);
            if (enemy == null) {
                return Target.getByFov(players, maxRange);
            }
            return enemy;
        }
    }, 
    Angle {
        @Override
        public EntityPlayer getTarget(final List<EntityPlayer> players, final List<EntityPlayer> enemies, final double maxRange) {
            final EntityPlayer enemy = Target.getByAngle(enemies, maxRange);
            if (enemy == null) {
                return Target.getByAngle(players, maxRange);
            }
            return enemy;
        }
    }, 
    Damage {
        @Override
        public EntityPlayer getTarget(final List<EntityPlayer> players, final List<EntityPlayer> enemies, final double maxRange) {
            return null;
        }
    };
    
    public static final String DESCRIPTION = "-Closest, will target the closest Enemy.\n-FOV, will target the player you are looking at (by Angle).\n-Angle, similar to FOV but will also target players outside your FOV.\n-Damage, Calculates Damages for all Players in Range and takes the best one (intensive).";
    
    public abstract EntityPlayer getTarget(final List<EntityPlayer> p0, final List<EntityPlayer> p1, final double p2);
    
    public static EntityPlayer getByFov(final List<EntityPlayer> players, final double maxRange) {
        EntityPlayer closest = null;
        double closestAngle = 360.0;
        for (final EntityPlayer player : players) {
            if (!EntityUtil.isValid((Entity)player, maxRange)) {
                continue;
            }
            final double angle = RotationUtil.getAngle((Entity)player, 1.4);
            if (angle >= closestAngle || angle >= Target.mc.gameSettings.fovSetting / 2.0f) {
                continue;
            }
            closest = player;
            closestAngle = angle;
        }
        return closest;
    }
    
    public static EntityPlayer getByAngle(final List<EntityPlayer> players, final double maxRange) {
        EntityPlayer closest = null;
        double closestAngle = 360.0;
        for (final EntityPlayer player : players) {
            if (!EntityUtil.isValid((Entity)player, maxRange)) {
                continue;
            }
            final double angle = RotationUtil.getAngle((Entity)player, 1.4);
            if (angle >= closestAngle || angle >= Target.mc.gameSettings.fovSetting / 2.0f) {
                continue;
            }
            closest = player;
            closestAngle = angle;
        }
        return closest;
    }
}
