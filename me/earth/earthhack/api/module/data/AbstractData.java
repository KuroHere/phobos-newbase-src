// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.module.data;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.config.preset.*;
import java.util.*;

public abstract class AbstractData<M extends Module> implements ModuleData
{
    protected final Map<Setting<?>, String> descriptions;
    protected final Set<ModulePreset> presets;
    protected final M module;
    
    public AbstractData(final M module) {
        this.descriptions = new HashMap<Setting<?>, String>();
        this.presets = new LinkedHashSet<ModulePreset>();
        this.module = module;
    }
    
    @Override
    public Map<Setting<?>, String> settingDescriptions() {
        return this.descriptions;
    }
    
    @Override
    public Collection<ModulePreset> getPresets() {
        return this.presets;
    }
    
    public void register(final String setting, final String description) {
        this.register(this.module.getSetting(setting), description);
    }
    
    public void register(final Setting<?> setting, final String description) {
        if (setting != null) {
            this.descriptions.put(setting, description);
        }
    }
}
