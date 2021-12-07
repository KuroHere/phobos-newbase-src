// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.safety;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.modules.client.safety.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.*;

public class Safety extends Module
{
    public Safety() {
        super("Safety", Category.Client);
        this.register(new NumberSetting("MaxDamage", 4.0f, 0.0f, 36.0f));
        this.register(new BooleanSetting("BedCheck", false));
        this.register(new BooleanSetting("1.13+", false));
        this.register(new BooleanSetting("1.13-Entities", false));
        this.register(new EnumSetting("Updates", Update.Tick));
        this.register(new NumberSetting("Delay", 25, 0, 100));
        this.register(new BooleanSetting("2x1s", true));
        this.register(new BooleanSetting("2x2s", true));
        this.register(new BooleanSetting("Post-Calc", false));
        this.register(new BooleanSetting("Terrain", false));
        this.register(new BooleanSetting("Anvils", false));
        this.register(ICachedDamage.SHOULD_CACHE);
        this.setData(new SafetyData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return Managers.SAFETY.isSafe() ? "§aSafe" : "§cUnsafe";
    }
}
