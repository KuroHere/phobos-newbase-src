// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.hud.modes;

public enum HudRainbow
{
    None(""), 
    Horizontal("§+"), 
    Vertical("§-"), 
    Static("");
    
    private final String color;
    
    private HudRainbow(final String color) {
        this.color = color;
    }
    
    public String getColor() {
        return this.color;
    }
}
