//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.scaffold;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.freecam.*;
import me.earth.earthhack.impl.modules.player.spectate.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.core.ducks.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerMotion extends ModuleListener<Scaffold, MotionUpdateEvent>
{
    private static final ModuleCache<Freecam> FREECAM;
    private static final ModuleCache<Spectate> SPECTATE;
    
    public ListenerMotion(final Scaffold module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if ((ListenerMotion.FREECAM.isEnabled() && !((Scaffold)this.module).freecam.getValue()) || (ListenerMotion.SPECTATE.isEnabled() && !((Scaffold)this.module).spectate.getValue())) {
            return;
        }
        if (((Scaffold)this.module).aac.getValue() && ((Scaffold)this.module).aacTimer.passed(((Scaffold)this.module).aacDelay.getValue()) && ListenerMotion.mc.player.onGround) {
            ListenerMotion.mc.player.motionX = 0.0;
            ListenerMotion.mc.player.motionZ = 0.0;
            ((Scaffold)this.module).aacTimer.reset();
        }
        if (event.getStage() == Stage.PRE) {
            ((Scaffold)this.module).facing = null;
            final BlockPos prev = ((Scaffold)this.module).pos;
            ((Scaffold)this.module).pos = null;
            ((Scaffold)this.module).pos = ((Scaffold)this.module).findNextPos();
            if (((Scaffold)this.module).pos != null) {
                ((Scaffold)this.module).rot = ((Scaffold)this.module).pos;
                if (!((Scaffold)this.module).pos.equals((Object)prev)) {
                    ((Scaffold)this.module).rotationTimer.reset();
                }
                this.setRotations(((Scaffold)this.module).pos, event);
            }
            else if (((Scaffold)this.module).rot != null && ((Scaffold)this.module).rotate.getValue() && ((Scaffold)this.module).keepRotations.getValue() != 0 && !((Scaffold)this.module).rotationTimer.passed(((Scaffold)this.module).keepRotations.getValue())) {
                this.setRotations(((Scaffold)this.module).rot, event);
            }
            else {
                ((Scaffold)this.module).rot = null;
            }
        }
        else {
            if (((Scaffold)this.module).pos == null || ((Scaffold)this.module).facing == null || (((Scaffold)this.module).preRotate.getValue() != 0 && ((Scaffold)this.module).rotate.getValue() && !((Scaffold)this.module).rotationTimer.passed(((Scaffold)this.module).preRotate.getValue()))) {
                return;
            }
            int slot = -1;
            int optional = -1;
            final ItemStack offhand = ListenerMotion.mc.player.getHeldItemOffhand();
            if (((Scaffold)this.module).isStackValid(offhand)) {
                if (offhand.getItem() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)offhand.getItem()).getBlock();
                    if (!((Scaffold)this.module).checkState.getValue() || !block.getBlockState().getBaseState().getMaterial().isReplaceable()) {
                        if (block instanceof BlockContainer) {
                            optional = -2;
                        }
                        else {
                            slot = -2;
                        }
                    }
                }
                else {
                    optional = -2;
                }
            }
            if (slot == -1) {
                for (int i = 0; i < 9; ++i) {
                    final ItemStack stack = ListenerMotion.mc.player.inventory.getStackInSlot(i);
                    if (((Scaffold)this.module).isStackValid(stack) && stack.getItem() instanceof ItemBlock) {
                        final Block block2 = ((ItemBlock)stack.getItem()).getBlock();
                        if (!((Scaffold)this.module).checkState.getValue() || !block2.getBlockState().getBaseState().getMaterial().isReplaceable()) {
                            if (block2 instanceof BlockContainer) {
                                optional = i;
                            }
                            else if ((slot = i) == ListenerMotion.mc.player.inventory.currentItem) {
                                break;
                            }
                        }
                    }
                }
            }
            slot = ((slot == -1) ? optional : slot);
            if (slot != -1) {
                final boolean jump = ListenerMotion.mc.player.movementInput.jump && ((Scaffold)this.module).tower.getValue();
                final boolean sneak = ListenerMotion.mc.player.movementInput.sneak && ((Scaffold)this.module).down.getValue();
                if (jump && !sneak && !MovementUtil.isMoving()) {
                    ((IMinecraft)ListenerMotion.mc).setRightClickDelay(3);
                    ListenerMotion.mc.player.jump();
                    if (((Scaffold)this.module).towerTimer.passed(1500L)) {
                        ListenerMotion.mc.player.motionY = -0.28;
                        ((Scaffold)this.module).towerTimer.reset();
                    }
                }
                else {
                    ((Scaffold)this.module).towerTimer.reset();
                }
                final boolean sneaking = ((Scaffold)this.module).smartSneak.getValue() && !SpecialBlocks.shouldSneak(((Scaffold)this.module).pos.offset(((Scaffold)this.module).facing), true);
                if (((Scaffold)this.module).attack.getValue() && Managers.SWITCH.getLastSwitch() > ((Scaffold)this.module).cooldown.getValue() && ((Scaffold)this.module).breakTimer.passed(((Scaffold)this.module).breakDelay.getValue())) {
                    Entity entity = null;
                    float minDmg = Float.MAX_VALUE;
                    for (final EntityEnderCrystal crystal : ListenerMotion.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(((Scaffold)this.module).pos))) {
                        if (crystal != null) {
                            if (crystal.isDead) {
                                continue;
                            }
                            final float damage = DamageUtil.calculate((Entity)crystal);
                            if (damage >= minDmg || !((Scaffold)this.module).pop.getValue().shouldPop(damage, ((Scaffold)this.module).popTime.getValue())) {
                                continue;
                            }
                            entity = (Entity)crystal;
                            minDmg = damage;
                        }
                    }
                    if (entity != null) {
                        PacketUtil.attack(entity);
                        ((Scaffold)this.module).breakTimer.reset();
                    }
                }
                final int finalSlot = slot;
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                    final int lastSlot = ListenerMotion.mc.player.inventory.currentItem;
                    final boolean sprinting = ListenerMotion.mc.player.isSprinting() && ((Scaffold)this.module).stopSprint.getValue();
                    InventoryUtil.switchTo(finalSlot);
                    if (sprinting) {
                        PacketUtil.sendAction(CPacketEntityAction.Action.STOP_SPRINTING);
                    }
                    if (!sneaking) {
                        PacketUtil.sendAction(CPacketEntityAction.Action.START_SNEAKING);
                    }
                    final RayTraceResult result = RayTraceUtil.getRayTraceResult(((Scaffold)this.module).rotations[0], ((Scaffold)this.module).rotations[1]);
                    ListenerMotion.mc.playerController.processRightClickBlock(ListenerMotion.mc.player, ListenerMotion.mc.world, ((Scaffold)this.module).pos.offset(((Scaffold)this.module).facing), ((Scaffold)this.module).facing.getOpposite(), result.hitVec, InventoryUtil.getHand(finalSlot));
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketAnimation(InventoryUtil.getHand(finalSlot)));
                    if (!sneaking) {
                        PacketUtil.sendAction(CPacketEntityAction.Action.STOP_SNEAKING);
                    }
                    if (sprinting) {
                        PacketUtil.sendAction(CPacketEntityAction.Action.START_SPRINTING);
                    }
                    InventoryUtil.switchTo(lastSlot);
                    return;
                });
                if (((Scaffold)this.module).swing.getValue()) {
                    Swing.Client.swing(InventoryUtil.getHand(slot));
                }
            }
        }
    }
    
    private void setRotations(final BlockPos pos, final MotionUpdateEvent event) {
        ((Scaffold)this.module).facing = BlockUtil.getFacing(pos);
        if (((Scaffold)this.module).facing != null) {
            this.setRotations(pos, event, ((Scaffold)this.module).facing);
        }
        else if (((Scaffold)this.module).helping.getValue()) {
            for (final EnumFacing facing : EnumFacing.VALUES) {
                final BlockPos p = pos.offset(facing);
                final EnumFacing f = BlockUtil.getFacing(p);
                if (f != null) {
                    ((Scaffold)this.module).facing = f;
                    this.setRotations(((Scaffold)this.module).pos = p, event, f);
                }
            }
        }
    }
    
    private void setRotations(final BlockPos pos, final MotionUpdateEvent event, final EnumFacing facing) {
        ((Scaffold)this.module).rotations = RotationUtil.getRotations(pos.offset(facing), facing.getOpposite());
        if (((Scaffold)this.module).rotate.getValue() && ((Scaffold)this.module).rotations != null) {
            event.setYaw(((Scaffold)this.module).rotations[0]);
            event.setPitch(((Scaffold)this.module).rotations[1]);
        }
    }
    
    static {
        FREECAM = Caches.getModule(Freecam.class);
        SPECTATE = Caches.getModule(Spectate.class);
    }
}
