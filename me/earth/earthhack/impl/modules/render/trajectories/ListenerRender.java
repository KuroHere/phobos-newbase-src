//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.trajectories;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.util.render.*;
import java.awt.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import org.lwjgl.util.glu.*;
import net.minecraft.item.*;
import java.util.*;

final class ListenerRender extends ModuleListener<Trajectories, Render3DEvent>
{
    public ListenerRender(final Trajectories module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (ListenerRender.mc.player == null || ListenerRender.mc.world == null || ListenerRender.mc.gameSettings.thirdPersonView != 0) {
            return;
        }
        if ((ListenerRender.mc.player.getHeldItemMainhand() == ItemStack.field_190927_a || !(ListenerRender.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow)) && (ListenerRender.mc.player.getHeldItemMainhand() == ItemStack.field_190927_a || !((Trajectories)this.module).isThrowable(ListenerRender.mc.player.getHeldItemMainhand().getItem())) && (ListenerRender.mc.player.getHeldItemOffhand() == ItemStack.field_190927_a || !((Trajectories)this.module).isThrowable(ListenerRender.mc.player.getHeldItemOffhand().getItem()))) {
            return;
        }
        final double renderPosX = Interpolation.getRenderPosX();
        final double renderPosY = Interpolation.getRenderPosY();
        final double renderPosZ = Interpolation.getRenderPosZ();
        Item item = null;
        if (ListenerRender.mc.player.getHeldItemMainhand() != ItemStack.field_190927_a && (ListenerRender.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow || ((Trajectories)this.module).isThrowable(ListenerRender.mc.player.getHeldItemMainhand().getItem()))) {
            item = ListenerRender.mc.player.getHeldItemMainhand().getItem();
        }
        else if (ListenerRender.mc.player.getHeldItemOffhand() != ItemStack.field_190927_a && ((Trajectories)this.module).isThrowable(ListenerRender.mc.player.getHeldItemOffhand().getItem())) {
            item = ListenerRender.mc.player.getHeldItemOffhand().getItem();
        }
        if (item == null) {
            return;
        }
        RenderUtil.startRender();
        double posX = renderPosX - MathHelper.cos(ListenerRender.mc.player.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        double posY = renderPosY + ListenerRender.mc.player.getEyeHeight() - 0.1000000014901161;
        double posZ = renderPosZ - MathHelper.sin(ListenerRender.mc.player.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        final float maxDist = ((Trajectories)this.module).getDistance(item);
        double motionX = -MathHelper.sin(ListenerRender.mc.player.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(ListenerRender.mc.player.rotationPitch / 180.0f * 3.1415927f) * maxDist;
        double motionY = -MathHelper.sin((ListenerRender.mc.player.rotationPitch - ((Trajectories)this.module).getThrowPitch(item)) / 180.0f * 3.141593f) * maxDist;
        double motionZ = MathHelper.cos(ListenerRender.mc.player.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(ListenerRender.mc.player.rotationPitch / 180.0f * 3.1415927f) * maxDist;
        final int var6 = 72000 - ListenerRender.mc.player.getItemInUseCount();
        float power = var6 / 20.0f;
        power = (power * power + power * 2.0f) / 3.0f;
        if (power > 1.0f) {
            power = 1.0f;
        }
        final float distance = MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= distance;
        motionY /= distance;
        motionZ /= distance;
        final float pow = ((item instanceof ItemBow) ? (power * 2.0f) : 1.0f) * ((Trajectories)this.module).getThrowVelocity(item);
        motionX *= pow;
        motionY *= pow;
        motionZ *= pow;
        if (!ListenerRender.mc.player.onGround) {
            motionY += ListenerRender.mc.player.motionY;
        }
        GlStateManager.color(((Trajectories)this.module).color.getValue().getRed() / 255.0f, ((Trajectories)this.module).color.getValue().getGreen() / 255.0f, ((Trajectories)this.module).color.getValue().getBlue() / 255.0f, ((Trajectories)this.module).color.getValue().getAlpha() / 255.0f);
        GL11.glEnable(2848);
        final float size = (float)((item instanceof ItemBow) ? 0.3 : 0.25);
        boolean hasLanded = false;
        Entity landingOnEntity = null;
        RayTraceResult landingPosition = null;
        GL11.glBegin(3);
        while (!hasLanded && posY > 0.0) {
            final Vec3d present = new Vec3d(posX, posY, posZ);
            final Vec3d future = new Vec3d(posX + motionX, posY + motionY, posZ + motionZ);
            final RayTraceResult possibleLandingStrip = ListenerRender.mc.world.rayTraceBlocks(present, future, false, true, false);
            if (possibleLandingStrip != null && possibleLandingStrip.typeOfHit != RayTraceResult.Type.MISS) {
                landingPosition = possibleLandingStrip;
                hasLanded = true;
            }
            final AxisAlignedBB arrowBox = new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
            final List<Entity> entities = ((Trajectories)this.module).getEntitiesWithinAABB(arrowBox.offset(motionX, motionY, motionZ).addCoord(1.0, 1.0, 1.0));
            for (final Object entity : entities) {
                final Entity boundingBox = (Entity)entity;
                if (boundingBox.canBeCollidedWith() && boundingBox != ListenerRender.mc.player) {
                    final float var7 = 0.3f;
                    final AxisAlignedBB var8 = boundingBox.getEntityBoundingBox().addCoord((double)var7, (double)var7, (double)var7);
                    final RayTraceResult possibleEntityLanding = var8.calculateIntercept(present, future);
                    if (possibleEntityLanding == null) {
                        continue;
                    }
                    hasLanded = true;
                    landingOnEntity = boundingBox;
                    landingPosition = possibleEntityLanding;
                }
            }
            if (landingOnEntity != null) {
                GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);
            }
            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            final float motionAdjustment = 0.99f;
            motionX *= 0.9900000095367432;
            motionY *= 0.9900000095367432;
            motionZ *= 0.9900000095367432;
            motionY -= ((Trajectories)this.module).getGravity(item);
            this.drawLine3D(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
        }
        GL11.glEnd();
        if (((Trajectories)this.module).landed.getValue() && landingPosition != null && landingPosition.typeOfHit == RayTraceResult.Type.BLOCK) {
            GlStateManager.translate(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
            final int side = landingPosition.sideHit.getIndex();
            if (side == 2) {
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            }
            else if (side == 3) {
                GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            }
            else if (side == 4) {
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            }
            else if (side == 5) {
                GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            }
            final Cylinder c = new Cylinder();
            GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
            c.setDrawStyle(100013);
            if (landingOnEntity != null) {
                GlStateManager.color(0.0f, 0.0f, 0.0f, 1.0f);
                GL11.glLineWidth(2.5f);
                c.draw(0.5f, 0.15f, 0.0f, 8, 1);
                GL11.glLineWidth(0.1f);
                GlStateManager.color(1.0f, 0.0f, 0.0f, 1.0f);
            }
            c.draw(0.5f, 0.15f, 0.0f, 8, 1);
        }
        RenderUtil.endRender();
    }
    
    public void drawLine3D(final double var1, final double var2, final double var3) {
        GL11.glVertex3d(var1, var2, var3);
    }
}
