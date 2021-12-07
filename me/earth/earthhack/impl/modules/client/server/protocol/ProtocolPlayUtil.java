//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol;

import net.minecraft.entity.player.*;
import java.nio.*;
import me.earth.earthhack.impl.modules.client.server.api.*;
import java.io.*;

public class ProtocolPlayUtil
{
    public static byte[] velocityAndPosition(final EntityPlayer player) {
        final double x = player.posX;
        final double y = player.posY;
        final double z = player.posZ;
        final double dX = player.motionX;
        final double dY = player.motionY;
        final double dZ = player.motionZ;
        final byte[] packets = new byte[64];
        final ByteBuffer buf = ByteBuffer.wrap(packets);
        buf.putInt(9).putInt(24).putDouble(x).putDouble(y).putDouble(z).putInt(10).putInt(24).putDouble(dX).putDouble(dY).putDouble(dZ);
        return packets;
    }
    
    public static void sendVelocityAndPosition(final IConnectionManager manager, final EntityPlayer player) {
        try {
            manager.send(velocityAndPosition(player));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
