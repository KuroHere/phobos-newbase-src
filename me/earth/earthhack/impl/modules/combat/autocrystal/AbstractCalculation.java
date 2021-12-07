//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.impl.util.helpers.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.offhand.*;
import me.earth.earthhack.impl.modules.combat.legswitch.*;
import me.earth.earthhack.impl.modules.player.speedmine.*;
import me.earth.earthhack.impl.modules.combat.antisurround.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.helpers.*;
import java.util.function.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.combat.offhand.modes.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import java.util.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.modules.*;

public abstract class AbstractCalculation<T extends CrystalData> extends Finishable implements Globals
{
    protected static final ModuleCache<Offhand> OFFHAND;
    protected static final ModuleCache<LegSwitch> LEG_SWITCH;
    protected static final ModuleCache<Speedmine> SPEEDMINE;
    protected static final ModuleCache<AntiSurround> ANTISURROUND;
    protected final Set<BlockPos> blackList;
    protected final List<Entity> entities;
    protected final AutoCrystal module;
    protected final List<EntityPlayer> raw;
    protected double maxY;
    protected List<EntityPlayer> friends;
    protected List<EntityPlayer> enemies;
    protected List<EntityPlayer> players;
    protected List<EntityPlayer> all;
    protected BreakData<T> breakData;
    protected PlaceData placeData;
    protected boolean scheduling;
    protected boolean attacking;
    protected boolean noPlace;
    protected boolean noBreak;
    protected boolean rotating;
    protected boolean placing;
    protected boolean fallback;
    protected int motionID;
    protected int count;
    
    public AbstractCalculation(final AutoCrystal module, final List<Entity> entities, final List<EntityPlayer> players, final BlockPos... blackList) {
        this.maxY = Double.MAX_VALUE;
        this.noPlace = false;
        this.noBreak = false;
        this.motionID = module.motionID.get();
        if (blackList.length == 0) {
            this.blackList = new EmptySet<BlockPos>();
        }
        else {
            this.blackList = new HashSet<BlockPos>();
            for (final BlockPos pos : blackList) {
                if (pos != null) {
                    this.blackList.add(pos);
                }
            }
        }
        this.module = module;
        this.entities = entities;
        this.raw = players;
    }
    
    public AbstractCalculation(final AutoCrystal module, final List<Entity> entities, final List<EntityPlayer> players, final boolean breakOnly, final boolean noBreak, final BlockPos... blackList) {
        this(module, entities, players, blackList);
        this.noPlace = breakOnly;
        this.noBreak = noBreak;
    }
    
    protected abstract IBreakHelper<T> getBreakHelper();
    
