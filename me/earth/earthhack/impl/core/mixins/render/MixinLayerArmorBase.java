//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.layers.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.chams.*;
import me.earth.earthhack.impl.modules.render.norender.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ LayerArmorBase.class })
public abstract class MixinLayerArmorBase
{
    private static final ModuleCache<Chams> CHAMS;
    private static final ModuleCache<NoRender> NO_RENDER;
    
    @Redirect(method = { "renderArmorLayer" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderArmorHook(final ModelBase modelBase, final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        if (entityIn instanceof EntityLivingBase) {
            final Color color = MixinLayerArmorBase.CHAMS.get().getArmorVisibleColor(entityIn);
            if (MixinLayerArmorBase.CHAMS.get().shouldArmorChams() && MixinLayerArmorBase.CHAMS.isEnabled()) {
                GL11.glPushMatrix();
                GL11.glEnable(32823);
                GL11.glPolygonOffset(1.0f, -2000000.0f);
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
            }
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            if (MixinLayerArmorBase.CHAMS.get().shouldArmorChams() && MixinLayerArmorBase.CHAMS.isEnabled()) {
                GL11.glPolygonOffset(1.0f, 2000000.0f);
                GL11.glDisable(32823);
                GL11.glPopMatrix();
            }
        }
    }
    
    @Inject(method = { "renderArmorLayer" }, at = { @At("HEAD") }, cancellable = true)
    public void renderArmorLayer(final EntityLivingBase entityLivingBaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale, final EntityEquipmentSlot slotIn, final CallbackInfo ci) {
        if (MixinLayerArmorBase.NO_RENDER.returnIfPresent(m -> !m.isValidArmorPiece(slotIn), Boolean.valueOf(false))) {
            ci.cancel();
        }
    }
    
    static {
        CHAMS = Caches.getModule(Chams.class);
        NO_RENDER = Caches.getModule(NoRender.class);
    }
}
