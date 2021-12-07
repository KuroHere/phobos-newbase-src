// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.esp.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderDragon.class })
public abstract class MixinRenderDragon
{
    @Inject(method = { "renderCrystalBeams" }, at = { @At("HEAD") }, cancellable = true)
    private static void renderCrystalBeamsHook(final double x, final double y, final double z, final float partialTicks, final double entityX, final double entityY, final double entityZ, final int entityTicks, final double healingX, final double healingY, final double healingZ, final CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }
}
