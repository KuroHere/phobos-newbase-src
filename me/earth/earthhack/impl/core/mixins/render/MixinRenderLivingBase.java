//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import java.util.*;
import net.minecraft.client.renderer.entity.layers.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.renderer.entity.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderLivingBase.class })
public abstract class MixinRenderLivingBase
{
    @Shadow
    protected List<LayerRenderer<?>> layerRenderers;
    
    @Inject(method = { "renderLayers" }, at = { @At("HEAD") }, cancellable = true)
    public void renderLayersPreHook(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleIn, final CallbackInfo ci) {
        final RenderLayersEvent pre = new RenderLayersEvent((Render<EntityLivingBase>)Render.class.cast(this), entitylivingbaseIn, this.layerRenderers, Stage.PRE);
        Bus.EVENT_BUS.post(pre);
        if (pre.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderLayers" }, at = { @At("RETURN") })
    public void renderLayersPostHook(final EntityLivingBase entitylivingbaseIn, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scaleIn, final CallbackInfo ci) {
        final RenderLayersEvent post = new RenderLayersEvent((Render<EntityLivingBase>)Render.class.cast(this), entitylivingbaseIn, this.layerRenderers, Stage.POST);
        Bus.EVENT_BUS.post(post);
    }
}
