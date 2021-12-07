//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.modules.combat.legswitch.*;
import me.earth.earthhack.impl.modules.combat.antisurround.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.safety.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.modules.*;
import me.earth.earthhack.api.setting.*;

final class ListenerSpawnObject extends ModuleListener<AutoCrystal, PacketEvent.Receive<SPacketSpawnObject>>
{
    private static final ModuleCache<LegSwitch> LEG_SWITCH;
    private static final ModuleCache<AntiSurround> ANTISURROUND;
    private static final SettingCache<Float, NumberSetting<Float>, Safety> DMG;
    
    public ListenerSpawnObject(final AutoCrystal module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, Integer.MIN_VALUE, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        try {
            this.onEvent(event);
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private void onEvent(final PacketEvent.Receive<SPacketSpawnObject> event) {
        if (!((AutoCrystal)this.module).spectator.getValue() && ListenerSpawnObject.mc.player.isSpectator()) {
            return;
        }
        final SPacketSpawnObject packet = event.getPacket();
        final double x = packet.getX();
        final double y = packet.getY();
        final double z = packet.getZ();
        final EntityEnderCrystal entity = new EntityEnderCrystal((World)ListenerSpawnObject.mc.world, x, y, z);
        if (((AutoCrystal)this.module).simulatePlace.getValue() != 0) {
            event.addPostEvent(() -> {
                if (ListenerSpawnObject.mc.world == null) {
                    return;
                }
                else {
                    final Entity e = ListenerSpawnObject.mc.world.getEntityByID(packet.getEntityID());
                    if (e instanceof EntityEnderCrystal) {
                        ((AutoCrystal)this.module).crystalRender.onSpawn((EntityEnderCrystal)e);
                    }
                    return;
                }
            });
        }
        if (packet.getType() != 51 || !((AutoCrystal)this.module).instant.getValue() || ((AutoCrystal)this.module).isPingBypass() || !((AutoCrystal)this.module).breakTimer.passed(((AutoCrystal)this.module).breakDelay.getValue()) || ListenerSpawnObject.ANTISURROUND.returnIfPresent(AntiSurround::isActive, false) || ListenerSpawnObject.LEG_SWITCH.returnIfPresent(LegSwitch::isActive, false)) {
            return;
        }
        final BlockPos pos = new BlockPos(x, y, z);
        final CrystalTimeStamp stamp = ((AutoCrystal)this.module).placed.get(pos);
        entity.setShowBottom(false);
        entity.setEntityId(packet.getEntityID());
        entity.setUniqueId(packet.getUniqueId());
        if (!((AutoCrystal)this.module).alwaysCalc.getValue() && stamp != null && stamp.isValid() && (stamp.getDamage() > ((AutoCrystal)this.module).slowBreakDamage.getValue() || ((AutoCrystal)this.module).breakTimer.passed(((AutoCrystal)this.module).slowBreakDelay.getValue()) || pos.down().equals((Object)((AutoCrystal)this.module).antiTotemHelper.getTargetPos()))) {
            final float damage = this.checkPos((Entity)entity);
            if (damage <= -1000.0f) {
                final MutableWrapper<Boolean> a = new MutableWrapper<Boolean>(false);
                ((AutoCrystal)this.module).rotation = ((AutoCrystal)this.module).rotationHelper.forBreaking((Entity)entity, a);
                event.addPostEvent(() -> {
                    if (ListenerSpawnObject.mc.world != null) {
                        final Entity e2 = ListenerSpawnObject.mc.world.getEntityByID(packet.getEntityID());
                        if (e2 != null) {
                            ((AutoCrystal)this.module).post.add(((AutoCrystal)this.module).rotationHelper.post(e2, a));
                            ((AutoCrystal)this.module).rotation = ((AutoCrystal)this.module).rotationHelper.forBreaking(e2, a);
                            ((AutoCrystal)this.module).setCrystal(e2);
                        }
                    }
                });
                return;
            }
            if (damage < 0.0f) {
                return;
            }
            this.attack(packet, event, entity, stamp.getDamage() <= ((AutoCrystal)this.module).slowBreakDamage.getValue());
        }
        else if (((AutoCrystal)this.module).asyncCalc.getValue() || ((AutoCrystal)this.module).alwaysCalc.getValue()) {
            final List<EntityPlayer> players = Managers.ENTITIES.getPlayers();
            if (players == null) {
                return;
            }
            final float self = this.checkPos((Entity)entity);
            if (self < 0.0f) {
                return;
            }
            boolean slow = true;
            boolean attack = false;
            for (final EntityPlayer player : players) {
                if (player != null && !EntityUtil.isDead((Entity)player)) {
                    if (player.getDistanceSq(x, y, z) > 144.0) {
                        continue;
                    }
                    if (Managers.FRIENDS.contains(player)) {
                        if (((AutoCrystal)this.module).antiFriendPop.getValue().shouldCalc(AntiFriendPop.Break) && ((AutoCrystal)this.module).damageHelper.getDamage(x, y, z, (EntityLivingBase)player) > EntityUtil.getHealth((EntityLivingBase)player) - 0.5f) {
                            attack = false;
                            break;
                        }
                        continue;
                    }
                    else {
                        final float dmg = ((AutoCrystal)this.module).damageHelper.getDamage(x, y, z, (EntityLivingBase)player);
                        if ((dmg <= self && (!((AutoCrystal)this.module).suicide.getValue() || dmg < ((AutoCrystal)this.module).minDamage.getValue())) || (dmg <= ((AutoCrystal)this.module).slowBreakDamage.getValue() && !((AutoCrystal)this.module).shouldDanger() && !((AutoCrystal)this.module).breakTimer.passed(((AutoCrystal)this.module).slowBreakDelay.getValue()))) {
                            continue;
                        }
                        slow = (slow && dmg <= ((AutoCrystal)this.module).slowBreakDamage.getValue());
                        attack = true;
                    }
                }
            }
            if (attack) {
                this.attack(packet, event, entity, slow);
            }
        }
        if (((AutoCrystal)this.module).spawnThread.getValue()) {
            ((AutoCrystal)this.module).threadHelper.schedulePacket(event);
        }
    }
    
    private void attack(final SPacketSpawnObject packet, final PacketEvent.Receive<?> event, final EntityEnderCrystal entityIn, final boolean slow) {
        final CPacketUseEntity p = new CPacketUseEntity((Entity)entityIn);
        final WeaknessSwitch w = HelperRotation.antiWeakness((AutoCrystal)this.module);
        if (w.needsSwitch() && (w.getSlot() == -1 || !((AutoCrystal)this.module).instantAntiWeak.getValue())) {
            return;
        }
        final int lastSlot = ListenerSpawnObject.mc.player.inventory.currentItem;
        final Runnable runnable = () -> {
            if (w.getSlot() != -1) {
                switch (((AutoCrystal)this.module).antiWeaknessBypass.getValue()) {
                    case None: {
                        InventoryUtil.switchTo(w.getSlot());
                        break;
                    }
                    case Slot: {
                        InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(w.getSlot()));
                        break;
                    }
                    case Pick: {
                        InventoryUtil.bypassSwitch(w.getSlot());
                        break;
                    }
                }
            }
            if (((AutoCrystal)this.module).breakSwing.getValue() == SwingTime.Pre) {
                Swing.Packet.swing(EnumHand.MAIN_HAND);
            }
            ListenerSpawnObject.mc.player.connection.sendPacket((Packet)p);
            if (((AutoCrystal)this.module).breakSwing.getValue() == SwingTime.Post) {
                Swing.Packet.swing(EnumHand.MAIN_HAND);
            }
            if (w.getSlot() != -1) {
                switch (((AutoCrystal)this.module).antiWeaknessBypass.getValue()) {
                    case None: {
                        InventoryUtil.switchTo(lastSlot);
                        break;
                    }
                    case Slot: {
                        InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(w.getSlot()));
                        break;
                    }
                    case Pick: {
                        InventoryUtil.bypassSwitch(w.getSlot());
                        break;
                    }
                }
            }
            return;
        };
        if (w.getSlot() != -1) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, runnable);
        }
        else {
            runnable.run();
        }
        ((AutoCrystal)this.module).breakTimer.reset(slow ? ((long)((AutoCrystal)this.module).slowBreakDelay.getValue()) : ((long)((AutoCrystal)this.module).breakDelay.getValue()));
        event.addPostEvent(() -> {
            final Entity entity = ListenerSpawnObject.mc.world.getEntityByID(packet.getEntityID());
            if (entity instanceof EntityEnderCrystal) {
                ((AutoCrystal)this.module).setCrystal(entity);
            }
            return;
        });
        if (((AutoCrystal)this.module).simulateExplosion.getValue()) {
            HelperUtil.simulateExplosion((AutoCrystal)this.module, packet.getX(), packet.getY(), packet.getZ());
        }
        if (((AutoCrystal)this.module).pseudoSetDead.getValue()) {
            event.addPostEvent(() -> {
                final Entity entity2 = ListenerSpawnObject.mc.world.getEntityByID(packet.getEntityID());
                if (entity2 != null) {
                    ((IEntity)entity2).setPseudoDead(true);
                }
            });
            return;
        }
        if (((AutoCrystal)this.module).instantSetDead.getValue()) {
            event.setCancelled(true);
            ListenerSpawnObject.mc.addScheduledTask(() -> {
                final Entity entity3 = ListenerSpawnObject.mc.world.getEntityByID(packet.getEntityID());
                if (entity3 instanceof EntityEnderCrystal) {
                    ((AutoCrystal)this.module).crystalRender.onSpawn((EntityEnderCrystal)entity3);
                }
                if (!(!event.isCancelled())) {
                    EntityTracker.updateServerPosition((Entity)entityIn, packet.getX(), packet.getY(), packet.getZ());
                    Managers.SET_DEAD.setDead((Entity)entityIn);
                }
            });
        }
    }
    
    private float checkPos(final Entity entity) {
        final BreakValidity validity = HelperUtil.isValid((AutoCrystal)this.module, entity);
        switch (validity) {
            case INVALID: {
                return -1.0f;
            }
            case ROTATIONS: {
                final float damage = this.getSelfDamage(entity);
                if (damage < 0.0f) {
                    return damage;
                }
                return -1000.0f - damage;
            }
            default: {
                return this.getSelfDamage(entity);
            }
        }
    }
    
    private float getSelfDamage(final Entity entity) {
        final float damage = ((AutoCrystal)this.module).damageHelper.getDamage(entity);
        if (damage > EntityUtil.getHealth((EntityLivingBase)ListenerSpawnObject.mc.player) - 1.0f || damage > ListenerSpawnObject.DMG.getValue()) {
            Managers.SAFETY.setSafe(false);
        }
        return (damage > ((AutoCrystal)this.module).maxSelfBreak.getValue() || (damage > EntityUtil.getHealth((EntityLivingBase)ListenerSpawnObject.mc.player) - 1.0f && !((AutoCrystal)this.module).suicide.getValue())) ? -1.0f : damage;
    }
    
    static {
        LEG_SWITCH = Caches.getModule(LegSwitch.class);
        ANTISURROUND = Caches.getModule(AntiSurround.class);
        DMG = Caches.getSetting(Safety.class, Setting.class, "MaxDamage", 4.0f);
    }
}
