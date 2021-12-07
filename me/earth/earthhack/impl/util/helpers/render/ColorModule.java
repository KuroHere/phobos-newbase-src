// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.render;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.util.*;
import java.awt.*;

public class ColorModule extends Module
{
    public final ColorSetting color;
    
    public ColorModule(final String name, final Category category) {
        super(name, category);
        this.color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 240)));
    }
}
