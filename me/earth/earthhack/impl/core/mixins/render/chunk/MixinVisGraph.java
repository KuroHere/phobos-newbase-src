// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.chunk;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.chunk.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.xray.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.render.xray.mode.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ VisGraph.class })
public abstract class MixinVisGraph
{
    private static final ModuleCache<XRay> XRAY;
    
    @Inject(method = { "setOpaqueCube" }, at = { @At("HEAD") }, cancellable = true)
    public void setOpaqueCubeHook(final BlockPos pos, final CallbackInfo info) {
        if (MixinVisGraph.XRAY.isEnabled() && MixinVisGraph.XRAY.get().getMode() == XrayMode.Simple) {
            info.cancel();
        }
    }
    
    static {
        XRAY = Caches.getModule(XRay.class);
    }
}
