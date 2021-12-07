// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.autosprint;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class AutoSprintData extends DefaultData<AutoSprint>
{
    public AutoSprintData(final AutoSprint module) {
        super(module);
        this.register(module.mode, "-Rage will sprint into all directions.\n-Legit normal sprint but you don't have to press the button.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Sprints for you.";
    }
}
