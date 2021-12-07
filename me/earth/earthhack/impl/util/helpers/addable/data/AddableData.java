// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.addable.data;

import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

public abstract class AddableData<T extends AddableModule> extends AbstractData<T>
{
    public AddableData(final T module) {
        super(module);
        this.register(module.listType, "-Whitelist: All added Elements are valid.\n-Blacklist everything added won't be used.");
    }
}
