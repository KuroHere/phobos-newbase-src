// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.autoconfig;

import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.impl.gui.chat.factory.*;

public class RemovingString extends StringSetting implements Removable
{
    public RemovingString(final String nameIn, final String initialValue) {
        super(nameIn, initialValue);
    }
    
    @Override
    public void remove() {
        if (this.container != null) {
            this.container.unregister(this);
        }
    }
    
    @Override
    public SettingResult fromString(final String string) {
        if ("remove".equalsIgnoreCase(string)) {
            this.remove();
            return new SettingResult(false, this.getName() + " was removed.");
        }
        return super.fromString(string);
    }
    
    @Override
    public String getInputs(final String string) {
        if (string == null || string.isEmpty()) {
            return super.getInputs(string) + " or <remove>";
        }
        if ("remove".startsWith(string.toLowerCase())) {
            return "remove";
        }
        return super.getInputs(string);
    }
    
    @Override
    public String getInitial() {
        return this.getValue();
    }
    
    @Override
    public void reset() {
    }
    
    static {
        ComponentFactory.register(RemovingString.class, RemovingStringComponent::new);
    }
}
