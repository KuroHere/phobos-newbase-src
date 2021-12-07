// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antiaim;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class AntiAimData extends DefaultData<AntiAim>
{
    public AntiAimData(final AntiAim module) {
        super(module);
        this.register(module.strict, "Doesn't rotate when you place/attack.");
        this.register(module.skip, "Skips every Nth tick, off if value is 1.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "";
    }
}
