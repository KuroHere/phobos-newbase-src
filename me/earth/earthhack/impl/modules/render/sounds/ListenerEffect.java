//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.sounds;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.render.sounds.util.*;
import net.minecraft.util.math.*;

final class ListenerEffect extends ModuleListener<Sounds, PacketEvent.Receive<SPacketEffect>>
{
    public ListenerEffect(final Sounds module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketEffect.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketEffect> event) {
        final SPacketEffect packet = event.getPacket();
        switch (packet.getSoundType()) {
            case 1038: {
                if (((Sounds)this.module).portal.getValue()) {
                    ((Sounds)this.module).log("EndPortal: " + packet.getSoundPos().getX() + "-X, " + packet.getSoundPos().getY() + "-Y, " + packet.getSoundPos().getZ() + "-Z.");
                    break;
                }
                break;
            }
            case 1023: {
                if (!((Sounds)this.module).wither.getValue()) {
                    break;
                }
                if (((Sounds)this.module).coordLogger.getValue() == CoordLogger.Vanilla) {
                    ((Sounds)this.module).log("Wither: " + packet.getSoundPos().getX() + "-X, " + packet.getSoundPos().getY() + "-Y, " + packet.getSoundPos().getZ() + "-Z.");
                    break;
                }
                final double x = packet.getSoundPos().getX() - ListenerEffect.mc.player.posX;
                final double z = packet.getSoundPos().getZ() - ListenerEffect.mc.player.posZ;
                final double yaw = MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(x, z) - 90.0));
                ((Sounds)this.module).log("Wither: " + ListenerEffect.mc.player.posX + "-X, " + ListenerEffect.mc.player.posZ + "-Z, " + yaw + "-Angle.");
                break;
            }
            case 1028: {
                if (((Sounds)this.module).dragon.getValue()) {
                    final double x = packet.getSoundPos().getX() - ListenerEffect.mc.player.posX;
                    final double z = packet.getSoundPos().getZ() - ListenerEffect.mc.player.posZ;
                    final double yaw = MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(x, z) - 90.0));
                    ((Sounds)this.module).log("Dragon: " + ListenerEffect.mc.player.posX + "-X, " + ListenerEffect.mc.player.posZ + "-Z, " + yaw + "-Angle.");
                    break;
                }
                break;
            }
        }
    }
}
