// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.util;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.extensibility.*;

public class ReplacePlugin implements IMixinConfigPlugin
{
    @Override
    public void onLoad(final String mixinPackage) {
    }
    
    @Override
    public String getRefMapperConfig() {
        return null;
    }
    
    @Override
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
        return true;
    }
    
    @Override
    public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {
    }
    
    @Override
    public List<String> getMixins() {
        return null;
    }
    
    @Override
    public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {
    }
    
    @Override
    public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {
    }
}
