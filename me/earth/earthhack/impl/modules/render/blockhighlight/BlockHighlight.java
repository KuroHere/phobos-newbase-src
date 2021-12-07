// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.blockhighlight;

import me.earth.earthhack.impl.util.helpers.render.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import java.awt.*;

public class BlockHighlight extends BlockESPModule
{
    protected final Setting<Boolean> distance;
    protected final Setting<Boolean> hitVec;
    protected String current;
    
    public BlockHighlight() {
        super("BlockHighlight", Category.Render);
        this.distance = this.register(new BooleanSetting("Distance", false));
        this.hitVec = this.register(new BooleanSetting("HitVec", false));
        this.listeners.add(new ListenerRender(this));
        this.listeners.add(new ListenerUpdate(this));
        this.listeners.add(new ListenerMotion(this));
        this.setData(new SimpleData(this, "Highlights the block that you are currently looking at."));
        this.unregister(this.height);
        this.color.setValue(new Color(0, 0, 0, 0));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.current;
    }
}
