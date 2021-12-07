//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.forge.util;

import net.minecraftforge.fml.common.network.handshake.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class ReplaceNetworkDispatcher extends NetworkDispatcher
{
    public ReplaceNetworkDispatcher(final NetworkManager manager) {
        super(manager);
    }
    
    public int getOverrideDimension(final SPacketJoinGame packetIn) {
        return packetIn.getDimension();
    }
}
