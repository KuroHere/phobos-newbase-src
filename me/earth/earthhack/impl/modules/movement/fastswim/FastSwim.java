// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.fastswim;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class FastSwim extends Module
{
    protected final Setting<Double> vWater;
    protected final Setting<Double> downWater;
    protected final Setting<Double> hWater;
    protected final Setting<Double> vLava;
    protected final Setting<Double> downLava;
    protected final Setting<Double> hLava;
    protected final Setting<Boolean> strafe;
    protected final Setting<Boolean> fall;
    protected final Setting<Boolean> accelerate;
    protected final Setting<Double> accelerateFactor;
    protected double lavaSpeed;
    protected double waterSpeed;
    
    public FastSwim() {
        super("FastSwim", Category.Movement);
        this.vWater = this.register(new NumberSetting("V-Water", 1.0, 0.1, 20.0));
        this.downWater = this.register(new NumberSetting("Down-Water", 1.0, 0.1, 20.0));
        this.hWater = this.register(new NumberSetting("H-Water", 1.0, 0.1, 20.0));
        this.vLava = this.register(new NumberSetting("Up-Lava", 1.0, 0.1, 20.0));
        this.downLava = this.register(new NumberSetting("Down-Lava", 1.0, 0.1, 20.0));
        this.hLava = this.register(new NumberSetting("H-Lava", 1.0, 0.1, 20.0));
        this.strafe = this.register(new BooleanSetting("Strafe", false));
        this.fall = this.register(new BooleanSetting("Fall", false));
        this.accelerate = this.register(new BooleanSetting("Accelerate", false));
        this.accelerateFactor = this.register(new NumberSetting("Factor", 1.1, 0.1, 20.0));
        this.listeners.add(new ListenerMove(this));
        this.setData(new FastSwimData(this));
    }
}
