//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.nointerp.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.core.ducks.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.core.ducks.render.*;
import net.minecraft.client.renderer.culling.*;
import me.earth.earthhack.impl.modules.*;

public class Interpolation implements Globals
{
    private static final ModuleCache<NoInterp> NOINTERP;
    
    public static Vec3d interpolatedEyePos() {
        return Interpolation.mc.player.getPositionEyes(Interpolation.mc.getRenderPartialTicks());
    }
    
    public static Vec3d interpolatedEyeVec() {
        return Interpolation.mc.player.getLook(Interpolation.mc.getRenderPartialTicks());
    }
    
    public static Vec3d interpolatedEyeVec(final EntityPlayer player) {
        return player.getLook(Interpolation.mc.getRenderPartialTicks());
    }
    
    public static Vec3d interpolateEntity(final Entity entity) {
        double x;
        double y;
        double z;
        if (Interpolation.NOINTERP.isEnabled() && Interpolation.NOINTERP.get().isSilent() && entity instanceof IEntityNoInterp && ((IEntityNoInterp)entity).isNoInterping()) {
            x = interpolateLastTickPos(((IEntityNoInterp)entity).getNoInterpX(), entity.lastTickPosX) - getRenderPosX();
            y = interpolateLastTickPos(((IEntityNoInterp)entity).getNoInterpY(), entity.lastTickPosY) - getRenderPosY();
            z = interpolateLastTickPos(((IEntityNoInterp)entity).getNoInterpZ(), entity.lastTickPosZ) - getRenderPosZ();
        }
        else {
            x = interpolateLastTickPos(entity.posX, entity.lastTickPosX) - getRenderPosX();
            y = interpolateLastTickPos(entity.posY, entity.lastTickPosY) - getRenderPosY();
            z = interpolateLastTickPos(entity.posZ, entity.lastTickPosZ) - getRenderPosZ();
        }
        return new Vec3d(x, y, z);
    }
    
    public static Vec3d interpolateVectors(final Vec3d current, final Vec3d last) {
        final double x = interpolateLastTickPos(current.xCoord, last.xCoord);
        final double y = interpolateLastTickPos(current.yCoord, last.yCoord);
        final double z = interpolateLastTickPos(current.zCoord, last.zCoord);
        return new Vec3d(x, y, z);
    }
    
    public static double interpolateLastTickPos(final double pos, final double lastPos) {
        return lastPos + (pos - lastPos) * ((IMinecraft)Interpolation.mc).getTimer().field_194147_b;
    }
    
    public static AxisAlignedBB interpolatePos(final BlockPos pos, final float height) {
        return new AxisAlignedBB(pos.getX() - Interpolation.mc.getRenderManager().viewerPosX, pos.getY() - Interpolation.mc.getRenderManager().viewerPosY, pos.getZ() - Interpolation.mc.getRenderManager().viewerPosZ, pos.getX() - Interpolation.mc.getRenderManager().viewerPosX + 1.0, pos.getY() - Interpolation.mc.getRenderManager().viewerPosY + height, pos.getZ() - Interpolation.mc.getRenderManager().viewerPosZ + 1.0);
    }
    
    public static AxisAlignedBB interpolateAxis(final AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.minX - Interpolation.mc.getRenderManager().viewerPosX, bb.minY - Interpolation.mc.getRenderManager().viewerPosY, bb.minZ - Interpolation.mc.getRenderManager().viewerPosZ, bb.maxX - Interpolation.mc.getRenderManager().viewerPosX, bb.maxY - Interpolation.mc.getRenderManager().viewerPosY, bb.maxZ - Interpolation.mc.getRenderManager().viewerPosZ);
    }
    
    public static AxisAlignedBB offsetRenderPos(final AxisAlignedBB bb) {
        return bb.offset(-getRenderPosX(), -getRenderPosY(), -getRenderPosZ());
    }
    
    public static double getRenderPosX() {
        return ((IRenderManager)Interpolation.mc.getRenderManager()).getRenderPosX();
    }
    
    public static double getRenderPosY() {
        return ((IRenderManager)Interpolation.mc.getRenderManager()).getRenderPosY();
    }
    
    public static double getRenderPosZ() {
        return ((IRenderManager)Interpolation.mc.getRenderManager()).getRenderPosZ();
    }
    
    public static Frustum createFrustum(final Entity entity) {
        final Frustum frustum = new Frustum();
        final double x = interpolateLastTickPos(entity.posX, entity.lastTickPosX);
        final double y = interpolateLastTickPos(entity.posY, entity.lastTickPosY);
        final double z = interpolateLastTickPos(entity.posZ, entity.lastTickPosZ);
        frustum.setPosition(x, y, z);
        return frustum;
    }
    
    static {
        NOINTERP = Caches.getModule(NoInterp.class);
    }
}
