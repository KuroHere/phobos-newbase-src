//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bowkill;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

final class ListenerMotion extends ModuleListener<BowKiller, MotionUpdateEvent>
{
    public ListenerMotion(final BowKiller module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        ((BowKiller)this.module).entityDataArrayList.removeIf(e -> e.getTime() + 60000L < System.currentTimeMillis());
        if (!ListenerMotion.mc.player.isCollidedVertically) {
            return;
        }
        if (event.getStage() == Stage.PRE) {
            ((BowKiller)this.module).blockUnder = this.isBlockUnder();
            if (((BowKiller)this.module).rotate.getValue() && ListenerMotion.mc.player.getActiveItemStack().getItem() == Items.BOW && ListenerMotion.mc.gameSettings.keyBindUseItem.isKeyDown() && ((BowKiller)this.module).blockUnder) {
                ((BowKiller)this.module).target = ((BowKiller)this.module).findTarget();
                if (((BowKiller)this.module).target != null) {
                    final float[] rotations = ((BowKiller)this.module).rotationSmoother.getRotations((Entity)RotationUtil.getRotationPlayer(), ((BowKiller)this.module).target, ((BowKiller)this.module).height.getValue(), ((BowKiller)this.module).soft.getValue());
                    if (rotations != null) {
                        if (((BowKiller)this.module).silent.getValue()) {
                            event.setYaw(rotations[0]);
                            event.setPitch(rotations[1]);
                        }
                        else {
                            ListenerMotion.mc.player.rotationYaw = rotations[0];
                            ListenerMotion.mc.player.rotationPitch = rotations[1];
                        }
                    }
                }
            }
            if (ListenerMotion.mc.player.getActiveItemStack().getItem() == Items.BOW && ListenerMotion.mc.player.isHandActive() && !((BowKiller)this.module).blockUnder) {
                final int newSlot = this.findBlockInHotbar();
                if (newSlot != -1) {
                    final int oldSlot = ListenerMotion.mc.player.inventory.currentItem;
                    ListenerMotion.mc.player.inventory.currentItem = newSlot;
                    this.placeBlock(PositionUtil.getPosition((Entity)RotationUtil.getRotationPlayer()).down(1), event);
                    ListenerMotion.mc.player.inventory.currentItem = oldSlot;
                }
            }
        }
        else if (ListenerMotion.mc.player.getActiveItemStack().getItem() != Items.BOW) {
            ((BowKiller)this.module).cancelling = false;
            ((BowKiller)this.module).packetsSent = 0;
        }
        else if (ListenerMotion.mc.player.getActiveItemStack().getItem() == Items.BOW && ListenerMotion.mc.player.isHandActive() && ((BowKiller)this.module).cancelling && ((BowKiller)this.module).blockUnder) {
            final BowKiller bowKiller = (BowKiller)this.module;
            ++bowKiller.packetsSent;
            if (((BowKiller)this.module).packetsSent > ((BowKiller)this.module).runs.getValue() * 2 && !((BowKiller)this.module).always.getValue() && ((BowKiller)this.module).needsMessage) {
                ModuleUtil.sendMessage((Module)this.module, "§aCharged!");
            }
        }
    }
    
    private int findBlockInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = ListenerMotion.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.field_190927_a && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private boolean canBeClicked(final BlockPos pos) {
        return ListenerMotion.mc.world.getBlockState(pos).getBlock().canCollideCheck(ListenerMotion.mc.world.getBlockState(pos), false);
    }
    
    private void placeBlock(final BlockPos pos, final MotionUpdateEvent event) {
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (this.canBeClicked(neighbor)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(new Vec3d(0.5, 0.5, 0.5)).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                final float[] rotations = RotationUtil.getRotations(hitVec);
                event.setYaw(rotations[0]);
                event.setPitch(rotations[1]);
                ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerMotion.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                ListenerMotion.mc.playerController.processRightClickBlock(ListenerMotion.mc.player, ListenerMotion.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                ListenerMotion.mc.player.swingArm(EnumHand.MAIN_HAND);
                ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerMotion.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                return;
            }
        }
    }
    
    private boolean isBlockUnder() {
        return !(ListenerMotion.mc.world.getBlockState(PositionUtil.getPosition((Entity)RotationUtil.getRotationPlayer()).down(1)).getBlock() instanceof BlockAir);
    }
}
