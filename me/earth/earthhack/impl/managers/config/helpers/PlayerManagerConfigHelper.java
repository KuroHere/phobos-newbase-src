// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.helpers;

import me.earth.earthhack.impl.managers.config.util.*;
import me.earth.earthhack.impl.managers.client.*;
import com.google.gson.*;
import java.util.*;
import me.earth.earthhack.api.config.*;
import java.io.*;

public class PlayerManagerConfigHelper extends AbstractConfigHelper<PlayerConfig>
{
    private final PlayerManager manager;
    
    public PlayerManagerConfigHelper(final String name, final String path, final PlayerManager manager) {
        super(name, path);
        this.manager = manager;
    }
    
    @Override
    protected PlayerConfig create(final String name) {
        return PlayerConfig.fromManager(name, this.manager);
    }
    
    @Override
    protected JsonObject toJson(final PlayerConfig config) {
        return config.getAsJsonObject();
    }
    
    @Override
    protected PlayerConfig readFile(final InputStream stream, final String name) {
        final PlayerConfig config = new PlayerConfig(name, this.manager);
        final JsonObject object = Jsonable.PARSER.parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            config.register(entry.getKey(), UUID.fromString(entry.getValue().getAsString()));
        }
        return config;
    }
}
