// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.register;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.register.exception.*;
import java.util.*;

public interface Register<T extends Nameable>
{
    void register(final T p0) throws AlreadyRegisteredException;
    
    void unregister(final T p0) throws CantUnregisterException;
    
    T getObject(final String p0);
    
     <C extends T> C getByClass(final Class<C> p0);
    
    Collection<T> getRegistered();
}
