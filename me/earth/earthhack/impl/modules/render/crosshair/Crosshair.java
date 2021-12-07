// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.crosshair;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.render.crosshair.mode.*;
import java.awt.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class Crosshair extends Module
{
    protected final Setting<Boolean> indicator;
    protected final Setting<Boolean> outline;
    protected final Setting<GapMode> gapMode;
    protected final Setting<Color> color;
    protected final Setting<Color> outlineColor;
    protected final Setting<Float> length;
    protected final Setting<Float> gapSize;
    protected final Setting<Float> width;
    
    public Crosshair() {
        super("Crosshair", Category.Render);
        this.indicator = this.register(new BooleanSetting("Attack-Indicator", true));
        this.outline = this.register(new BooleanSetting("Outline", true));
        this.gapMode = this.register(new EnumSetting("Gap-Mode", GapMode.NORMAL));
        this.color = this.register(new ColorSetting("Color", new Color(190, 60, 190)));
        this.outlineColor = this.register(new ColorSetting("Outline-Color", new Color(0, 0, 0)));
        this.length = this.register(new NumberSetting("Length", 5.5f, 0.5f, 50.0f));
        this.gapSize = this.register(new NumberSetting("Gap-Size", 2.0f, 0.5f, 20.0f));
        this.width = this.register(new NumberSetting("Width", 0.5f, 0.1f, 10.0f));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new EventListener<CrosshairEvent>(CrosshairEvent.class) {
            @Override
            public void invoke(final CrosshairEvent event) {
                event.setCancelled(true);
            }
        });
        final SimpleData data = new SimpleData(this, "Gives you a custom crosshair.");
        this.setData(data);
    }
}
