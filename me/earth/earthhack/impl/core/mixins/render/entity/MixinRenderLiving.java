// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.nointerp.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.esp.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ RenderLiving.class })
public abstract class MixinRenderLiving
{
    private static final ModuleCache<NoInterp> NOINTERP;
    
    @Inject(method = { "renderLeash" }, at = { @At("HEAD") }, cancellable = true)
    private void renderLeashHook(final CallbackInfo info) {
        if (ESP.isRendering) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "renderLeash" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posX:D"))
    private double posXHook(final Entity entity) {
        return NoInterp.noInterpX(MixinRenderLiving.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "renderLeash" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posY:D"))
    private double posYHook(final Entity entity) {
        return NoInterp.noInterpY(MixinRenderLiving.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "renderLeash" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posZ:D"))
    private double posZHook(final Entity entity) {
        return NoInterp.noInterpZ(MixinRenderLiving.NOINTERP.get(), entity);
    }
    
    static {
        NOINTERP = Caches.getModule(NoInterp.class);
    }
}
