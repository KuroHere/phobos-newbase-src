// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.phase;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class PhaseData extends DefaultData<Phase>
{
    public PhaseData(final Phase module) {
        super(module);
        this.register(module.mode, "-Sand use doors/heads/sand to phase.\n-Climb goes down\n-Packet uses packets to phase\n-Normal just plain phase.");
        this.register(module.autoClip, "Tries to get you into a block when you enable this module.");
        this.register(module.blocks, "Modifier for the AutoClip.");
        this.register(module.distance, "Modifier for the distance you want to travel when using Phase-Mode-Normal.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Allows you to walk through blocks.";
    }
}
