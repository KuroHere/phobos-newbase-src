// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol;

import me.earth.earthhack.impl.modules.client.server.api.*;

public class SimplePacket implements IPacket
{
    private final byte[] buffer;
    private final int id;
    
    public SimplePacket(final int id, final byte[] buffer) {
        this.id = id;
        this.buffer = buffer;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public byte[] getBuffer() {
        return this.buffer;
    }
}
