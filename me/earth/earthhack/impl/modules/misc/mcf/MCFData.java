// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.mcf;

import me.earth.earthhack.api.module.data.*;

final class MCFData extends DefaultData<MCF>
{
    public MCFData(final MCF mcf) {
        super(mcf);
    }
    
    @Override
    public int getColor() {
        return -7077976;
    }
    
    @Override
    public String getDescription() {
        return "Middleclick on players to friend/unfriend them.";
    }
}
