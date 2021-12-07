// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autorespawn;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class AutoRespawn extends Module
{
    protected final Setting<Boolean> coords;
    
    public AutoRespawn() {
        super("AutoRespawn", Category.Misc);
        this.coords = this.register(new BooleanSetting("Coords", false));
        this.listeners.add(new ListenerScreens(this));
        this.setData(new AutoRespawnData(this));
    }
}
