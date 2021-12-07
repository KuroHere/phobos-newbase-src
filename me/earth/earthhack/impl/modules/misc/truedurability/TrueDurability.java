// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.truedurability;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;

public class TrueDurability extends Module
{
    public TrueDurability() {
        super("TrueDurability", Category.Player);
        this.setData(new SimpleData(this, "Displays the true durability of unbreakables."));
    }
}
