// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.esp.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderMinecart.class })
public abstract class MixinRenderMinecart
{
    @Inject(method = { "renderCartContents" }, at = { @At("HEAD") }, cancellable = true)
    private void renderCartContentsHook(final CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }
}
