//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.blocklag;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.impl.modules.movement.blocklag.mode.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;

final class ListenerMotion extends ModuleListener<BlockLag, MotionUpdateEvent>
{
    public ListenerMotion(final BlockLag module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE && ((BlockLag)this.module).isInsideBlock() && ((BlockLag)this.module).bypass.getValue()) {
            event.setY(event.getY() - ((BlockLag)this.module).bypassOffset.getValue());
            event.setOnGround(false);
        }
        if (!((BlockLag)this.module).timer.passed(((BlockLag)this.module).delay.getValue()) || !((BlockLag)this.module).stage.getValue().shouldBlockLag(event.getStage())) {
            return;
        }
        if (((BlockLag)this.module).wait.getValue()) {
            final BlockPos currentPos = ((BlockLag)this.module).getPlayerPos();
            if (!currentPos.equals((Object)((BlockLag)this.module).startPos)) {
                ((BlockLag)this.module).disable();
                return;
            }
        }
        if (((BlockLag)this.module).isInsideBlock()) {
            return;
        }
        EntityPlayer rEntity = (EntityPlayer)ListenerMotion.mc.player;
        if (BlockLag.FREECAM.isEnabled()) {
            if (!((BlockLag)this.module).freecam.getValue()) {
                ((BlockLag)this.module).disable();
                return;
            }
            rEntity = (EntityPlayer)BlockLag.FREECAM.get().getPlayer();
            if (rEntity == null) {
                rEntity = (EntityPlayer)ListenerMotion.mc.player;
            }
        }
        final BlockPos pos = PositionUtil.getPosition((Entity)rEntity);
        if (!ListenerMotion.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            if (!((BlockLag)this.module).wait.getValue()) {
                ((BlockLag)this.module).disable();
            }
            return;
        }
        final BlockPos posHead = PositionUtil.getPosition((Entity)rEntity).up().up();
        if (!ListenerMotion.mc.world.getBlockState(posHead).getMaterial().isReplaceable() && ((BlockLag)this.module).wait.getValue()) {
            return;
        }
        CPacketUseEntity attacking = null;
        boolean crystals = false;
        float currentDmg = Float.MAX_VALUE;
        for (final Entity entity : ListenerMotion.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos))) {
            if (entity != null && !rEntity.equals((Object)entity) && !ListenerMotion.mc.player.equals((Object)entity) && !EntityUtil.isDead(entity) && entity.preventEntitySpawning) {
                if (!(entity instanceof EntityEnderCrystal) || !((BlockLag)this.module).attack.getValue() || Managers.SWITCH.getLastSwitch() < ((BlockLag)this.module).cooldown.getValue()) {
                    if (!((BlockLag)this.module).wait.getValue()) {
                        ((BlockLag)this.module).disable();
                    }
                    return;
                }
                final float damage = DamageUtil.calculate(entity, (EntityLivingBase)ListenerMotion.mc.player);
                if (damage < currentDmg) {
                    currentDmg = damage;
                    if (((BlockLag)this.module).pop.getValue().shouldPop(damage, ((BlockLag)this.module).popTime.getValue())) {
                        attacking = new CPacketUseEntity(entity);
                        continue;
                    }
                }
                crystals = true;
            }
        }
        int weaknessSlot = -1;
        if (crystals) {
            if (attacking == null) {
                if (!((BlockLag)this.module).wait.getValue()) {
                    ((BlockLag)this.module).disable();
                }
                return;
            }
            if (!DamageUtil.canBreakWeakness(true) && (!((BlockLag)this.module).antiWeakness.getValue() || ((BlockLag)this.module).cooldown.getValue() != 0 || (weaknessSlot = DamageUtil.findAntiWeakness()) == -1)) {
                if (!((BlockLag)this.module).wait.getValue()) {
                    ((BlockLag)this.module).disable();
                }
                return;
            }
        }
        if (!((BlockLag)this.module).allowUp.getValue()) {
            final BlockPos upUp = pos.up(2);
            final IBlockState upState = ListenerMotion.mc.world.getBlockState(upUp);
            if (upState.getMaterial().blocksMovement()) {
                if (!((BlockLag)this.module).wait.getValue()) {
                    ((BlockLag)this.module).disable();
                }
                return;
            }
        }
        final int slot = ((BlockLag)this.module).anvil.getValue() ? InventoryUtil.findHotbarBlock(Blocks.ANVIL, new Block[0]) : (((BlockLag)this.module).beacon.getValue() ? InventoryUtil.findHotbarBlock((Block)Blocks.BEACON, new Block[0]) : ((((BlockLag)this.module).echest.getValue() || ListenerMotion.mc.world.getBlockState(pos.down()).getBlock() == Blocks.ENDER_CHEST) ? InventoryUtil.findHotbarBlock(Blocks.ENDER_CHEST, Blocks.OBSIDIAN) : InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, Blocks.ENDER_CHEST)));
        if (slot == -1) {
            ModuleUtil.disableRed((Module)this.module, "No Block found!");
            return;
        }
        final EnumFacing f = BlockUtil.getFacing(pos);
        if (f == null) {
            if (!((BlockLag)this.module).wait.getValue()) {
                ((BlockLag)this.module).disable();
            }
            return;
        }
        final double y = ((BlockLag)this.module).applyScale(((BlockLag)this.module).getY((Entity)rEntity, ((BlockLag)this.module).offsetMode.getValue()));
        if (Double.isNaN(y)) {
            return;
        }
        final BlockPos on = pos.offset(f);
        final float[] r = RotationUtil.getRotations(on, f.getOpposite(), (Entity)rEntity);
        final RayTraceResult result = RayTraceUtil.getRayTraceResultWithEntity(r[0], r[1], (Entity)rEntity);
        final float[] vec = RayTraceUtil.hitVecToPlaceVec(on, result.hitVec);
        final boolean sneaking = !SpecialBlocks.shouldSneak(on, true);
        final EntityPlayer finalREntity = rEntity;
        final int finalWeaknessSlot = weaknessSlot;
        final CPacketUseEntity finalAttacking = attacking;
        if (((BlockLag)this.module).singlePlayerCheck(pos)) {
            if (!((BlockLag)this.module).wait.getValue() || ((BlockLag)this.module).placeDisable.getValue()) {
                ((BlockLag)this.module).disable();
            }
            return;
        }
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
            final int lastSlot = ListenerMotion.mc.player.inventory.currentItem;
            if (((BlockLag)this.module).attackBefore.getValue() && finalAttacking != null) {
                ((BlockLag)this.module).attack((Packet<?>)finalAttacking, finalWeaknessSlot);
            }
            if (((BlockLag)this.module).conflict.getValue() || ((BlockLag)this.module).rotate.getValue()) {
                if (((BlockLag)this.module).rotate.getValue()) {
                    if (finalREntity.getPositionVector().equals((Object)Managers.POSITION.getVec())) {
                        PacketUtil.doRotation(r[0], r[1], true);
                    }
                    else {
                        PacketUtil.doPosRot(finalREntity.posX, finalREntity.posY, finalREntity.posZ, r[0], r[1], true);
                    }
                }
                else {
                    PacketUtil.doPosition(finalREntity.posX, finalREntity.posY, finalREntity.posZ, true);
                }
            }
            PacketUtil.doY((Entity)finalREntity, finalREntity.posY + 0.42, ((BlockLag)this.module).onGround.getValue());
            PacketUtil.doY((Entity)finalREntity, finalREntity.posY + 0.75, ((BlockLag)this.module).onGround.getValue());
            PacketUtil.doY((Entity)finalREntity, finalREntity.posY + 1.01, ((BlockLag)this.module).onGround.getValue());
            PacketUtil.doY((Entity)finalREntity, finalREntity.posY + 1.16, ((BlockLag)this.module).onGround.getValue());
            if (((BlockLag)this.module).highBlock.getValue()) {}
            if (!((BlockLag)this.module).attackBefore.getValue() && finalAttacking != null) {
                ((BlockLag)this.module).attack((Packet<?>)finalAttacking, finalWeaknessSlot);
            }
            InventoryUtil.switchTo(slot);
            if (!sneaking) {
                ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerMotion.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            PacketUtil.place(on, f.getOpposite(), slot, vec[0], vec[1], vec[2]);
            if (((BlockLag)this.module).highBlock.getValue()) {
                PacketUtil.doY((Entity)finalREntity, finalREntity.posY + 1.67, ((BlockLag)this.module).onGround.getValue());
                PacketUtil.doY((Entity)finalREntity, finalREntity.posY + 2.01, ((BlockLag)this.module).onGround.getValue());
                PacketUtil.doY((Entity)finalREntity, finalREntity.posY + 2.42, ((BlockLag)this.module).onGround.getValue());
                final BlockPos highPos = pos.up();
                final EnumFacing face = EnumFacing.DOWN;
                PacketUtil.place(highPos.offset(face), face.getOpposite(), slot, vec[0], vec[1], vec[2]);
            }
            PacketUtil.swing(slot);
            InventoryUtil.switchTo(lastSlot);
            return;
        });
        if (!sneaking) {
            ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerMotion.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        PacketUtil.doY((Entity)rEntity, y, false);
        ((BlockLag)this.module).timer.reset();
        if (!((BlockLag)this.module).wait.getValue() || ((BlockLag)this.module).placeDisable.getValue()) {
            ((BlockLag)this.module).disable();
        }
    }
}
