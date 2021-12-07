// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.blocks.data;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.api.setting.*;

public class ObbyListenerData<T extends ObbyListenerModule<?>> extends ObbyData<T>
{
    public ObbyListenerData(final T module) {
        super(module);
        this.register(module.confirm, "Time from placing a block until it's confirmed by the server.");
    }
}
