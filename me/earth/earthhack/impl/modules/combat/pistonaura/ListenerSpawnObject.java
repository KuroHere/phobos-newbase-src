//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.pistonaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.combat.pistonaura.util.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;

final class ListenerSpawnObject extends ModuleListener<PistonAura, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawnObject(final PistonAura module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        if (((PistonAura)this.module).stage == PistonStage.BREAK && ((PistonAura)this.module).current != null && ((PistonAura)this.module).breakTimer.passed(((PistonAura)this.module).breakDelay.getValue()) && Managers.SWITCH.getLastSwitch() > ((PistonAura)this.module).coolDown.getValue()) {
            final SPacketSpawnObject packet = event.getPacket();
            if (packet.getType() == 51) {
                final BlockPos pos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
                try {
                    if (((PistonAura)this.module).current.getCrystalPos().equals((Object)pos.down()) || (((PistonAura)this.module).current.getStartPos().equals((Object)pos.down()) && (((PistonAura)this.module).rotate.getValue() == Rotate.None || RotationUtil.isLegit(pos))) || RotationUtil.isLegit(pos.up())) {
                        ((PistonAura)this.module).entityId = packet.getEntityID();
                        if (!((PistonAura)this.module).instant.getValue() || !((PistonAura)this.module).explode.getValue()) {
                            return;
                        }
                        final ICPacketUseEntity useEntity = (ICPacketUseEntity)new CPacketUseEntity();
                        useEntity.setAction(CPacketUseEntity.Action.ATTACK);
                        useEntity.setEntityId(packet.getEntityID());
                        ListenerSpawnObject.mc.player.connection.sendPacket((Packet)useEntity);
                        ListenerSpawnObject.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                        ((PistonAura)this.module).breakTimer.reset();
                    }
                }
                catch (Exception ex) {}
            }
        }
    }
}
