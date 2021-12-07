// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.crystalscale;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;
import me.earth.earthhack.impl.util.animation.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;

public class CrystalScale extends Module
{
    public final Setting<Float> scale;
    public final Setting<Boolean> animate;
    public final Setting<Integer> time;
    public final Map<Integer, TimeAnimation> scaleMap;
    
    public CrystalScale() {
        super("CrystalScale", Category.Render);
        this.scale = this.register(new NumberSetting("Scale", 1.0f, 0.1f, 2.0f));
        this.animate = this.register(new BooleanSetting("Animate", false));
        this.time = this.register(new NumberSetting("AnimationTime", 200, 1, 500));
        this.scaleMap = new ConcurrentHashMap<Integer, TimeAnimation>();
        this.listeners.add(new ListenerDestroyEntities(this));
        this.listeners.add(new ListenerSpawnObject(this));
    }
}
