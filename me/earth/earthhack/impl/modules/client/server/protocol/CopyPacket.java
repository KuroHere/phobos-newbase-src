//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol;

import net.minecraft.network.play.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;

public class CopyPacket implements Packet<INetHandlerPlayServer>, CustomPacket
{
    private final byte[] buffer;
    private final int ordinal;
    private final int offset;
    private final int id;
    
    public CopyPacket(final int id, final int ordinal, final byte[] buffer) {
        this(id, ordinal, buffer, 0);
    }
    
    public CopyPacket(final int id, final int ordinal, final byte[] buffer, final int offset) {
        this.id = id;
        this.ordinal = ordinal;
        this.buffer = buffer;
        this.offset = offset;
    }
    
    public int getId() throws Exception {
        return this.id;
    }
    
    public EnumConnectionState getState() {
        return EnumConnectionState.values()[this.ordinal];
    }
    
    public void readPacketData(final PacketBuffer buf) {
        throw new UnsupportedOperationException();
    }
    
    public void writePacketData(final PacketBuffer buf) {
        buf.writeBytes(this.buffer, this.offset, this.buffer.length - this.offset);
    }
    
    public void processPacket(final INetHandlerPlayServer handler) {
        throw new UnsupportedOperationException();
    }
}
