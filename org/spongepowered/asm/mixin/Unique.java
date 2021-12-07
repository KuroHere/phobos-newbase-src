// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.mixin;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    boolean silent() default false;
}
