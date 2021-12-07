// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.config.preset;

import me.earth.earthhack.api.module.*;
import com.google.gson.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.config.*;

public class ValuePreset extends ModulePreset
{
    private final Map<String, JsonElement> values;
    
    public ValuePreset(final String name, final Module module, final String description) {
        super(name, module, description);
        this.values = new HashMap<String, JsonElement>();
    }
    
    public Map<String, JsonElement> getValues() {
        return this.values;
    }
    
    public JsonObject toJson() {
        final JsonObject object = new JsonObject();
        for (final Map.Entry<String, JsonElement> entry : this.values.entrySet()) {
            object.add((String)entry.getKey(), (JsonElement)entry.getValue());
        }
        return object;
    }
    
    @Override
    public void apply() {
        final Module module = this.getModule();
        final Set<Setting<?>> generated = GeneratedSettings.getGenerated(module);
        for (final Setting<?> setting : generated) {
            module.unregister(setting);
        }
        GeneratedSettings.clear(module);
        Map.Entry<String, JsonElement> enabled = null;
        for (final Map.Entry<String, JsonElement> entry : this.values.entrySet()) {
            if (entry.getKey().equalsIgnoreCase("Enabled")) {
                enabled = entry;
            }
            else {
                this.setSetting(module, entry);
            }
        }
        if (enabled != null) {
            this.setSetting(module, enabled);
        }
    }
    
    public static ValuePreset snapshot(final String name, final Module module) {
        final ValuePreset preset = new ValuePreset(name, module, "A config Preset.");
        for (final Setting<?> setting : module.getSettings()) {
            if (setting instanceof BindSetting) {
                continue;
            }
            final JsonElement element = Jsonable.parse(setting.toJson());
            preset.getValues().put(setting.getName(), element);
        }
        return preset;
    }
    
    protected void setSetting(final Module module, final Map.Entry<String, JsonElement> entry) {
        final Setting<?> setting = module.getSettingConfig(entry.getKey());
        if (setting != null) {
            try {
                setting.fromJson(entry.getValue());
            }
            catch (Exception e) {
                System.out.println(module.getName() + " : " + setting.getName() + " : Couldn't set value from json:");
                e.printStackTrace();
            }
        }
    }
}
