// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.trails;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.animation.*;
import java.util.*;
import me.earth.earthhack.impl.modules.render.breadcrumbs.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.awt.*;
import java.util.concurrent.*;

public class Trails extends Module
{
    protected final Setting<Boolean> arrows;
    protected final Setting<Boolean> pearls;
    protected final Setting<Boolean> snowballs;
    protected final Setting<Integer> time;
    protected final ColorSetting color;
    protected final Setting<Float> width;
    protected Map<Integer, TimeAnimation> ids;
    protected Map<Integer, List<Trace>> traceLists;
    protected Map<Integer, Trace> traces;
    
    public Trails() {
        super("Trails", Category.Render);
        this.arrows = this.register(new BooleanSetting("Arrows", false));
        this.pearls = this.register(new BooleanSetting("Pearls", false));
        this.snowballs = this.register(new BooleanSetting("Snowballs", false));
        this.time = this.register(new NumberSetting("Time", 1, 1, 10));
        this.color = this.register(new ColorSetting("Color", new Color(255, 0, 0, 255)));
        this.width = this.register(new NumberSetting("Width", 1.6f, 0.1f, 10.0f));
        this.ids = new ConcurrentHashMap<Integer, TimeAnimation>();
        this.traceLists = new ConcurrentHashMap<Integer, List<Trace>>();
        this.traces = new ConcurrentHashMap<Integer, Trace>();
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerSpawnObject(this));
        this.listeners.add(new ListenerDestroyEntities(this));
    }
    
    @Override
    protected void onEnable() {
        this.ids = new ConcurrentHashMap<Integer, TimeAnimation>();
        this.traces = new ConcurrentHashMap<Integer, Trace>();
        this.traceLists = new ConcurrentHashMap<Integer, List<Trace>>();
    }
}
