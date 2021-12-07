// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.setting.event;

import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.api.setting.*;

public class SettingEvent<T> extends Event
{
    private final Setting<T> setting;
    private T value;
    
    public SettingEvent(final Setting<T> setting, final T value) {
        this.setting = setting;
        this.value = value;
    }
    
    public Setting<T> getSetting() {
        return this.setting;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setValue(final T value) {
        this.value = value;
    }
}
