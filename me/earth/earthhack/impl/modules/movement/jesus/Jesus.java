// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.jesus;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.movement.jesus.mode.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class Jesus extends Module implements CollisionEvent.Listener
{
    protected final Setting<JesusMode> mode;
    protected final ListenerCollision listenerCollision;
    protected final StopWatch timer;
    protected boolean jumped;
    
    public Jesus() {
        super("Jesus", Category.Movement);
        this.mode = this.register(new EnumSetting("Mode", JesusMode.Solid));
        this.timer = new StopWatch();
        this.listenerCollision = new ListenerCollision(this);
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerLiquidJump(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.listeners.add(new ListenerTick(this));
        final SimpleData data = new SimpleData(this, "Walk on water like Jesus.");
        data.register(this.mode, "-Solid just walk on water.\n-Trampoline makes you jump high on water.\n-Dolphin mini jumps.");
        this.setData(data);
    }
    
    @Override
    public void onCollision(final CollisionEvent event) {
        if (this.isEnabled()) {
            this.listenerCollision.invoke(event);
        }
    }
    
    @Override
    public String getDisplayInfo() {
        return this.mode.getValue().toString();
    }
}
