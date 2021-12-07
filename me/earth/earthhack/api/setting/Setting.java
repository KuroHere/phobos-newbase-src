// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting;

import me.earth.earthhack.api.observable.*;
import me.earth.earthhack.api.config.*;
import me.earth.earthhack.api.util.interfaces.*;
import com.google.gson.*;
import me.earth.earthhack.api.setting.event.*;
import java.util.*;

public abstract class Setting<T> extends Observable<SettingEvent<T>> implements Jsonable, Nameable
{
    protected final String name;
    protected final T initial;
    protected SettingContainer container;
    protected T value;
    
    public Setting(final String nameIn, final T initialValue) {
        this.name = nameIn;
        this.initial = initialValue;
        this.value = initialValue;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public abstract void fromJson(final JsonElement p0);
    
    @Override
    public String toJson() {
        return (this.value == null) ? "null" : this.value.toString();
    }
    
    public abstract SettingResult fromString(final String p0);
    
    public abstract String getInputs(final String p0);
    
    public void setValue(final T value) {
        this.setValue(value, true);
    }
    
    public void setValue(final T value, final boolean withEvent) {
        if (withEvent) {
            final SettingEvent<T> event = this.onChange(new SettingEvent<T>(this, value));
            if (!event.isCancelled()) {
                this.value = event.getValue();
            }
        }
        else {
            this.value = value;
        }
    }
    
    public T getValue() {
        return this.value;
    }
    
    public T getInitial() {
        return this.initial;
    }
    
    public void reset() {
        this.value = this.initial;
    }
    
    protected void setContainer(final SettingContainer container) {
        this.container = container;
    }
    
    public SettingContainer getContainer() {
        return this.container;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            final Setting<T> o = (Setting<T>)obj;
            return Objects.equals(o.getName(), this.getName()) && Objects.equals(o.getValue(), this.getValue()) && Objects.equals(o.getContainer(), this.getContainer()) && Objects.equals(o.getInitial(), this.getInitial());
        }
        return false;
    }
}
