// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc.io;

import java.io.*;

@FunctionalInterface
public interface IOConsumer<T>
{
    void accept(final T p0) throws IOException;
}
