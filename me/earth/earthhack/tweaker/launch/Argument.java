// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.tweaker.launch;

import me.earth.earthhack.api.config.*;

public interface Argument<T> extends Jsonable
{
    T getValue();
}
