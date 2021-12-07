// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.skinblink;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class SkinBlinkData extends DefaultData<SkinBlink>
{
    public SkinBlinkData(final SkinBlink module) {
        super(module);
        this.register(module.delay, "The interval in milliseconds your skin will blink in.");
        this.register(module.random, "Randomized the delay.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Toggles the layers of your skin.";
    }
}
