// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sSafety;

import me.earth.earthhack.impl.gui.module.impl.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.modules.client.safety.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class ServerSafety extends SimpleSubModule<PingBypass>
{
    public ServerSafety(final PingBypass pingBypass) {
        super(pingBypass, "S-Safety", Category.Client);
        this.register(new NumberSetting("MaxDamage", 4.0f, 0.0f, 36.0f));
        this.register(new BooleanSetting("BedCheck", false));
        this.register(new BooleanSetting("1.13+", false));
        this.register(new BooleanSetting("SafetyPlayer", false));
        this.register(new EnumSetting("Updates", Update.Tick));
        this.register(new NumberSetting("Delay", 25, 0, 100));
        this.setData(new ServerSafetyData(this));
    }
}
