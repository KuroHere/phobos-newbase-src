// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.packets;

import net.minecraft.network.*;

public interface PayloadReader
{
    void read(final PacketBuffer p0);
    
    default PayloadReader compose(final PayloadReader reader) {
        return buffer -> {
            this.read(buffer);
            buffer.resetReaderIndex();
            reader.read(buffer);
        };
    }
}
