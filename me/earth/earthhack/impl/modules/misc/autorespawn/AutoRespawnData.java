// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autorespawn;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class AutoRespawnData extends DefaultData<AutoRespawn>
{
    public AutoRespawnData(final AutoRespawn module) {
        super(module);
        this.register(module.coords, "Displays the coordinates of your death in chat.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Automatically respawns you after you died.";
    }
}
