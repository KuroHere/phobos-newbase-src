// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoreconnect;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class AutoReconnectData extends DefaultData<AutoReconnect>
{
    public AutoReconnectData(final AutoReconnect module) {
        super(module);
        this.register(module.delay, "After this delay in seconds passed you will be reconnected.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Automatically reconnects you after you got kicked.";
    }
}
