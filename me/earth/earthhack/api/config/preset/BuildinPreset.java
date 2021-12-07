// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.config.preset;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.*;
import java.util.*;

public class BuildinPreset extends ModulePreset
{
    private final Map<Setting, Object> values;
    
    public BuildinPreset(final String name, final Module module, final String description) {
        super(name, module, description);
        this.values = new HashMap<Setting, Object>();
    }
    
    public <T> void add(final Setting<T> setting, final T value) {
        this.values.put(setting, value);
    }
    
    public <T> void add(final String setting, final T value) {
        final Setting<T> s = (Setting<T>)this.getModule().getSetting(setting);
        this.add(s, value);
    }
    
    @Override
    public void apply() {
        for (final Map.Entry<Setting, Object> entry : this.values.entrySet()) {
            entry.getKey().setValue(entry.getValue());
        }
    }
}
