//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nointerp;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class NoInterp extends Module
{
    private final Setting<Boolean> silent;
    private final Setting<Boolean> setRotations;
    private final Setting<Boolean> noDeathJitter;
    private final Setting<Boolean> onlyPlayers;
    
    public NoInterp() {
        super("NoInterp", Category.Misc);
        this.silent = this.register(new BooleanSetting("Silent", true));
        this.setRotations = this.register(new BooleanSetting("Fast-Rotations", false));
        this.noDeathJitter = this.register(new BooleanSetting("NoDeathJitter", true));
        this.onlyPlayers = this.register(new BooleanSetting("OnlyPlayers", false));
        this.setData(new SimpleData(this, "Makes the client more accurate. The Packets module is recommended when using this."));
    }
    
    public boolean isSilent() {
        return this.silent.getValue();
    }
    
    public boolean shouldFixDeathJitter() {
        return this.noDeathJitter.getValue();
    }
    
    public static void handleNoInterp(final NoInterp noInterp, final Entity entity, final int posIncrements, final double x, final double y, final double z, final float yaw, final float pitch) {
        final IEntityNoInterp entityNoInterp = (IEntityNoInterp)entity;
        if (!entityNoInterp.isNoInterping()) {
            return;
        }
        if (noInterp.setRotations.getValue()) {
            entity.setPosition(x, y, z);
            entity.rotationYaw = yaw % 360.0f;
            entity.rotationPitch = pitch % 360.0f;
        }
        else {
            entity.setPosition(x, y, z);
        }
        entityNoInterp.setPosIncrements(posIncrements);
    }
    
    public static double noInterpX(final NoInterp noInterp, final Entity entity) {
        if (noInterp != null && noInterp.isEnabled() && noInterp.isSilent() && entity instanceof IEntityNoInterp && ((IEntityNoInterp)entity).isNoInterping()) {
            return ((IEntityNoInterp)entity).getNoInterpX();
        }
        return entity.posX;
    }
    
    public static double noInterpY(final NoInterp noInterp, final Entity entity) {
        if (noInterp != null && noInterp.isEnabled() && noInterp.isSilent() && entity instanceof IEntityNoInterp && ((IEntityNoInterp)entity).isNoInterping()) {
            return ((IEntityNoInterp)entity).getNoInterpY();
        }
        return entity.posY;
    }
    
    public static double noInterpZ(final NoInterp noInterp, final Entity entity) {
        if (noInterp != null && noInterp.isEnabled() && noInterp.isSilent() && entity instanceof IEntityNoInterp && ((IEntityNoInterp)entity).isNoInterping()) {
            return ((IEntityNoInterp)entity).getNoInterpZ();
        }
        return entity.posZ;
    }
    
    public static boolean update(final NoInterp module, final Entity entity) {
        if (module == null || !module.isEnabled() || !module.silent.getValue() || EntityUtil.isDead(entity)) {
            return false;
        }
        final IEntityNoInterp noInterp;
        if (!(entity instanceof IEntityNoInterp) || !(noInterp = (IEntityNoInterp)entity).isNoInterping()) {
            return false;
        }
        if (noInterp.getPosIncrements() > 0) {
            final double x = noInterp.getNoInterpX() + (entity.posX - noInterp.getNoInterpX()) / noInterp.getPosIncrements();
            final double y = noInterp.getNoInterpY() + (entity.posY - noInterp.getNoInterpY()) / noInterp.getPosIncrements();
            final double z = noInterp.getNoInterpZ() + (entity.posZ - noInterp.getNoInterpZ()) / noInterp.getPosIncrements();
            entity.prevPosX = noInterp.getNoInterpX();
            entity.prevPosY = noInterp.getNoInterpY();
            entity.prevPosZ = noInterp.getNoInterpZ();
            entity.lastTickPosX = noInterp.getNoInterpX();
            entity.lastTickPosY = noInterp.getNoInterpY();
            entity.lastTickPosZ = noInterp.getNoInterpZ();
            noInterp.setNoInterpX(x);
            noInterp.setNoInterpY(y);
            noInterp.setNoInterpZ(z);
            noInterp.setPosIncrements(noInterp.getPosIncrements() - 1);
        }
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase base = (EntityLivingBase)entity;
            final double xDiff = noInterp.getNoInterpX() - entity.prevPosX;
            final double zDiff = noInterp.getNoInterpZ() - entity.prevPosZ;
            final double yDiff = (entity instanceof EntityFlying) ? (noInterp.getNoInterpY() - entity.prevPosY) : 0.0;
            float diff = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff + yDiff * yDiff) * 4.0f;
            if (diff > 1.0f) {
                diff = 1.0f;
            }
            final float limbSwingAmount = noInterp.getNoInterpSwingAmount();
            noInterp.setNoInterpPrevSwing(base.prevLimbSwingAmount = limbSwingAmount);
            noInterp.setNoInterpSwingAmount(limbSwingAmount + (diff - limbSwingAmount) * 0.4f);
            base.limbSwingAmount = noInterp.getNoInterpSwingAmount();
            final float limbSwing = noInterp.getNoInterpSwing() + base.limbSwingAmount;
            noInterp.setNoInterpSwing(limbSwing);
            base.limbSwing = limbSwing;
        }
        return true;
    }
}
