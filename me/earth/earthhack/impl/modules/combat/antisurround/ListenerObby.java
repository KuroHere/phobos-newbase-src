//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antisurround;

import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.modules.combat.legswitch.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import me.earth.earthhack.impl.util.helpers.blocks.modes.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.util.minecraft.blocks.states.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import java.util.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.helpers.blocks.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import net.minecraft.util.math.*;

final class ListenerObby extends ObbyListener<AntiSurround>
{
    private BlockPos crystalPos;
    
    public ListenerObby(final AntiSurround module) {
        super(module, 10);
        this.crystalPos = null;
    }
    
    @Override
    public void invoke(final MotionUpdateEvent event) {
        if (!((AntiSurround)this.module).async.getValue() && !((AntiSurround)this.module).normal.getValue()) {
            ((AntiSurround)this.module).reset();
            return;
        }
        this.crystalPos = null;
        if (AntiSurround.LEG_SWITCH.returnIfPresent(LegSwitch::isActive, false)) {
            if (((AntiSurround)this.module).active.get() || ((AntiSurround)this.module).semiActive.get()) {
                synchronized ((AntiSurround)this.module) {
                    ((AntiSurround)this.module).reset();
                }
            }
            return;
        }
        synchronized ((AntiSurround)this.module) {
            if (((AntiSurround)this.module).active.get()) {
                final EntityPlayer target = ((AntiSurround)this.module).target;
                if (target == null || EntityUtil.isDead((Entity)target)) {
                    ((AntiSurround)this.module).reset();
                    return;
                }
                final IBlockState state;
                if (!(state = ListenerObby.mc.world.getBlockState(PositionUtil.getPosition((Entity)target))).getMaterial().isReplaceable() && state.getBlock().getExplosionResistance((Entity)ListenerObby.mc.player) > 100.0f) {
                    ((AntiSurround)this.module).reset();
                    return;
                }
                if (((AntiSurround)this.module).pos == null) {
                    ((AntiSurround)this.module).reset();
                    return;
                }
                final IBlockStateHelper helper = new BlockStateHelper();
                helper.addAir(((AntiSurround)this.module).pos);
                final float damage = DamageUtil.calculate(((AntiSurround)this.module).pos, (EntityLivingBase)target, (IBlockAccess)helper);
                if (damage < ((AntiSurround)this.module).minDmg.getValue()) {
                    ((AntiSurround)this.module).reset();
                    return;
                }
            }
            else if (((AntiSurround)this.module).semiActive.get() && System.nanoTime() - ((AntiSurround)this.module).semiActiveTime > TimeUnit.MILLISECONDS.toNanos(15L)) {
                ((AntiSurround)this.module).semiActive.set(false);
            }
        }
        if (!((AntiSurround)this.module).active.get() && event.getStage() == Stage.PRE && ((AntiSurround)this.module).persistent.getValue() && !((AntiSurround)this.module).holdingCheck()) {
            final MineSlots mine = HelperLiquids.getSlots(((AntiSurround)this.module).onGround.getValue());
            if (mine.getBlockSlot() == -1 || mine.getToolSlot() == -1 || (mine.getDamage() < ((AntiSurround)this.module).minMine.getValue() && !(((AntiSurround)this.module).isAnvil = ((AntiSurround)this.module).anvilCheck(mine)))) {
                return;
            }
            final int crystalSlot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
            if (crystalSlot == -1) {
                return;
            }
            final int obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
            for (final EntityPlayer player : ListenerObby.mc.world.playerEntities) {
                if (player != null && !EntityUtil.isDead((Entity)player) && !player.equals((Object)ListenerObby.mc.player) && !player.equals((Object)RotationUtil.getRotationPlayer()) && !Managers.FRIENDS.contains(player)) {
                    if (player.getDistanceSqToEntity((Entity)RotationUtil.getRotationPlayer()) > MathUtil.square(((AntiSurround)this.module).range.getValue() + 2.0)) {
                        continue;
                    }
                    final BlockPos playerPos = PositionUtil.getPosition((Entity)player);
                    for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
                        final BlockPos pos = playerPos.offset(facing);
                        if (BlockUtil.getDistanceSq(pos) <= MathUtil.square(((AntiSurround)this.module).range.getValue())) {
                            final BlockPos down = pos.offset(facing).down();
                            if (BlockUtil.getDistanceSq(down) <= MathUtil.square(((AntiSurround)this.module).range.getValue())) {
                                final Entity blocking = ((AntiSurround)this.module).getBlockingEntity(pos, ListenerObby.mc.world.loadedEntityList);
                                if (blocking == null || blocking instanceof EntityEnderCrystal) {
                                    final IBlockState state2 = ListenerObby.mc.world.getBlockState(pos);
                                    if (!state2.getMaterial().isReplaceable() && state2.getBlock() != Blocks.BEDROCK && state2.getBlock() != Blocks.OBSIDIAN) {
                                        if (state2.getBlock() != Blocks.ENDER_CHEST) {
                                            final int slot = MineUtil.findBestTool(playerPos, state2);
                                            final double damage2 = MineUtil.getDamage(state2, ListenerObby.mc.player.inventory.getStackInSlot(slot), playerPos, RotationUtil.getRotationPlayer().onGround);
                                            if (damage2 >= ((AntiSurround)this.module).minMine.getValue()) {
                                                if (BlockUtil.canPlaceCrystalReplaceable(down, true, ((AntiSurround)this.module).newVer.getValue(), ListenerObby.mc.world.loadedEntityList, ((AntiSurround)this.module).newVerEntities.getValue(), 0L)) {
                                                    final IBlockState dState = ListenerObby.mc.world.getBlockState(down);
                                                    if ((((AntiSurround)this.module).obby.getValue() && obbySlot != -1) || dState.getBlock() == Blocks.OBSIDIAN || dState.getBlock() == Blocks.BEDROCK) {
                                                        BlockPos on = null;
                                                        EnumFacing onFacing = null;
                                                        for (final EnumFacing off : EnumFacing.values()) {
                                                            on = pos.offset(off);
                                                            if (BlockUtil.getDistanceSq(on) <= MathUtil.square(((AntiSurround)this.module).range.getValue()) && !ListenerObby.mc.world.getBlockState(on).getMaterial().isReplaceable()) {
                                                                onFacing = off.getOpposite();
                                                                break;
                                                            }
                                                        }
                                                        if (onFacing != null) {
                                                            synchronized ((AntiSurround)this.module) {
                                                                if (!((AntiSurround)this.module).isActive()) {
                                                                    ((AntiSurround)this.module).semiPos = null;
                                                                }
                                                                if (((AntiSurround)this.module).placeSync(pos, down, on, onFacing, obbySlot, mine, crystalSlot, blocking, player, false)) {
                                                                    ((AntiSurround)this.module).toolSlot = slot;
                                                                    ((AntiSurround)this.module).mine = true;
                                                                    if (((AntiSurround)this.module).rotations != null && ((AntiSurround)this.module).rotate.getValue() != Rotate.None) {
                                                                        this.setRotations(((AntiSurround)this.module).rotations, event);
                                                                    }
                                                                    else {
                                                                        ((AntiSurround)this.module).execute();
                                                                    }
                                                                }
                                                            }
                                                            return;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        synchronized ((AntiSurround)this.module) {
            if (!((AntiSurround)this.module).active.get()) {
                if (((AntiSurround)this.module).semiActive.get() && System.nanoTime() - ((AntiSurround)this.module).semiActiveTime > TimeUnit.MILLISECONDS.toNanos(15L)) {
                    ((AntiSurround)this.module).semiActive.set(false);
                }
                return;
            }
            if (((AntiSurround)this.module).holdingCheck()) {
                ((AntiSurround)this.module).reset();
                return;
            }
            super.invoke(event);
        }
    }
    
    @Override
    protected boolean updatePlaced() {
        super.updatePlaced();
        if (((AntiSurround)this.module).pos == null || ((AntiSurround)this.module).crystalPos == null) {
            ((AntiSurround)this.module).reset();
        }
        return !((AntiSurround)this.module).active.get();
    }
    
    @Override
    protected boolean hasTimerNotPassed() {
        final boolean result = super.hasTimerNotPassed();
        if (((AntiSurround)this.module).isAnvil && ((AntiSurround)this.module).pos != null) {
            if (!((AntiSurround)this.module).hasMined) {
                this.mine(((AntiSurround)this.module).pos);
                return false;
            }
            if (++((AntiSurround)this.module).ticks < 4) {
                return false;
            }
            if (!result) {
                return false;
            }
        }
        return result;
    }
    
    private void mine(final BlockPos pos) {
        final EnumFacing facing = RayTraceUtil.getFacing((Entity)RotationUtil.getRotationPlayer(), pos, true);
        PacketUtil.startDigging(pos, facing);
        if (((AntiSurround)this.module).digSwing.getValue()) {
            Swing.Packet.swing(EnumHand.MAIN_HAND);
        }
        ((AntiSurround)this.module).hasMined = true;
        ((AntiSurround)this.module).ticks = 0;
    }
    
    @Override
    protected int getSlot() {
        ((AntiSurround)this.module).obbySlot = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN, new Block[0]);
        final MineSlots slots = HelperLiquids.getSlots(((AntiSurround)this.module).onGround.getValue());
        if ((slots.getDamage() < ((AntiSurround)this.module).minMine.getValue() && !(((AntiSurround)this.module).isAnvil = ((AntiSurround)this.module).anvilCheck(slots))) || slots.getToolSlot() == -1 || slots.getBlockSlot() == -1) {
            ((AntiSurround)this.module).reset();
            return -1;
        }
        ((AntiSurround)this.module).crystalSlot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
        if (((AntiSurround)this.module).crystalSlot == -1) {
            ((AntiSurround)this.module).reset();
            return -1;
        }
        ((AntiSurround)this.module).toolSlot = slots.getToolSlot();
        return slots.getBlockSlot();
    }
    
    @Override
    protected TargetResult getTargets(final TargetResult result) {
        final BlockPos pos = ((AntiSurround)this.module).pos;
        final BlockPos crystalPos = ((AntiSurround)this.module).crystalPos;
        if (pos == null || crystalPos == null) {
            result.setValid(false);
            return result;
        }
        if (ListenerObby.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            AntiSurround.HELPER.addBlockState(pos, Blocks.AIR.getDefaultState());
            result.getTargets().add(pos);
        }
        else if (this.entityCheck(pos)) {
            AntiSurround.HELPER.addBlockState(pos, Blocks.AIR.getDefaultState());
            ((AntiSurround)this.module).mine = true;
            result.getTargets().add(pos);
        }
        else {
            this.placeObby(crystalPos, result);
        }
        return result;
    }
    
    @Override
    protected void disableModule() {
        ((AntiSurround)this.module).reset();
    }
    
    @Override
    protected boolean rotateCheck() {
        if (this.crystalPos != null && (!((AntiSurround)this.module).isAnvil || (((AntiSurround)this.module).ticks > 3 && ((AntiSurround)this.module).hasMined))) {
            final IBlockStateHelper helper = new BlockStateHelper();
            helper.addBlockState(this.crystalPos, Blocks.OBSIDIAN.getDefaultState());
            RayTraceResult ray = null;
            if (((AntiSurround)this.module).rotations != null) {
                ray = RotationUtil.rayTraceWithYP(this.crystalPos, (IBlockAccess)helper, ((AntiSurround)this.module).rotations[0], ((AntiSurround)this.module).rotations[1], (b, p) -> p.equals((Object)this.crystalPos));
            }
            if (ray == null) {
                final double x = RotationUtil.getRotationPlayer().posX;
                final double y = RotationUtil.getRotationPlayer().posY;
                final double z = RotationUtil.getRotationPlayer().posZ;
                ((AntiSurround)this.module).rotations = RotationUtil.getRotations(this.crystalPos.getX() + 0.5f, this.crystalPos.getY() + 1, this.crystalPos.getZ() + 0.5f, x, y, z, ListenerObby.mc.player.getEyeHeight());
                ray = RotationUtil.rayTraceWithYP(this.crystalPos, (IBlockAccess)helper, ((AntiSurround)this.module).rotations[0], ((AntiSurround)this.module).rotations[1], (b, p) -> p.equals((Object)this.crystalPos));
                if (ray == null) {
                    ray = new RayTraceResult(new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP, this.crystalPos);
                }
            }
            final int crystalSlot = ((AntiSurround)this.module).crystalSlot;
            final RayTraceResult finalResult = ray;
            final float[] f = RayTraceUtil.hitVecToPlaceVec(this.crystalPos, ray.hitVec);
            final EnumHand h = InventoryUtil.getHand(crystalSlot);
            final BlockPos finalPos = this.crystalPos;
            ((AntiSurround)this.module).post.add(() -> {
                InventoryUtil.switchTo(crystalSlot);
                ListenerObby.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(finalPos, finalResult.sideHit, h, f[0], f[1], f[2]));
                return;
            });
        }
        return super.rotateCheck();
    }
    
    private void placeObby(final BlockPos crystalPos, final TargetResult result) {
        if (((AntiSurround)this.module).crystalSlot == -1) {
            ((AntiSurround)this.module).reset();
            result.setValid(false);
            return;
        }
        List<Entity> entities = ListenerObby.mc.world.loadedEntityList;
        if (!((AntiSurround)this.module).attackTimer.passed(((AntiSurround)this.module).itemDeathTime.getValue())) {
            entities = entities.stream().filter(e -> !(e instanceof EntityItem)).collect((Collector<? super Object, ?, List<Entity>>)Collectors.toList());
        }
        if (!BlockUtil.canPlaceCrystalReplaceable(crystalPos, true, ((AntiSurround)this.module).newVer.getValue(), entities, ((AntiSurround)this.module).newVerEntities.getValue(), 0L)) {
            ((AntiSurround)this.module).reset();
            result.setValid(false);
            return;
        }
        final IBlockState state = ListenerObby.mc.world.getBlockState(crystalPos);
        if (state.getBlock() != Blocks.OBSIDIAN && state.getBlock() != Blocks.BEDROCK) {
            if (!state.getMaterial().isReplaceable() || !((AntiSurround)this.module).obby.getValue() || ((AntiSurround)this.module).obbySlot == -1) {
                ((AntiSurround)this.module).reset();
                result.setValid(false);
                return;
            }
            result.getTargets().add(crystalPos);
            ((AntiSurround)this.module).slot = ((AntiSurround)this.module).obbySlot;
        }
        this.crystalPos = crystalPos;
    }
    
    private boolean entityCheck(final BlockPos pos) {
        final BlockPos boost1 = pos.up();
        for (final Entity entity : ListenerObby.mc.world.getEntitiesWithinAABB((Class)EntityEnderCrystal.class, new AxisAlignedBB(boost1))) {
            if (entity != null) {
                if (EntityUtil.isDead(entity)) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }
}
