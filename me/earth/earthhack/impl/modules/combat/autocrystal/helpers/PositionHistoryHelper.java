//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.helpers;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.event.listeners.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.bus.*;
import net.minecraft.entity.*;
import java.util.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import net.minecraft.world.*;
import com.google.common.base.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.managers.*;

public class PositionHistoryHelper extends SubscriberImpl implements Globals
{
    private static final int REMOVE_TIME = 1000;
    private final Deque<RotationHistory> packets;
    
    public PositionHistoryHelper() {
        this.packets = new ConcurrentLinkedDeque<RotationHistory>();
        this.listeners.addAll(new CPacketPlayerPostListener() {
            @Override
            protected void onPacket(final PacketEvent.Post<CPacketPlayer> event) {
                PositionHistoryHelper.this.onPlayerPacket(event.getPacket());
            }
            
            @Override
            protected void onPosition(final PacketEvent.Post<CPacketPlayer.Position> event) {
                PositionHistoryHelper.this.onPlayerPacket(event.getPacket());
            }
            
            @Override
            protected void onRotation(final PacketEvent.Post<CPacketPlayer.Rotation> event) {
                PositionHistoryHelper.this.onPlayerPacket(event.getPacket());
            }
            
            @Override
            protected void onPositionRotation(final PacketEvent.Post<CPacketPlayer.PositionRotation> event) {
                PositionHistoryHelper.this.onPlayerPacket(event.getPacket());
            }
        }.getListeners());
        this.listeners.add(new EventListener<WorldClientEvent.Load>(WorldClientEvent.Load.class) {
            @Override
            public void invoke(final WorldClientEvent.Load event) {
                PositionHistoryHelper.this.packets.clear();
            }
        });
    }
    
    private void onPlayerPacket(final CPacketPlayer packet) {
        this.packets.removeIf(h -> h == null || System.currentTimeMillis() - h.time > 1000L);
        this.packets.addFirst(new RotationHistory(packet));
    }
    
    public boolean arePreviousRotationsLegit(final Entity entity, final int time, final boolean skipFirst) {
        if (time == 0) {
            return true;
        }
        final Iterator<RotationHistory> itr = this.packets.iterator();
        while (itr.hasNext()) {
            final RotationHistory next = itr.next();
            if (skipFirst) {
                continue;
            }
            if (next == null) {
                continue;
            }
            if (System.currentTimeMillis() - next.time > 1000L) {
                itr.remove();
            }
            else {
                if (System.currentTimeMillis() - next.time > time) {
                    break;
                }
                if (!this.isLegit(next, entity)) {
                    return false;
                }
                continue;
            }
        }
        return true;
    }
    
    private boolean isLegit(final RotationHistory history, final Entity entity) {
        final RayTraceResult result = RayTracer.rayTraceEntities((World)PositionHistoryHelper.mc.world, (Entity)RotationUtil.getRotationPlayer(), 7.0, history.x, history.y, history.z, history.yaw, history.pitch, history.bb, (Predicate<Entity>)(e -> e != null && e.equals((Object)entity)), entity, entity);
        return result != null && entity.equals((Object)result.entityHit);
    }
    
    private static final class RotationHistory
    {
        public final double x;
        public final double y;
        public final double z;
        public final float yaw;
        public final float pitch;
        public final long time;
        public final AxisAlignedBB bb;
        
        public RotationHistory(final CPacketPlayer packet) {
            this(packet.getX(Managers.POSITION.getX()), packet.getY(Managers.POSITION.getY()), packet.getZ(Managers.POSITION.getZ()), packet.getYaw(Managers.ROTATION.getServerYaw()), packet.getPitch(Managers.ROTATION.getServerPitch()));
        }
        
        public RotationHistory(final double x, final double y, final double z, final float yaw, final float pitch) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.time = System.currentTimeMillis();
            final float w = Globals.mc.player.width / 2.0f;
            final float h = Globals.mc.player.height;
            this.bb = new AxisAlignedBB(x - w, y, z - w, x + w, y + h, z + w);
        }
    }
}