    @Override
    protected void execute() {
        try {
            if (this.module.clearPost.getValue()) {
                this.module.post.clear();
            }
            this.runCalc();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private void runCalc() {
        if (this.check()) {
            return;
        }
        if (this.module.forceAntiTotem.getValue() && this.module.antiTotem.getValue() && this.checkForceAntiTotem()) {
            return;
        }
        final float minDamage = this.module.getMinDamage();
        if (this.module.focusRotations.getValue() && !this.module.rotate.getValue().noRotate(ACRotate.Break) && this.focusBreak()) {
            return;
        }
        this.module.focus = null;
        if (this.breakData == null && this.breakCheck()) {
            this.setCount(this.breakData = this.getBreakHelper().getData(this.getBreakDataSet(), this.entities, this.all, this.friends), minDamage);
            if (this.evaluate(this.breakData)) {
                return;
            }
        }
        else if (this.module.multiPlaceCalc.getValue()) {
            if (this.breakData != null) {
                this.setCount(this.breakData, minDamage);
                this.breakData = null;
            }
            else {
                final BreakData<T> onlyForCountData = this.getBreakHelper().getData(new ArrayList<T>(5), this.entities, this.all, this.friends);
                this.setCount(onlyForCountData, minDamage);
            }
        }
        if (this.placeCheck()) {
            this.placeData = this.module.placeHelper.getData(this.all, this.players, this.enemies, this.friends, this.entities, minDamage, this.blackList, this.maxY);
            if (AbstractCalculation.LEG_SWITCH.returnIfPresent(LegSwitch::isActive, false) || AbstractCalculation.ANTISURROUND.returnIfPresent(AntiSurround::isActive, false)) {
                return;
            }
            if (this.place(this.placeData)) {
                final boolean passed = this.module.obbyCalcTimer.passed(this.module.obbyCalc.getValue());
                if (this.obbyCheck() && passed && this.placeObby(this.placeData, null)) {
                    return;
                }
                if (this.preSpecialCheck() && (!this.module.requireOnGround.getValue() || RotationUtil.getRotationPlayer().onGround) && (this.module.interruptSpeedmine.getValue() || !AbstractCalculation.SPEEDMINE.isEnabled() || AbstractCalculation.SPEEDMINE.get().getPos() == null) && (!this.module.pickaxeOnly.getValue() || AbstractCalculation.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) && this.module.liquidTimer.passed(this.module.liqDelay.getValue()) && (this.module.lava.getValue() || this.module.water.getValue())) {
                    final MineSlots mineSlots = HelperLiquids.getSlots(this.module.requireOnGround.getValue());
                    if (mineSlots.getBlockSlot() == -1 || mineSlots.getDamage() < 1.0f) {
                        return;
                    }
                    final PlaceData liquidData = this.module.liquidHelper.calculate(this.module.placeHelper, this.placeData, this.friends, this.all, this.module.minDamage.getValue());
                    if (AbstractCalculation.LEG_SWITCH.returnIfPresent(LegSwitch::isActive, false) || AbstractCalculation.ANTISURROUND.returnIfPresent(AntiSurround::isActive, false)) {
                        return;
                    }
                    final boolean attackingBefore = this.attacking;
                    if (this.placeNoAntiTotem(liquidData, mineSlots) && attackingBefore == this.attacking && this.module.liquidObby.getValue() && this.obbyCheck() && passed) {
                        this.placeObby(this.placeData, mineSlots);
                    }
                }
            }
        }
    }
    
    protected void setCount(final BreakData<T> breakData, final float minDmg) {
        if (this.module.multiPlaceMinDmg.getValue()) {
            this.count = (int)breakData.getData().stream().filter(d -> d.getDamage() > minDmg).count();
            return;
        }
        this.count = breakData.getData().size();
    }
    
    protected boolean evaluate(final BreakData<T> breakData) {
        final boolean shouldDanger = this.module.shouldDanger();
        boolean slowReset = !shouldDanger;
        BreakValidity validity;
        if (this.breakData.getAntiTotem() != null && (validity = HelperUtil.isValid(this.module, this.breakData.getAntiTotem())) != BreakValidity.INVALID) {
            this.attack(this.breakData.getAntiTotem(), validity);
            this.module.breakTimer.reset(this.module.breakDelay.getValue());
            this.module.antiTotemHelper.setTarget(null);
            this.module.antiTotemHelper.setTargetPos(null);
        }
        else {
            final int packets = this.module.rotate.getValue().noRotate(ACRotate.Break) ? this.module.packets.getValue() : 1;
            T firstRotation = null;
            final List<T> valids = new ArrayList<T>(packets);
            for (final T data : this.breakData.getData()) {
                validity = HelperUtil.isValid(this.module, data.getCrystal());
                if (validity == BreakValidity.VALID && valids.size() < packets) {
                    valids.add(data);
                }
                else {
                    if (validity != BreakValidity.ROTATIONS || firstRotation != null) {
                        continue;
                    }
                    firstRotation = data;
                }
            }
            final int slowDelay = this.module.slowBreakDelay.getValue();
            final float slow = this.module.slowBreakDamage.getValue();
            if (valids.isEmpty()) {
                if (firstRotation != null && (this.module.shouldDanger() || !(slowReset = (firstRotation.getDamage() <= slow)) || this.module.breakTimer.passed(slowDelay))) {
                    this.attack(firstRotation.getCrystal(), BreakValidity.ROTATIONS);
                }
            }
            else {
                for (final T valid : valids) {
                    final boolean high = valid.getDamage() > this.module.slowBreakDamage.getValue();
                    if (high || shouldDanger || this.module.breakTimer.passed(this.module.slowBreakDelay.getValue())) {
                        slowReset = (slowReset && !high);
                        this.attack(valid.getCrystal(), BreakValidity.VALID);
                    }
                }
            }
        }
        if (this.attacking) {
            this.module.breakTimer.reset(slowReset ? ((long)this.module.slowBreakDelay.getValue()) : ((long)this.module.breakDelay.getValue()));
        }
        return this.rotating && !this.module.rotate.getValue().noRotate(ACRotate.Place);
    }
    
    protected boolean breakCheck() {
        return this.module.attack.getValue() && !this.noBreak && Managers.SWITCH.getLastSwitch() >= this.module.cooldown.getValue() && this.module.breakTimer.passed(this.module.breakDelay.getValue());
    }
    
    protected boolean placeCheck() {
        if (this.module.damageSync.getValue()) {
            final Confirmer c = this.module.damageSyncHelper.getConfirmer();
            if (c.isValid() && (!c.isPlaceConfirmed(this.module.placeConfirm.getValue()) || !c.isBreakConfirmed(this.module.breakConfirm.getValue())) && c.isValid() && this.module.preSynCheck.getValue()) {
                return false;
            }
        }
        return this.count < this.module.multiPlace.getValue() && Managers.SWITCH.getLastSwitch() >= this.module.placeCoolDown.getValue() && this.module.place.getValue() && (!this.attacking || this.module.multiTask.getValue()) && (!this.rotating || this.module.rotate.getValue().noRotate(ACRotate.Place)) && this.module.placeTimer.passed(this.module.placeDelay.getValue()) && !this.noPlace;
    }
    
    protected boolean obbyCheck() {
        return this.preSpecialCheck() && this.module.obsidian.getValue() && this.module.obbyTimer.passed(this.module.obbyDelay.getValue());
    }
    
    protected boolean preSpecialCheck() {
        return !this.placing && this.placeData != null && (this.placeData.getTarget() != null || this.module.targetMode.getValue() == Target.Damage) && !this.fallback;
    }
    
    protected boolean check() {
        if ((!this.module.spectator.getValue() && AbstractCalculation.mc.player.isSpectator()) || AbstractCalculation.ANTISURROUND.returnIfPresent(AntiSurround::isActive, false) || AbstractCalculation.LEG_SWITCH.returnIfPresent(LegSwitch::isActive, false) || this.raw == null || this.entities == null) {
            return true;
        }
        this.setFriendsAndEnemies();
        return this.all.isEmpty() || this.module.isPingBypass() || (!this.module.attackMode.getValue().shouldCalc() && this.module.autoSwitch.getValue() != AutoSwitch.Always && !this.module.weaknessHelper.canSwitch() && !this.module.switching) || (this.module.weaknessHelper.isWeaknessed() && this.module.antiWeakness.getValue() == AntiWeakness.None);
    }
    
    protected void setFriendsAndEnemies() {
        final List<List<EntityPlayer>> split = CollectionUtil.split(this.raw, p -> p == null || EntityUtil.isDead((Entity)p) || p.equals((Object)AbstractCalculation.mc.player) || p.getDistanceSqToEntity((Entity)AbstractCalculation.mc.player) > MathUtil.square(this.module.targetRange.getValue()) || (DamageUtil.cacheLowestDura((EntityLivingBase)p) && this.module.antiNaked.getValue()), Managers.FRIENDS::contains, Managers.ENEMIES::contains);
        this.friends = split.get(1);
        this.enemies = split.get(2);
        this.players = split.get(3);
        (this.all = new ArrayList<EntityPlayer>(this.enemies.size() + this.players.size())).addAll(this.enemies);
        this.all.addAll(this.players);
        if (this.module.yCalc.getValue()) {
            this.maxY = Double.MIN_VALUE;
            for (final EntityPlayer player : this.all) {
                if (player.posY > this.maxY) {
                    this.maxY = player.posY;
                }
            }
        }
    }
    
    protected boolean attack(final Entity entity, final BreakValidity validity) {
        this.module.setCrystal(entity);
        switch (validity) {
            case VALID: {
                if (!this.module.weaknessHelper.isWeaknessed()) {
                    if (this.module.breakSwing.getValue() == SwingTime.Pre) {
                        Swing.Packet.swing(EnumHand.MAIN_HAND);
                    }
                    AbstractCalculation.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
                    if (this.module.pseudoSetDead.getValue()) {
                        ((IEntity)entity).setPseudoDead(true);
                    }
                    else if (this.module.setDead.getValue()) {
                        Managers.SET_DEAD.setDead(entity);
                    }
                    if (this.module.breakSwing.getValue() == SwingTime.Post) {
                        Swing.Packet.swing(EnumHand.MAIN_HAND);
                    }
                    Swing.Client.swing(EnumHand.MAIN_HAND);
                    this.attacking = true;
                    if (!this.module.rotate.getValue().noRotate(ACRotate.Break)) {
                        this.module.rotation = this.module.rotationHelper.forBreaking(entity, new MutableWrapper<Boolean>(true));
                    }
                    return true;
                }
                if (this.module.antiWeakness.getValue() == AntiWeakness.None) {
                    return false;
                }
                final Runnable r = this.module.rotationHelper.post(entity, new MutableWrapper<Boolean>(false));
                r.run();
                this.attacking = true;
                if (!this.module.rotate.getValue().noRotate(ACRotate.Break)) {
                    this.module.rotation = this.module.rotationHelper.forBreaking(entity, new MutableWrapper<Boolean>(true));
                }
                return true;
            }
            case ROTATIONS: {
                this.attacking = true;
                this.rotating = true;
                final MutableWrapper<Boolean> attacked = new MutableWrapper<Boolean>(false);
                final Runnable post = this.module.rotationHelper.post(entity, attacked);
                final RotationFunction function = this.module.rotationHelper.forBreaking(entity, attacked);
                if (this.module.multiThread.getValue() && this.module.rotationThread.getValue() == RotationThread.Cancel && this.module.rotationCanceller.setRotations(function) && HelperUtil.isValid(this.module, entity) == BreakValidity.VALID) {
                    this.rotating = false;
                    post.run();
                }
                else {
                    this.module.rotation = function;
                    this.module.post.add(post);
                }
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    protected boolean checkForceAntiTotem() {
        final BlockPos forcePos = this.module.forceHelper.getPos();
        if (forcePos != null && this.module.forceHelper.isForcing(this.module.syncForce.getValue())) {
            for (final Entity entity : this.entities) {
                if (entity instanceof EntityEnderCrystal && !EntityUtil.isDead(entity) && entity.getEntityBoundingBox().intersectsWith(new AxisAlignedBB(forcePos.up()))) {
                    this.attack(entity, HelperUtil.isValid(this.module, entity));
                    return true;
                }
            }
            return true;
        }
        return false;
    }
    
    protected boolean place(final PlaceData data) {
        AntiTotemData antiTotem = null;
        final boolean god = this.module.godAntiTotem.getValue() && this.module.idHelper.isSafe(this.raw, this.module.holdingCheck.getValue(), this.module.toolCheck.getValue());
        for (final AntiTotemData antiTotemData : data.getAntiTotem()) {
            if (!antiTotemData.getCorresponding().isEmpty() && !antiTotemData.isBlocked()) {
                final BlockPos pos = antiTotemData.getPos();
                final Entity entity = (Entity)new EntityEnderCrystal((World)AbstractCalculation.mc.world, (double)(pos.getX() + 0.5f), (double)(pos.getY() + 1), (double)(pos.getZ() + 0.5f));
                if (god) {
                    for (final PositionData positionData : antiTotemData.getCorresponding()) {
                        if (positionData.isBlocked()) {
                            continue;
                        }
                        final BlockPos up = positionData.getPos().up();
                        final double y = this.module.newVerEntities.getValue() ? 1.0 : 2.0;
                        if (entity.getEntityBoundingBox().intersectsWith(new AxisAlignedBB((double)up.getX(), (double)up.getY(), (double)up.getZ(), up.getX() + 1.0, up.getY() + y, up.getZ() + 1.0))) {
                            continue;
                        }
                        if (this.module.totemSync.getValue() && this.module.damageSyncHelper.isSyncing(0.0f, true)) {
                            return false;
                        }
                        this.module.noGod = true;
                        this.module.antiTotemHelper.setTargetPos(antiTotemData.getPos());
                        final EntityPlayer player = antiTotemData.getFirstTarget();
                        Earthhack.getLogger().info("Attempting God-AntiTotem: " + ((player == null) ? "null" : player.getName()));
                        this.place(antiTotemData, player, false, false, false);
                        this.module.idHelper.attack(this.module.breakSwing.getValue(), this.module.godSwing.getValue(), 1, this.module.idPackets.getValue(), 0);
                        this.place(positionData, player, false, false, false);
                        this.module.idHelper.attack(this.module.breakSwing.getValue(), this.module.godSwing.getValue(), 2, this.module.idPackets.getValue(), 0);
                        this.module.breakTimer.reset(this.module.breakDelay.getValue());
                        return this.module.noGod = false;
                    }
                }
                if (antiTotem != null) {
                    continue;
                }
                antiTotem = antiTotemData;
                if (!god) {
                    break;
                }
                continue;
            }
        }
        if (antiTotem == null) {
            if (this.module.forceAntiTotem.getValue() && this.module.antiTotem.getValue() && this.module.forceTimer.passed(this.module.attempts.getValue())) {
                for (final Map.Entry<EntityPlayer, ForceData> entry : data.getForceData().entrySet()) {
                    final ForceData forceData = entry.getValue();
                    final PositionData first = forceData.getForceData().stream().findFirst().orElse(null);
                    if (first != null && forceData.hasPossibleAntiTotem()) {
                        if (!forceData.hasPossibleHighDamage()) {
                            continue;
                        }
                        if (this.module.syncForce.getValue() && this.module.damageSyncHelper.isSyncing(0.0f, true)) {
                            return false;
                        }
                        this.module.forceHelper.setSync(first.getPos(), this.module.newVerEntities.getValue());
                        this.place(first, entry.getKey(), !this.module.rotate.getValue().noRotate(ACRotate.Place), this.rotating || this.scheduling, this.module.forceSlow.getValue());
                        this.module.forceTimer.reset();
                        return false;
                    }
                }
            }
            return this.placeNoAntiTotem(data, null);
        }
        final EntityPlayer player2 = antiTotem.getFirstTarget();
        this.module.setTarget(player2);
        if (this.module.totemSync.getValue() && this.module.damageSyncHelper.isSyncing(0.0f, true)) {
            return false;
        }
        Earthhack.getLogger().info("Attempting AntiTotem: " + ((player2 == null) ? "null" : player2.getName()));
        this.module.antiTotemHelper.setTargetPos(antiTotem.getPos());
        this.place(antiTotem, player2, !this.module.rotate.getValue().noRotate(ACRotate.Place), this.rotating || this.scheduling, false);
        return false;
    }
    
    protected boolean placeNoAntiTotem(final PlaceData data, final MineSlots liquid) {
        float maxBlockedDamage = 0.0f;
        PositionData firstData = null;
        for (final PositionData d : data.getData()) {
            if (!d.isBlocked()) {
                firstData = d;
                break;
            }
            if (maxBlockedDamage >= d.getMaxDamage()) {
                continue;
            }
            maxBlockedDamage = d.getMaxDamage();
        }
        if (this.breakData != null && !this.attacking) {
            final Entity fallback = this.breakData.getFallBack();
            if (this.module.fallBack.getValue() && this.breakData.getFallBackDmg() < this.module.fallBackDmg.getValue() && fallback != null && maxBlockedDamage != 0.0f && (firstData == null || maxBlockedDamage - firstData.getMaxDamage() >= this.module.fallBackDiff.getValue())) {
                this.attack(fallback, HelperUtil.isValid(this.module, fallback));
                return false;
            }
        }
        if (firstData != null && !this.module.damageSyncHelper.isSyncing(firstData.getMaxDamage(), this.module.damageSync.getValue()) && (liquid == null || this.module.minDamage.getValue() <= firstData.getMaxDamage())) {
            boolean slow = false;
            if (firstData.getMaxDamage() <= this.module.slowPlaceDmg.getValue() && !this.module.shouldDanger()) {
                if (!this.module.placeTimer.passed(this.module.slowPlaceDelay.getValue())) {
                    return !this.module.damageSyncHelper.isSyncing(0.0f, this.module.damageSync.getValue());
                }
                slow = true;
            }
            MutableWrapper<Boolean> liquidBreak = null;
            if (liquid != null) {
                liquidBreak = this.placeAndBreakLiquid(firstData, liquid, this.rotating);
            }
            this.place(firstData, firstData.getTarget(), !this.module.rotate.getValue().noRotate(ACRotate.Place), this.rotating || this.scheduling, slow, slow ? firstData.getMaxDamage() : Float.MAX_VALUE, liquidBreak);
            return false;
        }
        return !this.module.damageSyncHelper.isSyncing(0.0f, this.module.damageSync.getValue());
    }
    
    protected void place(final PositionData data, final EntityPlayer target, final boolean rotate, final boolean schedule, final boolean resetSlow) {
        this.place(data, target, rotate, schedule, resetSlow, Float.MAX_VALUE, null);
    }
    
    protected void place(final PositionData data, final EntityPlayer target, final boolean rotate, final boolean schedule, final boolean resetSlow, final float damage, final MutableWrapper<Boolean> liquidBreak) {
        if (liquidBreak != null) {
            this.module.liquidTimer.reset();
        }
        this.module.placeTimer.reset(resetSlow ? ((long)this.module.slowPlaceDelay.getValue()) : ((long)this.module.placeDelay.getValue()));
        final BlockPos pos = data.getPos();
        final BlockPos crystalPos = new BlockPos((double)(pos.getX() + 0.5f), (double)(pos.getY() + 1), (double)(pos.getZ() + 0.5f));
        this.module.placed.put(crystalPos, new CrystalTimeStamp(damage));
        this.module.damageSyncHelper.setSync(pos, data.getMaxDamage(), this.module.newVerEntities.getValue());
        this.module.setTarget(target);
        final boolean realtime = this.module.realtime.getValue();
        if (!realtime) {
            this.module.setRenderPos(pos, data.getMaxDamage());
        }
        final MutableWrapper<Boolean> hasPlaced = new MutableWrapper<Boolean>(false);
        if (!InventoryUtil.isHolding(Items.END_CRYSTAL) && (this.module.autoSwitch.getValue() == AutoSwitch.Always || (this.module.autoSwitch.getValue() == AutoSwitch.Bind && this.module.switching)) && !this.module.mainHand.getValue()) {
            AbstractCalculation.mc.addScheduledTask(() -> AbstractCalculation.OFFHAND.computeIfPresent(o -> o.setMode(OffhandMode.CRYSTAL)));
        }
        final Runnable post = this.module.rotationHelper.post(this.module, data.getMaxDamage(), realtime, pos, hasPlaced, liquidBreak);
        if (rotate) {
            final RotationFunction function = this.module.rotationHelper.forPlacing(pos, hasPlaced);
            if (this.module.rotationCanceller.setRotations(function)) {
                this.module.runPost();
                post.run();
                if (this.module.attack.getValue() && hasPlaced.get()) {
                    this.module.rotation = function;
                }
                return;
            }
            this.module.rotation = function;
        }
        if (schedule || !this.placeCheckPre(pos)) {
            this.module.post.add(post);
        }
        else {
            post.run();
        }
    }
    
    protected boolean placeObby(final PlaceData data, final MineSlots liquid) {
        final PositionData bestData = this.module.obbyHelper.findBestObbyData((liquid != null) ? data.getLiquidObby() : data.getAllObbyData(), this.all, this.friends, this.entities, data.getTarget(), this.module.newVer.getValue());
        if (AbstractCalculation.LEG_SWITCH.returnIfPresent(LegSwitch::isActive, false) || AbstractCalculation.ANTISURROUND.returnIfPresent(AntiSurround::isActive, false)) {
            return true;
        }
        this.module.obbyCalcTimer.reset();
        if (bestData != null && bestData.getMaxDamage() > this.module.obbyMinDmg.getValue()) {
            this.module.setTarget(bestData.getTarget());
            if (this.module.obbyRotate.getValue() != Rotate.None && !this.rotating && bestData.getPath().length > 0) {
                this.module.rotation = this.module.rotationHelper.forObby(bestData);
                this.rotating = true;
            }
            final Runnable r = this.module.rotationHelper.postBlock(bestData);
            if (!this.rotating) {
                r.run();
            }
            else {
                this.module.post.add(r);
            }
            if (liquid != null) {
                this.placeAndBreakLiquid(bestData, liquid, this.rotating);
            }
            this.place(bestData, bestData.getTarget(), !this.module.rotate.getValue().noRotate(ACRotate.Place), this.rotating || this.scheduling, false);
            this.module.obbyTimer.reset();
            return true;
        }
        return false;
    }
    
    @Override
    public void setFinished(final boolean finished) {
        if (this.module.multiThread.getValue() && this.module.smartPost.getValue() && this.module.motionID.get() != this.motionID) {
            this.module.runPost();
        }
        super.setFinished(finished);
        if (finished) {
            synchronized (this.module) {
                this.module.notifyAll();
            }
        }
    }
    
    protected boolean placeCheckPre(final BlockPos pos) {
        final double x = Managers.POSITION.getX();
        final double y = Managers.POSITION.getY();
        final double z = Managers.POSITION.getZ();
        if (pos.distanceSqToCenter(x, y, z) >= MathUtil.square(this.module.placeRange.getValue())) {
            return false;
        }
        if (!this.module.rotate.getValue().noRotate(ACRotate.Place)) {
            final RayTraceResult result = RotationUtil.rayTraceTo(pos, (IBlockAccess)AbstractCalculation.mc.world);
            if (result == null || !result.getBlockPos().equals((Object)pos)) {
                return false;
            }
        }
        if (pos.distanceSqToCenter(x, y, z) >= MathUtil.square(this.module.placeTrace.getValue())) {
            final RayTraceResult result = RotationUtil.rayTraceTo(pos, (IBlockAccess)AbstractCalculation.mc.world, (b, p) -> true);
            return result != null && result.getBlockPos().equals((Object)pos);
        }
        return true;
    }
    
    protected MutableWrapper<Boolean> placeAndBreakLiquid(final PositionData data, final MineSlots liquid, final boolean rotating) {
        final boolean newVer = this.module.newVer.getValue();
        final boolean absorb = this.module.absorb.getValue();
        final List<Ray> path = new ArrayList<Ray>((newVer ? 1 : 2) + (absorb ? 1 : 0));
        final BlockStateHelper access = new BlockStateHelper();
        path.add(RayTraceFactory.rayTrace(data.getFrom(), data.getPos(), EnumFacing.UP, (IBlockAccess)access, Blocks.NETHERRACK.getDefaultState(), ((boolean)this.module.liquidRayTrace.getValue()) ? -1.0 : 2.0));
        final BlockPos up = data.getPos().up();
        access.addBlockState(up, Blocks.NETHERRACK.getDefaultState());
        final IBlockState upState = AbstractCalculation.mc.world.getBlockState(up);
        int[] order;
        if (!newVer && upState.getMaterial().isLiquid()) {
            path.add(RayTraceFactory.rayTrace(data.getFrom(), up, EnumFacing.UP, (IBlockAccess)access, Blocks.NETHERRACK.getDefaultState(), ((boolean)this.module.liquidRayTrace.getValue()) ? -1.0 : 2.0));
            access.addBlockState(up.up(), Blocks.NETHERRACK.getDefaultState());
            order = new int[] { 0, 1 };
        }
        else {
            order = new int[] { 0 };
        }
        if (absorb) {
            BlockPos absorpPos = up;
            EnumFacing absorbFacing = this.module.liquidHelper.getAbsorbFacing(absorpPos, this.entities, (IBlockAccess)access, this.module.placeRange.getValue());
            if (absorbFacing == null && !newVer) {
                absorpPos = up.up();
                absorbFacing = this.module.liquidHelper.getAbsorbFacing(absorpPos, this.entities, (IBlockAccess)access, this.module.placeRange.getValue());
            }
            if (absorbFacing != null) {
                path.add(RayTraceFactory.rayTrace(data.getFrom(), absorpPos, absorbFacing, (IBlockAccess)access, Blocks.NETHERRACK.getDefaultState(), ((boolean)this.module.liquidRayTrace.getValue()) ? -1.0 : 2.0));
                order = ((order.length == 2) ? new int[] { 2, 1, 0 } : new int[] { 1, 0 });
            }
        }
        final Ray[] pathArray = path.toArray(new Ray[0]);
        data.setPath(pathArray);
        data.setValid(true);
        final MutableWrapper<Boolean> placed = new MutableWrapper<Boolean>(false);
        final MutableWrapper<Integer> postBlock = new MutableWrapper<Integer>(-1);
        final Runnable r = this.module.rotationHelper.postBlock(data, liquid.getBlockSlot(), this.module.liqRotate.getValue(), placed, postBlock);
        final Runnable b = this.module.rotationHelper.breakBlock(liquid.getToolSlot(), placed, postBlock, order, pathArray);
        Runnable a = null;
        if (this.module.setAir.getValue()) {
            a = (() -> {
                path.iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final Ray ray = iterator.next();
                    AbstractCalculation.mc.world.setBlockState(ray.getPos().offset(ray.getFacing()), Blocks.AIR.getDefaultState());
                }
                return;
            });
        }
        if (rotating) {
            synchronized (this.module.post) {
                this.module.post.add(r);
                this.module.post.add(b);
                if (a != null) {
                    AbstractCalculation.mc.addScheduledTask(a);
                }
            }
        }
        else {
            r.run();
            b.run();
            if (a != null) {
                AbstractCalculation.mc.addScheduledTask(a);
            }
        }
        return placed;
    }
    
    protected boolean focusBreak() {
        final Entity focus = this.module.focus;
        if (focus == null) {
            return false;
        }
        if (EntityUtil.isDead(focus) || (Managers.POSITION.getDistanceSq(focus) > MathUtil.square(this.module.breakRange.getValue()) && RotationUtil.getRotationPlayer().getDistanceSqToEntity(focus) > MathUtil.square(this.module.breakRange.getValue()))) {
            this.module.focus = null;
            return false;
        }
        final double exponent = this.module.focusExponent.getValue();
        this.breakData = this.getBreakHelper().getData((Collection<T>)((this.module.focusAngleCalc.getValue() && exponent != 0.0) ? RotationComparator.asSet(exponent, this.module.focusDiff.getValue()) : this.getBreakDataSet()), this.entities, this.all, this.friends);
        final List<T> focusList = new ArrayList<T>(1);
        final BreakData<T> focusData = this.getBreakHelper().newData(focusList);
        T minData = null;
        double minAngle = Double.MAX_VALUE;
        for (final T data : this.breakData.getData()) {
            if (data.hasCachedRotations() && data.getAngle() < minAngle) {
                minAngle = data.getAngle();
                minData = data;
            }
            if (!data.getCrystal().equals((Object)focus)) {
                continue;
            }
            if (data.getSelfDmg() > this.module.maxSelfBreak.getValue() || data.getDamage() < this.module.minBreakDamage.getValue()) {
                return false;
            }
            focusData.getData().add(data);
        }
        final Optional<T> first = focusData.getData().stream().findFirst();
        if (!first.isPresent()) {
            this.module.focus = null;
            return false;
        }
        if (this.module.focusAngleCalc.getValue() && minData != null && !minData.equals(first.get())) {
            focusList.set(0, minData);
        }
        this.evaluate(focusData);
        return this.rotating || this.attacking;
    }
    
    protected Set<T> getBreakDataSet() {
        final double exponent = this.module.rotationExponent.getValue();
        if (Double.compare(exponent, 0.0) == 0 || this.module.rotate.getValue().noRotate(ACRotate.Break)) {
            return new TreeSet<T>();
        }
        return (Set<T>)RotationComparator.asSet(exponent, this.module.minRotDiff.getValue());
    }
    
    static {
        OFFHAND = Caches.getModule(Offhand.class);
        LEG_SWITCH = Caches.getModule(LegSwitch.class);
        SPEEDMINE = Caches.getModule(Speedmine.class);
        ANTISURROUND = Caches.getModule(AntiSurround.class);
    }
}
