// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.cache;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.register.*;
import java.util.function.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.util.bind.*;

public class ModuleCache<T extends Module> extends Cache<T>
{
    protected Class<T> type;
    
    private ModuleCache() {
    }
    
    public ModuleCache(final Register<Module> moduleManager, final Class<T> type) {
        this(() -> {
            if (moduleManager != null && type != null) {
                return (Module)moduleManager.getByClass(type);
            }
            else {
                return null;
            }
        }, (Class<Module>)type);
    }
    
    public ModuleCache(final Supplier<T> getter, final Class<T> type) {
        super(getter);
        this.type = type;
    }
    
    public void setModuleManager(final Register<Module> moduleManager) {
        this.getter = (Supplier<T>)(() -> {
            if (moduleManager != null && this.type != null) {
                return (T)moduleManager.getByClass(this.type);
            }
            else {
                return null;
            }
        });
    }
    
    public boolean enable() {
        return this.computeIfPresent(Module::enable);
    }
    
    public boolean disable() {
        return this.computeIfPresent(Module::disable);
    }
    
    public boolean toggle() {
        return this.computeIfPresent(Module::toggle);
    }
    
    public boolean isEnabled() {
        return this.isPresent() && this.get().isEnabled();
    }
    
    public String getDisplayInfo() {
        if (this.isPresent()) {
            return this.get().getDisplayInfo();
        }
        return null;
    }
    
    public Category getCategory() {
        if (this.isPresent()) {
            return this.get().getCategory();
        }
        return null;
    }
    
    public ModuleData getData() {
        if (this.isPresent()) {
            return this.get().getData();
        }
        return null;
    }
    
    public boolean setData(final ModuleData data) {
        if (this.isPresent()) {
            this.get().setData(data);
            return true;
        }
        return false;
    }
    
    public Bind getBind() {
        if (this.isPresent()) {
            return this.get().getBind();
        }
        return null;
    }
    
    public Toggle getBindMode() {
        if (this.isPresent()) {
            return this.get().getBindMode();
        }
        return null;
    }
    
    public static ModuleCache<Module> forName(final String name, final Register<Module> manager) {
        final NameCache cache = new NameCache(name);
        cache.setModuleManager(manager);
        return cache;
    }
    
    private static final class NameCache extends ModuleCache<Module>
    {
        private final String name;
        
        public NameCache(final String name) {
            super((ModuleCache$1)null);
            this.name = name;
            this.type = (Class<T>)Module.class;
        }
        
        @Override
        public void setModuleManager(final Register<Module> moduleManager) {
            this.getter = (Supplier<T>)(() -> {
                if (moduleManager != null) {
                    return (Module)moduleManager.getObject(this.name);
                }
                else {
                    return null;
                }
            });
        }
    }
}
