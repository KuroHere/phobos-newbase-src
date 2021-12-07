// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tpssync;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class TpsSync extends Module
{
    public TpsSync() {
        super("TpsSync", Category.Player);
        this.register(new BooleanSetting("Attack", false));
        this.register(new BooleanSetting("Mine", false));
        this.setData(new TpsSyncData(this));
    }
}
