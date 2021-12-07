// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.handchams;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.render.handchams.modes.*;
import java.awt.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;

public class HandChams extends Module
{
    public final Setting<ChamsMode> mode;
    public final Setting<Boolean> chams;
    public final Setting<Boolean> wireframe;
    public final Setting<Color> color;
    public final Setting<Color> wireFrameColor;
    
    public HandChams() {
        super("HandChams", Category.Render);
        this.mode = this.register(new EnumSetting("Mode", ChamsMode.Normal));
        this.chams = this.register(new BooleanSetting("Chams", true));
        this.wireframe = this.register(new BooleanSetting("Wireframe", true));
        this.color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 255)));
        this.wireFrameColor = this.register(new ColorSetting("WireframeColor", new Color(255, 255, 255, 255)));
    }
}
