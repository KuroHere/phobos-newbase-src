// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.reach;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class Reach extends Module
{
    protected final Setting<Float> reach;
    protected final Setting<Float> hitBox;
    
    public Reach() {
        super("Reach", Category.Player);
        this.reach = this.register(new NumberSetting("Add", 1.5f, 0.0f, 6.0f));
        this.hitBox = this.register(new NumberSetting("HitBox", 0.0f, 0.0f, 6.0f));
        this.listeners.add(new ListenerReach(this));
        final SimpleData data = new SimpleData(this, "Allows you to interact with blocks and entities outside your normal range.");
        data.register(this.reach, "Range in blocks that you want to add to your normal reach.");
        data.register(this.hitBox, "Makes entities hitboxes bigger.");
        this.setData(data);
    }
}
