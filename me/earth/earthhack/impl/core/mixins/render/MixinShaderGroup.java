// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.render;

import me.earth.earthhack.impl.core.ducks.render.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.client.shader.*;

@Mixin({ ShaderGroup.class })
public abstract class MixinShaderGroup implements IShaderGroup
{
    @Accessor("listFramebuffers")
    @Override
    public abstract List<Framebuffer> getListFramebuffers();
    
    @Accessor("listShaders")
    @Override
    public abstract List<Shader> getListShaders();
}
