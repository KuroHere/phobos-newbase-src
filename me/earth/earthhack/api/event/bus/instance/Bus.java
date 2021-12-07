// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.event.bus.instance;

import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.event.bus.*;

public class Bus
{
    public static final EventBus EVENT_BUS;
    
    static {
        EVENT_BUS = new SimpleBus();
    }
}
