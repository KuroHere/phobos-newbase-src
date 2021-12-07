//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.entity.*;

final class ListenerSound extends ModuleListener<Packets, PacketEvent.Receive<SPacketSoundEffect>>
{
    public ListenerSound(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSoundEffect.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSoundEffect> event) {
        if (((Packets)this.module).fastSetDead.getValue() && ListenerSound.mc.player != null) {
            final SPacketSoundEffect packet = event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                final List<Entity> entities = Managers.ENTITIES.getEntities();
                if (entities != null) {
                    Managers.SET_DEAD.removeCrystals(new Vec3d(packet.getX(), packet.getY(), packet.getZ()), 11.0f, entities);
                }
            }
        }
    }
}
