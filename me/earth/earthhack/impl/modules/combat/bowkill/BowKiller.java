//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bowkill;

import me.earth.earthhack.impl.util.minecraft.entity.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.killaura.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.item.*;
import java.util.*;

public class BowKiller extends EntityTypeModule
{
    protected final Setting<AuraTarget> targetMode;
    protected final Setting<Boolean> cancelRotate;
    protected final Setting<Boolean> move;
    protected final Setting<Boolean> blink;
    protected final Setting<Boolean> staticS;
    protected final Setting<Boolean> always;
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> prioEnemies;
    protected final Setting<Boolean> silent;
    protected final Setting<Boolean> visCheck;
    protected final Setting<Boolean> oppSpotted;
    protected final Setting<Integer> runs;
    protected final Setting<Integer> buffer;
    protected final Setting<Integer> teleports;
    protected final Setting<Integer> interval;
    protected final Setting<Double> range;
    protected final Setting<Double> wallRange;
    protected final Setting<Float> targetRange;
    protected final Setting<Double> height;
    protected final Setting<Float> soft;
    protected final Setting<Integer> armor;
    protected int packetsSent;
    protected boolean cancelling;
    protected boolean needsMessage;
    protected boolean blockUnder;
    protected final RotationSmoother rotationSmoother;
    protected Entity target;
    protected final ArrayList<EntityData> entityDataArrayList;
    
