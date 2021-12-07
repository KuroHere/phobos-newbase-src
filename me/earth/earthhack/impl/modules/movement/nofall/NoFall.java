// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.nofall;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.movement.nofall.mode.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class NoFall extends Module
{
    protected final Setting<FallMode> mode;
    protected final StopWatch timer;
    
    public NoFall() {
        super("NoFall", Category.Movement);
        this.mode = this.register(new EnumSetting("Mode", FallMode.Packet));
        this.timer = new StopWatch();
        this.listeners.add(new ListenerMotion(this));
        this.listeners.addAll(new ListenerPlayerPackets(this).getListeners());
        final SimpleData data = new SimpleData(this, "Prevents Falldamage.");
        data.register(this.mode, "-Packet standard NoFall.\n-AAC a NoFall for the AAC anticheat.\n-Anti prevents damage by elevating your position silently.\n-Bucket uses a water bucket if you have on in your hotbar.");
        this.setData(data);
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().toString();
    }
}
