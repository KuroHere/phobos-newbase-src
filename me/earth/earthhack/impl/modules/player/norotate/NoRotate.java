// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.norotate;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;

public class NoRotate extends Module
{
    protected final Setting<Boolean> noForceLook;
    protected final Setting<Boolean> async;
    protected final Setting<Boolean> noSpoof;
    
    public NoRotate() {
        super("NoRotate", Category.Player);
        this.noForceLook = this.register(new BooleanSetting("NoForceLook", false));
        this.async = this.register(new BooleanSetting("Async", false));
        this.noSpoof = this.register(new BooleanSetting("NoThrowableSpoof", false));
        this.listeners.add(new ListenerPosLook(this));
        this.listeners.addAll(new ListenerCPacket(this).getListeners());
    }
}