    public BowKiller() {
        super("BowKiller", Category.Combat);
        this.targetMode = this.register(new EnumSetting("Target", AuraTarget.Closest));
        this.cancelRotate = this.register(new BooleanSetting("CancelRotate", false));
        this.move = this.register(new BooleanSetting("Move", false));
        this.blink = this.register(new BooleanSetting("Blink", true));
        this.staticS = this.register(new BooleanSetting("Static", true));
        this.always = this.register(new BooleanSetting("Always", false));
        this.rotate = this.register(new BooleanSetting("Rotate", true));
        this.prioEnemies = this.register(new BooleanSetting("Prio-Enemies", true));
        this.silent = this.register(new BooleanSetting("Silent", true));
        this.visCheck = this.register(new BooleanSetting("VisCheck", true));
        this.oppSpotted = this.register(new BooleanSetting("Opp-Spotted", false));
        this.runs = this.register(new NumberSetting("Runs", 8, 1, 200));
        this.buffer = this.register(new NumberSetting("Buffer", 10, 0, 200));
        this.teleports = this.register(new NumberSetting("Teleports", 0, 0, 100));
        this.interval = this.register(new NumberSetting("Interval", 25, 0, 100));
        this.range = this.register(new NumberSetting("Range", 15.0, 0.0, 30.0));
        this.wallRange = this.register(new NumberSetting("WallRange", 10.0, 0.0, 30.0));
        this.targetRange = this.register(new NumberSetting("Target-Range", 30.0f, 0.0f, 50.0f));
        this.height = this.register(new NumberSetting("Height", 1.0, 0.0, 1.0));
        this.soft = this.register(new NumberSetting("Soft", 180.0f, 0.1f, 180.0f));
        this.armor = this.register(new NumberSetting("Armor", 0, 0, 100));
        this.packetsSent = 0;
        this.blockUnder = false;
        this.rotationSmoother = new RotationSmoother(Managers.ROTATION);
        this.entityDataArrayList = new ArrayList<EntityData>();
        this.listeners.addAll(new ListenerCPacket(this).getListeners());
        this.listeners.add(new ListenerMove(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerRightClick(this));
        this.listeners.add(new ListenerStopUsingItem(this));
        this.listeners.add(new ListenerEntityChunk(this));
    }
    
    @Override
    public String getDisplayInfo() {
        if (!this.cancelling) {
            return null;
        }
        if (this.packetsSent >= this.runs.getValue() * 2 || this.always.getValue()) {
            return "§a" + this.packetsSent;
        }
        return "§c" + this.packetsSent;
    }
    
    @Override
    protected void onEnable() {
        this.packetsSent = 0;
        this.cancelling = false;
        this.needsMessage = true;
    }
    
    protected void onPacket(final PacketEvent.Send<? extends CPacketPlayer> event) {
        if (!BowKiller.mc.player.onGround) {
            return;
        }
        if (this.blink.getValue() && this.cancelling) {
            event.setCancelled(true);
        }
    }
    
    protected Entity findTarget() {
        Entity closest = null;
        Entity bestEnemy = null;
        double bestAngle = 360.0;
        float lowest = Float.MAX_VALUE;
        double distance = Double.MAX_VALUE;
        double closestEnemy = Double.MAX_VALUE;
        for (final Entity entity : BowKiller.mc.world.loadedEntityList) {
            if (this.isValid(entity)) {
                if (BowKiller.mc.player.getDistanceSqToEntity(entity) > MathUtil.square(this.targetRange.getValue())) {
                    continue;
                }
                if (!BowKiller.mc.player.canEntityBeSeen(entity) && this.visCheck.getValue()) {
                    continue;
                }
                final double dist = BowKiller.mc.player.getDistanceSqToEntity(entity);
                if (this.targetMode.getValue() == AuraTarget.Angle) {
                    final double angle = RotationUtil.getAngle(entity, 1.75);
                    if (angle >= bestAngle) {
                        continue;
                    }
                    closest = entity;
                    bestAngle = angle;
                }
                else {
                    if (this.prioEnemies.getValue() && entity instanceof EntityPlayer && Managers.ENEMIES.contains((EntityPlayer)entity) && dist < closestEnemy) {
                        bestEnemy = entity;
                        closestEnemy = dist;
                    }
                    if (this.isInRange((Entity)RotationUtil.getRotationPlayer(), entity)) {
                        if (entity instanceof EntityLivingBase) {
                            final float h = EntityUtil.getHealth((EntityLivingBase)entity);
                            if (h < lowest) {
                                closest = entity;
                                distance = dist;
                                lowest = h;
                            }
                        }
                        if (this.armor.getValue() != 0) {
                            for (final ItemStack stack : entity.getArmorInventoryList()) {
                                if (!(stack.getItem() instanceof ItemElytra) && DamageUtil.getPercent(stack) < this.armor.getValue()) {
                                    closest = entity;
                                    distance = dist;
                                    break;
                                }
                            }
                        }
                    }
                    if (closest == null) {
                        closest = entity;
                        distance = dist;
                    }
                    else {
                        if (dist >= distance) {
                            continue;
                        }
                        closest = entity;
                        distance = dist;
                    }
                }
            }
        }
        return (bestEnemy != null) ? bestEnemy : closest;
    }
    
    public boolean isInRange(final Entity from, final Entity target) {
        return this.isInRange(from.getPositionVector(), target);
    }
    
    public boolean isInRange(final Vec3d from, final Entity target) {
        final double distance = from.squareDistanceTo(target.getPositionVector());
        return distance < MathUtil.square(this.range.getValue()) && (distance < MathUtil.square(this.wallRange.getValue()) || BowKiller.mc.world.rayTraceBlocks(new Vec3d(from.xCoord, from.yCoord + BowKiller.mc.player.getEyeHeight(), from.zCoord), new Vec3d(target.posX, target.posY + target.getEyeHeight(), target.posZ), false, true, false) == null);
    }
    
    @Override
    public boolean isValid(final Entity entity) {
        return entity != null && !EntityUtil.isDead(entity) && !entity.equals((Object)BowKiller.mc.player) && !entity.equals((Object)BowKiller.mc.player.getRidingEntity()) && (!(entity instanceof EntityPlayer) || !Managers.FRIENDS.contains((EntityPlayer)entity)) && !(entity instanceof EntityExpBottle) && !(entity instanceof EntityItem) && !(entity instanceof EntityArrow) && !(entity instanceof EntityEnderCrystal) && super.isValid(entity);
    }
    
    public boolean hasEntity(final String id) {
        return this.entityDataArrayList.stream().anyMatch(entityData -> Objects.equals(entityData.id, id));
    }
    
    public static class EntityData
    {
        private final String id;
        private final long time;
        
        public EntityData(final String id, final long time) {
            this.id = id;
            this.time = time;
        }
        
        public String getId() {
            return this.id;
        }
        
        public long getTime() {
            return this.time;
        }
    }
}
