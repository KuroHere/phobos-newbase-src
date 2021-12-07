// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.skeleton;

import me.earth.earthhack.api.module.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.setting.*;
import java.awt.*;
import me.earth.earthhack.api.module.util.*;
import java.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class Skeleton extends Module
{
    protected final Map<EntityPlayer, float[][]> rotations;
    public final Setting<Color> color;
    public final Setting<Color> friendColor;
    public final Setting<Color> targetColor;
    
    public Skeleton() {
        super("Skeleton", Category.Render);
        this.rotations = new HashMap<EntityPlayer, float[][]>();
        this.color = this.register(new ColorSetting("Color", new Color(255, 255, 255, 255)));
        this.friendColor = this.register(new ColorSetting("FriendColor", new Color(50, 255, 50, 255)));
        this.targetColor = this.register(new ColorSetting("TargetColor", new Color(255, 0, 0, 255)));
        this.listeners.add(new ListenerModel(this));
        this.listeners.add(new ListenerRender(this));
        this.setData(new SimpleData(this, "Spooky."));
    }
}
