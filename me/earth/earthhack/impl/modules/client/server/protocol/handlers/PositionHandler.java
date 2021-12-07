//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.server.protocol.handlers;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.modules.client.server.api.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import java.nio.*;
import net.minecraft.entity.player.*;

public class PositionHandler implements IPacketHandler, Globals
{
    private final ILogger logger;
    
    public PositionHandler(final ILogger logger) {
        this.logger = logger;
    }
    
    @Override
    public void handle(final IConnection connection, final byte[] bytes) {
        final EntityPlayer player = RotationUtil.getRotationPlayer();
        if (player == null) {
            this.logger.log("Received Position without being ingame!");
            return;
        }
        final ByteBuffer buf = ByteBuffer.wrap(bytes);
        final double x = buf.getDouble();
        final double y = buf.getDouble();
        final double z = buf.getDouble();
        PositionHandler.mc.addScheduledTask(() -> player.setPosition(x, y, z));
    }
}
