//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.norotate;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.core.mixins.network.client.*;

final class ListenerCPacket extends CPacketPlayerListener implements Globals
{
    private final NoRotate module;
    
    public ListenerCPacket(final NoRotate module) {
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
        if (this.module.noSpoof.getValue() && !Managers.ROTATION.isBlocking() && (ItemUtil.isThrowable(ListenerCPacket.mc.player.getActiveItemStack().getItem()) || ListenerCPacket.mc.player.getActiveItemStack().getItem() instanceof ItemBow) && packet.getYaw(ListenerCPacket.mc.player.rotationYaw) != ListenerCPacket.mc.player.rotationYaw) {
            ((ICPacketPlayer)packet).setYaw(ListenerCPacket.mc.player.rotationYaw);
            ((ICPacketPlayer)packet).setPitch(ListenerCPacket.mc.player.rotationPitch);
        }
    }
}
