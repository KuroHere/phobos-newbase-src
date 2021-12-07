//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packets;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

final class ListenerCollect extends ModuleListener<Packets, PacketEvent.Receive<SPacketCollectItem>>
{
    private final Random rnd;
    
    public ListenerCollect(final Packets module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketCollectItem.class);
        this.rnd = new Random();
    }
    
    public void invoke(final PacketEvent.Receive<SPacketCollectItem> event) {
        if (!((Packets)this.module).fastCollect.getValue()) {
            return;
        }
        final World world = (World)ListenerCollect.mc.world;
        if (world == null) {
            return;
        }
        final SPacketCollectItem p = event.getPacket();
        final Entity entity = world.getEntityByID(p.getCollectedItemEntityID());
        Entity living = world.getEntityByID(p.getEntityID());
        if (entity == null || (living != null && !(living instanceof EntityLivingBase))) {
            return;
        }
        if (living == null) {
            living = (Entity)ListenerCollect.mc.player;
            if (living == null) {
                return;
            }
        }
        event.setCancelled(true);
        SoundEvent soundEvent;
        float volume;
        float pitch;
        if (entity instanceof EntityXPOrb) {
            soundEvent = SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
            volume = 0.1f;
            pitch = (this.rnd.nextFloat() - this.rnd.nextFloat()) * 0.35f + 0.9f;
        }
        else {
            soundEvent = SoundEvents.ENTITY_ITEM_PICKUP;
            volume = 0.2f;
            pitch = (this.rnd.nextFloat() - this.rnd.nextFloat()) * 1.4f + 2.0f;
        }
        if (entity instanceof EntityItem) {
            ((EntityItem)entity).getEntityItem().func_190920_e(p.func_191208_c());
        }
        final Entity finalLiving = living;
        ListenerCollect.mc.addScheduledTask(() -> {
            world.playSound(entity.posX, entity.posY, entity.posZ, soundEvent, SoundCategory.PLAYERS, volume, pitch, false);
            ListenerCollect.mc.effectRenderer.addEffect((Particle)new ParticleItemPickup(world, entity, finalLiving, 0.5f));
            ListenerCollect.mc.world.removeEntityFromWorld(p.getCollectedItemEntityID());
            return;
        });
        entity.setDead();
    }
}
