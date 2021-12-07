// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.mixin;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
public @interface Intrinsic {
    boolean displace() default false;
}
