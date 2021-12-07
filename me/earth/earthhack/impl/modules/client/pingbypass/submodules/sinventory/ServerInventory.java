// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sinventory;

import me.earth.earthhack.impl.gui.module.impl.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class ServerInventory extends SimpleSubModule<PingBypass>
{
    public ServerInventory(final PingBypass pingBypass) {
        super(pingBypass, "S-Inventory", Category.Client);
        this.register(new NumberSetting("Delay", 5, 1, 60));
        this.setData(new ServerInventoryData(this));
    }
}
