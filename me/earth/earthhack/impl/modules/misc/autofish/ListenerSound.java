//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autofish;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.projectile.*;

final class ListenerSound extends ModuleListener<AutoFish, PacketEvent.Receive<SPacketSoundEffect>>
{
    public ListenerSound(final AutoFish module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSoundEffect.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSoundEffect> event) {
        final SPacketSoundEffect packet = event.getPacket();
        if (packet.getSound().equals(SoundEvents.ENTITY_BOBBER_SPLASH)) {
            final EntityFishHook fish = ListenerSound.mc.player.fishEntity;
            if (fish != null && ListenerSound.mc.player.equals((Object)fish.func_190619_l()) && (((AutoFish)this.module).range.getValue() == 0.0 || fish.getPositionVector().distanceTo(new Vec3d(packet.getX(), packet.getY(), packet.getZ())) <= ((AutoFish)this.module).range.getValue())) {
                ((AutoFish)this.module).splash = true;
            }
        }
    }
}
