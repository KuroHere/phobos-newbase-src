// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.register.exception;

import me.earth.earthhack.api.util.interfaces.*;

public class AlreadyRegisteredException extends Exception
{
    private final Nameable trying;
    private final Nameable registered;
    
    public AlreadyRegisteredException(final Nameable trying, final Nameable registered) {
        this(trying.getName() + " can't be registered, a Nameable with that name is already registered.", trying, registered);
    }
    
    public AlreadyRegisteredException(final String message, final Nameable trying, final Nameable registered) {
        super(message);
        this.trying = trying;
        this.registered = registered;
    }
    
    public Nameable getTrying() {
        return this.trying;
    }
    
    public Nameable getRegistered() {
        return this.registered;
    }
}
