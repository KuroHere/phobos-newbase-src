//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.holefiller;

import me.earth.earthhack.impl.managers.thread.holes.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;

public class HoleFiller extends ObbyListenerModule<ListenerObby> implements HoleObserver, IHoleManager
{
    protected final Setting<Double> range;
    protected final Setting<Integer> disable;
    protected final Setting<Boolean> longHoles;
    protected final Setting<Boolean> bigHoles;
    protected final Setting<Integer> calcDelay;
    protected final Setting<Boolean> requireTarget;
    protected final Setting<Double> targetRange;
    protected final Setting<Double> targetDistance;
    protected final Setting<Boolean> safety;
    protected final Setting<Double> minSelf;
    protected final Setting<Boolean> waitForHoleLeave;
    protected List<BlockPos> safes;
    protected List<BlockPos> unsafes;
    protected List<BlockPos> longs;
    protected List<BlockPos> bigs;
    protected final StopWatch disableTimer;
    protected final StopWatch calcTimer;
    protected EntityPlayer target;
    protected boolean waiting;
    
    public HoleFiller() {
        super("HoleFiller", Category.Combat);
        this.range = this.register(new NumberSetting("Range", 5.25, 0.0, 6.0));
        this.disable = this.register(new NumberSetting("Disable", 250, 0, 1000));
        this.longHoles = this.register(new BooleanSetting("2x1s", false));
        this.bigHoles = this.register(new BooleanSetting("2x2s", false));
        this.calcDelay = this.register(new NumberSetting("CalcDelay", 0, 0, 500));
        this.requireTarget = this.register(new BooleanSetting("RequireTarget", false));
        this.targetRange = this.register(new NumberSetting("Target-Range", 6.0, 0.0, 12.0));
        this.targetDistance = this.register(new NumberSetting("Target-Block", 3.0, 0.0, 12.0));
        this.safety = this.register(new BooleanSetting("Safety", false));
        this.minSelf = this.register(new NumberSetting("Min-Self", 2.0, 0.0, 6.0));
        this.waitForHoleLeave = this.register(new BooleanSetting("WaitForHoleLeave", false));
        this.safes = Collections.emptyList();
        this.unsafes = Collections.emptyList();
        this.longs = Collections.emptyList();
        this.bigs = Collections.emptyList();
        this.disableTimer = new StopWatch();
        this.calcTimer = new StopWatch();
        this.listeners.clear();
        this.listeners.add(this.listener);
        this.setData(new HoleFillerData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.target != null) {
            return (this.waiting ? "§c" : "") + this.target.getName();
        }
        return null;
    }
    
    @Override
    protected void onEnable() {
        super.onEnable();
        this.disableTimer.reset();
        this.calcTimer.setTime(0L);
        this.target = null;
        this.waiting = false;
    }
    
    @Override
    public double getRange() {
        return this.range.getValue();
    }
    
    @Override
    public int getSafeHoles() {
        return 20;
    }
    
    @Override
    public int getUnsafeHoles() {
        return 20;
    }
    
    @Override
    public int get2x1Holes() {
        return this.longHoles.getValue() ? 4 : 0;
    }
    
    @Override
    public int get2x2Holes() {
        return ((boolean)this.bigHoles.getValue()) ? 1 : 0;
    }
    
    @Override
    public void setSafe(final List<BlockPos> safe) {
        this.safes = safe;
    }
    
    @Override
    public void setUnsafe(final List<BlockPos> unsafe) {
        this.unsafes = unsafe;
    }
    
    @Override
    public void setLongHoles(final List<BlockPos> longHoles) {
        this.longs = longHoles;
    }
    
    @Override
    public void setBigHoles(final List<BlockPos> bigHoles) {
        this.bigs = bigHoles;
    }
    
    @Override
    public void setFinished() {
    }
    
    @Override
    protected ListenerObby createListener() {
        return new ListenerObby(this);
    }
}
