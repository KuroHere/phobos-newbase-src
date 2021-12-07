//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.nofall;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.modules.movement.nofall.mode.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;

final class ListenerPlayerPackets extends CPacketPlayerListener implements Globals
{
    public final NoFall module;
    
    protected ListenerPlayerPackets(final NoFall module) {
        this.module = module;
    }
    
    @Override
    protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
        this.onPacket(event.getPacket());
    }
    
    @Override
    protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
        this.onPacket(event.getPacket());
    }
    
    @Override
    protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
        this.onPacket(event.getPacket());
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
        this.onPacket(event.getPacket());
    }
    
    private void onPacket(final CPacketPlayer packet) {
        switch (this.module.mode.getValue()) {
            case Packet: {
                if (ListenerPlayerPackets.mc.player.fallDistance > 3.0f) {
                    ((ICPacketPlayer)packet).setOnGround(true);
                    return;
                }
                break;
            }
            case Anti: {
                if (ListenerPlayerPackets.mc.player.fallDistance > 3.0f) {
                    ((ICPacketPlayer)packet).setY(ListenerPlayerPackets.mc.player.posY + 0.10000000149011612);
                    return;
                }
                break;
            }
            case AAC: {
                if (ListenerPlayerPackets.mc.player.fallDistance > 3.0f) {
                    ListenerPlayerPackets.mc.player.onGround = true;
                    ListenerPlayerPackets.mc.player.capabilities.isFlying = true;
                    ListenerPlayerPackets.mc.player.capabilities.allowFlying = true;
                    ((ICPacketPlayer)packet).setOnGround(false);
                    ListenerPlayerPackets.mc.player.velocityChanged = true;
                    ListenerPlayerPackets.mc.player.capabilities.isFlying = false;
                    ListenerPlayerPackets.mc.player.jump();
                    break;
                }
                break;
            }
        }
    }
}
