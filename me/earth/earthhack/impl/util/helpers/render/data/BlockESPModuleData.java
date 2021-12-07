// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.render.data;

import me.earth.earthhack.impl.util.helpers.render.*;
import me.earth.earthhack.api.setting.*;

public abstract class BlockESPModuleData<T extends BlockESPModule> extends ColorModuleData<T>
{
    public BlockESPModuleData(final T module) {
        super(module);
        this.register(module.height, "The height to render BlockESPs with. A value of 0 results in a flat ESP.");
        this.register(module.lineWidth, "The width of the lines to draw the outline of the ESP with.");
        this.register(module.outline, "The Color to draw the outline with. An alpha value of 0 means no outline at all.");
    }
}
