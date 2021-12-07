// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.skinblink;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class SkinBlink extends Module
{
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> random;
    protected final StopWatch timer;
    
    public SkinBlink() {
        super("SkinBlink", Category.Misc);
        this.delay = this.register(new NumberSetting("Delay", 1000, 0, 2000));
        this.random = this.register(new BooleanSetting("Random", false));
        this.timer = new StopWatch();
        this.listeners.add(new ListenerGameLoop(this));
        this.setData(new SkinBlinkData(this));
    }
}
