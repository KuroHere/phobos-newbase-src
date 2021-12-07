// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.cleaner;

import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.impl.gui.chat.factory.*;

public class RemovingInteger extends NumberSetting<Integer> implements Removable
{
    public RemovingInteger(final String nameIn, final Integer initialValue, final Integer min, final Integer max) {
        super(nameIn, initialValue, min, max);
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
    
    static {
        ComponentFactory.register(RemovingInteger.class, RemovingIntegerComponent.FACTORY);
    }
}
