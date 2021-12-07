//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.sounds;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.render.sounds.util.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import net.minecraft.util.text.*;

final class ListenerSound extends ModuleListener<Sounds, PacketEvent.Receive<SPacketSoundEffect>>
{
    public ListenerSound(final Sounds module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketSoundEffect.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSoundEffect> event) {
        final SPacketSoundEffect packet = event.getPacket();
        if (((Sounds)this.module).thunder.getValue() && packet.getCategory() == SoundCategory.WEATHER && packet.getSound() == SoundEvents.ENTITY_LIGHTNING_THUNDER) {
            final double x = packet.getX() - ListenerSound.mc.player.posX;
            final double z = packet.getZ() - ListenerSound.mc.player.posZ;
            final double yaw = MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(x, z) - 90.0));
            ((Sounds)this.module).log("Lightning: " + ListenerSound.mc.player.posX + "-x, " + ListenerSound.mc.player.posZ + "-z, " + yaw + "-angle.");
        }
        final boolean cancelled = event.isCancelled();
        if (((Sounds)this.module).client.getValue() || !((Sounds)this.module).packet.getValue() || (cancelled && !((Sounds)this.module).cancelled.getValue())) {
            return;
        }
        final ResourceLocation location = packet.getSound().getSoundName();
        final SoundEventAccessor access = ListenerSound.mc.getSoundHandler().getAccessor(location);
        final ITextComponent c = (access == null) ? null : access.getSubtitle();
        if ((c != null && ((Sounds)this.module).isValid(c.getUnformattedComponentText())) || (c == null && ((Sounds)this.module).isValid(location.toString()))) {
            final String s = (c != null) ? c.getUnformattedComponentText() : location.toString();
            ((Sounds)this.module).sounds.put(new SoundPosition(packet.getX(), packet.getY(), packet.getZ(), (cancelled ? "Cancelled: " : "") + s), System.currentTimeMillis());
        }
    }
}
