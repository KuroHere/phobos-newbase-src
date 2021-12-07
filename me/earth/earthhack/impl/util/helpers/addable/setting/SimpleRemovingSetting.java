// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.addable.setting;

import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.helpers.addable.setting.component.*;
import me.earth.earthhack.impl.gui.chat.factory.*;
import me.earth.earthhack.api.observable.*;

public class SimpleRemovingSetting extends RemovingSetting<Boolean>
{
    public SimpleRemovingSetting(final String name) {
        super(name, true);
    }
    
    @Override
    public void setValue(final Boolean value, final boolean withEvent) {
        final SettingEvent<Boolean> event = ((Observable<SettingEvent<Boolean>>)this).onChange(new SettingEvent<Boolean>(this, value));
        if (!event.isCancelled() && !value) {
            super.remove();
        }
    }
    
    static {
        ComponentFactory.register(SimpleRemovingSetting.class, SimpleRemovingComponent::new);
    }
}
