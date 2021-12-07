//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.surround;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.minecraft.*;
import java.util.*;
import net.minecraft.util.math.*;

final class ListenerSpawnObject extends ModuleListener<Surround, PacketEvent.Receive<SPacketSpawnObject>>
{
    public ListenerSpawnObject(final Surround module) {
        super(module, (Class<? super Object>)PacketEvent.Receive.class, SPacketSpawnObject.class);
    }
    
    public void invoke(final PacketEvent.Receive<SPacketSpawnObject> event) {
        if (!((Surround)this.module).predict.getValue() || ((Surround)this.module).rotate.getValue() == Rotate.Normal || Managers.SWITCH.getLastSwitch() < ((Surround)this.module).cooldown.getValue()) {
            return;
        }
        final SPacketSpawnObject packet = event.getPacket();
        if (packet.getType() != 51) {
            return;
        }
        final EntityPlayer player = ((Surround)this.module).getPlayer();
        final BlockPos pos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
        if (player.getDistanceSq(pos) < 9.0) {
            if (!((Surround)this.module).async.getValue() || DamageUtil.isWeaknessed() || ((Surround)this.module).smartRay.getValue() != RayTraceMode.Fast || !((Surround)this.module).timer.passed(((Surround)this.module).delay.getValue()) || !((Surround)this.module).pop.getValue().shouldPop(DamageUtil.calculate(pos.down(), (EntityLivingBase)player), ((Surround)this.module).popTime.getValue())) {
                event.addPostEvent(() -> ListenerMotion.start((Surround)this.module));
                return;
            }
            try {
                this.placeAsync(packet, player);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    private void placeAsync(final SPacketSpawnObject packet, final EntityPlayer player) {
        final int slot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, Blocks.ENDER_CHEST);
        if (slot == -1) {
            return;
        }
        final AxisAlignedBB bb = new EntityEnderCrystal((World)ListenerSpawnObject.mc.world, packet.getX(), packet.getY(), packet.getZ()).getEntityBoundingBox();
        final Set<BlockPos> surrounding = ((Surround)this.module).createSurrounding(((Surround)this.module).createBlocked(), Managers.ENTITIES.getPlayers());
        final Map<BlockPos, EnumFacing> toPlace = new ConcurrentHashMap<BlockPos, EnumFacing>();
        for (final BlockPos pos : surrounding) {
            if (bb.intersectsWith(new AxisAlignedBB(pos)) && ListenerSpawnObject.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
                final EnumFacing facing = BlockUtil.getFacing(pos, (IBlockAccess)ListenerSpawnObject.mc.world);
                if (facing == null) {
                    continue;
                }
                toPlace.put(pos.offset(facing), facing.getOpposite());
            }
        }
        if (toPlace.isEmpty()) {
            return;
        }
        final List<BlockPos> placed = new ArrayList<BlockPos>(Math.min(((Surround)this.module).blocks.getValue(), toPlace.size()));
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
            final int lastSlot = ListenerSpawnObject.mc.player.inventory.currentItem;
            PacketUtil.attack(packet.getEntityID());
            InventoryUtil.switchTo(slot);
            final boolean sneaking = ListenerSpawnObject.mc.player.isSneaking();
            if (!sneaking) {
                PacketUtil.sneak(true);
            }
            int blocks = 0;
            toPlace.entrySet().iterator();
            final Iterator iterator2;
            while (iterator2.hasNext()) {
                final Map.Entry<BlockPos, EnumFacing> entry = iterator2.next();
                final float[] helpingRotations = RotationUtil.getRotations(entry.getKey(), entry.getValue(), (Entity)player);
                final RayTraceResult result = RayTraceUtil.getRayTraceResultWithEntity(helpingRotations[0], helpingRotations[1], (Entity)player);
                if (((Surround)this.module).rotate.getValue() == Rotate.Packet) {
                    ListenerSpawnObject.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(helpingRotations[0], helpingRotations[1], ListenerSpawnObject.mc.player.onGround));
                }
                final float[] f = RayTraceUtil.hitVecToPlaceVec(entry.getKey(), result.hitVec);
                ListenerSpawnObject.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock((BlockPos)entry.getKey(), (EnumFacing)entry.getValue(), InventoryUtil.getHand(slot), f[0], f[1], f[2]));
                if (((Surround)this.module).placeSwing.getValue() == PlaceSwing.Always) {
                    Swing.Packet.swing(InventoryUtil.getHand(slot));
                }
                placed.add(entry.getKey().offset((EnumFacing)entry.getValue()));
                if (++blocks >= ((Surround)this.module).blocks.getValue()) {
                    break;
                }
            }
            if (((Surround)this.module).placeSwing.getValue() == PlaceSwing.Once) {
                Swing.Packet.swing(InventoryUtil.getHand(slot));
            }
            if (!sneaking) {
                PacketUtil.sneak(false);
            }
            InventoryUtil.switchTo(lastSlot);
            return;
        });
        ((Surround)this.module).timer.reset(((Surround)this.module).delay.getValue());
        if (((Surround)this.module).resync.getValue()) {
            ListenerSpawnObject.mc.addScheduledTask(() -> ((Surround)this.module).placed.addAll(placed));
        }
    }
}
