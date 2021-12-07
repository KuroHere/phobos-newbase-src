// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass;

import me.earth.earthhack.api.module.data.*;

final class PingBypassData extends DefaultData<PingBypass>
{
    protected PingBypassData(final PingBypass module) {
        super(module);
        this.register("Port", "The port of the PingBypass proxy you want to connect to.");
        this.register("Pings", "Delay in seconds for sending packets that determine your ping to the PingBypass proxy.");
        this.register("NoRender", "Will make the PingBypass Proxy not render anything to decrease the workload.");
        this.register("IP", "The IP of the PingBypass proxy that you want to connect to.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "This module manages your PingBypass connection.";
    }
}
