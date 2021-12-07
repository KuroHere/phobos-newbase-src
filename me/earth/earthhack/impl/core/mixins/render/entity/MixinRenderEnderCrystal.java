//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.crystalscale.*;
import me.earth.earthhack.impl.modules.render.crystalchams.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.animation.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.impl.modules.render.handchams.modes.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ RenderEnderCrystal.class })
public abstract class MixinRenderEnderCrystal
{
    private static final ModuleCache<CrystalScale> SCALE;
    private static final ModuleCache<CrystalChams> CHAMS;
    
    @Redirect(method = { "doRender" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderHook(final ModelBase modelBase, final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (!MixinRenderEnderCrystal.SCALE.isPresent()) {
            return;
        }
        final float crystalScale = MixinRenderEnderCrystal.SCALE.get().animate.getValue() ? ((float)(MixinRenderEnderCrystal.SCALE.get().scaleMap.containsKey(entityIn.getEntityId()) ? MixinRenderEnderCrystal.SCALE.get().scaleMap.get(entityIn.getEntityId()).getCurrent() : 0.10000000149011612)) : MixinRenderEnderCrystal.SCALE.get().scale.getValue();
        final TimeAnimation animation = MixinRenderEnderCrystal.SCALE.get().scaleMap.get(entityIn.getEntityId());
        if (animation != null) {
            animation.add(Minecraft.getMinecraft().getRenderPartialTicks());
        }
        if (MixinRenderEnderCrystal.SCALE.isEnabled()) {
            GlStateManager.scale(crystalScale, crystalScale, crystalScale);
        }
        if (MixinRenderEnderCrystal.CHAMS.isEnabled()) {
            if (MixinRenderEnderCrystal.CHAMS.get().mode.getValue() == ChamsMode.Gradient) {
                GL11.glPushAttrib(1048575);
                GL11.glEnable(3042);
                GL11.glDisable(2896);
                GL11.glDisable(3553);
                final float alpha = MixinRenderEnderCrystal.CHAMS.get().color.getValue().getAlpha() / 255.0f;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, alpha);
                modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GL11.glEnable(3553);
                GL11.glBlendFunc(770, 771);
                final float f = entityIn.ticksExisted + Minecraft.getMinecraft().getRenderPartialTicks();
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/rainbow.png"));
                Minecraft.getMinecraft().entityRenderer.func_191514_d(true);
                GlStateManager.enableBlend();
                GlStateManager.depthFunc(514);
                GlStateManager.depthMask(false);
                GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
                for (int i = 0; i < 2; ++i) {
                    GlStateManager.disableLighting();
                    GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
                    GlStateManager.matrixMode(5890);
                    GlStateManager.loadIdentity();
                    GlStateManager.scale(0.33333334f, 0.33333334f, 0.33333334f);
                    GlStateManager.rotate(30.0f - i * 60.0f, 0.0f, 0.0f, 0.5f);
                    GlStateManager.translate(0.0f, f * (0.001f + i * 0.003f) * 20.0f, 0.0f);
                    GlStateManager.matrixMode(5888);
                    modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                }
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.enableLighting();
                GlStateManager.depthMask(true);
                GlStateManager.depthFunc(515);
                GlStateManager.disableBlend();
                Minecraft.getMinecraft().entityRenderer.func_191514_d(false);
                GL11.glPopAttrib();
            }
            else {
                if (MixinRenderEnderCrystal.CHAMS.get().wireframe.getValue()) {
                    final Color wireColor = MixinRenderEnderCrystal.CHAMS.get().wireFrameColor.getValue();
                    GL11.glPushAttrib(1048575);
                    GL11.glEnable(3042);
                    GL11.glDisable(3553);
                    GL11.glDisable(2896);
                    GL11.glBlendFunc(770, 771);
                    GL11.glPolygonMode(1032, 6913);
                    if (MixinRenderEnderCrystal.CHAMS.get().wireWalls.getValue()) {
                        GL11.glDepthMask(false);
                        GL11.glDisable(2929);
                    }
                    GL11.glColor4f(wireColor.getRed() / 255.0f, wireColor.getGreen() / 255.0f, wireColor.getBlue() / 255.0f, wireColor.getAlpha() / 255.0f);
                    modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    GL11.glPopAttrib();
                }
                if (MixinRenderEnderCrystal.CHAMS.get().chams.getValue()) {
                    final Color chamsColor = MixinRenderEnderCrystal.CHAMS.get().color.getValue();
                    GL11.glPushAttrib(1048575);
                    GL11.glEnable(3042);
                    GL11.glDisable(3553);
                    GL11.glDisable(2896);
                    GL11.glDisable(3008);
                    GL11.glBlendFunc(770, 771);
                    GL11.glEnable(2960);
                    GL11.glEnable(10754);
                    if (MixinRenderEnderCrystal.CHAMS.get().throughWalls.getValue()) {
                        GL11.glDepthMask(false);
                        GL11.glDisable(2929);
                    }
                    GL11.glColor4f(chamsColor.getRed() / 255.0f, chamsColor.getGreen() / 255.0f, chamsColor.getBlue() / 255.0f, chamsColor.getAlpha() / 255.0f);
                    modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    GL11.glPopAttrib();
                }
            }
        }
        else {
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
        if (MixinRenderEnderCrystal.SCALE.isEnabled()) {
            GlStateManager.scale(1.0f / crystalScale, 1.0f / crystalScale, 1.0f / crystalScale);
        }
    }
    
    static {
        SCALE = Caches.getModule(CrystalScale.class);
        CHAMS = Caches.getModule(CrystalChams.class);
    }
}
