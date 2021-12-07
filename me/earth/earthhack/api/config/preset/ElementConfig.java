// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.config.preset;

import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.config.*;
import java.util.*;
import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.hud.*;
import me.earth.earthhack.api.module.*;

public class ElementConfig extends IdentifiedNameable implements Config
{
    private List<ValuePreset> presets;
    
    public ElementConfig(final String name) {
        super(name);
        this.presets = new ArrayList<ValuePreset>();
    }
    
    public void setPresets(final List<ValuePreset> presets) {
        if (presets != null) {
            this.presets = presets;
        }
    }
    
    public List<ValuePreset> getPresets() {
        return this.presets;
    }
    
    @Override
    public void apply() {
        for (final ModulePreset preset : this.presets) {
            preset.apply();
        }
    }
    
    public static ElementConfig create(final String name, final Register<HudElement> elements) {
        final ElementConfig config = new ElementConfig(name);
        for (final HudElement element : elements.getRegistered()) {
            final ValuePreset preset = ValuePreset.snapshot(name, element);
            config.presets.add(preset);
        }
        return config;
    }
}
