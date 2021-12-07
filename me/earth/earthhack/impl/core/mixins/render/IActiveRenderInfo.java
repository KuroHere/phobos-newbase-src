// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import java.nio.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ ActiveRenderInfo.class })
public interface IActiveRenderInfo
{
    @Accessor("MODELVIEW")
    default FloatBuffer getViewport() {
        throw new IllegalStateException();
    }
    
    @Accessor("PROJECTION")
    default FloatBuffer getProjection() {
        throw new IllegalStateException();
    }
    
    @Accessor("MODELVIEW")
    default FloatBuffer getModelview() {
        throw new IllegalStateException();
    }
}
