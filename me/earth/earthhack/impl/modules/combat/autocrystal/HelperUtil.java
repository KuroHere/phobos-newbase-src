//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;

public class HelperUtil implements Globals
{
    public static BreakValidity isValid(final AutoCrystal module, final Entity crystal) {
        if (module.existed.getValue() != 0 && System.currentTimeMillis() - ((IEntity)crystal).getTimeStamp() + (module.pingExisted.getValue() ? (ServerUtil.getPingNoPingSpoof() / 2.0) : 0.0) < module.existed.getValue()) {
            return BreakValidity.INVALID;
        }
        if (RotationUtil.getRotationPlayer().getDistanceSqToEntity(crystal) >= MathUtil.square(module.breakRange.getValue())) {
            return BreakValidity.INVALID;
        }
        if (RotationUtil.getRotationPlayer().getDistanceSqToEntity(crystal) >= MathUtil.square(module.breakTrace.getValue()) && !RayTraceUtil.canBeSeen(new Vec3d(crystal.posX, crystal.posY + 1.7, crystal.posZ), (Entity)RotationUtil.getRotationPlayer())) {
            return BreakValidity.INVALID;
        }
        if (module.rotate.getValue().noRotate(ACRotate.Break) || (RotationUtil.isLegit(crystal, crystal) && module.positionHistoryHelper.arePreviousRotationsLegit(crystal, module.rotationTicks.getValue(), true))) {
            return BreakValidity.VALID;
        }
        return BreakValidity.ROTATIONS;
    }
    
    public static void simulateExplosion(final AutoCrystal module, final double x, final double y, final double z) {
        final List<Entity> entities = Managers.ENTITIES.getEntities();
        if (entities == null) {
            return;
        }
        for (final Entity entity : entities) {
            if (entity instanceof EntityEnderCrystal && entity.getDistanceSq(x, y, z) < 144.0) {
                if (module.pseudoSetDead.getValue()) {
                    ((IEntity)entity).setPseudoDead(true);
                }
                else {
                    Managers.SET_DEAD.setDead(entity);
                }
            }
        }
    }
    
    public static boolean validChange(final BlockPos pos, final List<EntityPlayer> players) {
        for (final EntityPlayer player : players) {
            if (player != null && !player.equals((Object)HelperUtil.mc.player) && !player.equals((Object)RotationUtil.getRotationPlayer()) && !EntityUtil.isDead((Entity)player)) {
                if (Managers.FRIENDS.contains(player)) {
                    continue;
                }
                if (player.getDistanceSqToCenter(pos) <= 4.0 && player.posY >= pos.getY()) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    public static boolean valid(final Entity entity, final double range, final double trace) {
        final EntityPlayer player = RotationUtil.getRotationPlayer();
        final double d = entity.getDistanceSqToEntity((Entity)player);
        return d < MathUtil.square(range) && (d < trace || RayTraceUtil.canBeSeen(entity, (EntityLivingBase)player));
    }
}
