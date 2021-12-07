// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GameSettings.Options.class })
public abstract class MixinOptions
{
    @ModifyConstant(method = { "<clinit>" }, constant = { @Constant(floatValue = 110.0f) })
    private static float fov(final float initial) {
        return 180.0f;
    }
    
    @ModifyConstant(method = { "<clinit>" }, constant = { @Constant(floatValue = 2.0f) })
    private static float renderDistance(final float initial) {
        return 1.0f;
    }
}
