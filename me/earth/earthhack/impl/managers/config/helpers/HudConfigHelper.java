// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.helpers;

import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.hud.*;
import me.earth.earthhack.api.config.preset.*;
import com.google.gson.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.api.module.*;
import java.util.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.config.*;
import java.io.*;

public class HudConfigHelper extends AbstractConfigHelper<ElementConfig>
{
    private final Register<HudElement> elements;
    
    public HudConfigHelper(final Register<HudElement> elements) {
        super("element", "hud");
        this.elements = elements;
    }
    
    @Override
    protected ElementConfig create(final String name) {
        return ElementConfig.create(name.toLowerCase(), this.elements);
    }
    
    @Override
    protected JsonObject toJson(final ElementConfig config) {
        final JsonObject object = new JsonObject();
        for (final ValuePreset preset : config.getPresets()) {
            final JsonObject presetObject = preset.toJson();
            object.add(preset.getModule().getName(), (JsonElement)presetObject);
        }
        return object;
    }
    
    @Override
    protected ElementConfig readFile(final InputStream stream, final String name) {
        final JsonObject object = Jsonable.PARSER.parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        final List<ValuePreset> presets = new ArrayList<ValuePreset>(object.entrySet().size());
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            final HudElement module = this.elements.getObject(entry.getKey());
            if (module == null) {
                Earthhack.getLogger().error("Config: Couldn't find element: " + entry.getKey());
            }
            else {
                final ValuePreset preset = new ValuePreset(name, module, "A config Preset.");
                final JsonObject element = entry.getValue().getAsJsonObject();
                for (final Map.Entry<String, JsonElement> s : element.entrySet()) {
                    final boolean generated = module.getSetting(s.getKey()) == null;
                    final Setting<?> setting = module.getSettingConfig(s.getKey());
                    if (setting == null) {
                        Earthhack.getLogger().error("Config: Couldn't find setting: " + s.getKey() + " in element: " + module.getName() + ".");
                    }
                    else {
                        preset.getValues().put(setting.getName(), s.getValue());
                        if (!generated || !GeneratedSettings.getGenerated(module).remove(setting)) {
                            continue;
                        }
                        module.unregister(setting);
                    }
                }
                presets.add(preset);
            }
        }
        final ElementConfig config = new ElementConfig(name);
        config.setPresets(presets);
        return config;
    }
}
