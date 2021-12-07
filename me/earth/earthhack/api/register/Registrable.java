// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.register;

import me.earth.earthhack.api.register.exception.*;

public interface Registrable
{
    default void onRegister() {
    }
    
    default void onUnRegister() throws CantUnregisterException {
    }
}
