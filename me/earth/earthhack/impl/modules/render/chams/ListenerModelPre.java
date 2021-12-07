//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.chams;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.render.esp.*;
import me.earth.earthhack.impl.modules.render.chams.mode.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.util.render.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import org.lwjgl.util.vector.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.core.mixins.render.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;

final class ListenerModelPre extends ModuleListener<Chams, ModelRenderEvent.Pre>
{
    public ListenerModelPre(final Chams module) {
        super(module, (Class<? super Object>)ModelRenderEvent.Pre.class);
    }
    
    public void invoke(final ModelRenderEvent.Pre event) {
        if (!ESP.isRendering && ((Chams)this.module).mode.getValue() == ChamsMode.CSGO) {
            final EntityLivingBase entity = event.getEntity();
            if (((Chams)this.module).isValid((Entity)entity)) {
                event.setCancelled(true);
                final boolean lightning = GL11.glIsEnabled(2896);
                final boolean blend = GL11.glIsEnabled(3042);
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                if (!((Chams)this.module).texture.getValue()) {
                    GL11.glDisable(3553);
                }
                if (lightning) {
                    GL11.glDisable(2896);
                }
                if (!blend) {
                    GL11.glEnable(3042);
                }
                GL11.glBlendFunc(770, 771);
                if (((Chams)this.module).xqz.getValue()) {
                    GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929);
                    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                    this.render(event);
                }
                GL11.glDisable(3042);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glEnable(2896);
                if (!((Chams)this.module).texture.getValue()) {
                    GL11.glEnable(3553);
                }
                GL11.glEnable(3008);
                GL11.glPopAttrib();
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                if (!((Chams)this.module).texture.getValue()) {
                    GL11.glDisable(3553);
                }
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                this.render(event);
                if (!blend) {
                    GL11.glDisable(3042);
                }
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                if (lightning) {
                    GL11.glEnable(2896);
                }
                if (!((Chams)this.module).texture.getValue()) {
                    GL11.glEnable(3553);
                }
                GL11.glEnable(3008);
                GL11.glPopAttrib();
            }
        }
        else if (!ESP.isRendering && ((Chams)this.module).mode.getValue() == ChamsMode.Better && event.getEntity() instanceof EntityPlayer) {
            event.setCancelled(true);
            final Color color = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
            final Color wallsColor = ((Chams)this.module).getWallsColor((Entity)event.getEntity());
            GL11.glPushMatrix();
            GL11.glPushAttrib(1048575);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glLineWidth(1.5f);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2960);
            GL11.glEnable(10754);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glColor4f(wallsColor.getRed() / 255.0f, wallsColor.getGreen() / 255.0f, wallsColor.getBlue() / 255.0f, wallsColor.getAlpha() / 255.0f);
            this.render(event);
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            if (((Chams)this.module).xqz.getValue()) {
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                this.render(event);
            }
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        else if (!ESP.isRendering) {
            final EntityLivingBase entity = event.getEntity();
            if (((Chams)this.module).isValid((Entity)entity) && ((Chams)this.module).mode.getValue() == ChamsMode.JelloBottom) {
                event.setCancelled(true);
                this.render(event);
                final Color color2 = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
                GL11.glPushMatrix();
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glLineWidth(1.5f);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2960);
                GL11.glEnable(10754);
                GL11.glDepthMask(false);
                GL11.glDisable(2929);
                GL11.glColor4f(color2.getRed() / 255.0f, color2.getGreen() / 255.0f, color2.getBlue() / 255.0f, color2.getAlpha() / 255.0f);
                this.render(event);
                GL11.glDepthMask(true);
                GL11.glEnable(2929);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
        }
        if (((Chams)this.module).mode.getValue() == ChamsMode.FireShader && !ESP.isRendering && ((Chams)this.module).fireShader != null) {
            if (!((Chams)this.module).isValid((Entity)event.getEntity())) {
                return;
            }
            event.setCancelled(true);
            GL11.glPushAttrib(1048575);
            GL11.glPushMatrix();
            final Color color = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
            ((Chams)this.module).fireShader.bind();
            ((Chams)this.module).fireShader.set("time", (System.currentTimeMillis() - ((Chams)this.module).initTime) / 2000.0f);
            ((Chams)this.module).fireShader.set("resolution", new Vec2f((float)(ListenerModelPre.mc.displayWidth * 2), (float)(ListenerModelPre.mc.displayHeight * 2)));
            ((Chams)this.module).fireShader.set("tex", 0);
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, (float)color.getAlpha());
            ((Chams)this.module).fireShader.set("alpha", color.getAlpha() / 255.0f);
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -2000000.0f);
            this.render(event);
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0f, 2000000.0f);
            GlStateManager.popMatrix();
            ((Chams)this.module).fireShader.unbind();
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
        if (((Chams)this.module).mode.getValue() == ChamsMode.GalaxyShader && !ESP.isRendering && ((Chams)this.module).galaxyShader != null) {
            if (!((Chams)this.module).isValid((Entity)event.getEntity())) {
                return;
            }
            event.setCancelled(true);
            GL11.glPushAttrib(1048575);
            GL11.glPushMatrix();
            final Color color = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
            ((Chams)this.module).galaxyShader.bind();
            ((Chams)this.module).galaxyShader.set("time", (System.currentTimeMillis() - ((Chams)this.module).initTime) / 2000.0f);
            ((Chams)this.module).galaxyShader.set("resolution", new Vec2f((float)(ListenerModelPre.mc.displayWidth * 2), (float)(ListenerModelPre.mc.displayHeight * 2)));
            ((Chams)this.module).galaxyShader.set("tex", 0);
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, (float)color.getAlpha());
            ((Chams)this.module).galaxyShader.set("alpha", color.getAlpha() / 255.0f);
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -2000000.0f);
            this.render(event);
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0f, 2000000.0f);
            GlStateManager.popMatrix();
            ((Chams)this.module).galaxyShader.unbind();
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
        if (((Chams)this.module).mode.getValue() == ChamsMode.WaterShader && !ESP.isRendering && ((Chams)this.module).waterShader != null) {
            if (!((Chams)this.module).isValid((Entity)event.getEntity())) {
                return;
            }
            event.setCancelled(true);
            GL11.glPushAttrib(1048575);
            GL11.glPushMatrix();
            final Color color = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
            ((Chams)this.module).waterShader.bind();
            ((Chams)this.module).waterShader.set("time", (System.currentTimeMillis() - ((Chams)this.module).initTime) / 2000.0f);
            ((Chams)this.module).waterShader.set("resolution", new Vec2f((float)(ListenerModelPre.mc.displayWidth * 2), (float)(ListenerModelPre.mc.displayHeight * 2)));
            ((Chams)this.module).waterShader.set("tex", 0);
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, (float)color.getAlpha());
            ((Chams)this.module).waterShader.set("alpha", color.getAlpha() / 255.0f);
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -2000000.0f);
            this.render(event);
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0f, 2000000.0f);
            GlStateManager.popMatrix();
            ((Chams)this.module).waterShader.unbind();
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
        if (((Chams)this.module).mode.getValue() == ChamsMode.Image && ((Chams)this.module).isValid((Entity)event.getEntity()) && ((Chams)this.module).imageShader != null) {
            final ScaledResolution resolution = new ScaledResolution(ListenerModelPre.mc);
            float[] rect = Render2DUtil.getOnScreen2DHitBox((Entity)event.getEntity(), resolution.getScaledWidth(), resolution.getScaledHeight());
            GL11.glPushMatrix();
            GL11.glPushAttrib(1048575);
            event.setCancelled(true);
            ((Chams)this.module).imageShader.bind();
            final int currentTexture = GL11.glGetInteger(32873);
            ((Chams)this.module).imageShader.set("sampler", 0);
            GL13.glActiveTexture(33990);
            if (((Chams)this.module).gif) {
                if (((Chams)this.module).gifImage != null) {
                    GL11.glBindTexture(3553, ((Chams)this.module).gifImage.getDynamicTexture().getGlTextureId());
                }
            }
            else if (((Chams)this.module).dynamicTexture != null) {
                GL11.glBindTexture(3553, ((Chams)this.module).dynamicTexture.getGlTextureId());
            }
            ((Chams)this.module).imageShader.set("overlaySampler", 6);
            GL13.glActiveTexture(33984);
            ((Chams)this.module).imageShader.set("mixFactor", ((Chams)this.module).mixFactor.getValue());
            ((Chams)this.module).imageShader.set("alpha", ((Chams)this.module).color.getValue().getAlpha() / 255.0f);
            final Vec3d gl_FragCoord = new Vec3d(1920.0, 1080.0, 0.0);
            final Vector4f imageDimensions = new Vector4f(0.0f, 0.0f, 1920.0f, 1080.0f);
            final Vec2d d = new Vec2d((gl_FragCoord.xCoord - imageDimensions.x) / imageDimensions.z, (gl_FragCoord.yCoord - imageDimensions.y) / imageDimensions.w);
            rect = null;
            if (rect != null) {
                rect[0] = MathHelper.clamp(rect[0], 0.0f, (float)ListenerModelPre.mc.displayWidth);
                rect[1] = MathHelper.clamp(rect[1], 0.0f, (float)ListenerModelPre.mc.displayHeight);
                rect[2] = MathHelper.clamp(rect[2], 0.0f, (float)ListenerModelPre.mc.displayWidth);
                rect[3] = MathHelper.clamp(rect[3], 0.0f, (float)ListenerModelPre.mc.displayHeight);
                ((Chams)this.module).imageShader.set("imageX", rect[2]);
                ((Chams)this.module).imageShader.set("imageY", rect[3]);
                ((Chams)this.module).imageShader.set("imageWidth", rect[0] - rect[2]);
                ((Chams)this.module).imageShader.set("imageHeight", rect[1] - rect[3]);
            }
            else {
                ((Chams)this.module).imageShader.set("imageX", 0.0f);
                ((Chams)this.module).imageShader.set("imageY", 0.0f);
                ((Chams)this.module).imageShader.set("imageWidth", (float)ListenerModelPre.mc.displayWidth);
                ((Chams)this.module).imageShader.set("imageHeight", (float)ListenerModelPre.mc.displayHeight);
            }
            final boolean shadows = ListenerModelPre.mc.gameSettings.entityShadows;
            ListenerModelPre.mc.gameSettings.entityShadows = false;
            ((Chams)this.module).renderLayers = false;
            this.render(event);
            ((Chams)this.module).renderLayers = true;
            ((Chams)this.module).imageShader.unbind();
            ListenerModelPre.mc.gameSettings.entityShadows = shadows;
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
    }
    
    private void render(final ModelRenderEvent.Pre event) {
        event.getModel().render((Entity)event.getEntity(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale());
    }
    
    private float getFOVModifier(final float partialTicks, final boolean useFOVSetting) {
        if (((IEntityRenderer)ListenerModelPre.mc.entityRenderer).isDebugView()) {
            return 90.0f;
        }
        final Entity entity = ListenerModelPre.mc.getRenderViewEntity();
        float f = 70.0f;
        if (useFOVSetting) {
            f = ListenerModelPre.mc.gameSettings.fovSetting;
            f *= ((IEntityRenderer)ListenerModelPre.mc.entityRenderer).getFovModifierHandPrev() + (((IEntityRenderer)ListenerModelPre.mc.entityRenderer).getFovModifierHand() - ((IEntityRenderer)ListenerModelPre.mc.entityRenderer).getFovModifierHandPrev()) * partialTicks;
        }
        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0f) {
            final float f2 = ((EntityLivingBase)entity).deathTime + partialTicks;
            f /= (1.0f - 500.0f / (f2 + 500.0f)) * 2.0f + 1.0f;
        }
        final IBlockState iblockstate = ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)ListenerModelPre.mc.world, entity, partialTicks);
        if (iblockstate.getMaterial() == Material.WATER) {
            f = f * 60.0f / 70.0f;
        }
        return f;
    }
}
