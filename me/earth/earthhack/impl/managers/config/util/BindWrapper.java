// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.util;

import com.google.gson.annotations.*;

public class BindWrapper
{
    @SerializedName("name")
    private String name;
    @SerializedName("module")
    private String module;
    @SerializedName("value")
    private String value;
    
    public BindWrapper(final String name, final String module, final String value) {
        this.name = name;
        this.module = module;
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getModule() {
        return this.module;
    }
    
    public String getValue() {
        return this.value;
    }
}
