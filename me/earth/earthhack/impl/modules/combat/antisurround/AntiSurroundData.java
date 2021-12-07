// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antisurround;

import me.earth.earthhack.impl.util.helpers.blocks.data.*;
import me.earth.earthhack.api.setting.*;

final class AntiSurroundData extends ObbyListenerData<AntiSurround>
{
    public AntiSurroundData(final AntiSurround module) {
        super(module);
        this.register(module.normal, "Turning this off (and Async) allows you to use this module for PreCrystal only.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Breaks enemies Surrounds.";
    }
}
