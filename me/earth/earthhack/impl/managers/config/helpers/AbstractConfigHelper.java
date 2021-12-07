// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.helpers;

import me.earth.earthhack.api.config.*;
import com.google.gson.*;
import java.io.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.managers.config.util.*;
import java.util.*;
import java.nio.file.*;
import me.earth.earthhack.impl.util.misc.*;

public abstract class AbstractConfigHelper<C extends Config> implements ConfigHelper<C>
{
    protected final Map<String, C> configs;
    protected final String name;
    protected final String path;
    
    public AbstractConfigHelper(final String name, final String path) {
        this.configs = new HashMap<String, C>();
        this.name = name;
        this.path = "earthhack/" + path;
    }
    
    protected abstract C create(final String p0);
    
    protected abstract JsonObject toJson(final C p0);
    
    protected abstract C readFile(final InputStream p0, final String p1) throws IOException;
    
    @Override
    public void save() throws IOException {
        this.ensureDir(this.path);
        if (this.registerDefaultIfNotPresent()) {
            final C config = this.create("default");
            this.configs.put("default", config);
        }
        final String current = CurrentConfig.getInstance().get(this);
        try {
            for (final String s : this.configs.keySet()) {
                if (s.equalsIgnoreCase(current)) {
                    this.ensureDir(this.path);
                    final C config2 = this.create(s);
                    this.configs.put(s, config2);
                    final JsonObject object = this.toJson(config2);
                    JsonPathWriter.write(Paths.get(this.path + "/" + s + ".json", new String[0]), object);
                }
                else {
                    this.save(s);
                }
            }
        }
        finally {
            CurrentConfig.getInstance().set(this, current);
        }
    }
    
    @Override
    public void refresh() throws IOException {
        this.ensureDir(this.path);
        final Map<String, C> configMap = new HashMap<String, C>();
        Files.walk(Paths.get(this.path, new String[0]), new FileVisitOption[0]).forEach(p -> {
            if (p.getFileName().toString().endsWith(".json")) {
                try {
                    Earthhack.getLogger().info(this.getName() + " config found : " + p);
                    final C config = this.read(p);
                    configMap.put(config.getName().toLowerCase(), config);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return;
        });
        this.configs.clear();
        this.configs.putAll((Map<? extends String, ? extends C>)configMap);
    }
    
    @Override
    public void save(String name) throws IOException {
        name = name.toLowerCase();
        this.ensureDir(this.path);
        C config = this.configs.get(name);
        if (config == null || name.equalsIgnoreCase(CurrentConfig.getInstance().get(this))) {
            config = this.create(name);
            this.configs.put(name, config);
        }
        final JsonObject object = this.toJson(config);
        JsonPathWriter.write(Paths.get(this.path + "/" + name + ".json", new String[0]), object);
        CurrentConfig.getInstance().set(this, name);
    }
    
    @Override
    public void load(String name) {
        name = name.toLowerCase();
        final C c = this.configs.get(name);
        if (c != null) {
            c.apply();
            CurrentConfig.getInstance().set(this, name);
        }
    }
    
    @Override
    public void refresh(String name) throws IOException {
        this.ensureDir(this.path);
        name = name.toLowerCase();
        final Path path = Paths.get(name, new String[0]);
        final C config = this.read(path);
        this.configs.put(name, config);
    }
    
    @Override
    public void delete(String name) throws ConfigDeleteException, IOException {
        name = name.toLowerCase();
        if ("default".equalsIgnoreCase(name)) {
            throw new ConfigDeleteException("Can't delete the Default config!");
        }
        if (name.equalsIgnoreCase(CurrentConfig.getInstance().get(this))) {
            throw new ConfigDeleteException("This config is currently active. Please switch to another config before deleting this.");
        }
        this.configs.remove(name);
        final Path deletePath = Paths.get(this.path + "/" + name + ".json", new String[0]);
        Files.delete(deletePath);
    }
    
    @Override
    public Collection<C> getConfigs() {
        return this.configs.values();
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    protected C read(final Path path) throws IOException {
        final String name = path.getFileName().toString();
        try (final InputStream stream = Files.newInputStream(path, new OpenOption[0])) {
            return this.readFile(stream, name.substring(0, name.length() - 5));
        }
    }
    
    protected boolean registerDefaultIfNotPresent() {
        final String current = CurrentConfig.getInstance().get(this);
        if (current == null || current.equals("default")) {
            CurrentConfig.getInstance().set(this, "default");
            return true;
        }
        return false;
    }
    
    protected void ensureDir(final String path) {
        FileUtil.createDirectory(Paths.get(path, new String[0]));
    }
}
