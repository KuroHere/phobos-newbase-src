// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting.settings;

import me.earth.earthhack.api.setting.*;
import com.google.gson.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.api.util.*;

public class EnumSetting<E extends Enum<E>> extends Setting<E>
{
    private final String concatenated;
    
    public EnumSetting(final String nameIn, final E initialValue) {
        super(nameIn, initialValue);
        this.concatenated = this.concatenateInputs();
    }
    
    @Override
    public void fromJson(final JsonElement element) {
        this.fromString(element.getAsString());
    }
    
    @Override
    public SettingResult fromString(final String string) {
        final Enum<?> entry = EnumHelper.fromString(this.value, string);
        this.setValue((E)entry);
        return SettingResult.SUCCESSFUL;
    }
    
    @Override
    public String getInputs(final String string) {
        if (string == null || string.isEmpty()) {
            return this.concatenated;
        }
        final Enum<?> entry = EnumHelper.getEnumStartingWith(string, this.initial.getDeclaringClass());
        return (entry == null) ? "" : entry.toString();
    }
    
    private String concatenateInputs() {
        final StringBuilder builder = new StringBuilder("<");
        final Class<? extends Enum<?>> clazz = this.initial.getDeclaringClass();
        for (final Enum<?> entry : (Enum[])clazz.getEnumConstants()) {
            builder.append(entry.name()).append(", ");
        }
        builder.replace(builder.length() - 2, builder.length(), ">");
        return builder.toString();
    }
}
