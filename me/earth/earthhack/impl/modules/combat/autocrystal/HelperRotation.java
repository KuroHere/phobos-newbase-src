//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.concurrent.atomic.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.offhand.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.raytrace.*;
import me.earth.earthhack.impl.modules.combat.offhand.modes.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.modes.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.modules.*;

public class HelperRotation implements Globals
{
    private static final AtomicInteger ID;
    private static final ModuleCache<Offhand> OFFHAND;
    private final RotationSmoother smoother;
    private final AutoCrystal module;
    
    public HelperRotation(final AutoCrystal module) {
        this.smoother = new RotationSmoother(Managers.ROTATION);
        this.module = module;
    }
    
    public RotationFunction forPlacing(final BlockPos pos, final MutableWrapper<Boolean> hasPlaced) {
        final int id = HelperRotation.ID.incrementAndGet();
        final StopWatch timer = new StopWatch();
        final MutableWrapper<Boolean> ended = new MutableWrapper<Boolean>(false);
        return (x, y, z, yaw, pitch) -> {
            boolean breaking = false;
            float[] rotations = null;
            if (hasPlaced.get() || (RotationUtil.getRotationPlayer().getDistanceSq(pos) > 64.0 && pos.distanceSq(x, y, z) > 64.0) || (this.module.autoSwitch.getValue() != AutoSwitch.Always && !this.module.switching && !this.module.weaknessHelper.canSwitch() && !InventoryUtil.isHolding(Items.END_CRYSTAL))) {
                if (!ended.get()) {
                    ended.set(true);
                    timer.reset();
                }
                if (!this.module.attack.getValue() || timer.passed(this.module.endRotations.getValue())) {
                    if (id == HelperRotation.ID.get()) {
                        this.module.rotation = null;
                    }
                    return new float[] { yaw, pitch };
                }
                else {
                    breaking = true;
                    final double height = 1.7 * this.module.height.getValue();
                    rotations = RotationUtil.getRotations(pos.getX() + 0.5f, pos.getY() + 1 + height, pos.getZ() + 0.5f, x, y, z, HelperRotation.mc.player.getEyeHeight());
                }
            }
            else {
                final double height2 = this.module.placeHeight.getValue();
                if (this.module.smartTrace.getValue()) {
                    EnumFacing.values();
                    final EnumFacing[] array;
                    final int length = array.length;
                    int i = 0;
                    while (i < length) {
                        final EnumFacing facing = array[i];
                        final Ray ray = RayTraceFactory.rayTrace((Entity)HelperRotation.mc.player, pos, facing, (IBlockAccess)HelperRotation.mc.world, Blocks.OBSIDIAN.getDefaultState(), this.module.traceWidth.getValue());
                        if (ray.isLegit()) {
                            rotations = ray.getRotations();
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
                if (rotations == null) {
                    if (this.module.fallbackTrace.getValue()) {
                        rotations = RotationUtil.getRotations(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, x, y, z, HelperRotation.mc.player.getEyeHeight());
                    }
                    else {
                        rotations = RotationUtil.getRotations(pos.getX() + 0.5, pos.getY() + height2, pos.getZ() + 0.5, x, y, z, HelperRotation.mc.player.getEyeHeight());
                    }
                }
            }
            return this.smoother.smoothen(rotations, breaking ? ((double)this.module.angle.getValue()) : ((double)this.module.placeAngle.getValue()));
        };
    }
    
    public RotationFunction forBreaking(final Entity entity, final MutableWrapper<Boolean> attacked) {
        final int id = HelperRotation.ID.incrementAndGet();
        final StopWatch timer = new StopWatch();
        final MutableWrapper<Boolean> ended = new MutableWrapper<Boolean>(false);
        return (x, y, z, yaw, pitch) -> {
            if (RotationUtil.getRotationPlayer().getDistanceSqToEntity(entity) > 64.0) {
                attacked.set(true);
            }
            if (attacked.get()) {
                if (!ended.get()) {
                    ended.set(true);
                    timer.reset();
                }
                if (ended.get() && timer.passed(this.module.endRotations.getValue())) {
                    if (id == HelperRotation.ID.get()) {
                        this.module.rotation = null;
                    }
                    return new float[] { yaw, pitch };
                }
            }
            return this.smoother.getRotations(entity, x, y, z, HelperRotation.mc.player.getEyeHeight(), this.module.height.getValue(), this.module.angle.getValue());
        };
    }
    
    public RotationFunction forObby(final PositionData data) {
        return (x, y, z, yaw, pitch) -> {
            if (data.getPath().length <= 0) {
                return new float[] { yaw, pitch };
            }
            else {
                final Ray ray = data.getPath()[0];
                ray.updateRotations((Entity)RotationUtil.getRotationPlayer());
                return ray.getRotations();
            }
        };
    }
    
    public Runnable post(final AutoCrystal module, final float damage, final boolean realtime, final BlockPos pos, final MutableWrapper<Boolean> placed, final MutableWrapper<Boolean> liquidBreak) {
        return () -> {
            if (liquidBreak == null || liquidBreak.get()) {
                if (!InventoryUtil.isHolding(Items.END_CRYSTAL)) {
                    if (module.autoSwitch.getValue() == AutoSwitch.Always || (module.autoSwitch.getValue() == AutoSwitch.Bind && module.switching)) {
                        if (!module.mainHand.getValue()) {
                            HelperRotation.OFFHAND.computeIfPresent(o -> o.setMode(OffhandMode.CRYSTAL));
                            if (module.instantOffhand.getValue()) {
                                if (HelperRotation.OFFHAND.get().isSafe()) {
                                    HelperRotation.OFFHAND.get().setMode(OffhandMode.CRYSTAL);
                                    for (int i = 0; i < 3; ++i) {
                                        HelperRotation.OFFHAND.get().getTimer().setTime(10000L);
                                        HelperRotation.OFFHAND.get().doOffhand();
                                    }
                                }
                                if (!InventoryUtil.isHolding(Items.END_CRYSTAL)) {
                                    return;
                                }
                            }
                            else {
                                return;
                            }
                        }
                    }
                    else {
                        return;
                    }
                }
                int slot = -1;
                EnumHand hand = InventoryUtil.getHand(Items.END_CRYSTAL);
                if (hand == null) {
                    if (module.mainHand.getValue()) {
                        slot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
                        if (slot == -1) {
                            return;
                        }
                        else {
                            hand = ((slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                        }
                    }
                    else {
                        return;
                    }
                }
                RayTraceResult ray = RotationUtil.rayTraceTo(pos, (IBlockAccess)HelperRotation.mc.world);
                if (ray == null || !pos.equals((Object)ray.getBlockPos())) {
                    if (!module.rotate.getValue().noRotate(ACRotate.Place)) {
                        return;
                    }
                    else {
                        new RayTraceResult(new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP);
                        final RayTraceResult rayTraceResult;
                        ray = rayTraceResult;
                    }
                }
                else if (module.fallbackTrace.getValue() && HelperRotation.mc.world.getBlockState(ray.getBlockPos().offset(ray.sideHit)).getMaterial().isSolid()) {
                    new RayTraceResult(new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP);
                    final RayTraceResult rayTraceResult2;
                    ray = rayTraceResult2;
                }
                module.switching = false;
                final SwingTime swingTime = module.placeSwing.getValue();
                final float[] f = RayTraceUtil.hitVecToPlaceVec(pos, ray.hitVec);
                boolean noGodded = false;
                if (module.idHelper.isDangerous((EntityPlayer)HelperRotation.mc.player, module.holdingCheck.getValue(), module.toolCheck.getValue())) {
                    module.noGod = true;
                    noGodded = true;
                }
                final int finalSlot = slot;
                final EnumHand finalHand = hand;
                final RayTraceResult finalRay = ray;
                final boolean finalNoGodded = noGodded;
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                    final int lastSlot = HelperRotation.mc.player.inventory.currentItem;
                    if (finalSlot != -1 && finalSlot != -2) {
                        switch (module.cooldownBypass.getValue()) {
                            case None: {
                                InventoryUtil.switchTo(finalSlot);
                                break;
                            }
                            case Slot: {
                                InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(finalSlot));
                                break;
                            }
                            case Pick: {
                                InventoryUtil.bypassSwitch(finalSlot);
                                break;
                            }
                        }
                    }
                    InventoryUtil.syncItem();
                    if (swingTime == SwingTime.Pre) {
                        this.swing(finalHand, false);
                    }
                    HelperRotation.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, finalRay.sideHit, finalHand, f[0], f[1], f[2]));
                    if (finalNoGodded) {
                        module.noGod = false;
                    }
                    placed.set(true);
                    if (swingTime == SwingTime.Post) {
                        this.swing(finalHand, false);
                    }
                    if (module.switchBack.getValue()) {
                        switch (module.cooldownBypass.getValue()) {
                            case None: {
                                InventoryUtil.switchTo(lastSlot);
                                break;
                            }
                            case Slot: {
                                InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(finalSlot));
                                break;
                            }
                            case Pick: {
                                InventoryUtil.bypassSwitch(finalSlot);
                                break;
                            }
                        }
                    }
                    return;
                });
                if (realtime) {
                    module.setRenderPos(pos, damage);
                }
                if (module.simulatePlace.getValue() != 0) {
                    module.crystalRender.addFakeCrystal(new EntityEnderCrystal((World)HelperRotation.mc.world, (double)(pos.getX() + 0.5f), (double)(pos.getY() + 1), (double)(pos.getZ() + 0.5f)));
                }
            }
        };
    }
    
    public Runnable post(final Entity entity, final MutableWrapper<Boolean> attacked) {
        return () -> {
            final WeaknessSwitch w = antiWeakness(this.module);
            if ((!w.needsSwitch() || w.getSlot() != -1) && !EntityUtil.isDead(entity) && (this.module.rotate.getValue().noRotate(ACRotate.Break) || RotationUtil.isLegit(entity, new Entity[0]))) {
                final CPacketUseEntity packet = new CPacketUseEntity(entity);
                final SwingTime swingTime = this.module.breakSwing.getValue();
                final Runnable runnable = () -> {
                    final int lastSlot = HelperRotation.mc.player.inventory.currentItem;
                    if (w.getSlot() != -1) {
                        switch (this.module.antiWeaknessBypass.getValue()) {
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
                    if (swingTime == SwingTime.Pre) {
                        this.swing(EnumHand.MAIN_HAND, true);
                    }
                    HelperRotation.mc.player.connection.sendPacket((Packet)packet);
                    attacked.set(true);
                    if (swingTime == SwingTime.Post) {
                        this.swing(EnumHand.MAIN_HAND, true);
                    }
                    if (w.getSlot() != -1) {
                        switch (this.module.antiWeaknessBypass.getValue()) {
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
                if (this.module.pseudoSetDead.getValue()) {
                    ((IEntity)entity).setPseudoDead(true);
                }
                if (this.module.setDead.getValue()) {
                    Managers.SET_DEAD.setDead(entity);
                }
            }
        };
    }
    
    public Runnable postBlock(final PositionData data) {
        return this.postBlock(data, -1, this.module.obbyRotate.getValue(), null, null);
    }
    
    public Runnable postBlock(final PositionData data, final int preSlot, final Rotate rotate, final MutableWrapper<Boolean> placed, final MutableWrapper<Integer> switchBack) {
        return () -> {
            if (!(!data.isValid())) {
                final int slot = (preSlot == -1) ? InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]) : preSlot;
                if (slot != -1) {
                    final EnumHand hand = (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
                    final PlaceSwing placeSwing = this.module.obbySwing.getValue();
                    Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                        final int lastSlot = HelperRotation.mc.player.inventory.currentItem;
                        if (switchBack != null) {
                            switchBack.set(lastSlot);
                        }
                        switch (this.module.obsidianBypass.getValue()) {
                            case None: {
                                InventoryUtil.switchTo(slot);
                                break;
                            }
                            case Slot: {
                                InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(slot));
                                break;
                            }
                            case Pick: {
                                InventoryUtil.bypassSwitch(slot);
                                break;
                            }
                        }
                        data.getPath();
                        final Ray[] array;
                        int i = 0;
                        for (int length = array.length; i < length; ++i) {
                            final Ray ray = array[i];
                            if (rotate == Rotate.Packet && !RotationUtil.isLegit(ray.getPos(), ray.getFacing())) {
                                Managers.ROTATION.setBlocking(true);
                                final float[] r = ray.getRotations();
                                HelperRotation.mc.player.connection.sendPacket((Packet)PacketUtil.rotation(r[0], r[1], HelperRotation.mc.player.onGround));
                                Managers.ROTATION.setBlocking(false);
                            }
                            final float[] f = RayTraceUtil.hitVecToPlaceVec(ray.getPos(), ray.getResult().hitVec);
                            HelperRotation.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(ray.getPos(), ray.getFacing(), hand, f[0], f[1], f[2]));
                            if (this.module.setState.getValue() && preSlot == -1) {
                                HelperRotation.mc.addScheduledTask(() -> {
                                    if (HelperRotation.mc.world != null) {
                                        HelperRotation.mc.world.setBlockState(ray.getPos().offset(ray.getFacing()), Blocks.OBSIDIAN.getDefaultState());
                                    }
                                    return;
                                });
                            }
                            if (placeSwing == PlaceSwing.Always) {
                                Swing.Packet.swing(hand);
                            }
                        }
                        if (placeSwing == PlaceSwing.Once) {
                            Swing.Packet.swing(hand);
                        }
                        if (switchBack == null) {
                            switch (this.module.obsidianBypass.getValue()) {
                                case None: {
                                    InventoryUtil.switchTo(lastSlot);
                                    break;
                                }
                                case Slot: {
                                    InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(slot));
                                    break;
                                }
                                case Pick: {
                                    InventoryUtil.bypassSwitch(slot);
                                    break;
                                }
                            }
                        }
                        if (placed != null) {
                            placed.set(true);
                        }
                        return;
                    });
                    final EnumHand swingHand = this.module.obbyHand.getValue().getHand();
                    if (swingHand != null) {
                        Swing.Client.swing(swingHand);
                    }
                }
            }
        };
    }
    
    public Runnable breakBlock(final int toolSlot, final MutableWrapper<Boolean> placed, final MutableWrapper<Integer> lastSlot, final int[] order, final Ray... positions) {
        return Locks.wrap(Locks.PLACE_SWITCH_LOCK, () -> {
            if (order.length != positions.length) {
                new IndexOutOfBoundsException("Order length: " + order.length + ", Positions length: " + positions.length);
                throw;
            }
            else if (!(!placed.get())) {
                switch (this.module.mineBypass.getValue()) {
                    case None: {
                        InventoryUtil.switchTo(toolSlot);
                        break;
                    }
                    case Slot: {
                        InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(toolSlot));
                        break;
                    }
                    case Pick: {
                        InventoryUtil.bypassSwitch(toolSlot);
                        break;
                    }
                }
                int j = 0;
                for (int length = order.length; j < length; ++j) {
                    final int i = order[j];
                    final Ray ray = positions[i];
                    final BlockPos pos = ray.getPos().offset(ray.getFacing());
                    PacketUtil.startDigging(pos, ray.getFacing().getOpposite());
                    PacketUtil.stopDigging(pos, ray.getFacing().getOpposite());
                    Swing.Packet.swing(EnumHand.MAIN_HAND);
                }
                switch (this.module.mineBypass.getValue()) {
                    case None: {
                        InventoryUtil.switchTo(lastSlot.get());
                        break;
                    }
                    case Slot: {
                        InventoryUtil.switchToBypassAlt(InventoryUtil.hotbarToInventory(toolSlot));
                        break;
                    }
                    case Pick: {
                        InventoryUtil.bypassSwitch(toolSlot);
                        break;
                    }
                }
            }
        });
    }
    
    private void swing(final EnumHand hand, final boolean breaking) {
        Swing.Packet.swing(hand);
        final EnumHand swingHand = breaking ? this.module.swing.getValue().getHand() : this.module.placeHand.getValue().getHand();
        if (swingHand != null) {
            Swing.Client.swing(swingHand);
        }
    }
    
    public static WeaknessSwitch antiWeakness(final AutoCrystal module) {
        if (!module.weaknessHelper.isWeaknessed()) {
            return WeaknessSwitch.NONE;
        }
        if (module.antiWeakness.getValue() == AntiWeakness.None || module.cooldown.getValue() != 0) {
            return WeaknessSwitch.INVALID;
        }
        return new WeaknessSwitch(DamageUtil.findAntiWeakness(), true);
    }
    
    static {
        ID = new AtomicInteger();
        OFFHAND = Caches.getModule(Offhand.class);
    }
}
