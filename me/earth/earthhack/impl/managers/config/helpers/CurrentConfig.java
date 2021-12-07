// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.helpers;

import java.nio.file.attribute.*;
import me.earth.earthhack.api.config.*;
import java.nio.file.*;
import com.google.gson.*;
import me.earth.earthhack.impl.managers.*;
import java.io.*;
import me.earth.earthhack.impl.managers.config.util.*;
import java.util.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.register.*;

public class CurrentConfig implements ConfigHelper
{
    private static final CurrentConfig INSTANCE;
    private static final String PATH = "earthhack/Configs.json";
    private final Map<ConfigHelper, String> configs;
    private final Map<String, String> additional;
    
    private CurrentConfig() {
        this.configs = new HashMap<ConfigHelper, String>();
        this.additional = new HashMap<String, String>();
    }
    
    public static CurrentConfig getInstance() {
        return CurrentConfig.INSTANCE;
    }
    
    public void set(final ConfigHelper helper, final String config) {
        this.configs.put(helper, config);
    }
    
    public String get(final ConfigHelper helper) {
        return this.configs.get(helper);
    }
    
    public void set(final String additional, final String config) {
        this.additional.put(additional, config);
    }
    
    public String get(final String additional) {
        return this.additional.get(additional);
    }
    
    @Override
    public void save() throws IOException {
        final Path file = Paths.get("earthhack/Configs.json", new String[0]);
        if (!Files.exists(file, new LinkOption[0])) {
            Files.createFile(file, (FileAttribute<?>[])new FileAttribute[0]);
        }
        final JsonObject object = new JsonObject();
        for (final Map.Entry<ConfigHelper, String> entry : this.configs.entrySet()) {
            object.add(entry.getKey().getName(), Jsonable.parse(entry.getValue()));
        }
        for (final Map.Entry<String, String> entry2 : this.additional.entrySet()) {
            object.add((String)entry2.getKey(), Jsonable.parse(entry2.getValue()));
        }
        JsonPathWriter.write(file, object);
    }
    
    @Override
    public void refresh() throws IOException {
        try (final InputStream stream = Files.newInputStream(Paths.get("earthhack/Configs.json", new String[0]), new OpenOption[0])) {
            final JsonObject object = Jsonable.PARSER.parse((Reader)new InputStreamReader(stream)).getAsJsonObject();
            for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                final ConfigHelper helper = ((AbstractRegister<ConfigHelper>)Managers.CONFIG).getObject(entry.getKey());
                if (helper != null) {
                    this.set(helper, entry.getValue().getAsString());
                }
                else {
                    this.additional.put(entry.getKey(), entry.getValue().getAsString());
                }
            }
        }
    }
    
    @Override
    public void save(final String name) throws IOException {
        throw new UnsupportedOperationException("CurrentConfig doesn't support multiple configs.");
    }
    
    @Override
    public void load(final String name) {
        throw new UnsupportedOperationException("CurrentConfig doesn't support multiple configs.");
    }
    
    @Override
    public void refresh(final String name) throws IOException {
        throw new UnsupportedOperationException("CurrentConfig doesn't support multiple configs.");
    }
    
    @Override
    public void delete(final String name) throws ConfigDeleteException {
        throw new ConfigDeleteException("CurrentConfig doesn't support multiple configs.");
    }
    
    @Override
    public Collection<? extends Nameable> getConfigs() {
        return this.configs.keySet();
    }
    
    @Override
    public String getName() {
        return "current";
    }
    
    static {
        INSTANCE = new CurrentConfig();
    }
}
