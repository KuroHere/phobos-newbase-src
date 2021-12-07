// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.module.data;

import me.earth.earthhack.api.setting.*;
import java.util.*;
import me.earth.earthhack.api.config.preset.*;

public interface ModuleData
{
    int getColor();
    
    String getDescription();
    
    Map<Setting<?>, String> settingDescriptions();
    
    Collection<ModulePreset> getPresets();
}
