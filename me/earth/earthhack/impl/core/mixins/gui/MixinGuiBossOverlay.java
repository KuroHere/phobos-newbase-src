// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.norender.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ GuiBossOverlay.class })
public abstract class MixinGuiBossOverlay
{
    private static final ModuleCache<NoRender> NO_RENDER;
    
    @Inject(method = { "renderBossHealth" }, at = { @At("HEAD") }, cancellable = true)
    public void renderHook(final CallbackInfo ci) {
        if (MixinGuiBossOverlay.NO_RENDER.get().boss.getValue()) {
            ci.cancel();
        }
    }
    
    static {
        NO_RENDER = Caches.getModule(NoRender.class);
    }
}
