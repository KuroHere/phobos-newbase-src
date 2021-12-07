// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.nointerp.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ ActiveRenderInfo.class })
public abstract class MixinActiveRenderInfo
{
    private static final ModuleCache<NoInterp> NOINTERP;
    
    @Redirect(method = { "projectViewFromEntity" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posX:D", ordinal = 1))
    private static double posXHook(final Entity entity) {
        return NoInterp.noInterpX(MixinActiveRenderInfo.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "projectViewFromEntity" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posY:D", ordinal = 1))
    private static double posYHook(final Entity entity) {
        return NoInterp.noInterpY(MixinActiveRenderInfo.NOINTERP.get(), entity);
    }
    
    @Redirect(method = { "projectViewFromEntity" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posZ:D", ordinal = 1))
    private static double posZHook(final Entity entity) {
        return NoInterp.noInterpZ(MixinActiveRenderInfo.NOINTERP.get(), entity);
    }
    
    static {
        NOINTERP = Caches.getModule(NoInterp.class);
    }
}
