// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc.io;

import java.io.*;

@FunctionalInterface
public interface BiIOConsumer<T, U>
{
    void accept(final T p0, final U p1) throws IOException;
}
