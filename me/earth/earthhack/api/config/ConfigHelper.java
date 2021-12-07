// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.config;

import me.earth.earthhack.api.util.interfaces.*;
import java.io.*;
import me.earth.earthhack.impl.managers.config.util.*;
import java.util.*;

public interface ConfigHelper<C extends Config> extends Nameable
{
    void save() throws IOException;
    
    void refresh() throws IOException;
    
    void save(final String p0) throws IOException;
    
    void load(final String p0);
    
    void refresh(final String p0) throws IOException;
    
    void delete(final String p0) throws IOException, ConfigDeleteException;
    
    Collection<C> getConfigs();
}
