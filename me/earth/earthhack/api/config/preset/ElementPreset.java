// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.config.preset;

import me.earth.earthhack.api.config.*;
import me.earth.earthhack.api.hud.*;

public abstract class ElementPreset implements Config
{
    private final HudElement element;
    private final String name;
    
    public ElementPreset(final String name, final HudElement element) {
        this.name = name;
        this.element = element;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public HudElement getElement() {
        return this.element;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof ElementPreset) {
            final ElementPreset other = (ElementPreset)o;
            return other.name.equals(this.name) && other.element.equals(this.element);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
