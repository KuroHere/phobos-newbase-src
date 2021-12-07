// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.xray.*;
import java.nio.*;
import me.earth.earthhack.impl.modules.render.xray.mode.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ BufferBuilder.class })
public abstract class MixinBufferBuilder
{
    private static final ModuleCache<XRay> XRAY;
    
    @Redirect(method = { "putColorMultiplier" }, at = @At(value = "INVOKE", remap = false, target = "java/nio/IntBuffer.put(II)Ljava/nio/IntBuffer;"))
    private IntBuffer putColorMultiplierHook(final IntBuffer buffer, final int index, int i) {
        if (MixinBufferBuilder.XRAY.isEnabled() && MixinBufferBuilder.XRAY.get().getMode() == XrayMode.Opacity) {
            i = (MixinBufferBuilder.XRAY.get().getOpacity() << 24 | (i & 0xFFFFFF));
        }
        return buffer.put(index, i);
    }
    
    static {
        XRAY = Caches.getModule(XRay.class);
    }
}
