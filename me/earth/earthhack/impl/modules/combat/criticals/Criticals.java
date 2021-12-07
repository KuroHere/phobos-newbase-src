// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.criticals;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.criticals.mode.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class Criticals extends Module
{
    protected final Setting<CritMode> mode;
    protected final Setting<Boolean> noDesync;
    protected final Setting<Integer> delay;
    protected final StopWatch timer;
    
    public Criticals() {
        super("Criticals", Category.Combat);
        this.mode = this.register(new EnumSetting("Mode", CritMode.Packet));
        this.noDesync = this.register(new BooleanSetting("NoDesync", true));
        this.delay = this.register(new NumberSetting("Delay", 250, 0, 1000));
        this.timer = new StopWatch();
        this.listeners.add(new ListenerUseEntity(this));
        this.setData(new CriticalsData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().name();
    }
}
