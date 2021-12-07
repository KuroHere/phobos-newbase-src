// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.xray.*;
import me.earth.earthhack.impl.modules.render.xray.mode.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ RenderGlobal.class })
public abstract class MixinRenderGlobal
{
    private static final ModuleCache<XRay> XRAY;
    
    @ModifyVariable(method = { "setupTerrain" }, at = @At("HEAD"))
    private boolean setupTerrainHook(final boolean playerSpectator) {
        return (MixinRenderGlobal.XRAY.isEnabled() && MixinRenderGlobal.XRAY.get().getMode() == XrayMode.Opacity) || playerSpectator;
    }
    
    static {
        XRAY = Caches.getModule(XRay.class);
    }
}
