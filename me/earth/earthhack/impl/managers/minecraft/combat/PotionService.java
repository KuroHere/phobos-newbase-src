//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.event.listeners.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.datasync.*;
import me.earth.earthhack.impl.core.mixins.entity.living.player.*;
import java.util.*;
import me.earth.earthhack.impl.event.events.network.*;

public class PotionService extends SubscriberImpl implements Globals
{
    public PotionService() {
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketEntityStatus.class, e -> {
            if (((SPacketEntityStatus)e.getPacket()).getOpCode() == 35) {
                PotionService.mc.addScheduledTask(() -> this.onTotemPop((SPacketEntityStatus)e.getPacket()));
            }
            return;
        }));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketEntityMetadata.class, e -> PotionService.mc.addScheduledTask(() -> this.onEntityMetaData((SPacketEntityMetadata)e.getPacket()))));
    }
    
    public void onTotemPop(final SPacketEntityStatus packet) {
        if (PotionService.mc.world == null) {
            return;
        }
        final Entity entity = packet.getEntity((World)PotionService.mc.world);
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase base = (EntityLivingBase)entity;
            base.clearActivePotions();
            base.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
            base.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
        }
    }
    
    public void onEntityMetaData(final SPacketEntityMetadata packet) {
        if (PotionService.mc.world == null || packet.getDataManagerEntries() == null) {
            return;
        }
        final Entity e = PotionService.mc.world.getEntityByID(packet.getEntityId());
        if (e instanceof EntityPlayer) {
            final EntityPlayer p = (EntityPlayer)e;
            for (final EntityDataManager.DataEntry<?> entry : packet.getDataManagerEntries()) {
                if (entry.getKey().getId() == IEntityPlayer.getAbsorption().getId()) {
                    final float value = (float)entry.getValue();
                    final float prev = p.getAbsorptionAmount();
                    if (value == 4.0f && prev < value) {
                        p.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1));
                        p.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 0));
                        break;
                    }
                    if (value == 16.0f) {
                        p.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 1));
                        p.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 0));
                        p.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0));
                        p.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 3));
                        break;
                    }
                    break;
                }
            }
        }
    }
}
