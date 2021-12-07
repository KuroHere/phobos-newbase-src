// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EntityRenderer.class })
public interface IEntityRenderer
{
    @Accessor("rendererUpdateCount")
    int getRendererUpdateCount();
    
    @Accessor("rainXCoords")
    float[] getRainXCoords();
    
    @Accessor("rainYCoords")
    float[] getRainYCoords();
    
    @Accessor("farPlaneDistance")
    float getFarPlaneDistance();
    
    @Accessor("fovModifierHandPrev")
    float getFovModifierHandPrev();
    
    @Accessor("fovModifierHand")
    float getFovModifierHand();
    
    @Accessor("debugView")
    boolean isDebugView();
}
