// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.serializer.setting;

import me.earth.earthhack.api.observable.*;
import me.earth.earthhack.api.setting.event.*;

final class ListenerSetting implements Observer<SettingEvent<?>>
{
    private final SettingSerializer serializer;
    
    public ListenerSetting(final SettingSerializer serializer) {
        this.serializer = serializer;
    }
    
    @Override
    public void onChange(final SettingEvent<?> value) {
        this.serializer.onSettingChange(value);
    }
}
