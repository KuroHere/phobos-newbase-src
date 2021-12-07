// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.timer;

import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.player.timer.mode.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;

public class Timer extends DisablingModule
{
    protected final Setting<TimerMode> mode;
    protected final Setting<Integer> autoOff;
    protected final Setting<Integer> lagTime;
    protected final Setting<Float> speed;
    protected final Setting<Integer> updates;
    protected final Setting<Float> fast;
    protected final Setting<Integer> fastTime;
    protected final Setting<Float> slow;
    protected final Setting<Integer> slowTime;
    protected final Setting<Integer> maxPackets;
    protected final Setting<Integer> offset;
    protected final Setting<Integer> letThrough;
    protected final StopWatch offTimer;
    protected final StopWatch switchTimer;
    protected float pSpeed;
    protected int ticks;
    protected int packets;
    protected int sent;
    protected boolean isSlow;
    
    public Timer() {
        super("Timer", Category.Player);
        this.mode = this.register(new EnumSetting("Mode", TimerMode.Normal));
        this.autoOff = this.register(new NumberSetting("AutoOff", 0, 0, 1000));
        this.lagTime = this.register(new NumberSetting("LagTime", 250, 0, 1000));
        this.speed = this.register(new NumberSetting("Speed", 1.0888f, 0.1f, 100.0f));
        this.updates = this.register(new NumberSetting("Updates", 2, 0, 100));
        this.fast = this.register(new NumberSetting("Fast", 20.0f, 0.1f, 100.0f));
        this.fastTime = this.register(new NumberSetting("FastTime", 100, 0, 5000));
        this.slow = this.register(new NumberSetting("Slow", 1.0f, 0.1f, 100.0f));
        this.slowTime = this.register(new NumberSetting("SlowTime", 250, 0, 5000));
        this.maxPackets = this.register(new NumberSetting("Max-Packets", 100, 0, 1000));
        this.offset = this.register(new NumberSetting("Offset", 10, 0, 100));
        this.letThrough = this.register(new NumberSetting("Network-Ticks", 10, 0, 100));
        this.offTimer = new StopWatch();
        this.switchTimer = new StopWatch();
        this.pSpeed = 1.0f;
        this.ticks = 0;
        this.packets = 0;
        this.sent = 0;
        this.listeners.add(new ListenerPosLook(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.addAll(new ListenerPlayerPackets(this).getListeners());
        this.setData(new TimerData(this));
    }
    
    @Override
    protected void onEnable() {
        this.packets = 0;
        this.sent = 0;
        this.isSlow = false;
        this.offTimer.reset();
    }
    
    @Override
    protected void onDisable() {
        Managers.TIMER.reset();
    }
    
    @Override
    public String getDisplayInfo() {
        String color;
        if (!Managers.NCP.passed(this.lagTime.getValue())) {
            color = "§c";
        }
        else {
            color = "";
        }
        switch (this.mode.getValue()) {
            case Switch: {
                return color + this.getSwitchSpeed();
            }
            case Physics: {
                return color + "Physics";
            }
            case Blink: {
                return ((this.packets > 0 && this.pSpeed != 1.0f) ? "§a" : color) + this.packets;
            }
            default: {
                return color + this.speed.getValue().toString();
            }
        }
    }
    
    public float getSpeed() {
        if (Managers.NCP.passed(this.lagTime.getValue())) {
            switch (this.mode.getValue()) {
                case Switch: {
                    if (this.switchTimer.passed(this.getTime())) {
                        this.isSlow = !this.isSlow;
                        this.switchTimer.reset();
                    }
                    return this.getSwitchSpeed();
                }
                case Normal: {
                    return this.speed.getValue();
                }
                case Blink: {
                    return this.pSpeed;
                }
            }
        }
        return 1.0f;
    }
    
    private int getTime() {
        return this.isSlow ? this.slowTime.getValue() : ((int)this.fastTime.getValue());
    }
    
    private float getSwitchSpeed() {
        return this.isSlow ? this.slow.getValue() : ((float)this.fast.getValue());
    }
}
