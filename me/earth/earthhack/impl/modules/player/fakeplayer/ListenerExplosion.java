//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.fakeplayer;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

final class ListenerExplosion extends ModuleListener<FakePlayer, PacketEvent.Receive<SPacketExplosion>>
{
    public ListenerExplosion(final FakePlayer module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketExplosion.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketExplosion> event) {
        if (((FakePlayer)this.module).damage.getValue()) {
            ListenerExplosion.mc.addScheduledTask(() -> this.handleExplosion((SPacketExplosion)event.getPacket()));
        }
    }
    
    private void handleExplosion(final SPacketExplosion packet) {
        if (ListenerExplosion.mc.world == null || ((FakePlayer)this.module).fakePlayer == null || !((FakePlayer)this.module).isEnabled()) {
            return;
        }
        final double x = packet.getX();
        final double y = packet.getY();
        final double z = packet.getZ();
        double distance = ((FakePlayer)this.module).fakePlayer.getDistance(x, y, z) / 12.0;
        if (distance > 1.0) {
            return;
        }
        final float size = packet.getStrength();
        final double density = ListenerExplosion.mc.world.getBlockDensity(new Vec3d(x, y, z), ((FakePlayer)this.module).fakePlayer.getEntityBoundingBox());
        final double densityDistance;
        distance = (densityDistance = (1.0 - distance) * density);
        final float damage = (float)((densityDistance * densityDistance + distance) / 2.0 * 7.0 * size * 2.0 + 1.0);
        final DamageSource damageSource = DamageSource.causeExplosionDamage(new Explosion((World)ListenerExplosion.mc.world, (Entity)ListenerExplosion.mc.player, x, y, z, size, false, true));
        final float limbSwing = ((FakePlayer)this.module).fakePlayer.limbSwingAmount;
        ((FakePlayer)this.module).fakePlayer.attackEntityFrom(damageSource, damage);
        ((FakePlayer)this.module).fakePlayer.limbSwingAmount = limbSwing;
    }
}
