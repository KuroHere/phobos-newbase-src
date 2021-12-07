//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bowkill;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;

final class ListenerStopUsingItem extends ModuleListener<BowKiller, PacketEvent.Send<CPacketPlayerDigging>>
{
    public ListenerStopUsingItem(final BowKiller module) {
        super(module, (Class<? super Object>)PacketEvent.Send.class, CPacketPlayerDigging.class);
    }
    
    public void invoke(final PacketEvent.Send<CPacketPlayerDigging> event) {
        if (!ListenerStopUsingItem.mc.player.isCollidedVertically) {
            return;
        }
        if (event.getPacket().getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM && ListenerStopUsingItem.mc.player.getActiveItemStack().getItem() == Items.BOW && ((BowKiller)this.module).blockUnder) {
            ((BowKiller)this.module).cancelling = false;
            if (((BowKiller)this.module).packetsSent >= ((BowKiller)this.module).runs.getValue() * 2 || ((BowKiller)this.module).always.getValue()) {
                PacketUtil.sendAction(CPacketEntityAction.Action.START_SPRINTING);
                if (((BowKiller)this.module).cancelRotate.getValue() && (ListenerStopUsingItem.mc.player.rotationYaw != Managers.ROTATION.getServerYaw() || ListenerStopUsingItem.mc.player.rotationPitch != Managers.ROTATION.getServerPitch())) {
                    PacketUtil.doRotation(ListenerStopUsingItem.mc.player.rotationYaw, ListenerStopUsingItem.mc.player.rotationPitch, true);
                }
                for (int i = 0; i < ((BowKiller)this.module).runs.getValue() + ((BowKiller)this.module).buffer.getValue(); ++i) {
                    if (i != 0 && i % ((BowKiller)this.module).interval.getValue() == 0) {
                        int id = Managers.POSITION.getTeleportID();
                        for (int j = 0; j < ((BowKiller)this.module).teleports.getValue(); ++j) {
                            ListenerStopUsingItem.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(++id));
                        }
                    }
                    final double[] dir = MovementUtil.strafe(0.001);
                    if (((BowKiller)this.module).rotate.getValue()) {
                        ((BowKiller)this.module).target = ((BowKiller)this.module).findTarget();
                        if (((BowKiller)this.module).target != null) {
                            final float[] rotations = ((BowKiller)this.module).rotationSmoother.getRotations((Entity)RotationUtil.getRotationPlayer(), ((BowKiller)this.module).target, ((BowKiller)this.module).height.getValue(), ((BowKiller)this.module).soft.getValue());
                            if (rotations != null) {
                                PacketUtil.doPosRotNoEvent(ListenerStopUsingItem.mc.player.posX + (((BowKiller)this.module).move.getValue() ? dir[0] : 0.0), ListenerStopUsingItem.mc.player.posY + 1.3E-13, ListenerStopUsingItem.mc.player.posZ + (((BowKiller)this.module).move.getValue() ? dir[1] : 0.0), rotations[0], rotations[1], true);
                                PacketUtil.doPosRotNoEvent(ListenerStopUsingItem.mc.player.posX + (((BowKiller)this.module).move.getValue() ? (dir[0] * 2.0) : 0.0), ListenerStopUsingItem.mc.player.posY + 2.7E-13, ListenerStopUsingItem.mc.player.posZ + (((BowKiller)this.module).move.getValue() ? (dir[1] * 2.0) : 0.0), rotations[0], rotations[1], false);
                            }
                        }
                        else {
                            PacketUtil.doPosRotNoEvent(ListenerStopUsingItem.mc.player.posX + (((BowKiller)this.module).move.getValue() ? dir[0] : 0.0), ListenerStopUsingItem.mc.player.posY + 1.3E-13, ListenerStopUsingItem.mc.player.posZ + (((BowKiller)this.module).move.getValue() ? dir[1] : 0.0), ListenerStopUsingItem.mc.player.rotationYaw, ListenerStopUsingItem.mc.player.rotationPitch, true);
                            PacketUtil.doPosRotNoEvent(ListenerStopUsingItem.mc.player.posX + (((BowKiller)this.module).move.getValue() ? (dir[0] * 2.0) : 0.0), ListenerStopUsingItem.mc.player.posY + 2.7E-13, ListenerStopUsingItem.mc.player.posZ + (((BowKiller)this.module).move.getValue() ? (dir[1] * 2.0) : 0.0), ListenerStopUsingItem.mc.player.rotationYaw, ListenerStopUsingItem.mc.player.rotationPitch, false);
                        }
                    }
                    else {
                        PacketUtil.doPosRotNoEvent(ListenerStopUsingItem.mc.player.posX + (((BowKiller)this.module).move.getValue() ? dir[0] : 0.0), ListenerStopUsingItem.mc.player.posY + 1.3E-13, ListenerStopUsingItem.mc.player.posZ + (((BowKiller)this.module).move.getValue() ? dir[1] : 0.0), ListenerStopUsingItem.mc.player.rotationYaw, ListenerStopUsingItem.mc.player.rotationPitch, true);
                        PacketUtil.doPosRotNoEvent(ListenerStopUsingItem.mc.player.posX + (((BowKiller)this.module).move.getValue() ? (dir[0] * 2.0) : 0.0), ListenerStopUsingItem.mc.player.posY + 2.7E-13, ListenerStopUsingItem.mc.player.posZ + (((BowKiller)this.module).move.getValue() ? (dir[1] * 2.0) : 0.0), ListenerStopUsingItem.mc.player.rotationYaw, ListenerStopUsingItem.mc.player.rotationPitch, false);
                    }
                }
            }
            ((BowKiller)this.module).packetsSent = 0;
        }
    }
}
