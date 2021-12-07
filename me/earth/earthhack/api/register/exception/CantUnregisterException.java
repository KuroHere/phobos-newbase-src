// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.register.exception;

import me.earth.earthhack.api.util.interfaces.*;

public class CantUnregisterException extends Exception
{
    private final Nameable nameable;
    
    public CantUnregisterException(final Nameable nameable) {
        this("Can't unregister " + ((nameable == null) ? "null" : nameable.getName()) + ".", nameable);
    }
    
    public CantUnregisterException(final String message, final Nameable nameable) {
        super(message);
        this.nameable = nameable;
    }
    
    public Nameable getObject() {
        return this.nameable;
    }
}
