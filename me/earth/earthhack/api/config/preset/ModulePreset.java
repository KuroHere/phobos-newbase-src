// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.config.preset;

import me.earth.earthhack.api.config.*;
import me.earth.earthhack.api.module.*;

public abstract class ModulePreset implements Config
{
    private final Module module;
    private final String name;
    private final String description;
    
    public ModulePreset(final String name, final Module module, final String description) {
        this.name = name;
        this.module = module;
        this.description = description;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof ModulePreset) {
            final ModulePreset other = (ModulePreset)o;
            return other.name.equals(this.name) && other.module.equals(this.module);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
