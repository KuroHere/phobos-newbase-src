// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.toasts.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.norender.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ GuiToast.class })
public abstract class MixinGuiToast
{
    private static final ModuleCache<NoRender> NO_RENDER;
    
    @Inject(method = { "drawToast" }, at = { @At("HEAD") }, cancellable = true)
    public void drawToastHook(final ScaledResolution resolution, final CallbackInfo info) {
        if (MixinGuiToast.NO_RENDER.returnIfPresent(NoRender::noAdvancements, false)) {
            info.cancel();
        }
    }
    
    static {
        NO_RENDER = Caches.getModule(NoRender.class);
    }
}
