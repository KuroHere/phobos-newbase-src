//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.killaura;

import me.earth.earthhack.impl.util.minecraft.entity.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.combat.killaura.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class KillAura extends EntityTypeModule
{
    protected final Setting<Boolean> passengers;
    protected final Setting<AuraTarget> targetMode;
    protected final Setting<Boolean> prioEnemies;
    protected final Setting<Double> range;
    protected final Setting<Double> wallRange;
    protected final Setting<Boolean> swordOnly;
    protected final Setting<Boolean> delay;
    protected final Setting<Float> cps;
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> stopSneak;
    protected final Setting<Boolean> stopSprint;
    protected final Setting<Boolean> stopShield;
    protected final Setting<Boolean> whileEating;
    protected final Setting<Boolean> stay;
    protected final Setting<Float> soft;
    protected final Setting<Integer> rotationTicks;
    protected final Setting<AuraTeleport> auraTeleport;
    protected final Setting<Double> teleportRange;
    protected final Setting<Boolean> yTeleport;
    protected final Setting<Boolean> movingTeleport;
    protected final Setting<Swing> swing;
    protected final Setting<Boolean> tps;
    protected final Setting<Boolean> t2k;
    protected final Setting<Float> health;
    protected final Setting<Integer> armor;
    protected final Setting<Float> targetRange;
    protected final Setting<Boolean> multi32k;
    protected final Setting<Integer> packets;
    protected final Setting<Double> height;
    protected final Setting<Boolean> ridingTeleports;
    protected final Setting<Boolean> efficient;
    protected final Setting<Boolean> cancelEntityEquip;
    protected final Setting<Boolean> tpInfo;
    protected final Setting<Integer> coolDown;
    protected final Setting<Boolean> m1Attack;
    protected final Setting<AuraSwitch> autoSwitch;
    protected final RotationSmoother rotationSmoother;
    protected final DiscreteTimer timer;
    protected boolean isTeleporting;
    protected boolean isAttacking;
    protected boolean ourCrit;
    protected Entity target;
    protected Vec3d eff;
    protected Vec3d pos;
    protected int slot;
    
    public KillAura() {
        super("KillAura", Category.Combat);
        this.passengers = this.register(new BooleanSetting("Passengers", false));
        this.targetMode = this.register(new EnumSetting("Target", AuraTarget.Closest));
        this.prioEnemies = this.register(new BooleanSetting("Enemies", true));
        this.range = this.register(new NumberSetting("Range", 6.0, 0.0, 6.0));
        this.wallRange = this.register(new NumberSetting("WallRange", 3.0, 0.0, 6.0));
        this.swordOnly = this.register(new BooleanSetting("Sword/Axe", true));
        this.delay = this.register(new BooleanSetting("Delay", true));
        this.cps = this.register(new NumberSetting("CPS", 20.0f, 0.1f, 100.0f));
        this.rotate = this.register(new BooleanSetting("Rotate", true));
        this.stopSneak = this.register(new BooleanSetting("Release-Sneak", true));
        this.stopSprint = this.register(new BooleanSetting("Release-Sprint", true));
        this.stopShield = this.register(new BooleanSetting("AutoBlock", true));
        this.whileEating = this.register(new BooleanSetting("While-Eating", true));
        this.stay = this.register(new BooleanSetting("Stay", false));
        this.soft = this.register(new NumberSetting("Soft", 180.0f, 0.1f, 180.0f));
        this.rotationTicks = this.register(new NumberSetting("Rotation-Ticks", 0, 0, 10));
        this.auraTeleport = this.register(new EnumSetting("Teleport", AuraTeleport.None));
        this.teleportRange = this.register(new NumberSetting("TP-Range", 0.0, 0.0, 100.0));
        this.yTeleport = this.register(new BooleanSetting("Y-Teleport", false));
        this.movingTeleport = this.register(new BooleanSetting("Move-Teleport", false));
        this.swing = this.register(new EnumSetting("Swing", Swing.Full));
        this.tps = this.register(new BooleanSetting("TPS-Sync", true));
        this.t2k = this.register(new BooleanSetting("Fast-32ks", true));
        this.health = this.register(new NumberSetting("Health", 0.0f, 0.0f, 15.0f));
        this.armor = this.register(new NumberSetting("Armor", 0, 0, 100));
        this.targetRange = this.register(new NumberSetting("Target-Range", 10.0f, 0.0f, 20.0f));
        this.multi32k = this.register(new BooleanSetting("Multi-32k", false));
        this.packets = this.register(new NumberSetting("Packets", 1, 0, 20));
        this.height = this.register(new NumberSetting("Height", 1.0, 0.0, 1.0));
        this.ridingTeleports = this.register(new BooleanSetting("Riding-Teleports", false));
        this.efficient = this.register(new BooleanSetting("Efficient", false));
        this.cancelEntityEquip = this.register(new BooleanSetting("NoEntityEquipment", false));
        this.tpInfo = this.register(new BooleanSetting("TP-Info", false));
        this.coolDown = this.register(new NumberSetting("Cooldown", 0, 0, 500));
        this.m1Attack = this.register(new BooleanSetting("Hold-Mouse", false));
        this.autoSwitch = this.register(new EnumSetting("AutoSwitch", AuraSwitch.None));
        this.rotationSmoother = new RotationSmoother(Managers.ROTATION);
        this.timer = new GuardTimer();
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerRiding(this));
        this.listeners.add(new ListenerGameLoop(this));
        this.listeners.add(new ListenerEntityEquipment(this));
        this.setData(new KillAuraData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.target == null || EntityUtil.isDead(this.target)) {
            return null;
        }
        final double distance = KillAura.mc.player.getDistanceSqToEntity(this.target);
        if (distance > MathUtil.square(this.targetRange.getValue()) || (!this.shouldAttack() && (!this.tpInfo.getValue() || this.teleportRange.getValue() == 0.0 || this.auraTeleport.getValue() != AuraTeleport.Smart))) {
            return null;
        }
        final StringBuilder name = new StringBuilder(EntityNames.getName(this.target)).append("§7").append(", ");
        if (distance >= 36.0) {
            name.append("§c");
        }
        else if (!RotationUtil.getRotationPlayer().canEntityBeSeen(this.target) && distance >= 9.0) {
            if (this.target instanceof EntityPlayer && ((EntityPlayer)this.target).canEntityBeSeen((Entity)RotationUtil.getRotationPlayer())) {
                name.append("§f");
            }
            else {
                name.append("§6");
            }
        }
        else {
            name.append("§a");
        }
        return name.append(MathUtil.round(Math.sqrt(distance), 2)).toString();
    }
    
    @Override
    public boolean isValid(final Entity entity) {
        return entity != null && KillAura.mc.player.getDistanceSqToEntity(entity) <= MathUtil.square(this.targetRange.getValue()) && !EntityUtil.isDead(entity) && !entity.equals((Object)KillAura.mc.player) && !entity.equals((Object)KillAura.mc.player.getRidingEntity()) && (!(entity instanceof EntityPlayer) || !Managers.FRIENDS.contains((EntityPlayer)entity)) && (this.passengers.getValue() || !KillAura.mc.player.getPassengers().contains(entity)) && !(entity instanceof EntityExpBottle) && !(entity instanceof EntityItem) && !(entity instanceof EntityArrow) && !(entity instanceof EntityEnderCrystal) && super.isValid(entity);
    }
    
    public Entity getTarget() {
        return this.target;
    }
    
    protected Entity findTarget() {
        Entity closest = null;
        Entity bestEnemy = null;
        double bestAngle = 360.0;
        float lowest = Float.MAX_VALUE;
        double distance = Double.MAX_VALUE;
        double closestEnemy = Double.MAX_VALUE;
        for (final Entity entity : KillAura.mc.world.loadedEntityList) {
            if (!this.isValid(entity)) {
                continue;
            }
            final double dist = KillAura.mc.player.getDistanceSqToEntity(entity);
            if (this.targetMode.getValue() == AuraTarget.Angle) {
                final double angle = RotationUtil.getAngle(entity, 1.75);
                if (angle >= bestAngle || Math.sqrt(dist) - this.teleportRange.getValue() >= 6.0) {
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
                    if (this.health.getValue() != 0.0f && entity instanceof EntityLivingBase) {
                        final float h = EntityUtil.getHealth((EntityLivingBase)entity);
                        if (h < this.health.getValue() && h < lowest) {
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
        return (bestEnemy != null) ? bestEnemy : closest;
    }
    
    public boolean isInRange(final Entity from, final Entity target) {
        return this.isInRange(from.getPositionVector(), target);
    }
    
    public boolean isInRange(final Vec3d from, final Entity target) {
        final double distance = from.squareDistanceTo(target.getPositionVector());
        return distance < MathUtil.square(this.range.getValue()) && (distance < MathUtil.square(this.wallRange.getValue()) || KillAura.mc.world.rayTraceBlocks(new Vec3d(from.xCoord, from.yCoord + KillAura.mc.player.getEyeHeight(), from.zCoord), new Vec3d(target.posX, target.posY + target.getEyeHeight(), target.posZ), false, true, false) == null);
    }
    
    protected boolean shouldAttack() {
        return (!this.m1Attack.getValue() || Mouse.isButtonDown(0)) && (!this.swordOnly.getValue() || KillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword || KillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe);
    }
    
    protected void releaseShield() {
        if (KillAura.mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
            KillAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos((Entity)KillAura.mc.player), EnumFacing.getFacingFromVector((float)Managers.POSITION.getX(), (float)Managers.POSITION.getY(), (float)Managers.POSITION.getZ())));
        }
    }
    
    protected void useShield() {
        if ((KillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword || KillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe) && KillAura.mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> KillAura.mc.playerController.processRightClick((EntityPlayer)KillAura.mc.player, (World)KillAura.mc.world, EnumHand.OFF_HAND));
        }
    }
    
    public Vec3d criticalCallback(final Vec3d vec3d) {
        if (this.isEnabled() && this.ourCrit) {
            if (this.eff != null) {
                return this.eff;
            }
            switch (this.auraTeleport.getValue()) {
                case Smart: {
                    if (this.isTeleporting && this.pos != null) {
                        return this.pos;
                    }
                    break;
                }
                case Full: {
                    return Managers.POSITION.getVec();
                }
            }
        }
        return vec3d;
    }
}
