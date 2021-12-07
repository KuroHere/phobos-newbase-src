//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.suicide;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.math.geocache.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.raytrace.*;

final class ListenerMotion extends ModuleListener<Suicide, MotionUpdateEvent>
{
    public ListenerMotion(final Suicide module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (((Suicide)this.module).displaying) {
            return;
        }
        if (ListenerMotion.mc.player.getHealth() <= 0.0f) {
            ((Suicide)this.module).disable();
            return;
        }
        if (((Suicide)this.module).mode.getValue() == SuicideMode.Command) {
            NetworkUtil.sendPacketNoEvent((Packet<?>)new CPacketChatMessage("/kill"));
            ((Suicide)this.module).disable();
            return;
        }
        if (((Suicide)this.module).throwAwayTotem.getValue() && InventoryUtil.validScreen() && ((Suicide)this.module).timer.passed(((Suicide)this.module).throwDelay.getValue()) && ListenerMotion.mc.player.getHeldItemOffhand().getItem() == Items.field_190929_cY) {
            Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> ListenerMotion.mc.playerController.windowClick(0, 45, 1, ClickType.THROW, (EntityPlayer)ListenerMotion.mc.player));
            ((Suicide)this.module).timer.reset();
        }
        final int slot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
        if (slot == -1) {
            ModuleUtil.disableRed((Module)this.module, "No Crystals found!");
            return;
        }
        if (event.getStage() == Stage.PRE) {
            ((Suicide)this.module).result = null;
            ((Suicide)this.module).pos = null;
            ((Suicide)this.module).crystal = null;
            if (((Suicide)this.module).breakTimer.passed(((Suicide)this.module).breakDelay.getValue())) {
                Entity crystal = null;
                float maxDamage = Float.MIN_VALUE;
                for (final Entity entity : ListenerMotion.mc.world.loadedEntityList) {
                    if (!entity.isDead && entity instanceof EntityEnderCrystal && RotationUtil.getRotationPlayer().getDistanceSqToEntity(entity) < MathUtil.square(((Suicide)this.module).breakRange.getValue()) && (RotationUtil.getRotationPlayer().canEntityBeSeen(entity) || RotationUtil.getRotationPlayer().getDistanceSqToEntity(entity) < MathUtil.square(((Suicide)this.module).trace.getValue()))) {
                        final float damage = DamageUtil.calculate(entity);
                        if (damage <= maxDamage) {
                            continue;
                        }
                        maxDamage = damage;
                        crystal = entity;
                    }
                }
                if (crystal != null) {
                    ((Suicide)this.module).crystal = crystal;
                    if (((Suicide)this.module).rotate.getValue()) {
                        final float[] rotations = RotationUtil.getRotations(crystal);
                        event.setYaw(rotations[0]);
                        event.setPitch(rotations[1]);
                    }
                    return;
                }
            }
            if (!((Suicide)this.module).placeTimer.passed(((Suicide)this.module).placeDelay.getValue())) {
                return;
            }
            float maxDamage2 = Float.MIN_VALUE;
            final BlockPos middle = PositionUtil.getPosition();
            final int x = middle.getX();
            final int y = middle.getY();
            final int z = middle.getZ();
            final int maxRadius = Sphere.getRadius(6.0);
            final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            BlockPos bestPos = null;
            for (int i = 1; i < maxRadius; ++i) {
                final Vec3i v = Sphere.get(i);
                pos.setPos(x + v.getX(), y + v.getY(), z + v.getZ());
                if (BlockUtil.canPlaceCrystal((BlockPos)pos, false, ((Suicide)this.module).newVer.getValue(), ListenerMotion.mc.world.loadedEntityList, ((Suicide)this.module).newVerEntities.getValue(), 0L)) {
                    if (BlockUtil.isCrystalPosInRange((BlockPos)pos, ((Suicide)this.module).placeRange.getValue(), ((Suicide)this.module).placeRange.getValue(), ((Suicide)this.module).trace.getValue())) {
                        final float damage2 = DamageUtil.calculate((BlockPos)pos);
                        if (damage2 > maxDamage2) {
                            maxDamage2 = damage2;
                            bestPos = pos.toImmutable();
                        }
                    }
                }
            }
            if (bestPos != null) {
                final Ray result = RayTraceFactory.fullTrace((Entity)RotationUtil.getRotationPlayer(), (IBlockAccess)ListenerMotion.mc.world, bestPos, -1.0);
                if (result == null) {
                    return;
                }
                if (((Suicide)this.module).rotate.getValue()) {
                    event.setYaw(result.getRotations()[0]);
                    event.setPitch(result.getRotations()[1]);
                }
                ((Suicide)this.module).pos = bestPos;
                ((Suicide)this.module).result = result.getResult();
            }
        }
        else if (event.getStage() == Stage.POST) {
            if (((Suicide)this.module).crystal != null) {
                ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(((Suicide)this.module).crystal));
                ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                ((Suicide)this.module).breakTimer.reset();
                return;
            }
            if (((Suicide)this.module).pos != null && ((Suicide)this.module).result != null) {
                final float[] r = RayTraceUtil.hitVecToPlaceVec(((Suicide)this.module).pos, ((Suicide)this.module).result.hitVec);
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                    final int last = ListenerMotion.mc.player.inventory.currentItem;
                    InventoryUtil.switchTo(slot);
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(((Suicide)this.module).pos, ((Suicide)this.module).result.sideHit, InventoryUtil.getHand(slot), r[0], r[1], r[2]));
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketAnimation(InventoryUtil.getHand(slot)));
                    if (((Suicide)this.module).silent.getValue()) {
                        InventoryUtil.switchTo(last);
                    }
                    return;
                });
                ((Suicide)this.module).placed.add(((Suicide)this.module).pos);
                ((Suicide)this.module).placeTimer.reset();
            }
        }
    }
}
