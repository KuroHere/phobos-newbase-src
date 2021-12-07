// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.velocity;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.math.*;

public class Velocity extends Module
{
    protected final Setting<Boolean> knockBack;
    protected final Setting<Float> horizontal;
    protected final Setting<Float> vertical;
    protected final Setting<Boolean> noPush;
    protected final Setting<Boolean> explosions;
    protected final Setting<Boolean> bobbers;
    protected final Setting<Boolean> water;
    protected final Setting<Boolean> blocks;
    protected final Setting<Boolean> shulkers;
    
    public Velocity() {
        super("Velocity", Category.Movement);
        this.knockBack = this.register(new BooleanSetting("KnockBack", true));
        this.horizontal = this.register(new NumberSetting("Horizontal", 0.0f, 0.0f, 100.0f));
        this.vertical = this.register(new NumberSetting("Vertical", 0.0f, 0.0f, 100.0f));
        this.noPush = this.register(new BooleanSetting("NoPush", true));
        this.explosions = this.register(new BooleanSetting("Explosions", true));
        this.bobbers = this.register(new BooleanSetting("Bobbers", true));
        this.water = this.register(new BooleanSetting("Water", false));
        this.blocks = this.register(new BooleanSetting("Blocks", false));
        this.shulkers = this.register(new BooleanSetting("Shulkers", false));
        this.listeners.add(new ListenerBlockPush(this));
        this.listeners.add(new ListenerEntityVelocity(this));
        this.listeners.add(new ListenerWaterPush(this));
        this.listeners.add(new ListenerExplosion(this));
        this.listeners.add(new ListenerBobber(this));
        this.setData(new VelocityData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return "H" + MathUtil.round(this.horizontal.getValue(), 1) + "%V" + MathUtil.round(this.vertical.getValue(), 1) + "%";
    }
}
