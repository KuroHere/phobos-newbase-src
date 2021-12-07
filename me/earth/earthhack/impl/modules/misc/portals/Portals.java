// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.portals;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class Portals extends Module
{
    protected final Setting<Boolean> godMode;
    
    public Portals() {
        super("Portals", Category.Misc);
        this.godMode = this.register(new BooleanSetting("GodMode", false));
        this.listeners.add(new ListenerTeleport(this));
        this.register(new BooleanSetting("Chat", true));
        this.setData(new PortalsData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.godMode.getValue()) {
            return "§cGodMode";
        }
        return null;
    }
}
