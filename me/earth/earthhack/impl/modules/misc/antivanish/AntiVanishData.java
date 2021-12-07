// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antivanish;

import me.earth.earthhack.api.module.data.*;

final class AntiVanishData extends DefaultData<AntiVanish>
{
    public AntiVanishData(final AntiVanish module) {
        super(module);
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Detects players that go into vanish.";
    }
}
