// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.breadcrumbs;

import me.earth.earthhack.impl.util.helpers.render.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.render.breadcrumbs.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import java.awt.*;

public class BreadCrumbs extends ColorModule
{
    public static final Vec3d ORIGIN;
    protected final Setting<Boolean> render;
    protected final Setting<Integer> delay;
    protected final Setting<Float> width;
    protected final Setting<Integer> fadeDelay;
    protected final Setting<Boolean> clearD;
    protected final Setting<Boolean> clearL;
    protected final Setting<Boolean> fade;
    protected final Setting<Boolean> players;
    protected final StopWatch timer;
    protected final List<Trace> positions;
    protected Trace trace;
    
    public BreadCrumbs() {
        super("BreadCrumbs", Category.Render);
        this.render = this.register(new BooleanSetting("Render", true));
        this.delay = this.register(new NumberSetting("Delay", 0, 0, 10000));
        this.width = this.register(new NumberSetting("Width", 1.6f, 0.1f, 10.0f));
        this.fadeDelay = this.register(new NumberSetting("Fade-Delay", 2000, 0, 10000));
        this.clearD = this.register(new BooleanSetting("Death-Clear", true));
        this.clearL = this.register(new BooleanSetting("Logout-Clear", true));
        this.fade = this.register(new BooleanSetting("Fade", false));
        this.players = this.register(new BooleanSetting("Players", false));
        this.timer = new StopWatch();
        this.positions = new ArrayList<Trace>();
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerLogout(this));
        this.listeners.add(new ListenerDeath(this));
        final SimpleData data = new SimpleData(this, "Shows where you came from.");
        data.register(this.color, "The color the path will be rendered in.");
        data.register(this.render, "If the path should be rendered.");
        data.register(this.delay, "Intervals in which the BreadCrumbs aren't drawn.");
        data.register(this.fadeDelay, "Delay at which the breadcrumb fades away.");
        data.register(this.width, "Width of the rendered path.");
        data.register(this.clearD, "Clears the path when you die.");
        data.register(this.clearL, "Clears the path when you disconnect from the server.");
        data.register(this.fade, "Makes the breadcrumb fade away.");
        this.setData(data);
        this.color.setValue(new Color(255, 0, 0, 125));
    }
    
    @Override
    protected void onDisable() {
        this.positions.clear();
        this.trace = null;
    }
    
    static {
        ORIGIN = new Vec3d(8.0, 64.0, 8.0);
    }
}
