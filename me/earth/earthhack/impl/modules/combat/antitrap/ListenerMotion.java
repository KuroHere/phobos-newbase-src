//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antitrap;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.offhand.*;
import me.earth.earthhack.impl.util.math.position.*;
import me.earth.earthhack.impl.modules.combat.antitrap.util.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.modules.combat.offhand.modes.*;
import net.minecraft.block.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.minecraft.*;
import java.util.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerMotion extends ModuleListener<AntiTrap, MotionUpdateEvent>
{
    protected static final ModuleCache<Offhand> OFFHAND;
    
    public ListenerMotion(final AntiTrap module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (((AntiTrap)this.module).autoOff.getValue() && !PositionUtil.getPosition().equals((Object)((AntiTrap)this.module).startPos)) {
            ((AntiTrap)this.module).disable();
            return;
        }
        switch (((AntiTrap)this.module).mode.getValue()) {
            case Crystal: {
                this.doCrystal(event);
                break;
            }
            case FacePlace:
            case Fill: {
                this.doObby(event, ((AntiTrap)this.module).mode.getValue().getOffsets());
                break;
            }
        }
    }
    
    private void doObby(final MotionUpdateEvent event, final Vec3i[] offsets) {
        if (event.getStage() == Stage.PRE) {
            ((AntiTrap)this.module).rotations = null;
            ((AntiTrap)this.module).blocksPlaced = 0;
            for (final BlockPos confirmed : ((AntiTrap)this.module).confirmed) {
                ((AntiTrap)this.module).placed.remove(confirmed);
            }
            ((AntiTrap)this.module).placed.entrySet().removeIf(entry -> System.currentTimeMillis() - entry.getValue() >= ((AntiTrap)this.module).confirm.getValue());
            final BlockPos playerPos = PositionUtil.getPosition();
            final BlockPos[] positions = new BlockPos[offsets.length];
            for (int i = 0; i < offsets.length; ++i) {
                final Vec3i offset = offsets[i];
                if (((AntiTrap)this.module).mode.getValue() != AntiTrapMode.Fill || ListenerMotion.mc.world.getBlockState(playerPos.add(offset.getX() / 2, 0, offset.getZ() / 2)).getBlock() != Blocks.BEDROCK) {
                    positions[i] = playerPos.add(offset);
                }
            }
            if (((AntiTrap)this.module).offhand.getValue()) {
                if (!InventoryUtil.isHolding(Blocks.OBSIDIAN)) {
                    ((AntiTrap)this.module).previous = ListenerMotion.OFFHAND.returnIfPresent(Offhand::getMode, null);
                    ListenerMotion.OFFHAND.computeIfPresent(o -> o.setMode(OffhandMode.CRYSTAL));
                    return;
                }
            }
            else {
                ((AntiTrap)this.module).slot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
                if (((AntiTrap)this.module).slot == -1) {
                    ModuleUtil.disable((Module)this.module, "§cNo Obsidian found.");
                    return;
                }
            }
            boolean done = true;
            final List<BlockPos> toPlace = new LinkedList<BlockPos>();
            final BlockPos[] array = positions;
            BlockPos pos = null;
            for (int length = array.length, j = 0; j < length; ++j) {
                pos = array[j];
                if (pos != null) {
                    if (((AntiTrap)this.module).mode.getValue() != AntiTrapMode.Fill || ((AntiTrap)this.module).highFill.getValue() || pos.getY() <= playerPos.getY()) {
                        if (ListenerMotion.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
                            toPlace.add(pos);
                            done = false;
                        }
                    }
                }
            }
            if (done) {
                ((AntiTrap)this.module).disable();
                return;
            }
            boolean hasPlaced = false;
            final Optional<BlockPos> crystalPos = toPlace.stream().filter(pos -> !ListenerMotion.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(pos)).isEmpty() && ListenerMotion.mc.world.getBlockState(pos).getMaterial().isReplaceable()).findFirst();
            if (crystalPos.isPresent()) {
                final BlockPos pos2 = crystalPos.get();
                hasPlaced = ((AntiTrap)this.module).placeBlock(pos2);
            }
            if (!hasPlaced) {
                final Iterator<BlockPos> iterator2 = toPlace.iterator();
                while (iterator2.hasNext()) {
                    pos = iterator2.next();
                    if (!((AntiTrap)this.module).placed.containsKey(pos) && ObbyModule.HELPER.getBlockState(pos).getMaterial().isReplaceable()) {
                        ((AntiTrap)this.module).confirmed.remove(pos);
                        if (((AntiTrap)this.module).placeBlock(pos)) {
                            break;
                        }
                        continue;
                    }
                }
            }
            if (((AntiTrap)this.module).rotate.getValue() != Rotate.None) {
                if (((AntiTrap)this.module).rotations != null) {
                    event.setYaw(((AntiTrap)this.module).rotations[0]);
                    event.setPitch(((AntiTrap)this.module).rotations[1]);
                }
            }
            else {
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, (AntiTrap)this.module::execute);
            }
        }
        else {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, (AntiTrap)this.module::execute);
        }
    }
    
    private void doCrystal(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            final List<BlockPos> positions = ((AntiTrap)this.module).getCrystalPositions();
            if (positions.isEmpty() || !((AntiTrap)this.module).isEnabled()) {
                if (!((AntiTrap)this.module).empty.getValue()) {
                    ((AntiTrap)this.module).disable();
                }
                return;
            }
            if (((AntiTrap)this.module).offhand.getValue()) {
                if (!InventoryUtil.isHolding(Items.END_CRYSTAL)) {
                    ((AntiTrap)this.module).previous = ListenerMotion.OFFHAND.returnIfPresent(Offhand::getMode, null);
                    ListenerMotion.OFFHAND.computeIfPresent(o -> o.setMode(OffhandMode.CRYSTAL));
                    return;
                }
            }
            else {
                ((AntiTrap)this.module).slot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
                if (((AntiTrap)this.module).slot == -1) {
                    ModuleUtil.disable((Module)this.module, "§cNo crystals found.");
                    return;
                }
            }
            final EntityPlayer closest = EntityUtil.getClosestEnemy();
            if (closest != null) {
                positions.sort(Comparator.comparingDouble(pos -> BlockUtil.getDistanceSq((Entity)closest, pos)));
            }
            ((AntiTrap)this.module).pos = positions.get(positions.size() - 1);
            ((AntiTrap)this.module).rotations = RotationUtil.getRotationsToTopMiddle(((AntiTrap)this.module).pos.up());
            ((AntiTrap)this.module).result = RayTraceUtil.getRayTraceResult(((AntiTrap)this.module).rotations[0], ((AntiTrap)this.module).rotations[1], 3.0f);
            if (((AntiTrap)this.module).rotate.getValue() == Rotate.Normal) {
                event.setYaw(((AntiTrap)this.module).rotations[0]);
                event.setPitch(((AntiTrap)this.module).rotations[1]);
            }
            else {
                this.executeCrystal();
            }
        }
        else {
            this.executeCrystal();
        }
    }
    
    private void executeCrystal() {
        if (((AntiTrap)this.module).pos != null && ((AntiTrap)this.module).result != null) {
            Locks.acquire(Locks.PLACE_SWITCH_LOCK, this::executeLocked);
        }
    }
    
    private void executeLocked() {
        final int lastSlot = ListenerMotion.mc.player.inventory.currentItem;
        if (!InventoryUtil.isHolding(Items.END_CRYSTAL)) {
            if (((AntiTrap)this.module).offhand.getValue() || ((AntiTrap)this.module).slot == -1) {
                return;
            }
            InventoryUtil.switchTo(((AntiTrap)this.module).slot);
        }
        final EnumHand hand = (ListenerMotion.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        final CPacketPlayerTryUseItemOnBlock place = new CPacketPlayerTryUseItemOnBlock(((AntiTrap)this.module).pos, ((AntiTrap)this.module).result.sideHit, hand, (float)((AntiTrap)this.module).result.hitVec.xCoord, (float)((AntiTrap)this.module).result.hitVec.yCoord, (float)((AntiTrap)this.module).result.hitVec.zCoord);
        final CPacketAnimation swing = new CPacketAnimation(hand);
        if (((AntiTrap)this.module).rotate.getValue() == Rotate.Packet && ((AntiTrap)this.module).rotations != null) {
            ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(((AntiTrap)this.module).rotations[0], ((AntiTrap)this.module).rotations[1], ListenerMotion.mc.player.onGround));
        }
        ListenerMotion.mc.player.connection.sendPacket((Packet)place);
        ListenerMotion.mc.player.connection.sendPacket((Packet)swing);
        InventoryUtil.switchTo(lastSlot);
        if (((AntiTrap)this.module).swing.getValue()) {
            Swing.Client.swing(hand);
        }
        ((AntiTrap)this.module).disable();
    }
    
    static {
        OFFHAND = Caches.getModule(Offhand.class);
    }
}
