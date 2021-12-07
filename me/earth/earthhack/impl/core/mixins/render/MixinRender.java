// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.esp.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Render.class })
public abstract class MixinRender
{
    @Inject(method = { "doRenderShadowAndFire" }, at = { @At("HEAD") }, cancellable = true)
    private void doRenderShadowAndFireHook(final CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }
    
    @Inject(method = { "renderLivingLabel" }, at = { @At("HEAD") }, cancellable = true)
    private void renderLivingLabelHook(final CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }
}
