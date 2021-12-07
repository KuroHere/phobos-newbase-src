// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.crystalchams;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.render.handchams.modes.*;
import java.awt.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;

public class CrystalChams extends Module
{
    public final Setting<ChamsMode> mode;
    public final Setting<Boolean> chams;
    public final Setting<Boolean> throughWalls;
    public final Setting<Boolean> wireframe;
    public final Setting<Boolean> wireWalls;
    public final Setting<Color> color;
    public final Setting<Color> wireFrameColor;
    
    public CrystalChams() {
        super("CrystalChams", Category.Render);
        this.mode = this.register(new EnumSetting("Mode", ChamsMode.Normal));
        this.chams = this.register(new BooleanSetting("Chams", false));
        this.throughWalls = this.register(new BooleanSetting("ThroughWalls", false));
        this.wireframe = this.register(new BooleanSetting("Wireframe", false));
        this.wireWalls = this.register(new BooleanSetting("WireThroughWalls", false));
        this.color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 255)));
        this.wireFrameColor = this.register(new ColorSetting("WireframeColor", new Color(255, 255, 255, 255)));
    }
}
