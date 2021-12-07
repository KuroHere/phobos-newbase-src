//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.viewmodel.*;
import me.earth.earthhack.impl.modules.render.norender.*;
import me.earth.earthhack.impl.modules.render.handchams.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.injection.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.modules.render.handchams.modes.*;
import java.awt.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ ItemRenderer.class })
public abstract class MixinItemRenderer
{
    private static final ResourceLocation RESOURCE;
    private static final ResourceLocation RES_ITEM_GLINT;
    private static final ResourceLocation ENCHANTED_ITEM_GLINT_RES;
    private static final ModuleCache<ViewModel> VIEW_MODEL;
    private static final ModuleCache<NoRender> NO_RENDER;
    private static final ModuleCache<HandChams> HAND_CHAMS;
    
    @Shadow
    protected abstract void renderArmFirstPerson(final float p0, final float p1, final EnumHandSide p2);
    
    @Inject(method = { "renderFireInFirstPerson" }, at = { @At("HEAD") }, cancellable = true)
    public void renderFireInFirstPersonHook(final CallbackInfo info) {
        if (MixinItemRenderer.NO_RENDER.returnIfPresent(NoRender::noFire, false)) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "renderItemInFirstPerson(F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"))
    private void renderItemInFirstPersonHook(final ItemRenderer itemRenderer, final AbstractClientPlayer player, final float drinkOffset, final float mapAngle, final EnumHand hand, final float x, final ItemStack stack, final float y) {
        final float xOffset = MixinItemRenderer.VIEW_MODEL.isPresent() ? MixinItemRenderer.VIEW_MODEL.get().getX(hand) : 0.0f;
        final float yOffset = MixinItemRenderer.VIEW_MODEL.isPresent() ? MixinItemRenderer.VIEW_MODEL.get().getY(hand) : 0.0f;
        itemRenderer.renderItemInFirstPerson(player, drinkOffset, mapAngle, hand, x + xOffset, stack, y + yOffset);
    }
    
    @Inject(method = { "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", shift = At.Shift.AFTER) })
    private void pushMatrixHook(final CallbackInfo info) {
        if (MixinItemRenderer.VIEW_MODEL.isEnabled()) {
            final float[] scale = MixinItemRenderer.VIEW_MODEL.isPresent() ? MixinItemRenderer.VIEW_MODEL.get().getScale() : ViewModel.DEFAULT_SCALE;
            final float[] translation = MixinItemRenderer.VIEW_MODEL.isPresent() ? MixinItemRenderer.VIEW_MODEL.get().getTranslation() : ViewModel.DEFAULT_TRANSLATION;
            GL11.glScalef(scale[0], scale[1], scale[2]);
            GL11.glRotatef(translation[0], translation[1], translation[2], translation[3]);
        }
    }
    
    @Redirect(method = { "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderArmFirstPerson(FFLnet/minecraft/util/EnumHandSide;)V"))
    public void mrHook(final ItemRenderer itemRenderer, final float p_187456_1_, final float p_187456_2_, final EnumHandSide p_187456_3_) {
        if (MixinItemRenderer.HAND_CHAMS.isEnabled()) {
            if (MixinItemRenderer.HAND_CHAMS.get().mode.getValue() == ChamsMode.Normal) {
                if (MixinItemRenderer.HAND_CHAMS.get().chams.getValue()) {
                    final Color handColor = MixinItemRenderer.HAND_CHAMS.get().color.getValue();
                    GL11.glPushMatrix();
                    GL11.glPushAttrib(1048575);
                    GL11.glDisable(3553);
                    GL11.glDisable(2896);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glColor4f(handColor.getRed() / 255.0f, handColor.getGreen() / 255.0f, handColor.getBlue() / 255.0f, handColor.getAlpha() / 255.0f);
                    this.renderArmFirstPerson(p_187456_1_, p_187456_2_, p_187456_3_);
                    GL11.glDisable(3042);
                    GL11.glEnable(3553);
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();
                }
                if (MixinItemRenderer.HAND_CHAMS.get().wireframe.getValue()) {
                    final Color handColor = MixinItemRenderer.HAND_CHAMS.get().wireFrameColor.getValue();
                    GL11.glPushMatrix();
                    GL11.glPushAttrib(1048575);
                    GL11.glDisable(3553);
                    GL11.glDisable(2896);
                    GL11.glEnable(3042);
                    GL11.glPolygonMode(1032, 6913);
                    GL11.glBlendFunc(770, 771);
                    GL11.glLineWidth(1.5f);
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glColor4f(handColor.getRed() / 255.0f, handColor.getGreen() / 255.0f, handColor.getBlue() / 255.0f, handColor.getAlpha() / 255.0f);
                    this.renderArmFirstPerson(p_187456_1_, p_187456_2_, p_187456_3_);
                    GL11.glDisable(3042);
                    GL11.glEnable(3553);
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();
                }
            }
            else {
                GL11.glPushAttrib(1048575);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glDisable(3008);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
                GL11.glDisable(3553);
                GL11.glEnable(10754);
                GL11.glEnable(2960);
                this.renderArmFirstPerson(p_187456_1_, p_187456_2_, p_187456_3_);
                GL11.glEnable(3553);
                this.renderEffect(p_187456_1_, p_187456_2_, p_187456_3_);
                GL11.glPopAttrib();
            }
        }
        else {
            this.renderArmFirstPerson(p_187456_1_, p_187456_2_, p_187456_3_);
        }
    }
    
    private void renderEffect(final float p_187456_1_, final float p_187456_2_, final EnumHandSide p_187456_3_) {
        final float f = Minecraft.getMinecraft().player.ticksExisted + Minecraft.getMinecraft().getRenderPartialTicks();
        Minecraft.getMinecraft().getTextureManager().bindTexture(MixinItemRenderer.RESOURCE);
        Minecraft.getMinecraft().entityRenderer.func_191514_d(true);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        final float f2 = 0.5f;
        GlStateManager.color(0.5f, 0.5f, 0.5f, 0.5f);
        for (int i = 0; i < 2; ++i) {
            GlStateManager.disableLighting();
            final float f3 = 0.76f;
            GlStateManager.color(0.38f, 0.19f, 0.608f, 0.5f);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            final float f4 = 0.33333334f;
            GlStateManager.scale(0.33333334f, 0.33333334f, 0.33333334f);
            GlStateManager.rotate(30.0f - i * 60.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(0.0f, f * (0.001f + i * 0.003f) * 20.0f, 0.0f);
            GlStateManager.matrixMode(5888);
            final RenderPlayer renderPlayer = (RenderPlayer)Minecraft.getMinecraft().getRenderManager().getEntityRenderObject((Entity)Minecraft.getMinecraft().player);
            if (renderPlayer != null) {
                if (p_187456_3_ == EnumHandSide.RIGHT) {
                    renderPlayer.renderRightArm((AbstractClientPlayer)Minecraft.getMinecraft().player);
                }
                else {
                    renderPlayer.renderLeftArm((AbstractClientPlayer)Minecraft.getMinecraft().player);
                }
            }
        }
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
        Minecraft.getMinecraft().entityRenderer.func_191514_d(false);
    }
    
    static {
        RESOURCE = new ResourceLocation("textures/rainbow.png");
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        VIEW_MODEL = Caches.getModule(ViewModel.class);
        NO_RENDER = Caches.getModule(NoRender.class);
        HAND_CHAMS = Caches.getModule(HandChams.class);
    }
}
