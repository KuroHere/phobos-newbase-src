// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.ncptweaks;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;

public class NCPTweaks extends Module
{
    protected final Setting<Boolean> eating;
    protected final Setting<Boolean> moving;
    protected final Setting<Boolean> packet;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> resetNCP;
    protected final Setting<Boolean> sneakEat;
    protected final Setting<Boolean> stopSpeed;
    protected boolean speedStopped;
    
    public NCPTweaks() {
        super("NCPTweaks", Category.Player);
        this.eating = this.register(new BooleanSetting("Eating", true));
        this.moving = this.register(new BooleanSetting("Moving", true));
        this.packet = this.register(new BooleanSetting("Packet", true));
        this.delay = this.register(new NumberSetting("Delay", 50, 0, 500));
        this.resetNCP = this.register(new BooleanSetting("Reset-NCP", false));
        this.sneakEat = this.register(new BooleanSetting("Sneak-Eat", false));
        this.stopSpeed = this.register(new BooleanSetting("Stop-Speed", false));
        this.listeners.add(new ListenerWindowClick(this));
        this.listeners.add(new ListenerInput(this));
    }
    
    @Override
    protected void onDisable() {
        this.speedStopped = false;
    }
    
    public boolean isSpeedStopped() {
        return this.stopSpeed.getValue() && this.speedStopped;
    }
}
