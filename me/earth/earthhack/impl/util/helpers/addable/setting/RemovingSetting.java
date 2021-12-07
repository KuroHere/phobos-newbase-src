// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.addable.setting;

import me.earth.earthhack.api.setting.*;
import com.google.gson.*;
import me.earth.earthhack.api.setting.event.*;

public abstract class RemovingSetting<T> extends Setting<T> implements Removable
{
    public RemovingSetting(final String name, final T initial) {
        super(name, initial);
    }
    
    @Override
    public void remove() {
        if (this.container != null) {
            this.container.unregister(this);
        }
    }
    
    @Override
    public void setValue(final T value) {
        this.setValue(value, true);
    }
    
    @Override
    public void setValue(final T value, final boolean withEvent) {
        final SettingEvent<T> event = (SettingEvent<T>)this.onChange((SettingEvent<T>)new SettingEvent<Object>((Setting<T>)this, (T)value));
        if (!event.isCancelled()) {
            this.remove();
        }
    }
    
    @Override
    public void fromJson(final JsonElement element) {
    }
    
    @Override
    public SettingResult fromString(final String string) {
        if ("remove".equalsIgnoreCase(string)) {
            this.remove();
            return new SettingResult(false, this.getName() + " was removed.");
        }
        return new SettingResult(false, "Possible input: \"remove\".");
    }
    
    @Override
    public String getInputs(final String string) {
        if (string == null || string.isEmpty()) {
            return "<remove>";
        }
        if ("remove".startsWith(string.toLowerCase())) {
            return "remove";
        }
        return "";
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof RemovingSetting && this.name.equalsIgnoreCase(((RemovingSetting)o).name) && this.container != null && this.container.equals(((RemovingSetting)o).getContainer());
    }
}
