// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.cleaner;

import me.earth.earthhack.api.setting.*;
import java.util.*;

public class SettingMap
{
    private final Setting<Integer> setting;
    private final Map<Integer, Integer> map;
    
    public SettingMap(final Setting<Integer> setting, final Map<Integer, Integer> map) {
        this.setting = setting;
        this.map = map;
    }
    
    public Map<Integer, Integer> getMap() {
        return this.map;
    }
    
    public Setting<Integer> getSetting() {
        return this.setting;
    }
}
