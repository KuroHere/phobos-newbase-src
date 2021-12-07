//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.core.mixins.network.server.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import java.util.*;

final class ListenerEntity extends SPacketEntityListener
{
    private final AutoCrystal module;
    
    public ListenerEntity(final AutoCrystal module) {
        this.module = module;
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketEntityTeleport.class, e -> {
            if (!(!this.shouldCalc())) {
                final EntityPlayer p = this.getEntity(((SPacketEntityTeleport)e.getPacket()).getEntityId());
                if (p != null) {
                    final double x = ((SPacketEntityTeleport)e.getPacket()).getX();
                    final double y = ((SPacketEntityTeleport)e.getPacket()).getY();
                    final double z = ((SPacketEntityTeleport)e.getPacket()).getZ();
                    this.onEvent(p, x, y, z);
                }
            }
        }));
    }
    
    @Override
    protected void onPacket(final PacketEvent.Receive<SPacketEntity> event) {
    }
    
    @Override
    protected void onRotation(final PacketEvent.Receive<SPacketEntity.S16PacketEntityLook> event) {
    }
    
    @Override
    protected void onPosition(final PacketEvent.Receive<SPacketEntity.S15PacketEntityRelMove> event) {
        this.onEvent(event.getPacket());
    }
    
    @Override
    protected void onPositionRotation(final PacketEvent.Receive<SPacketEntity.S17PacketEntityLookMove> event) {
        this.onEvent(event.getPacket());
    }
    
    protected void onEvent(final SPacketEntity packet) {
        if (!this.shouldCalc()) {
            return;
        }
        final EntityPlayer p = this.getEntity(((ISPacketEntity)packet).getEntityId());
        if (p == null) {
            return;
        }
        final double x = (p.serverPosX + packet.getX()) / 4096.0;
        final double y = (p.serverPosY + packet.getY()) / 4096.0;
        final double z = (p.serverPosZ + packet.getZ()) / 4096.0;
        this.onEvent(p, x, y, z);
    }
    
    protected void onEvent(final EntityPlayer player, final double x, final double y, final double z) {
        final Entity entity = (Entity)RotationUtil.getRotationPlayer();
        if (entity != null && entity.getDistanceSq(x, y, z) < MathUtil.square(this.module.targetRange.getValue()) && !Managers.FRIENDS.contains(player)) {
            final boolean enemied = Managers.ENEMIES.contains(player);
            Scheduler.getInstance().scheduleAsynchronously(() -> {
                if (ListenerEntity.mc.world != null) {
                    List<EntityPlayer> enemies;
                    if (enemied) {
                        enemies = new ArrayList<EntityPlayer>(1);
                        enemies.add(player);
                    }
                    else {
                        enemies = Collections.emptyList();
                    }
                    final EntityPlayer target = this.module.targetMode.getValue().getTarget(ListenerEntity.mc.world.playerEntities, enemies, this.module.targetRange.getValue());
                    if (target == null || target.equals((Object)player)) {
                        this.module.threadHelper.startThread(new BlockPos[0]);
                    }
                }
            });
        }
    }
    
    protected boolean shouldCalc() {
        return this.module.multiThread.getValue() && this.module.entityThread.getValue() && (this.module.rotate.getValue() == ACRotate.None || this.module.rotationThread.getValue() != RotationThread.Predict);
    }
    
    protected EntityPlayer getEntity(final int id) {
        final List<Entity> entities = Managers.ENTITIES.getEntities();
        if (entities == null) {
            return null;
        }
        Entity entity = null;
        for (final Entity e : entities) {
            if (e != null && e.getEntityId() == id) {
                entity = e;
                break;
            }
        }
        if (entity instanceof EntityPlayer) {
            return (EntityPlayer)entity;
        }
        return null;
    }
}
