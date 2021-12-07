// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.blink.mode;

import net.minecraft.network.*;
import net.minecraft.network.play.client.*;

public enum PacketMode
{
    All {
        @Override
        public boolean shouldCancel(final Packet<?> packet) {
            return true;
        }
    }, 
    CPacketPlayer {
        @Override
        public boolean shouldCancel(final Packet<?> packet) {
            return packet instanceof CPacketPlayer;
        }
    }, 
    Filtered {
        @Override
        public boolean shouldCancel(final Packet<?> packet) {
            return !(packet instanceof CPacketChatMessage) && !(packet instanceof CPacketConfirmTeleport) && !(packet instanceof CPacketKeepAlive) && !(packet instanceof CPacketTabComplete) && !(packet instanceof CPacketClientStatus);
        }
    };
    
    public abstract boolean shouldCancel(final Packet<?> p0);
}
