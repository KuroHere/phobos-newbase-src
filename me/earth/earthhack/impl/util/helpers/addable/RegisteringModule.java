// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.addable;

import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import java.util.function.*;
import me.earth.earthhack.api.module.util.*;
import java.util.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.data.*;

public class RegisteringModule<I, E extends Setting<I> & Removable> extends AddableModule
{
    protected final Function<Setting<?>, String> settingDescription;
    protected final Set<E> added;
    protected final Function<String, E> create;
    
    public RegisteringModule(final String name, final Category category, final String command, final String descriptor, final Function<String, E> create, final Function<Setting<?>, String> settingDescription) {
        super(name, category, command, descriptor);
        this.added = new LinkedHashSet<E>();
        this.create = create;
        this.settingDescription = settingDescription;
    }
    
    @Override
    public void add(final String string) {
        this.addSetting(string);
    }
    
    @Override
    public void del(final String string) {
        super.del(string);
        final Setting<?> setting = this.getSetting(string);
        if (setting != null) {
            this.unregister(setting);
        }
    }
    
    @Override
    public Setting<?> getSettingConfig(final String name) {
        final Setting<?> setting = super.getSetting(name);
        if (setting == null) {
            final Setting<?> generated = this.addSetting(name);
            if (generated != null) {
                final ModuleData data = this.getData();
                if (data != null) {
                    data.settingDescriptions().put(generated, this.settingDescription.apply(generated));
                }
                GeneratedSettings.add(this, generated);
            }
            return generated;
        }
        return setting;
    }
    
    @Override
    public Setting<?> unregister(final Setting<?> setting) {
        this.added.remove(setting);
        this.strings.remove(this.formatString(setting.getName()));
        return super.unregister(setting);
    }
    
    protected E addSetting(final String string) {
        final E newSetting = this.create.apply(string);
        if (this.added.add(newSetting)) {
            final ModuleData data = this.getData();
            if (data != null) {
                data.settingDescriptions().put(newSetting, this.settingDescription.apply(newSetting));
            }
            this.register(newSetting);
            super.add(string);
            return newSetting;
        }
        return null;
    }
}
