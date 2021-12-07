// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.main;

import me.earth.earthhack.impl.modules.client.server.api.*;

public class SystemShutdownHandler implements IShutDownHandler
{
    @Override
    public void disable(final String message) {
        System.out.println(message);
        System.exit(0);
    }
}
