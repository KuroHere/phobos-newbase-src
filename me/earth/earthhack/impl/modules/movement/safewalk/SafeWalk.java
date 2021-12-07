// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.safewalk;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class SafeWalk extends Module
{
    public SafeWalk() {
        super("SafeWalk", Category.Movement);
        this.listeners.add(new ListenerMove(this));
        this.setData(new SimpleData(this, "Never fall down edges."));
    }
}
