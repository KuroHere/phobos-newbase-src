// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.ducks.render;

import java.util.*;
import net.minecraft.client.shader.*;

public interface IShaderGroup
{
    List<Framebuffer> getListFramebuffers();
    
    List<Shader> getListShaders();
}
