// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.mixin.transformer.meta;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MixinRenamed {
    String originalName();
    
    boolean isInterfaceMember();
}
