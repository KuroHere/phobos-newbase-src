//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoregear;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;

final class ListenerMotion extends ModuleListener<AutoRegear, MotionUpdateEvent>
{
    public ListenerMotion(final AutoRegear module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {}
        if (((AutoRegear)this.module).steal.getValue() && ListenerMotion.mc.currentScreen == null && !((AutoRegear)this.module).shouldRegear) {
            final BlockPos craftingPos = ((AutoRegear)this.module).getShulkerBox();
            final float[] rotations = RotationUtil.getRotations(craftingPos, EnumFacing.UP);
            final RayTraceResult ray = RotationUtil.rayTraceTo(craftingPos, (IBlockAccess)ListenerMotion.mc.world);
            final float[] f = RayTraceUtil.hitVecToPlaceVec(craftingPos, ray.hitVec);
            if (((AutoRegear)this.module).rotate.getValue() == Rotate.Normal) {
                event.setYaw(rotations[0]);
                event.setPitch(rotations[1]);
            }
            else if (((AutoRegear)this.module).rotate.getValue() != Rotate.None) {
                PacketUtil.doRotation(rotations[0], rotations[1], ListenerMotion.mc.player.onGround);
            }
            NetworkUtil.send((Packet<?>)new CPacketPlayerTryUseItemOnBlock(craftingPos, ray.sideHit, EnumHand.MAIN_HAND, f[0], f[1], f[2]));
            return;
        }
        if (((AutoRegear)this.module).shouldRegear && ListenerMotion.mc.currentScreen == null) {
            BlockPos optimal = ((AutoRegear)this.module).getOptimalPlacePos(false);
            boolean swapped = false;
            if (((AutoRegear)this.module).placeEchest.getValue() && ((AutoRegear)this.module).getBlock(Blocks.ENDER_CHEST) == null && InventoryUtil.findHotbarBlock(Blocks.ENDER_CHEST, new Block[0]) != -1 && !((AutoRegear)this.module).hasKit() && optimal != null) {
                final int slot = InventoryUtil.findHotbarBlock(Blocks.ENDER_CHEST, new Block[0]);
                if (slot == -1) {
                    return;
                }
                ((AutoRegear)this.module).slot = slot;
                final EnumFacing facing = BlockUtil.getFacing(optimal);
                ((AutoRegear)this.module).placeBlock(optimal.offset(facing), facing.getOpposite());
                if (((AutoRegear)this.module).rotate.getValue() == Rotate.Normal && ((AutoRegear)this.module).rotations != null) {
                    event.setYaw(((AutoRegear)this.module).rotations[0]);
                    event.setPitch(((AutoRegear)this.module).rotations[1]);
                }
                ((AutoRegear)this.module).execute();
            }
            else {
                if (((AutoRegear)this.module).grabShulker.getValue() && ((AutoRegear)this.module).getBlock(Blocks.ENDER_CHEST) != null && ((AutoRegear)this.module).getShulkerBox() == null && ListenerMotion.mc.currentScreen == null && !((AutoRegear)this.module).hasKit()) {
                    final BlockPos craftingPos2 = ((AutoRegear)this.module).getBlock(Blocks.ENDER_CHEST);
                    final float[] rotations2 = RotationUtil.getRotations(craftingPos2, EnumFacing.UP);
                    final RayTraceResult ray2 = RotationUtil.rayTraceTo(craftingPos2, (IBlockAccess)ListenerMotion.mc.world);
                    final float[] f2 = RayTraceUtil.hitVecToPlaceVec(craftingPos2, ray2.hitVec);
                    if (((AutoRegear)this.module).rotate.getValue() == Rotate.Normal) {
                        event.setYaw(rotations2[0]);
                        event.setPitch(rotations2[1]);
                    }
                    else if (((AutoRegear)this.module).rotate.getValue() != Rotate.None) {
                        PacketUtil.doRotation(rotations2[0], rotations2[1], ListenerMotion.mc.player.onGround);
                    }
                    NetworkUtil.send((Packet<?>)new CPacketPlayerTryUseItemOnBlock(craftingPos2, ray2.sideHit, EnumHand.MAIN_HAND, f2[0], f2[1], f2[2]));
                    return;
                }
                if (((AutoRegear)this.module).placeShulker.getValue() && ((AutoRegear)this.module).getShulkerBox() == null && ((AutoRegear)this.module).hasKit() && optimal != null) {
                    optimal = ((AutoRegear)this.module).getOptimalPlacePos(true);
                    if (optimal == null) {
                        return;
                    }
                    int slot = InventoryUtil.findInHotbar(stack -> stack.getItem() instanceof ItemBlock && ((ItemBlock)stack.getItem()).getBlock() instanceof BlockShulkerBox);
                    final int inventorySlot = InventoryUtil.findInInventory(stack -> stack.getItem() instanceof ItemBlock && ((ItemBlock)stack.getItem()).getBlock() instanceof BlockShulkerBox, true);
                    if (slot == -1) {
                        if (inventorySlot == -1) {
                            return;
                        }
                        slot = InventoryUtil.hotbarToInventory(8);
                        ListenerMotion.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
                        ListenerMotion.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
                        ListenerMotion.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
                        swapped = true;
                    }
                    ((AutoRegear)this.module).slot = slot;
                    final EnumFacing facing2 = EnumFacing.DOWN;
                    ((AutoRegear)this.module).placeBlock(optimal.offset(facing2), facing2.getOpposite());
                    if (((AutoRegear)this.module).rotate.getValue() == Rotate.Normal && ((AutoRegear)this.module).rotations != null) {
                        event.setYaw(((AutoRegear)this.module).rotations[0]);
                        event.setPitch(((AutoRegear)this.module).rotations[1]);
                    }
                    ((AutoRegear)this.module).execute();
                    if (swapped) {
                        ListenerMotion.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
                        ListenerMotion.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
                        ListenerMotion.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
                    }
                }
                else {
                    final BlockPos craftingPos2 = ((AutoRegear)this.module).getShulkerBox();
                    if (craftingPos2 == null) {
                        return;
                    }
                    final float[] rotations2 = RotationUtil.getRotations(craftingPos2, EnumFacing.UP);
                    final RayTraceResult ray2 = RotationUtil.rayTraceTo(craftingPos2, (IBlockAccess)ListenerMotion.mc.world);
                    final float[] f2 = RayTraceUtil.hitVecToPlaceVec(craftingPos2, ray2.hitVec);
                    if (((AutoRegear)this.module).rotate.getValue() == Rotate.Normal) {
                        event.setYaw(rotations2[0]);
                        event.setPitch(rotations2[1]);
                    }
                    else if (((AutoRegear)this.module).rotate.getValue() != Rotate.None) {
                        PacketUtil.doRotation(rotations2[0], rotations2[1], ListenerMotion.mc.player.onGround);
                    }
                    NetworkUtil.send((Packet<?>)new CPacketPlayerTryUseItemOnBlock(craftingPos2, ray2.sideHit, EnumHand.MAIN_HAND, f2[0], f2[1], f2[2]));
                    ((AutoRegear)this.module).shouldRegear = false;
                }
            }
        }
    }
}
