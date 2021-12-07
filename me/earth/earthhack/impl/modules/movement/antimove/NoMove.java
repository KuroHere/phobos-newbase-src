// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.antimove;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.movement.antimove.modes.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class NoMove extends Module
{
    protected final Setting<StaticMode> mode;
    protected final Setting<Float> height;
    protected final Setting<Boolean> timer;
    
    public NoMove() {
        super("Static", Category.Movement);
        this.mode = this.register(new EnumSetting("Mode", StaticMode.Stop));
        this.height = this.register(new NumberSetting("Height", 4.0f, 0.0f, 256.0f));
        this.timer = this.register(new BooleanSetting("Timer", false));
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerUpdate(this));
        final SimpleData data = new SimpleData(this, "Stops all Movement depending on the mode.");
        data.register(this.mode, "-Stop Stops all movement while this module is enabled. Can be used to lag you back up when you fall.\n-NoVoid stops all movement if there's void underneath you.\n-Roof used to tp you up 120 blocks on certain servers.");
        this.setData(data);
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().toString();
    }
}
