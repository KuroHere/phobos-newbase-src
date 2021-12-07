//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autocraft;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;

final class ListenerMotion extends ModuleListener<AutoCraft, MotionUpdateEvent>
{
    public ListenerMotion(final AutoCraft module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        final BlockPos pos = ((AutoCraft)this.module).getCraftingTableBlock();
        final BlockPos wackyPos = ((AutoCraft)this.module).getCraftingTable();
        final int inventorySlot = InventoryUtil.findBlock(Blocks.CRAFTING_TABLE, false);
        int slot = InventoryUtil.findHotbarBlock(Blocks.CRAFTING_TABLE, new Block[0]);
        boolean swapped = false;
        if (slot == -1 && inventorySlot != -1) {
            ListenerMotion.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
            ListenerMotion.mc.playerController.windowClick(0, InventoryUtil.hotbarToInventory(8), 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
            ListenerMotion.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
            swapped = true;
        }
        slot = InventoryUtil.findHotbarBlock(Blocks.CRAFTING_TABLE, new Block[0]);
        if (((AutoCraft)this.module).shouldTable && (pos != null || wackyPos != null) && slot != -1) {
            if (wackyPos == null && ((AutoCraft)this.module).placeTable.getValue()) {
                ((AutoCraft)this.module).slot = slot;
                final EnumFacing facing = BlockUtil.getFacing(pos);
                ((AutoCraft)this.module).placeBlock(pos.offset(facing), facing.getOpposite());
                if (((AutoCraft)this.module).rotate.getValue() == Rotate.Normal && ((AutoCraft)this.module).rotations != null) {
                    event.setYaw(((AutoCraft)this.module).rotations[0]);
                    event.setPitch(((AutoCraft)this.module).rotations[1]);
                }
                ((AutoCraft)this.module).execute();
                if (swapped) {
                    ListenerMotion.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
                    ListenerMotion.mc.playerController.windowClick(0, InventoryUtil.hotbarToInventory(8), 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
                    ListenerMotion.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)ListenerMotion.mc.player);
                }
            }
            else if (wackyPos != null && ListenerMotion.mc.currentScreen == null) {
                final BlockPos craftingPos = ((AutoCraft)this.module).getCraftingTable();
                final float[] rotations = RotationUtil.getRotations(craftingPos, EnumFacing.UP);
                final RayTraceResult ray = RotationUtil.rayTraceTo(craftingPos, (IBlockAccess)ListenerMotion.mc.world);
                final float[] f = RayTraceUtil.hitVecToPlaceVec(craftingPos, ray.hitVec);
                if (((AutoCraft)this.module).rotate.getValue() == Rotate.Normal) {
                    event.setYaw(rotations[0]);
                    event.setPitch(rotations[1]);
                }
                else if (((AutoCraft)this.module).rotate.getValue() == Rotate.None) {
                    PacketUtil.doRotation(rotations[0], rotations[1], ListenerMotion.mc.player.onGround);
                }
                NetworkUtil.send((Packet<?>)new CPacketPlayerTryUseItemOnBlock(craftingPos, ray.sideHit, EnumHand.MAIN_HAND, f[0], f[1], f[2]));
                ((AutoCraft)this.module).shouldTable = false;
            }
        }
    }
}
