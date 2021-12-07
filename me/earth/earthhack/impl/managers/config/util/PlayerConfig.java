// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.util;

import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.managers.client.*;
import java.util.concurrent.*;
import java.util.*;
import com.google.gson.*;
import me.earth.earthhack.api.config.*;

public class PlayerConfig extends IdentifiedNameable implements Config
{
    private final Map<String, UUID> players;
    private final PlayerManager manager;
    
    public PlayerConfig(final String name, final PlayerManager manager) {
        super(name);
        this.players = new ConcurrentHashMap<String, UUID>();
        this.manager = manager;
    }
    
    public void register(final String name, final UUID uuid) {
        this.players.put(name, uuid);
    }
    
    @Override
    public void apply() {
        this.manager.clear();
        for (final Map.Entry<String, UUID> entry : this.players.entrySet()) {
            this.manager.add(entry.getKey(), entry.getValue());
        }
    }
    
    public JsonObject getAsJsonObject() {
        final JsonObject object = new JsonObject();
        for (final Map.Entry<String, UUID> entry : this.players.entrySet()) {
            object.add((String)entry.getKey(), Jsonable.parse(entry.getValue().toString()));
        }
        return object;
    }
    
    public static PlayerConfig fromManager(final String name, final PlayerManager p) {
        final PlayerConfig config = new PlayerConfig(name, p);
        for (final Map.Entry<String, UUID> entry : p.getPlayersWithUUID().entrySet()) {
            config.register(entry.getKey(), entry.getValue());
        }
        return config;
    }
}
