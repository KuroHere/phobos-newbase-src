// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.entitycontrol;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class EntityControl extends Module
{
    protected final Setting<Boolean> control;
    protected final Setting<Double> jumpHeight;
    protected final Setting<Boolean> noAI;
    
    public EntityControl() {
        super("EntityControl", Category.Movement);
        this.control = this.register(new BooleanSetting("Control", true));
        this.jumpHeight = this.register(new NumberSetting("JumpHeight", 0.7, 0.0, 2.0));
        this.noAI = this.register(new BooleanSetting("NoAI", true));
        this.listeners.add(new ListenerControl(this));
        this.listeners.add(new ListenerAI(this));
        this.listeners.add(new ListenerHorse(this));
        this.listeners.add(new ListenerTick(this));
        this.setData(new EntityControlData(this));
    }
}
