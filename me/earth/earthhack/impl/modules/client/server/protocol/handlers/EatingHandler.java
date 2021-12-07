// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol.handlers;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.autoeat.*;
import me.earth.earthhack.impl.modules.client.server.api.*;
import java.io.*;
import me.earth.earthhack.impl.modules.*;

public class EatingHandler implements IPacketHandler
{
    private static final ModuleCache<AutoEat> AUTO_EAT;
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) throws IOException {
        if (bytes[0] != 0) {
            EatingHandler.AUTO_EAT.computeIfPresent(a -> a.setServer(true));
        }
        else {
            EatingHandler.AUTO_EAT.computeIfPresent(a -> a.setServer(false));
        }
    }
    
    static {
        AUTO_EAT = Caches.getModule(AutoEat.class);
    }
}
