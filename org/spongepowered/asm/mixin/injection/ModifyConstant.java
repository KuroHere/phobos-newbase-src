// 
// Decompiled by Procyon v0.6-prerelease
// 

package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyConstant {
    String[] method();
    
    Slice[] slice() default {};
    
    Constant[] constant() default {};
    
    boolean remap() default true;
    
    int require() default -1;
    
    int expect() default 1;
    
    int allow() default -1;
    
    String constraints() default "";
}
