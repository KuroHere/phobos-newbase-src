// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.mixin.gen;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Invoker {
    String value() default "";
    
    boolean remap() default true;
}
