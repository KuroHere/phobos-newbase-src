//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.network;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class WrappedPacket implements Packet<INetHandlerPlayServer>, CustomPacket
{
    private final Packet<INetHandlerPlayServer> wrapped;
    
    public WrappedPacket(final Packet<INetHandlerPlayServer> wrapped) {
        this.wrapped = wrapped;
    }
    
    public int getId() throws Exception {
        return this.getState().getPacketId(EnumPacketDirection.SERVERBOUND, (Packet)this.wrapped);
    }
    
    public EnumConnectionState getState() {
        return EnumConnectionState.getFromPacket((Packet)this.wrapped);
    }
    
    public void readPacketData(final PacketBuffer buf) {
        throw new UnsupportedOperationException();
    }
    
    public void writePacketData(final PacketBuffer buf) throws IOException {
        this.wrapped.writePacketData(buf);
    }
    
    public void processPacket(final INetHandlerPlayServer handler) {
        throw new UnsupportedOperationException();
    }
}
