// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.module;

import me.earth.earthhack.api.setting.*;

public class SettingHeader
{
    private final String name;
    private final Setting<?> firstChild;
    private String description;
    
    public SettingHeader(final String nameIn, final Setting<?> firstChild) {
        this.name = nameIn;
        this.firstChild = firstChild;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Setting<?> getSetting() {
        return this.firstChild;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public SettingHeader setDescription(final String description) {
        this.description = description;
        return this;
    }
}
