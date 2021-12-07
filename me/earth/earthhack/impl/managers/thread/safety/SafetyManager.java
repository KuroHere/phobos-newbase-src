//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.safety;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.util.interfaces.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.safety.*;
import me.earth.earthhack.impl.modules.client.safety.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.thread.*;
import java.util.*;

public class SafetyManager extends SubscriberImpl implements Globals
{
    private final AtomicBoolean safe;
    protected final SettingCache<Boolean, BooleanSetting, Safety> newV;
    protected final SettingCache<Boolean, BooleanSetting, Safety> newVEntities;
    protected final SettingCache<Boolean, BooleanSetting, Safety> beds;
    protected final SettingCache<Float, NumberSetting<Float>, Safety> damage;
    protected final SettingCache<Integer, NumberSetting<Integer>, Safety> d;
    protected final SettingCache<Update, EnumSetting<Update>, Safety> mode;
    protected final SettingCache<Boolean, BooleanSetting, Safety> longs;
    protected final SettingCache<Boolean, BooleanSetting, Safety> big;
    protected final SettingCache<Boolean, BooleanSetting, Safety> post;
    protected final SettingCache<Boolean, BooleanSetting, Safety> anvils;
    protected final SettingCache<Boolean, BooleanSetting, Safety> terrain;
    
    public SafetyManager() {
        this.safe = new AtomicBoolean(false);
        this.newV = Caches.getSetting(Safety.class, BooleanSetting.class, "1.13+", false);
        this.newVEntities = Caches.getSetting(Safety.class, BooleanSetting.class, "1.13-Entities", false);
        this.beds = Caches.getSetting(Safety.class, BooleanSetting.class, "BedCheck", false);
        this.damage = Caches.getSetting(Safety.class, Setting.class, "MaxDamage", 4.0f);
        this.d = Caches.getSetting(Safety.class, Setting.class, "Delay", 25);
        this.mode = Caches.getSetting(Safety.class, Setting.class, "Updates", Update.Tick);
        this.longs = Caches.getSetting(Safety.class, BooleanSetting.class, "2x1s", false);
        this.big = Caches.getSetting(Safety.class, BooleanSetting.class, "2x2s", false);
        this.post = Caches.getSetting(Safety.class, BooleanSetting.class, "Post-Calc", false);
        this.anvils = Caches.getSetting(Safety.class, BooleanSetting.class, "Anvils", false);
        this.terrain = Caches.getSetting(Safety.class, BooleanSetting.class, "Terrain", false);
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerGameLoop(this));
        this.listeners.add(new ListenerSpawnObject(this));
        this.listeners.add(new ListenerMotionUpdate(this));
    }
    
    public boolean isSafe() {
        return this.safe.get();
    }
    
    public void setSafe(final boolean safeIn) {
        this.safe.set(safeIn);
    }
    
    protected void runThread() {
        if (SafetyManager.mc.player != null && SafetyManager.mc.world != null) {
            final List<Entity> crystals = new ArrayList<Entity>(SafetyManager.mc.world.loadedEntityList);
            final SafetyRunnable runnable = new SafetyRunnable(this, crystals, this.newVEntities.getValue(), this.newV.getValue(), this.beds.getValue(), this.damage.getValue(), this.longs.getValue(), this.big.getValue(), this.anvils.getValue(), this.terrain.getValue());
            Managers.THREAD.submit(runnable);
        }
    }
}
