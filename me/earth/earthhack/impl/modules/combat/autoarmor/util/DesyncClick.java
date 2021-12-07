// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autoarmor.util;

import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;

public class DesyncClick extends TimeStamp
{
    private final WindowClick click;
    
    public DesyncClick(final WindowClick click) {
        this.click = click;
    }
    
    public WindowClick getClick() {
        return this.click;
    }
}
