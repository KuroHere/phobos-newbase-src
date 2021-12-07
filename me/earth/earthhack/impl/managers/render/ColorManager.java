// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.render;

import me.earth.earthhack.api.setting.*;
import java.awt.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.*;

public class ColorManager extends SubscriberImpl
{
    private final Setting<Integer> speed;
    private final Setting<Color> color;
    private Color universal;
    private float hue;
    
    public ColorManager() {
        this.speed = new NumberSetting<Integer>("RainbowSpeed", 50, 0, 100);
        this.color = new ColorSetting("Color", new Color(127, 66, 186));
        this.universal = new Color(255, 255, 255, 255);
        this.listeners.add(new EventListener<TickEvent>(TickEvent.class) {
            @Override
            public void invoke(final TickEvent event) {
                ColorManager.this.update();
            }
        });
    }
    
    private void update() {
        if (this.speed.getValue() == 0) {
            return;
        }
        this.hue = System.currentTimeMillis() % (360 * this.speed.getValue()) / (360.0f * this.speed.getValue());
    }
    
    public void setUniversal(final Color color) {
        this.universal = color;
    }
    
    public Color getUniversal() {
        return this.universal;
    }
    
    public float getHue() {
        return this.hue;
    }
    
    public float getHueByPosition(final double pos) {
        return (float)(this.hue - pos * 0.0010000000474974513);
    }
    
    public Setting<Integer> getRainbowSpeed() {
        return this.speed;
    }
    
    public Setting<Color> getColorSetting() {
        return this.color;
    }
}
