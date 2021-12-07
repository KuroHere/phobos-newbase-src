// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.plugin;

import com.google.gson.annotations.*;

public final class PluginConfig
{
    @SerializedName("name")
    private String name;
    @SerializedName("mainClass")
    private String mainClass;
    @SerializedName("mixinConfig")
    private String mixinConfig;
    
    public String getName() {
        return this.name;
    }
    
    public String getMainClass() {
        return this.mainClass;
    }
    
    public String getMixinConfig() {
        return this.mixinConfig;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o instanceof PluginConfig && this.name != null && this.name.equals(((PluginConfig)o).name));
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
