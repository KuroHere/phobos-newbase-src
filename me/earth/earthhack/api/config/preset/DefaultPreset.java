// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.config.preset;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;

public class DefaultPreset extends ModulePreset
{
    public DefaultPreset(final Module module) {
        super("reset", module, "Resets all settings to the default value.");
    }
    
    @Override
    public void apply() {
        for (final Setting<?> setting : this.getModule().getSettings()) {
            setting.reset();
        }
    }
}
