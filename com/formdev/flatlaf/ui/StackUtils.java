// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.formdev.flatlaf.ui;

import java.util.function.*;

class StackUtils
{
    private static final StackUtils INSTANCE;
    
    public static boolean wasInvokedFrom(final String className, final String methodName, final int limit) {
        return wasInvokedFrom((c, m) -> c.equals(className) && m.equals(methodName), limit);
    }
    
    public static boolean wasInvokedFrom(final BiPredicate<String, String> predicate, final int limit) {
        return StackUtils.INSTANCE.wasInvokedFromImpl(predicate, limit);
    }
    
    boolean wasInvokedFromImpl(final BiPredicate<String, String> predicate, final int limit) {
        throw new UnsupportedOperationException();
    }
    
    static {
        INSTANCE = new StackUtilsImpl();
    }
}
