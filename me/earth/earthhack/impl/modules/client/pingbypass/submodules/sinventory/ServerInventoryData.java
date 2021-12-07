// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sinventory;

import me.earth.earthhack.api.module.data.*;

final class ServerInventoryData extends DefaultData<ServerInventory>
{
    public ServerInventoryData(final ServerInventory module) {
        super(module);
        this.register("Delay", "The Delay in seconds to resync your Inventory with.");
    }
    
    @Override
    public int getColor() {
        return -65536;
    }
    
    @Override
    public String getDescription() {
        return "Resyncs your Inventory with the PingBypass.";
    }
}
