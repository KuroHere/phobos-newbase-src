// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.render.data;

import me.earth.earthhack.impl.util.helpers.render.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

public abstract class ColorModuleData<T extends ColorModule> extends AbstractData<T>
{
    public ColorModuleData(final T module) {
        super(module);
        this.register(module.color, "The color to render with. An alpha value of 0 means no color at all.");
    }
}
