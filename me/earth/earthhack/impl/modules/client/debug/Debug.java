//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.debug;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import net.minecraft.init.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import me.earth.earthhack.api.event.bus.api.*;
import net.minecraft.entity.*;

public class Debug extends Module
{
    private final Setting<Boolean> debugPlace;
    private final Setting<Boolean> debugBreak;
    private final Map<BlockPos, Long> times;
    private final Map<BlockPos, Long> attack;
    private final Map<Integer, BlockPos> ids;
    
    public Debug() {
        super("Debug", Category.Client);
        this.debugPlace = this.register(new BooleanSetting("DebugPlacePing", false));
        this.debugBreak = this.register(new BooleanSetting("DebugBreakPing", false));
        this.times = new ConcurrentHashMap<BlockPos, Long>();
        this.attack = new ConcurrentHashMap<BlockPos, Long>();
        this.ids = new ConcurrentHashMap<Integer, BlockPos>();
        final SimpleData data = new SimpleData(this, "An empty module for debugging.");
        final Setting<?> s = this.register(new BooleanSetting("SlowUpdates", false));
        data.register(s, "Makes all Chunk Updates happen on a separate Thread. Might increase FPS, but could cause Render lag.");
        this.setData(data);
        this.listeners.add(new EventListener<TickEvent>(TickEvent.class) {
            @Override
            public void invoke(final TickEvent event) {
            }
        });
        this.listeners.add(new EventListener<MotionUpdateEvent>(MotionUpdateEvent.class) {
            @Override
            public void invoke(final MotionUpdateEvent event) {
            }
        });
        this.listeners.add(new EventListener<UpdateEntitiesEvent>(UpdateEntitiesEvent.class) {
            @Override
            public void invoke(final UpdateEntitiesEvent event) {
            }
        });
        this.listeners.add(new EventListener<WorldClientEvent>(WorldClientEvent.class) {
            @Override
            public void invoke(final WorldClientEvent event) {
                Debug.this.reset();
            }
        });
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSoundEffect.class, e -> {
            final SPacketSoundEffect p = (SPacketSoundEffect)e.getPacket();
            if (this.debugBreak.getValue() && p.getCategory() == SoundCategory.BLOCKS && p.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                final BlockPos pos = new BlockPos(p.getX(), p.getY() - 1.0, p.getZ());
                final Long l = this.attack.remove(pos);
                if (l != null) {
                    ChatUtil.sendMessageScheduled("Attack took " + (System.currentTimeMillis() - l) + "ms.");
                }
            }
            return;
        }));
        this.listeners.add(new PostSendListener<Object>((Class<Object>)CPacketUseEntity.class, e -> {
            if (!this.debugBreak.getValue()) {
                return;
            }
            else {
                final int entityId = ((ICPacketUseEntity)e.getPacket()).getEntityID();
                final Entity entity = Debug.mc.world.getEntityByID(entityId);
                BlockPos pos2;
                if (entity == null) {
                    pos2 = this.ids.get(entityId);
                }
                else {
                    pos2 = entity.getPosition().down();
                }
                if (pos2 != null) {
                    this.attack.put(pos2, System.currentTimeMillis());
                }
                return;
            }
        }));
        this.listeners.add(new PostSendListener<Object>((Class<Object>)CPacketPlayerTryUseItemOnBlock.class, e -> {
            if (Debug.mc.player.getHeldItem(((CPacketPlayerTryUseItemOnBlock)e.getPacket()).getHand()).getItem() == Items.END_CRYSTAL && !this.times.containsKey(((CPacketPlayerTryUseItemOnBlock)e.getPacket()).getPos())) {
                this.times.put(((CPacketPlayerTryUseItemOnBlock)e.getPacket()).getPos(), System.currentTimeMillis());
            }
            return;
        }));
        this.listeners.add(new ReceiveListener<Object>((Class<Object>)SPacketSpawnObject.class, Integer.MAX_VALUE, e -> {
            if (((SPacketSpawnObject)e.getPacket()).getType() == 51) {
                final BlockPos pos3 = new BlockPos(((SPacketSpawnObject)e.getPacket()).getX(), ((SPacketSpawnObject)e.getPacket()).getY() - 1.0, ((SPacketSpawnObject)e.getPacket()).getZ());
                if (this.debugPlace.getValue()) {
                    final Long i = this.times.remove(pos3);
                    if (i != null) {
                        final long curr = System.currentTimeMillis();
                        Debug.mc.addScheduledTask(() -> DebugUtil.debug(pos, "Crystal took " + (curr - l) + "ms to spawn."));
                    }
                }
                if (this.debugBreak.getValue()) {
                    this.ids.put(((SPacketSpawnObject)e.getPacket()).getEntityID(), pos3);
                }
            }
            return;
        }));
        this.listeners.addAll(new CPacketPlayerListener() {
            @Override
            protected void onPacket(final PacketEvent.Send<CPacketPlayer> event) {
            }
            
            @Override
            protected void onPosition(final PacketEvent.Send<CPacketPlayer.Position> event) {
            }
            
            @Override
            protected void onRotation(final PacketEvent.Send<CPacketPlayer.Rotation> event) {
            }
            
            @Override
            protected void onPositionRotation(final PacketEvent.Send<CPacketPlayer.PositionRotation> event) {
            }
        }.getListeners());
    }
    
    @Override
    protected void onEnable() {
        this.reset();
    }
    
    @Override
    protected void onDisable() {
        this.reset();
    }
    
    private void reset() {
        this.times.clear();
        this.attack.clear();
        this.ids.clear();
    }
}
