// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.module.data;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.config.preset.*;

public class DefaultData<M extends Module> extends AbstractData<M>
{
    public DefaultData(final M module) {
        super(module);
        this.presets.add(new DefaultPreset(module));
        this.descriptions.put(module.getSetting("Enabled"), "Enables this module.");
        this.descriptions.put(module.getSetting("Name"), "Name shown in the Arraylist.");
        this.descriptions.put(module.getSetting("Hidden"), "Decides if this module should show up in the Arraylist.");
        this.descriptions.put(module.getSetting("Bind"), "Keybind to toggle this module.");
        this.descriptions.put(module.getSetting("Toggle"), "<Normal>: Toggle when you press the bind.\n<Hold>: Toggle when you press or release.\n<Disable>: Toggle when pressed, disable when released.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "A " + this.module.getCategory().toString() + " module.";
    }
}
