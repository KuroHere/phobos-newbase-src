//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.spectate.*;
import me.earth.earthhack.impl.modules.render.chams.*;
import me.earth.earthhack.impl.modules.render.esp.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.impl.managers.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.render.chams.mode.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.model.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRenderLivingBase
{
    private static final ModuleCache<Spectate> SPECTATE;
    private static final ModuleCache<Chams> CHAMS;
    private static final ModuleCache<ESP> ESP_MODULE;
    private float prevRenderYawOffset;
    private float renderYawOffset;
    private float prevRotationYawHead;
    private float rotationYawHead;
    private float prevRotationPitch;
    private float rotationPitch;
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") })
    private void doRenderHookHead(final EntityLivingBase entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo info) {
        if (entity instanceof EntityPlayerSP || (MixinRenderLivingBase.SPECTATE.isEnabled() && entity.equals((Object)MixinRenderLivingBase.SPECTATE.get().getFake()))) {
            this.prevRenderYawOffset = entity.prevRenderYawOffset;
            this.renderYawOffset = entity.renderYawOffset;
            this.prevRotationYawHead = entity.prevRotationYawHead;
            this.rotationYawHead = entity.rotationYawHead;
            this.prevRotationPitch = entity.prevRotationPitch;
            this.rotationPitch = entity.rotationPitch;
            entity.prevRenderYawOffset = Managers.ROTATION.getPrevRenderYawOffset();
            entity.renderYawOffset = Managers.ROTATION.getRenderYawOffset();
            entity.prevRotationYawHead = Managers.ROTATION.getPrevRotationYawHead();
            entity.rotationYawHead = Managers.ROTATION.getRotationYawHead();
            entity.prevRotationPitch = Managers.ROTATION.getPrevPitch();
            entity.rotationPitch = Managers.ROTATION.getRenderPitch();
        }
    }
    
    @Inject(method = { "doRender" }, at = { @At("RETURN") })
    private void doRenderHookReturn(final EntityLivingBase entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo info) {
        if (entity instanceof EntityPlayerSP || (MixinRenderLivingBase.SPECTATE.isEnabled() && entity.equals((Object)MixinRenderLivingBase.SPECTATE.get().getFake()))) {
            entity.prevRenderYawOffset = this.prevRenderYawOffset;
            entity.renderYawOffset = this.renderYawOffset;
            entity.prevRotationYawHead = this.prevRotationYawHead;
            entity.rotationYawHead = this.rotationYawHead;
            entity.prevRotationPitch = this.prevRotationPitch;
            entity.rotationPitch = this.rotationPitch;
        }
    }
    
    @Inject(method = { "renderLayers" }, at = { @At("HEAD") }, cancellable = true)
    private void renderLayersHook(final CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderName" }, at = { @At("HEAD") }, cancellable = true)
    private void renderNameHook(final EntityLivingBase entity, final double x, final double y, final double z, final CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "setBrightness" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;hurtTime:I"))
    public int hurtTimeHook(final EntityLivingBase base) {
        if (!MixinRenderLivingBase.ESP_MODULE.returnIfPresent(ESP::shouldHurt, false)) {
            return 0;
        }
        return base.hurtTime;
    }
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") })
    public void doRender_Pre(final EntityLivingBase entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo info) {
        if (MixinRenderLivingBase.CHAMS.returnIfPresent(c -> c.isValid((Entity)entity, ChamsMode.Normal), Boolean.valueOf(false))) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1100000.0f);
        }
    }
    
    @Inject(method = { "doRender" }, at = { @At("RETURN") })
    public void doRender_Post(final EntityLivingBase entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo info) {
        if (MixinRenderLivingBase.CHAMS.returnIfPresent(c -> c.isValid((Entity)entity, ChamsMode.Normal), Boolean.valueOf(false))) {
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0f, 1100000.0f);
        }
    }
    
    @Redirect(method = { "renderModel" }, at = @At(value = "INVOKE", target = "net/minecraft/client/model/ModelBase.render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void renderHook(final ModelBase model, final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        final RenderLivingBase<?> renderLiving = (RenderLivingBase<?>)RenderLivingBase.class.cast(this);
        final EntityLivingBase entity = (EntityLivingBase)entityIn;
        final ModelRenderEvent event = new ModelRenderEvent.Pre(renderLiving, entity, model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Bus.EVENT_BUS.post(event);
        if (!event.isCancelled()) {
            model.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
        Bus.EVENT_BUS.post(new ModelRenderEvent.Post(renderLiving, entity, model, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale));
    }
    
    static {
        SPECTATE = Caches.getModule(Spectate.class);
        CHAMS = Caches.getModule(Chams.class);
        ESP_MODULE = Caches.getModule(ESP.class);
    }
}
