// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.helpers;

import me.earth.earthhack.api.config.*;
import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.register.exception.*;
import me.earth.earthhack.api.util.interfaces.*;
import java.io.*;
import me.earth.earthhack.impl.managers.config.util.*;
import java.util.*;

public class PlaceHolderConfig implements ConfigHelper, Registrable
{
    private final String name;
    
    public PlaceHolderConfig(final String name) {
        this.name = name;
    }
    
    @Override
    public void onUnRegister() throws CantUnregisterException {
        throw new CantUnregisterException(this);
    }
    
    @Override
    public void save() {
        throw new UnsupportedOperationException("This is a PlaceHolder");
    }
    
    @Override
    public void refresh() throws IOException {
        throw new UnsupportedOperationException("This is a PlaceHolder");
    }
    
    @Override
    public void save(final String name) {
        throw new UnsupportedOperationException("This is a PlaceHolder");
    }
    
    @Override
    public void load(final String name) {
        throw new UnsupportedOperationException("This is a PlaceHolder");
    }
    
    @Override
    public void refresh(final String name) throws IOException {
        throw new UnsupportedOperationException("This is a PlaceHolder");
    }
    
    @Override
    public void delete(final String name) throws ConfigDeleteException {
        throw new ConfigDeleteException("This is just a PlaceHolder");
    }
    
    @Override
    public Collection<? extends Nameable> getConfigs() {
        return new ArrayList<Nameable>();
    }
    
    @Override
    public String getName() {
        return this.name;
    }
}
