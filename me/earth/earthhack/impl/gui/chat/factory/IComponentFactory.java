// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.factory;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.gui.chat.components.*;

public interface IComponentFactory<E, S extends Setting<E>>
{
    SettingComponent<E, S> create(final S p0);
}
