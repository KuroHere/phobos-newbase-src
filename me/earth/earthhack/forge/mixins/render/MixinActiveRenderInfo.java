// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.forge.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.util.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ActiveRenderInfo.class })
public abstract class MixinActiveRenderInfo
{
    @Inject(method = { "updateRenderInfo(Lnet/minecraft/entity/Entity;Z)V" }, at = { @At("HEAD") }, remap = false)
    private static void updateRenderInfo(final Entity entityplayerIn, final boolean p_74583_1_, final CallbackInfo ci) {
        RenderUtil.updateMatrices();
    }
}
