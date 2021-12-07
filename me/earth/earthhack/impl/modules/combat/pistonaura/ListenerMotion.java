//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.pistonaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.noglitchblocks.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.block.*;
import me.earth.earthhack.impl.modules.combat.pistonaura.util.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import net.minecraft.entity.item.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerMotion extends ModuleListener<PistonAura, MotionUpdateEvent>
{
    private static final ModuleCache<NoGlitchBlocks> NO_GLITCH_BLOCKS;
    
    public ListenerMotion(final PistonAura module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            ((PistonAura)this.module).clicked.clear();
            ((PistonAura)this.module).blocksPlaced = 0;
            ((PistonAura)this.module).rotations = null;
            ((PistonAura)this.module).pistonSlot = InventoryUtil.findHotbarBlock((Block)Blocks.PISTON, (Block)Blocks.STICKY_PISTON);
            if (((PistonAura)this.module).pistonSlot == -1 && this.shouldDisable(PistonStage.PISTON)) {
                ((PistonAura)this.module).disableWithMessage("<" + ((PistonAura)this.module).getDisplayName() + "> " + "§c" + "No Pistons found!");
                return;
            }
            ((PistonAura)this.module).redstoneSlot = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK, Blocks.REDSTONE_TORCH);
            if (((PistonAura)this.module).redstoneSlot == -1 && this.shouldDisable(PistonStage.REDSTONE)) {
                ((PistonAura)this.module).disableWithMessage("<" + ((PistonAura)this.module).getDisplayName() + "> " + "§c" + "No Redstone found!");
                return;
            }
            ((PistonAura)this.module).crystalSlot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
            if (((PistonAura)this.module).crystalSlot == -1 && this.shouldDisable(PistonStage.CRYSTAL)) {
                ((PistonAura)this.module).disableWithMessage("<" + ((PistonAura)this.module).getDisplayName() + "> " + "§c" + "No Crystals found!");
                return;
            }
            if (((PistonAura)this.module).reset && ((PistonAura)this.module).nextTimer.passed(((PistonAura)this.module).next.getValue()) && ((PistonAura)this.module).current != null) {
                ((PistonAura)this.module).current.setValid(false);
            }
            if (((PistonAura)this.module).current == null || !((PistonAura)this.module).current.isValid()) {
                ((PistonAura)this.module).current = ((PistonAura)this.module).findTarget();
                if (((PistonAura)this.module).current == null || !((PistonAura)this.module).current.isValid()) {
                    return;
                }
            }
            ((PistonAura)this.module).stage = ((PistonAura)this.module).current.getOrder()[((PistonAura)this.module).index];
            while (((PistonAura)this.module).index < 3 && ((PistonAura)this.module).stage == null) {
                final PistonAura pistonAura = (PistonAura)this.module;
                ++pistonAura.index;
                ((PistonAura)this.module).stage = ((PistonAura)this.module).current.getOrder()[((PistonAura)this.module).index];
            }
            while (((PistonAura)this.module).blocksPlaced < ((PistonAura)this.module).blocks.getValue() && this.runPre() && (((PistonAura)this.module).rotations == null || ((PistonAura)this.module).rotate.getValue() != Rotate.Normal)) {}
            if (((PistonAura)this.module).blocksPlaced > 0) {
                ((PistonAura)this.module).timer.reset(((PistonAura)this.module).delay.getValue());
            }
            if (((PistonAura)this.module).rotations != null) {
                event.setYaw(((PistonAura)this.module).rotations[0]);
                event.setPitch(((PistonAura)this.module).rotations[1]);
            }
        }
        else {
            if (((PistonAura)this.module).current == null || !((PistonAura)this.module).current.isValid()) {
                return;
            }
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
                ((PistonAura)this.module).slot = ListenerMotion.mc.player.inventory.currentItem;
                final boolean sneak = ((PistonAura)this.module).blocksPlaced == 0 || ((PistonAura)this.module).actions.isEmpty() || (((PistonAura)this.module).smartSneak.getValue() && !Managers.ACTION.isSneaking() && !((PistonAura)this.module).clicked.stream().anyMatch(b -> SpecialBlocks.shouldSneak(b, false)));
                if (!sneak) {
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerMotion.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                }
                CollectionUtil.emptyQueue(((PistonAura)this.module).actions);
                if (!sneak) {
                    ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerMotion.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (ListenerMotion.mc.player.inventory.currentItem != ((PistonAura)this.module).slot) {
                    InventoryUtil.switchTo(((PistonAura)this.module).slot);
                }
            });
        }
    }
    
    private boolean runPre() {
        ((PistonAura)this.module).stage = ((PistonAura)this.module).current.getOrder()[((PistonAura)this.module).index];
        BlockPos pos = ((PistonAura)this.module).stage.getPos(((PistonAura)this.module).current);
        if (((PistonAura)this.module).stage == PistonStage.BREAK) {
            if (!((PistonAura)this.module).explode.getValue()) {
                return false;
            }
            for (final EntityEnderCrystal crystal : ListenerMotion.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(pos))) {
                if (crystal.getPosition().equals((Object)pos.up()) || crystal.getPosition().equals((Object)((PistonAura)this.module).current.getStartPos().up())) {
                    final float[] crystalRots = RotationUtil.getRotations((Entity)crystal);
                    CPacketPlayer rotation = null;
                    if (((PistonAura)this.module).rotate.getValue() == Rotate.Packet && ((PistonAura)this.module).rotations != null) {
                        rotation = (CPacketPlayer)new CPacketPlayer.Rotation(crystalRots[0], crystalRots[1], ListenerMotion.mc.player.onGround);
                    }
                    else if (((PistonAura)this.module).rotate.getValue() != Rotate.None) {
                        ((PistonAura)this.module).rotations = crystalRots;
                    }
                    final CPacketPlayer finalRotation = rotation;
                    ((PistonAura)this.module).actions.add(() -> {
                        if (((PistonAura)this.module).breakTimer.passed(((PistonAura)this.module).breakDelay.getValue()) && Managers.SWITCH.getLastSwitch() >= ((PistonAura)this.module).coolDown.getValue()) {
                            if (finalRotation != null) {
                                ListenerMotion.mc.player.connection.sendPacket((Packet)finalRotation);
                            }
                            ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketUseEntity((Entity)crystal));
                            ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                            ((PistonAura)this.module).breakTimer.reset();
                            ((PistonAura)this.module).nextTimer.reset();
                            ((PistonAura)this.module).reset = true;
                        }
                        return;
                    });
                    return false;
                }
            }
            return false;
        }
        else {
            if (!((PistonAura)this.module).timer.passed(((PistonAura)this.module).delay.getValue())) {
                return false;
            }
            if (((PistonAura)this.module).stage == PistonStage.CRYSTAL) {
                for (final Entity entity : ListenerMotion.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos.up(), ((PistonAura)this.module).current.getStartPos()))) {
                    if (entity != null) {
                        if (EntityUtil.isDead(entity)) {
                            continue;
                        }
                        if (entity instanceof EntityEnderCrystal && entity.getPosition().equals((Object)pos.up())) {
                            final PistonAura pistonAura = (PistonAura)this.module;
                            ++pistonAura.index;
                            ((PistonAura)this.module).stage = ((PistonAura)this.module).current.getOrder()[((PistonAura)this.module).index];
                            pos = ((PistonAura)this.module).stage.getPos(((PistonAura)this.module).current);
                            break;
                        }
                        ((PistonAura)this.module).current.setValid(false);
                        return false;
                    }
                }
            }
            if (pos == null) {
                return false;
            }
            EnumFacing facing = BlockUtil.getFacing(pos);
            if ((facing == null && ((PistonAura)this.module).stage != PistonStage.CRYSTAL && (!((PistonAura)this.module).packet.getValue() || ((PistonAura)this.module).packetTimer.passed(((PistonAura)this.module).confirmation.getValue()))) || (((PistonAura)this.module).stage != PistonStage.CRYSTAL && ((PistonAura)this.module).checkEntities(pos))) {
                ((PistonAura)this.module).current.setValid(false);
                return false;
            }
            if (facing == null && ((PistonAura)this.module).stage != PistonStage.CRYSTAL) {
                return false;
            }
            final int slot = ((PistonAura)this.module).getSlot();
            if (slot == -1) {
                ((PistonAura)this.module).disableWithMessage("<" + ((PistonAura)this.module).getDisplayName() + "> " + "§c" + "Items missing!");
                return false;
            }
            ((PistonAura)this.module).actions.add(() -> InventoryUtil.switchTo(slot));
            float[] rotations;
            if (((PistonAura)this.module).stage == PistonStage.CRYSTAL) {
                RayTraceResult result;
                if (((PistonAura)this.module).rotate.getValue() != Rotate.None) {
                    rotations = RotationUtil.getRotationsToTopMiddle(pos.up());
                    result = RayTraceUtil.getRayTraceResult(rotations[0], rotations[1], ((PistonAura)this.module).placeRange.getValue());
                }
                else {
                    result = new RayTraceResult(new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP);
                }
                facing = result.sideHit.getOpposite();
                rotations = RotationUtil.getRotationsToTopMiddle(pos.up());
            }
            else {
                assert facing != null;
                rotations = RotationUtil.getRotations(pos.offset(facing), facing.getOpposite());
            }
            final EnumHand hand = (slot == -2) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
            if (((PistonAura)this.module).stage == PistonStage.PISTON && ((PistonAura)this.module).multiDirectional.getValue()) {
                final EnumFacing toFace = ((PistonAura)this.module).current.getFacing().getOpposite();
                EnumFacing piston = ((PistonAura)this.module).getFacing(pos, rotations);
                if (piston == EnumFacing.UP || piston == EnumFacing.DOWN) {
                    ((PistonAura)this.module).current.setValid(false);
                    return false;
                }
                for (int index = 0; piston != toFace && index < 36; piston = ((PistonAura)this.module).getFacing(pos, rotations), ++index) {
                    rotations[0] = (rotations[0] + 10.0f) % 360.0f;
                }
                if (piston != toFace) {
                    return false;
                }
            }
            switch (((PistonAura)this.module).rotate.getValue()) {
                case None: {
                    if (((PistonAura)this.module).stage != PistonStage.PISTON) {
                        break;
                    }
                }
                case Normal: {
                    if (((PistonAura)this.module).rotations == null) {
                        ((PistonAura)this.module).rotations = rotations;
                        break;
                    }
                    return false;
                }
                case Packet: {
                    final CPacketPlayer rotation2 = (CPacketPlayer)new CPacketPlayer.Rotation(rotations[0], rotations[1], ListenerMotion.mc.player.onGround);
                    final CPacketPlayer rotation;
                    ((PistonAura)this.module).actions.add(() -> ListenerMotion.mc.player.connection.sendPacket((Packet)rotation));
                    break;
                }
            }
            final RayTraceResult result2 = RayTraceUtil.getRayTraceResult(rotations[0], rotations[1]);
            final float[] f = RayTraceUtil.hitVecToPlaceVec(pos.offset(facing), result2.hitVec);
            final BlockPos on = (((PistonAura)this.module).stage == PistonStage.CRYSTAL) ? pos : pos.offset(facing);
            ((PistonAura)this.module).clicked.add(ListenerMotion.mc.world.getBlockState(on).getBlock());
            final CPacketPlayerTryUseItemOnBlock place = new CPacketPlayerTryUseItemOnBlock(on, facing.getOpposite(), hand, f[0], f[1], f[2]);
            if (((PistonAura)this.module).stage != PistonStage.CRYSTAL && !((PistonAura)this.module).packet.getValue() && (!ListenerMotion.NO_GLITCH_BLOCKS.isPresent() || !ListenerMotion.NO_GLITCH_BLOCKS.get().noPlace())) {
                final ItemStack stack = (slot == -2) ? ListenerMotion.mc.player.getHeldItemOffhand() : ListenerMotion.mc.player.inventory.getStackInSlot(slot);
                ((PistonAura)this.module).placeClient(stack, on, InventoryUtil.getHand(slot), facing, f[0], f[1], f[2]);
            }
            ((PistonAura)this.module).actions.add(() -> {
                ListenerMotion.mc.player.connection.sendPacket((Packet)place);
                ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
                if (((PistonAura)this.module).swing.getValue()) {
                    Swing.Client.swing(hand);
                }
                ((PistonAura)this.module).packetTimer.reset();
                return;
            });
            final PistonAura pistonAura2 = (PistonAura)this.module;
            ++pistonAura2.blocksPlaced;
            ((PistonAura)this.module).index = ((((PistonAura)this.module).index == 4) ? 4 : (((PistonAura)this.module).index + 1));
            return ((PistonAura)this.module).rotate.getValue() != Rotate.Normal;
        }
    }
    
    private boolean shouldDisable(final PistonStage missing) {
        if (((PistonAura)this.module).current == null || !((PistonAura)this.module).current.isValid()) {
            return true;
        }
        if (((PistonAura)this.module).stage == PistonStage.BREAK) {
            return false;
        }
        for (int i = ((PistonAura)this.module).index; i < 4; ++i) {
            if (((PistonAura)this.module).current.getOrder()[i] == missing) {
                return true;
            }
        }
        return false;
    }
    
    static {
        NO_GLITCH_BLOCKS = Caches.getModule(NoGlitchBlocks.class);
    }
}
