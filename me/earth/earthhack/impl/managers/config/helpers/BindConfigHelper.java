// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.helpers;

import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.managers.config.util.*;
import java.util.*;
import com.google.gson.*;
import me.earth.earthhack.api.config.*;
import java.io.*;

public class BindConfigHelper extends AbstractConfigHelper<BindConfig>
{
    public BindConfigHelper() {
        super("bind", "binds");
    }
    
    @Override
    protected BindConfig create(final String name) {
        return BindConfig.create(name, Managers.MODULES);
    }
    
    @Override
    protected JsonObject toJson(final BindConfig config) {
        final JsonObject object = new JsonObject();
        for (final BindWrapper wrapper : config.getBinds()) {
            final String wrapped = Jsonable.GSON.toJson((Object)wrapper);
            object.add(wrapper.getModule() + "-" + wrapper.getName(), Jsonable.parse(wrapped, false));
        }
        return object;
    }
    
    @Override
    protected BindConfig readFile(final InputStream stream, final String name) {
        final BindConfig config = new BindConfig(name, Managers.MODULES);
        final JsonObject object = Jsonable.PARSER.parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            config.add((BindWrapper)Jsonable.GSON.fromJson((JsonElement)entry.getValue(), (Class)BindWrapper.class));
        }
        return config;
    }
}
