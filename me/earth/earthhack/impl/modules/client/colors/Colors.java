// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.colors;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.api.event.bus.api.*;

public class Colors extends Module
{
    public Colors() {
        super("Colors", Category.Client);
        this.register(Managers.COLOR.getColorSetting());
        this.register(Managers.COLOR.getRainbowSpeed());
        Bus.EVENT_BUS.register(new ListenerTick(this));
    }
}
