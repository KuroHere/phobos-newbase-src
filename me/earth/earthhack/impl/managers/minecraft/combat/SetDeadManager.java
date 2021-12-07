//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.minecraft.combat;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.management.*;
import me.earth.earthhack.api.setting.settings.*;
import io.netty.util.internal.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.event.bus.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.managers.minecraft.combat.util.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.*;

public class SetDeadManager extends SubscriberImpl implements Globals
{
    private static final SettingCache<Integer, NumberSetting<Integer>, Management> DEATH_TIME;
    private static final SettingCache<Boolean, BooleanSetting, Management> SOUND_REMOVE;
    private final Map<Integer, EntityTime> killed;
    private final Set<SoundObserver> observers;
    
    public SetDeadManager() {
        this.observers = (Set<SoundObserver>)new ConcurrentSet();
        this.killed = new ConcurrentHashMap<Integer, EntityTime>();
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketSoundEffect>>(PacketEvent.Receive.class, Integer.MAX_VALUE, SPacketSoundEffect.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketSoundEffect> event) {
                final SPacketSoundEffect p = event.getPacket();
                if (p.getCategory() == SoundCategory.BLOCKS && p.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE && SetDeadManager.this.shouldRemove()) {
                    final Vec3d pos = new Vec3d(p.getX(), p.getY(), p.getZ());
                    Globals.mc.addScheduledTask(() -> {
                        SetDeadManager.this.removeCrystals(pos, 11.0f, Globals.mc.world.loadedEntityList);
                        SetDeadManager.this.observers.iterator();
                        final Iterator iterator;
                        while (iterator.hasNext()) {
                            final SoundObserver observer = iterator.next();
                            if (observer.shouldBeNotified()) {
                                observer.onChange(p);
                            }
                        }
                    });
                }
            }
        });
        this.listeners.add(new EventListener<PacketEvent.Receive<SPacketDestroyEntities>>(PacketEvent.Receive.class, Integer.MAX_VALUE, SPacketDestroyEntities.class) {
            @Override
            public void invoke(final PacketEvent.Receive<SPacketDestroyEntities> event) {
                Globals.mc.addScheduledTask(() -> {
                    ((SPacketDestroyEntities)event.getPacket()).getEntityIDs();
                    final int[] array;
                    int i = 0;
                    for (int length = array.length; i < length; ++i) {
                        final int id = array[i];
                        SetDeadManager.this.confirmKill(id);
                    }
                });
            }
        });
        this.listeners.add(new EventListener<UpdateEvent>(UpdateEvent.class) {
            @Override
            public void invoke(final UpdateEvent event) {
                SetDeadManager.this.updateKilled();
            }
        });
        this.listeners.add(new EventListener<WorldClientEvent.Load>(WorldClientEvent.Load.class) {
            @Override
            public void invoke(final WorldClientEvent.Load event) {
                SetDeadManager.this.clear();
            }
        });
        this.listeners.add(new EventListener<WorldClientEvent.Unload>(WorldClientEvent.Unload.class) {
            @Override
            public void invoke(final WorldClientEvent.Unload event) {
                SetDeadManager.this.clear();
            }
        });
    }
    
    public Entity getEntity(final int id) {
        final EntityTime time = this.killed.get(id);
        if (time != null) {
            return time.getEntity();
        }
        return null;
    }
    
    public void setDeadCustom(final Entity entity, final long t) {
        final EntityTime time = this.killed.get(entity.getEntityId());
        if (time instanceof CustomEntityTime) {
            time.getEntity().setDead();
            time.reset();
        }
        else {
            entity.setDead();
            this.killed.put(entity.getEntityId(), new CustomEntityTime(entity, t));
        }
    }
    
    public void revive(final int id) {
        final EntityTime time = this.killed.remove(id);
        if (time != null && time.isValid()) {
            final Entity entity = time.getEntity();
            entity.isDead = false;
            SetDeadManager.mc.world.addEntityToWorld(entity.getEntityId(), entity);
            entity.isDead = false;
        }
    }
    
    public void updateKilled() {
        for (final Map.Entry<Integer, EntityTime> entry : this.killed.entrySet()) {
            if (!entry.getValue().isValid()) {
                entry.getValue().getEntity().setDead();
                this.killed.remove(entry.getKey());
            }
            else {
                if (!entry.getValue().passed(SetDeadManager.DEATH_TIME.getValue())) {
                    continue;
                }
                final Entity entity = entry.getValue().getEntity();
                entity.isDead = false;
                if (SetDeadManager.mc.world.loadedEntityList.contains(entity)) {
                    continue;
                }
                SetDeadManager.mc.world.addEntityToWorld((int)entry.getKey(), entity);
                entity.isDead = false;
                this.killed.remove(entry.getKey());
            }
        }
    }
    
    public void removeCrystals(final Vec3d pos, final float range, final List<Entity> entities) {
        for (final Entity entity : entities) {
            if (entity instanceof EntityEnderCrystal && entity.getDistanceSq(pos.xCoord, pos.yCoord, pos.zCoord) <= MathUtil.square(range)) {
                this.setDead(entity);
            }
        }
    }
    
    public void setDead(final Entity entity) {
        final EntityTime time = this.killed.get(entity.getEntityId());
        if (time != null) {
            time.getEntity().setDead();
            time.reset();
        }
        else if (!entity.isDead) {
            entity.setDead();
            this.killed.put(entity.getEntityId(), new EntityTime(entity));
        }
    }
    
    public void confirmKill(final int id) {
        final EntityTime time = this.killed.get(id);
        if (time != null) {
            time.setValid(false);
            time.getEntity().setDead();
        }
    }
    
    public boolean passedDeathTime(final Entity entity, final long deathTime) {
        return this.passedDeathTime(entity.getEntityId(), deathTime);
    }
    
    public boolean passedDeathTime(final int id, final long deathTime) {
        if (deathTime <= 0L) {
            return true;
        }
        final EntityTime time = this.killed.get(id);
        return time == null || !time.isValid() || time.passed(deathTime);
    }
    
    public void clear() {
        this.killed.clear();
    }
    
    public void addObserver(final SoundObserver observer) {
        this.observers.add(observer);
    }
    
    public void removeObserver(final SoundObserver observer) {
        this.observers.remove(observer);
    }
    
    private boolean shouldRemove() {
        if (!SetDeadManager.SOUND_REMOVE.getValue()) {
            return false;
        }
        for (final SoundObserver soundObserver : this.observers) {
            if (soundObserver.shouldRemove()) {
                return true;
            }
        }
        return false;
    }
    
    static {
        DEATH_TIME = Caches.getSetting(Management.class, Setting.class, "DeathTime", 500);
        SOUND_REMOVE = Caches.getSetting(Management.class, BooleanSetting.class, "SoundRemove", true);
    }
}
