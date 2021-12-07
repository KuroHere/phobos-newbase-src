// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.client;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.module.*;

public class SimpleData extends DefaultData<Module>
{
    private final int color;
    private final String description;
    
    public SimpleData(final Module module, final String description) {
        this(module, description, -1);
    }
    
    public SimpleData(final Module module, final String description, final int color) {
        super(module);
        this.color = color;
        this.description = description;
    }
    
    @Override
    public int getColor() {
        return this.color;
    }
    
    @Override
    public String getDescription() {
        return this.description;
    }
}
