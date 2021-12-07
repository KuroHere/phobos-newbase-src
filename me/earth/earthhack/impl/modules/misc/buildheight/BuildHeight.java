// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.buildheight;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class BuildHeight extends Module
{
    protected final Setting<Integer> height;
    protected final Setting<Boolean> crystals;
    
    public BuildHeight() {
        super("BuildHeight", Category.Misc);
        this.height = this.register(new NumberSetting("Height", 255, 0, 420));
        this.crystals = this.register(new BooleanSetting("CrystalsOnly", false));
        this.listeners.add(new ListenerPlaceBlock(this));
        this.setData(new SimpleData(this, "Allows you to place crystals at buildheight."));
    }
}
