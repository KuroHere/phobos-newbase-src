// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.client;

import me.earth.earthhack.api.plugin.*;
import java.net.*;
import me.earth.earthhack.impl.core.*;
import me.earth.earthhack.impl.*;
import java.util.*;
import java.lang.reflect.*;
import me.earth.earthhack.impl.managers.client.exception.*;
import me.earth.earthhack.vanilla.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.api.config.*;
import java.io.*;
import java.util.jar.*;

public class PluginManager
{
    private static final PluginManager INSTANCE;
    private static final String PATH = "earthhack/plugins";
    private final Map<PluginConfig, Plugin> plugins;
    private final Map<String, PluginConfig> configs;
    private final PluginRemapper remapper;
    private ClassLoader classLoader;
    
    private PluginManager() {
        this.plugins = new HashMap<PluginConfig, Plugin>();
        this.configs = new HashMap<String, PluginConfig>();
        this.remapper = new PluginRemapper();
    }
    
    public static PluginManager getInstance() {
        return PluginManager.INSTANCE;
    }
    
    public void createPluginConfigs(final ClassLoader pluginClassLoader) {
        if (!(pluginClassLoader instanceof URLClassLoader)) {
            throw new IllegalArgumentException("PluginClassLoader was not an URLClassLoader, but: " + pluginClassLoader.getClass().getName());
        }
        this.classLoader = pluginClassLoader;
        Core.LOGGER.info("PluginManager: Scanning for PluginConfigs.");
        final File d = new File("earthhack/plugins");
        final Map<String, File> remap = this.scanPlugins(d.listFiles(), pluginClassLoader);
        remap.keySet().removeAll(this.configs.keySet());
        try {
            final File[] remappedPlugins = this.remapper.remap(remap.values());
            this.scanPlugins(remappedPlugins, pluginClassLoader);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Map<String, File> scanPlugins(final File[] files, final ClassLoader pluginClassLoader) {
        final Map<String, File> remap = new HashMap<String, File>();
        try {
            for (final File file : Objects.requireNonNull(files)) {
                if (file.getName().endsWith(".jar")) {
                    Core.LOGGER.info("PluginManager: Scanning " + file.getName());
                    try {
                        this.scanJarFile(file, pluginClassLoader, remap);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        return remap;
    }
    
    public void instantiatePlugins() {
        for (final PluginConfig config : this.configs.values()) {
            if (this.plugins.containsKey(config)) {
                Earthhack.getLogger().error("Can't register Plugin " + config.getName() + ", a plugin with that name is already registered.");
            }
            else {
                Earthhack.getLogger().info("Instantiating: " + config.getName() + ", MainClass: " + config.getMainClass());
                try {
                    final Class<?> clazz = Class.forName(config.getMainClass());
                    final Constructor<?> constructor = clazz.getConstructor((Class<?>[])new Class[0]);
                    constructor.setAccessible(true);
                    final Plugin plugin = (Plugin)constructor.newInstance(new Object[0]);
                    this.plugins.put(config, plugin);
                }
                catch (Throwable e) {
                    Earthhack.getLogger().error("Error instantiating : " + config.getName() + ", caused by:");
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void scanJarFile(final File file, final ClassLoader pluginClassLoader, final Map<String, File> remap) throws Exception {
        final JarFile jarFile = new JarFile(file);
        final Manifest manifest = jarFile.getManifest();
        final Attributes attributes = manifest.getMainAttributes();
        final String configName = attributes.getValue("3arthh4ckConfig");
        if (configName == null) {
            throw new BadPluginException(jarFile.getName() + ": Manifest doesn't provide a 3arthh4ckConfig!");
        }
        final String vanilla = attributes.getValue("3arthh4ckVanilla");
        switch (Environment.getEnvironment()) {
            case VANILLA: {
                if (vanilla == null || vanilla.equals("false")) {
                    Core.LOGGER.info("Found Plugin to remap!");
                    remap.put(configName, file);
                    return;
                }
                break;
            }
            case SEARGE:
            case MCP: {
                if (vanilla != null && vanilla.equals("true")) {
                    return;
                }
                break;
            }
        }
        ReflectionUtil.addToClassPath((URLClassLoader)pluginClassLoader, file);
        final PluginConfig config = (PluginConfig)Jsonable.GSON.fromJson((Reader)new InputStreamReader(Objects.requireNonNull(pluginClassLoader.getResourceAsStream(configName))), (Class)PluginConfig.class);
        if (config == null) {
            throw new BadPluginException(jarFile.getName() + ": Found a PluginConfig, but couldn't instantiate it.");
        }
        Core.LOGGER.info("Found PluginConfig: " + config.getName() + ", MainClass: " + config.getMainClass() + ", Mixins: " + config.getMixinConfig());
        this.configs.put(configName, config);
    }
    
    public Map<String, PluginConfig> getConfigs() {
        return this.configs;
    }
    
    public Map<PluginConfig, Plugin> getPlugins() {
        return this.plugins;
    }
    
    public ClassLoader getPluginClassLoader() {
        return this.classLoader;
    }
    
    static {
        INSTANCE = new PluginManager();
    }
}
