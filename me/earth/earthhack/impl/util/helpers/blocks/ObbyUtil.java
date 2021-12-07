//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks;

import me.earth.earthhack.impl.util.math.path.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import net.minecraft.entity.*;
import java.util.*;
import me.earth.earthhack.impl.util.math.raytrace.*;

public class ObbyUtil
{
    public static boolean place(final ObbyModule module, final Pathable path) {
        if (!path.isValid()) {
            return false;
        }
        Entity target = null;
        boolean crystalFound = false;
        float maxDamage = Float.MAX_VALUE;
        for (final BlockingEntity entity : path.getBlockingEntities()) {
            if (!module.attack.getValue() || !(entity.getEntity() instanceof EntityEnderCrystal)) {
                return false;
            }
            crystalFound = true;
            final float damage = DamageUtil.calculate(entity.getEntity(), (EntityLivingBase)module.getPlayer());
            if (damage >= maxDamage || !module.pop.getValue().shouldPop(damage, module.popTime.getValue())) {
                continue;
            }
            maxDamage = damage;
            target = entity.getEntity();
        }
        if (target != null) {
            module.attacking = new CPacketUseEntity(target);
        }
        else if (crystalFound && module.blockingType.getValue() != BlockingType.Crystals) {
            return false;
        }
        for (final Ray ray : path.getPath()) {
            module.placeBlock(ray.getPos(), ray.getFacing(), ray.getRotations(), ray.getResult().hitVec);
            if (module.blocksPlaced >= module.blocks.getValue() || module.rotate.getValue() == Rotate.Normal) {
                return true;
            }
        }
        return true;
    }
}
