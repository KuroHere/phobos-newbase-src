// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.monster.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.esp.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderGuardian.class })
public abstract class MixinRenderGuardian
{
    @Inject(method = { "doRender" }, at = { @At(value = "INVOKE", target = "net/minecraft/entity/monster/EntityGuardian.getAttackAnimationScale(F)F") }, cancellable = true)
    private void doRenderHook(final EntityGuardian entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }
}
