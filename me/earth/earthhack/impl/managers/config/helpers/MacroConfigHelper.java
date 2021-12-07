// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.helpers;

import me.earth.earthhack.impl.managers.config.util.*;
import java.util.*;
import com.google.gson.*;
import me.earth.earthhack.api.util.bind.*;
import me.earth.earthhack.impl.managers.client.macro.*;
import me.earth.earthhack.api.config.*;
import java.io.*;

public class MacroConfigHelper extends AbstractConfigHelper<MacroConfig>
{
    private final MacroManager manager;
    
    public MacroConfigHelper(final MacroManager manager) {
        super("macro", "macros");
        this.manager = manager;
    }
    
    @Override
    protected MacroConfig create(final String name) {
        return MacroConfig.create(name, this.manager);
    }
    
    @Override
    protected JsonObject toJson(final MacroConfig config) {
        final JsonObject object = new JsonObject();
        for (final Macro macro : config.getMacros()) {
            object.add(macro.getName(), Jsonable.parse(macro.toJson(), false));
        }
        return object;
    }
    
    @Override
    protected MacroConfig readFile(final InputStream stream, final String name) {
        final MacroConfig config = new MacroConfig(name, this.manager);
        final JsonObject object = Jsonable.PARSER.parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            final JsonObject value = entry.getValue().getAsJsonObject();
            final MacroType type = MacroType.fromString(value.get("type").getAsString());
            Macro macro = null;
            switch (type) {
                case NORMAL: {
                    macro = new Macro(entry.getKey(), Bind.none(), new String[0]);
                    break;
                }
                case FLOW: {
                    macro = new FlowMacro(entry.getKey(), Bind.none(), new Macro[0]);
                    break;
                }
                case COMBINED: {
                    macro = new CombinedMacro(entry.getKey(), Bind.none(), new Macro[0]);
                    break;
                }
                case DELEGATE: {
                    macro = new DelegateMacro(entry.getKey(), "");
                    break;
                }
                default: {
                    continue;
                }
            }
            macro.fromJson(entry.getValue());
            config.add(macro);
        }
        return config;
    }
}
