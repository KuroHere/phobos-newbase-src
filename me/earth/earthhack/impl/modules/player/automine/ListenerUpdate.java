//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.player.speedmine.*;
import me.earth.earthhack.impl.modules.combat.anvilaura.*;
import me.earth.earthhack.impl.modules.combat.antisurround.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.impl.modules.player.automine.mode.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.modules.player.automine.util.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.managers.*;
import java.util.function.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import java.util.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerUpdate extends ModuleListener<AutoMine, UpdateEvent>
{
    private static final ModuleCache<Speedmine> SPEED_MINE;
    private static final ModuleCache<AnvilAura> ANVIL_AURA;
    private static final ModuleCache<AntiSurround> ANTISURROUND;
    
    public ListenerUpdate(final AutoMine module) {
        super(module, (Class<? super Object>)UpdateEvent.class, 1);
    }
    
    public void invoke(final UpdateEvent event) {
        if (ListenerUpdate.ANTISURROUND.returnIfPresent(AntiSurround::isActive, false) || (ListenerUpdate.ANVIL_AURA.isEnabled() && ListenerUpdate.ANVIL_AURA.get().isMining())) {
            return;
        }
        if (!ListenerUpdate.SPEED_MINE.isPresent()) {
            ModuleUtil.disable((Module)this.module, "§cDisabled, Speedmine isn't registered on this version of the client!");
            return;
        }
        if ((((AutoMine)this.module).mode.getValue() == AutoMineMode.Combat || ((AutoMine)this.module).mode.getValue() == AutoMineMode.AntiTrap) && (!ListenerUpdate.SPEED_MINE.isEnabled() || (ListenerUpdate.SPEED_MINE.get().getMode() != MineMode.Smart && ListenerUpdate.SPEED_MINE.get().getMode() != MineMode.Instant))) {
            ModuleUtil.disable((Module)this.module, "§cDisabled, enable Speedmine - Smart for AutoMine - Combat!");
            return;
        }
        if (ListenerUpdate.mc.player.isCreative() || ListenerUpdate.mc.player.isSpectator() || !((AutoMine)this.module).timer.passed(((AutoMine)this.module).delay.getValue()) || (((AutoMine)this.module).mode.getValue() == AutoMineMode.Combat && ListenerUpdate.SPEED_MINE.get().getPos() != null && (((AutoMine)this.module).current == null || !((AutoMine)this.module).current.equals((Object)ListenerUpdate.SPEED_MINE.get().getPos())))) {
            return;
        }
        BlockPos invalid = null;
        if (((AutoMine)this.module).constellation != null) {
            ((AutoMine)this.module).constellation.update((IAutomine)this.module);
        }
        if (((AutoMine)this.module).constellationCheck.getValue() && ((AutoMine)this.module).constellation != null) {
            if (((AutoMine)this.module).constellation.isValid((IBlockAccess)ListenerUpdate.mc.world, ((AutoMine)this.module).checkPlayerState.getValue()) && !((AutoMine)this.module).constellationTimer.passed(((AutoMine)this.module).maxTime.getValue()) && ((AutoMine)this.module).constellation.cantBeImproved()) {
                return;
            }
            if (((AutoMine)this.module).constellation.cantBeImproved()) {
                invalid = ((AutoMine)this.module).current;
                ((AutoMine)this.module).constellation = null;
                ((AutoMine)this.module).current = null;
            }
        }
        if (!((AutoMine)this.module).improve.getValue() && ((AutoMine)this.module).constellation != null && ((AutoMine)this.module).constellation.cantBeImproved()) {
            return;
        }
        ((AutoMine)this.module).blackList.entrySet().removeIf(e -> (System.currentTimeMillis() - e.getValue()) / 1000.0f > ((AutoMine)this.module).blackListFor.getValue());
        if (((AutoMine)this.module).mode.getValue() == AutoMineMode.Combat) {
            if ((((AutoMine)this.module).prioSelf.getValue() && this.checkSelfTrap()) || this.checkEnemies(false)) {
                return;
            }
            final BlockPos position = PositionUtil.getPosition();
            if (((AutoMine)this.module).self.getValue() && ((!((AutoMine)this.module).prioSelf.getValue() && this.checkSelfTrap()) || this.checkPos((EntityPlayer)ListenerUpdate.mc.player, position))) {
                return;
            }
            if (((AutoMine)this.module).mineBurrow.getValue() && this.checkEnemies(true)) {
                return;
            }
            final IBlockState state;
            if (((AutoMine)this.module).selfEchestMine.getValue() && ((AutoMine)this.module).isValid(Blocks.ENDER_CHEST.getDefaultState()) && (state = ListenerUpdate.mc.world.getBlockState(position)).getBlock() == Blocks.ENDER_CHEST) {
                this.attackPos(position, new Constellation((IBlockAccess)ListenerUpdate.mc.world, (EntityPlayer)ListenerUpdate.mc.player, position, position, state));
                return;
            }
            if (invalid != null && invalid.equals((Object)ListenerUpdate.SPEED_MINE.get().getPos()) && ((AutoMine)this.module).resetIfNotValid.getValue()) {
                ListenerUpdate.SPEED_MINE.get().reset();
            }
            if (((AutoMine)this.module).constellation == null && ((AutoMine)this.module).echest.getValue()) {
                TileEntity closest = null;
                double minDist = Double.MAX_VALUE;
                for (final TileEntity entity : ListenerUpdate.mc.world.loadedTileEntityList) {
                    if (entity instanceof TileEntityEnderChest && BlockUtil.getDistanceSq(entity.getPos()) < MathUtil.square(((AutoMine)this.module).echestRange.getValue())) {
                        final double dist = entity.getPos().distanceSqToCenter(RotationUtil.getRotationPlayer().posX, RotationUtil.getRotationPlayer().posY + ListenerUpdate.mc.player.getEyeHeight(), RotationUtil.getRotationPlayer().posZ);
                        if (dist >= minDist) {
                            continue;
                        }
                        minDist = dist;
                        closest = entity;
                    }
                }
                if (closest != null) {
                    ((AutoMine)this.module).offer(new EchestConstellation(closest.getPos()));
                    ((AutoMine)this.module).attackPos(closest.getPos());
                    return;
                }
            }
            if ((((AutoMine)this.module).constellation == null || (!((AutoMine)this.module).constellation.cantBeImproved() && !(((AutoMine)this.module).constellation instanceof BigConstellation))) && ((AutoMine)this.module).terrain.getValue() && ((AutoMine)this.module).terrainTimer.passed(((AutoMine)this.module).terrainDelay.getValue()) && ((AutoMine)this.module).future == null && (!((AutoMine)this.module).checkCrystalDownTime.getValue() || ((AutoMine)this.module).downTimer.passed(((AutoMine)this.module).downTime.getValue()))) {
                final boolean c = ((AutoMine)this.module).closestPlayer.getValue();
                double closest2 = Double.MAX_VALUE;
                EntityPlayer best = null;
                final List<EntityPlayer> players = new ArrayList<EntityPlayer>(c ? 0 : 10);
                for (final EntityPlayer p : ListenerUpdate.mc.world.playerEntities) {
                    if (p != null && !EntityUtil.isDead((Entity)p) && p.getDistanceSqToEntity((Entity)RotationUtil.getRotationPlayer()) <= 400.0) {
                        if (Managers.FRIENDS.contains(p)) {
                            continue;
                        }
                        if (c) {
                            final double dist2 = p.getDistanceSqToEntity((Entity)RotationUtil.getRotationPlayer());
                            if (dist2 >= closest2) {
                                continue;
                            }
                            closest2 = dist2;
                            best = p;
                        }
                        else {
                            players.add(p);
                        }
                    }
                }
                if ((c && best == null) || (!c && players.isEmpty())) {
                    return;
                }
                final List<Entity> entities = (List<Entity>)ListenerUpdate.mc.world.loadedEntityList.stream().filter(Objects::nonNull).filter(e -> !(e instanceof EntityItem)).filter(e -> !EntityUtil.isDead(e)).filter(e -> e.getDistanceSqToEntity((Entity)RotationUtil.getRotationPlayer()) < MathUtil.square(((AutoMine)this.module).range.getValue())).collect(Collectors.toList());
                final AutoMineCalc calc = new AutoMineCalc((IAutomine)this.module, players, entities, best, ((AutoMine)this.module).minDmg.getValue(), ((AutoMine)this.module).maxSelfDmg.getValue(), ((AutoMine)this.module).range.getValue(), ((AutoMine)this.module).obbyPositions.getValue(), ((AutoMine)this.module).newV.getValue(), ((AutoMine)this.module).newVEntities.getValue(), ((AutoMine)this.module).mineObby.getValue(), ((AutoMine)this.module).breakTrace.getValue(), ((AutoMine)this.module).suicide.getValue());
                ((AutoMine)this.module).future = Managers.THREAD.submit(calc);
                ((AutoMine)this.module).terrainTimer.reset();
            }
        }
        else if (((AutoMine)this.module).mode.getValue() == AutoMineMode.AntiTrap) {
            final BlockPos boost = PositionUtil.getPosition().up(2);
            if (!boost.equals((Object)((AutoMine)this.module).last) && !MovementUtil.isMoving()) {
                ListenerUpdate.SPEED_MINE.get().getTimer().setTime(0L);
                ((AutoMine)this.module).current = boost;
                ListenerUpdate.mc.playerController.onPlayerDamageBlock(boost, EnumFacing.DOWN);
                ((AutoMine)this.module).timer.reset();
                ((AutoMine)this.module).last = boost;
            }
        }
    }
    
    private boolean checkEnemies(final boolean burrow) {
        BlockPos closestPos = null;
        Constellation closest = null;
        double distance = Double.MAX_VALUE;
        for (final EntityPlayer player : ListenerUpdate.mc.world.playerEntities) {
            if (EntityUtil.isValid((Entity)player, ((AutoMine)this.module).range.getValue() + 1.0f) && !player.equals((Object)ListenerUpdate.mc.player)) {
                final BlockPos playerPos = PositionUtil.getPosition((Entity)player);
                if (burrow) {
                    final double dist = ListenerUpdate.mc.player.getDistanceSq(playerPos);
                    if (dist >= distance) {
                        continue;
                    }
                    final IBlockState state;
                    if (!this.isValid(playerPos, state = ListenerUpdate.mc.world.getBlockState(playerPos))) {
                        continue;
                    }
                    closestPos = playerPos;
                    closest = new Constellation((IBlockAccess)ListenerUpdate.mc.world, player, playerPos, playerPos, state);
                    distance = dist;
                }
                else {
                    final IBlockState playerPosState = ListenerUpdate.mc.world.getBlockState(playerPos);
                    if (!playerPosState.getMaterial().isReplaceable() && playerPosState.getBlock().getExplosionResistance((Entity)ListenerUpdate.mc.player) >= 100.0f) {
                        continue;
                    }
                    if (((AutoMine)this.module).head.getValue()) {
                        final BlockPos upUp = playerPos.up(2);
                        final IBlockState state = ListenerUpdate.mc.world.getBlockState(upUp);
                        if (this.isValid(upUp, state)) {
                            this.attackPos(upUp, new Constellation((IBlockAccess)ListenerUpdate.mc.world, player, upUp, playerPos, state));
                            return true;
                        }
                    }
                    for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
                        final BlockPos offset = playerPos.offset(facing);
                        final double dist2 = ListenerUpdate.mc.player.getDistanceSq(offset);
                        Label_0624: {
                            if (dist2 < distance) {
                                final IBlockState state2 = ListenerUpdate.mc.world.getBlockState(offset);
                                if (this.isValid(offset, state2)) {
                                    if (((AutoMine)this.module).mineL.getValue() && ListenerUpdate.mc.world.getBlockState(offset.up()).getMaterial().isReplaceable()) {
                                        boolean found = false;
                                        for (final EnumFacing l : EnumFacing.HORIZONTALS) {
                                            if (l != facing) {
                                                if (l != facing.getOpposite()) {
                                                    if (((AutoMine)this.module).checkCrystalPos(offset.offset(l).down())) {
                                                        closestPos = offset;
                                                        closest = new Constellation((IBlockAccess)ListenerUpdate.mc.world, player, offset, playerPos, state2);
                                                        distance = dist2;
                                                        found = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        if (found) {
                                            break Label_0624;
                                        }
                                    }
                                    if (((AutoMine)this.module).checkCrystalPos(offset.offset(facing).down())) {
                                        closestPos = offset;
                                        closest = new Constellation((IBlockAccess)ListenerUpdate.mc.world, player, offset, playerPos, state2);
                                        distance = dist2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (closest != null) {
            this.attackPos(closestPos, closest);
            return true;
        }
        return false;
    }
    
    private boolean checkSelfTrap() {
        final BlockPos playerPos = PositionUtil.getPosition();
        final BlockPos upUp = playerPos.up(2);
        final IBlockState state = ListenerUpdate.mc.world.getBlockState(upUp);
        if (this.isValid(upUp, state)) {
            this.attackPos(upUp, new Constellation((IBlockAccess)ListenerUpdate.mc.world, (EntityPlayer)ListenerUpdate.mc.player, upUp, playerPos, state));
            return true;
        }
        return false;
    }
    
    private boolean checkPos(final EntityPlayer player, final BlockPos playerPos) {
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            final BlockPos offset = playerPos.offset(facing);
            final IBlockState state = ListenerUpdate.mc.world.getBlockState(offset);
            if (this.isValid(offset, state) && ((AutoMine)this.module).checkCrystalPos(offset.offset(facing).down())) {
                this.attackPos(offset, new Constellation((IBlockAccess)ListenerUpdate.mc.world, player, offset, playerPos, state));
                return true;
            }
        }
        return false;
    }
    
    private boolean isValid(final BlockPos pos, final IBlockState state) {
        return !((AutoMine)this.module).blackList.containsKey(pos) && MineUtil.canBreak(state, pos) && ((AutoMine)this.module).isValid(state) && ListenerUpdate.mc.player.getDistanceSq(pos) <= MathUtil.square(ListenerUpdate.SPEED_MINE.get().getRange()) && !state.getMaterial().isReplaceable();
    }
    
    public void attackPos(final BlockPos pos, final Constellation c) {
        if (((AutoMine)this.module).checkCurrent.getValue() && pos.equals((Object)((AutoMine)this.module).current)) {
            return;
        }
        ((AutoMine)this.module).offer(c);
        ((AutoMine)this.module).attackPos(pos);
    }
    
    static {
        SPEED_MINE = Caches.getModule(Speedmine.class);
        ANVIL_AURA = Caches.getModule(AnvilAura.class);
        ANTISURROUND = Caches.getModule(AntiSurround.class);
    }
}
