//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.render;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.vector.*;
import me.earth.earthhack.impl.core.mixins.render.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class VectorUtil
{
    static Matrix4f modelMatrix;
    static Matrix4f projectionMatrix;
    private static final Minecraft mc;
    
    public static Plane toScreen(final double x, final double y, final double z) {
        final Entity view = VectorUtil.mc.getRenderViewEntity();
        if (view == null) {
            return new Plane(0.0, 0.0, false);
        }
        final Vec3d camPos = ActiveRenderInfo.getCameraPosition();
        final Vec3d eyePos = ActiveRenderInfo.projectViewFromEntity(view, (double)VectorUtil.mc.getRenderPartialTicks());
        final float vecX = (float)(camPos.xCoord + eyePos.xCoord - (float)x);
        final float vecY = (float)(camPos.yCoord + eyePos.yCoord - (float)y);
        final float vecZ = (float)(camPos.zCoord + eyePos.zCoord - (float)z);
        final Vector4f pos = new Vector4f(vecX, vecY, vecZ, 1.0f);
        VectorUtil.modelMatrix.load(IActiveRenderInfo.getModelview().asReadOnlyBuffer());
        VectorUtil.projectionMatrix.load(IActiveRenderInfo.getProjection().asReadOnlyBuffer());
        VecTransformCoordinate(pos, VectorUtil.modelMatrix);
        VecTransformCoordinate(pos, VectorUtil.projectionMatrix);
        if (pos.w > 0.0f) {
            final Vector4f vector4f = pos;
            vector4f.x *= -100000.0f;
            final Vector4f vector4f2 = pos;
            vector4f2.y *= -100000.0f;
        }
        else {
            final float invert = 1.0f / pos.w;
            final Vector4f vector4f3 = pos;
            vector4f3.x *= invert;
            final Vector4f vector4f4 = pos;
            vector4f4.y *= invert;
        }
        final ScaledResolution res = new ScaledResolution(VectorUtil.mc);
        final float halfWidth = res.getScaledWidth() / 2.0f;
        final float halfHeight = res.getScaledHeight() / 2.0f;
        pos.x = halfWidth + (0.5f * pos.x * res.getScaledWidth() + 0.5f);
        pos.y = halfHeight - (0.5f * pos.y * res.getScaledHeight() + 0.5f);
        boolean bVisible = true;
        if (pos.x < 0.0f || pos.y < 0.0f || pos.x > res.getScaledWidth() || pos.y > res.getScaledHeight()) {
            bVisible = false;
        }
        return new Plane(pos.x, pos.y, bVisible);
    }
    
    private static void VecTransformCoordinate(final Vector4f vec, final Matrix4f matrix) {
        final float x = vec.x;
        final float y = vec.y;
        final float z = vec.z;
        vec.x = x * matrix.m00 + y * matrix.m10 + z * matrix.m20 + matrix.m30;
        vec.y = x * matrix.m01 + y * matrix.m11 + z * matrix.m21 + matrix.m31;
        vec.z = x * matrix.m02 + y * matrix.m12 + z * matrix.m22 + matrix.m32;
        vec.w = x * matrix.m03 + y * matrix.m13 + z * matrix.m23 + matrix.m33;
    }
    
    static {
        VectorUtil.modelMatrix = new Matrix4f();
        VectorUtil.projectionMatrix = new Matrix4f();
        mc = Minecraft.getMinecraft();
    }
    
    public static class Plane
    {
        private final double x;
        private final double y;
        private final boolean visible;
        
        public Plane(final double x, final double y, final boolean visible) {
            this.x = x;
            this.y = y;
            this.visible = visible;
        }
        
        public double getX() {
            return this.x;
        }
        
        public double getY() {
            return this.y;
        }
        
        public boolean isVisible() {
            return this.visible;
        }
    }
}
