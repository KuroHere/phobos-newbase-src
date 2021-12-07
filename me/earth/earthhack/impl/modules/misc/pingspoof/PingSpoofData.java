// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.pingspoof;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class PingSpoofData extends DefaultData<PingSpoof>
{
    public PingSpoofData(final PingSpoof module) {
        super(module);
        this.register(module.delay, "By how much you want to spoof your ping.");
        this.register(module.keepAlive, "Default PingSpoof.");
        this.register(module.transactions, "Crystalpvp.cc PingSpoof bypass.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Allows you to spoof your ping.";
    }
}
