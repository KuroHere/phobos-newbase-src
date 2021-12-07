// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config.util;

import me.earth.earthhack.api.util.*;
import me.earth.earthhack.api.config.*;
import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.*;
import java.util.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.setting.settings.*;

public class BindConfig extends IdentifiedNameable implements Config
{
    private final List<BindWrapper> binds;
    private final Register<Module> modules;
    
    public BindConfig(final String name, final Register<Module> modules) {
        super(name);
        this.binds = new ArrayList<BindWrapper>();
        this.modules = modules;
    }
    
    public void add(final BindWrapper wrapper) {
        this.binds.add(wrapper);
    }
    
    public List<BindWrapper> getBinds() {
        return this.binds;
    }
    
    @Override
    public void apply() {
        for (final BindWrapper wrapper : this.binds) {
            final Module module = this.modules.getObject(wrapper.getModule());
            if (module == null) {
                Earthhack.getLogger().error("BindWrapper: Couldn't find module: " + wrapper.getModule() + ".");
            }
            else {
                final Setting<?> setting = module.getSettingConfig(wrapper.getName());
                if (setting == null) {
                    Earthhack.getLogger().error("BindWrapper: Couldn't find setting: " + wrapper.getName() + " in module: " + wrapper.getModule() + ".");
                }
                else {
                    setting.fromString(wrapper.getValue());
                }
            }
        }
    }
    
    public static BindConfig create(final String name, final Register<Module> modules) {
        final BindConfig config = new BindConfig(name, modules);
        for (final Module module : modules.getRegistered()) {
            for (final Setting<?> setting : module.getSettings()) {
                if (setting instanceof BindSetting) {
                    config.add(new BindWrapper(setting.getName(), module.getName(), setting.toJson()));
                }
            }
        }
        return config;
    }
}
