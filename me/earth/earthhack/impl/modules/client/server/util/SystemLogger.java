// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.util;

import me.earth.earthhack.impl.modules.client.server.api.*;

public class SystemLogger implements ILogger
{
    @Override
    public void log(final String message) {
        System.out.println(message);
    }
}
