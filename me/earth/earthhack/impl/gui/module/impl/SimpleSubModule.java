// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.module.impl;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.gui.module.*;
import me.earth.earthhack.api.module.util.*;

public abstract class SimpleSubModule<T extends Module> extends Module implements SubModule<T>
{
    private final T parent;
    
    public SimpleSubModule(final T parent, final String name, final Category category) {
        super(name, category);
        this.parent = parent;
    }
    
    @Override
    public T getParent() {
        return this.parent;
    }
}
