// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.chunk;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.chunk.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.debug.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ RenderChunk.class })
public abstract class MixinRenderChunk
{
    private static final SettingCache<Boolean, BooleanSetting, Debug> SLOW;
    
    @Inject(method = { "needsImmediateUpdate" }, at = { @At("HEAD") }, cancellable = true)
    private void needsImmediateUpdateHook(final CallbackInfoReturnable<Boolean> cir) {
        if (MixinRenderChunk.SLOW.getValue()) {
            cir.setReturnValue(false);
        }
    }
    
    static {
        SLOW = Caches.getSetting(Debug.class, BooleanSetting.class, "SlowUpdates", false);
    }
}
