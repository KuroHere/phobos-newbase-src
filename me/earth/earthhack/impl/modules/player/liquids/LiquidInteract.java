// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.liquids;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class LiquidInteract extends Module
{
    public LiquidInteract() {
        super("LiquidInteract", Category.Player);
        this.setData(new SimpleData(this, "Allows you to place on liquids"));
    }
}
