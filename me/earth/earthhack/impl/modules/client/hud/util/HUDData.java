// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.hud.util;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.modules.client.hud.*;

public class HUDData extends DefaultData<HUD>
{
    public HUDData(final HUD hud) {
        super(hud);
    }
    
    @Override
    public int getColor() {
        return -13327873;
    }
    
    @Override
    public String getDescription() {
        return "Displays info like ping, tps or toggled modules on your screen.";
    }
}
