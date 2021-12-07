//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.packets;

import java.util.*;
import java.util.concurrent.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.*;
import net.minecraft.network.*;

public class PayloadManager
{
    private final Map<Short, PayloadReader> readers;
    
    public PayloadManager() {
        this.readers = new ConcurrentHashMap<Short, PayloadReader>();
    }
    
    public void onPacket(final SPacketCustomPayload packet) {
        final PacketBuffer buffer = packet.getBufferData();
        short id;
        try {
            id = buffer.readShort();
        }
        catch (Exception e) {
            Earthhack.getLogger().error("Could not read id from PayloadPacket.");
            return;
        }
        final PayloadReader reader = this.readers.get(id);
        if (reader == null) {
            Earthhack.getLogger().error("Couldn't find PayloadReader for ID: " + id);
            return;
        }
        try {
            reader.read(buffer);
        }
        catch (Throwable e2) {
            e2.printStackTrace();
        }
    }
    
    public void register(final short id, final PayloadReader reader) {
        this.readers.compute(id, (i, v) -> (v == null) ? reader : v.compose(reader));
    }
}
