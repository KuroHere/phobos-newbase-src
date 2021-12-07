// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.multitask;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class MultiTask extends Module
{
    public MultiTask() {
        super("MultiTask", Category.Player);
        this.setData(new SimpleData(this, "Allows you to eat while mining for example."));
    }
}
