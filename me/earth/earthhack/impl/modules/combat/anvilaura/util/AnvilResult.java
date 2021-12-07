//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.anvilaura.util;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.math.position.*;
import java.util.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.helpers.blocks.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;

public class AnvilResult implements Globals, Comparable<AnvilResult>
{
    private static final AxisAlignedBB ANVIL_BB;
    private final Set<BlockPos> positions;
    private final Set<BlockPos> mine;
    private final Set<BlockPos> trap;
    private final EntityPlayer player;
    private final BlockPos playerPos;
    private final BlockPos pressurePos;
    private final boolean validPressure;
    private final boolean fallingEntities;
    private final boolean specialPressure;
    
    public AnvilResult(final Set<BlockPos> positions, final Set<BlockPos> mine, final Set<BlockPos> trap, final EntityPlayer player, final BlockPos playerPos, final BlockPos pressurePos, final boolean validPressure, final boolean fallingEntities, final boolean specialPressure) {
        this.positions = positions;
        this.mine = mine;
        this.trap = trap;
        this.player = player;
        this.playerPos = playerPos;
        this.pressurePos = pressurePos;
        this.validPressure = validPressure;
        this.fallingEntities = fallingEntities;
        this.specialPressure = specialPressure;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public BlockPos getPressurePos() {
        return this.pressurePos;
    }
    
    public BlockPos getPlayerPos() {
        return this.playerPos;
    }
    
    public Set<BlockPos> getPositions() {
        return this.positions;
    }
    
    public Set<BlockPos> getMine() {
        return this.mine;
    }
    
    public Set<BlockPos> getTrap() {
        return this.trap;
    }
    
    public boolean hasValidPressure() {
        return this.validPressure;
    }
    
    public boolean hasFallingEntities() {
        return this.fallingEntities;
    }
    
    public boolean hasSpecialPressure() {
        return this.specialPressure;
    }
    
    @Override
    public int hashCode() {
        return this.player.getEntityId() * 31 + this.playerPos.hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj == this || (obj instanceof AnvilResult && ((AnvilResult)obj).player.equals((Object)this.player) && ((AnvilResult)obj).playerPos.equals((Object)this.playerPos));
    }
    
    @Override
    public int compareTo(final AnvilResult o) {
        if (this.equals(o)) {
            return 0;
        }
        final int r = Double.compare(BlockUtil.getDistanceSq(o.playerPos), BlockUtil.getDistanceSq(this.playerPos));
        return (r == 0) ? 1 : r;
    }
    
    public static Set<AnvilResult> create(final List<EntityPlayer> players, final List<Entity> entities, final double minY, final double range) {
        final Set<AnvilResult> results = new TreeSet<AnvilResult>();
        final EntityPlayer rotation = RotationUtil.getRotationPlayer();
        for (final EntityPlayer player : players) {
            if (player.posY >= 0.0 && !EntityUtil.isDead((Entity)player) && !player.equals((Object)RotationUtil.getRotationPlayer()) && !player.equals((Object)AnvilResult.mc.player)) {
                if (Managers.FRIENDS.contains(player)) {
                    continue;
                }
                final double distance = MathUtil.square(player.posX - rotation.posX) + MathUtil.square(player.posZ - rotation.posZ);
                if (distance > MathUtil.square(range)) {
                    continue;
                }
                for (final BlockPos pos : PositionUtil.getBlockedPositions(player.getEntityBoundingBox(), 1.0)) {
                    if (player.getEntityBoundingBox().intersectsWith(AnvilResult.ANVIL_BB.offset(pos))) {
                        checkPos(player, pos, results, entities, minY, range);
                    }
                }
            }
        }
        return results;
    }
    
    private static void checkPos(final EntityPlayer player, final BlockPos playerPos, final Set<AnvilResult> results, final List<Entity> entities, final double minY, final double range) {
        final int x = playerPos.getX();
        final int z = playerPos.getZ();
        final BlockPos upUp = playerPos.up(2);
        final Set<BlockPos> trap = new LinkedHashSet<BlockPos>();
        for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
            final BlockPos trapPos = upUp.offset(facing);
            if (ObbyModule.HELPER.getBlockState(trapPos).getMaterial().isReplaceable()) {
                trap.add(trapPos);
            }
        }
        boolean validPressure = true;
        BlockPos pressure = playerPos;
        boolean specialPressure = false;
        final Set<BlockPos> mine = new LinkedHashSet<BlockPos>();
        IBlockState playerState = ObbyModule.HELPER.getBlockState(pressure);
        if (!playerState.getMaterial().isReplaceable() && !SpecialBlocks.PRESSURE_PLATES.contains(playerState.getBlock())) {
            if (playerState.getBlock() == Blocks.ANVIL) {
                validPressure = false;
                mine.add(pressure);
            }
            else if (!AnvilResult.mc.world.func_190527_a(Blocks.ANVIL, pressure, true, EnumFacing.UP, (Entity)null) && playerState.getBoundingBox((IBlockAccess)ObbyModule.HELPER, pressure).maxY < 1.0) {
                specialPressure = true;
            }
            pressure = playerPos.up();
            playerState = ObbyModule.HELPER.getBlockState(pressure);
            if (!playerState.getMaterial().isReplaceable()) {
                if (playerState.getBlock() != Blocks.ANVIL) {
                    return;
                }
                mine.add(pressure);
            }
        }
        if (validPressure && !specialPressure) {
            final BlockPos pressureDown = pressure.down();
            final IBlockState state = ObbyModule.HELPER.getBlockState(pressureDown);
            if (!isTopSolid(pressureDown, state.getBlock(), state, EnumFacing.UP, (IBlockAccess)ObbyModule.HELPER) && !(state.getBlock() instanceof BlockFence)) {
                validPressure = false;
            }
        }
        BlockPos lowest = null;
        boolean fallingEntities = false;
        final double yPos = RotationUtil.getRotationPlayer().posY;
        final Set<BlockPos> positions = new LinkedHashSet<BlockPos>();
        for (double y = yPos - range; y < yPos + range; ++y) {
            final BlockPos pos = new BlockPos((double)x, y, (double)z);
            fallingEntities = (fallingEntities || checkForFalling(pos, entities));
            if (y >= player.posY + minY) {
                if (!BlockFalling.canFallThrough(ObbyModule.HELPER.getBlockState(pos))) {
                    break;
                }
                if (lowest == null) {
                    lowest = pos;
                }
                positions.add(pos);
            }
        }
        if (lowest == null) {
            return;
        }
        boolean bad = false;
        for (int y2 = pressure.getY(); y2 < lowest.getY(); ++y2) {
            final BlockPos pos = new BlockPos(x, y2, z);
            fallingEntities = (fallingEntities || checkForFalling(pos, entities));
            if (pos.getY() != pressure.getY()) {
                final IBlockState state2 = ObbyModule.HELPER.getBlockState(pos);
                if (!BlockFalling.canFallThrough(state2)) {
                    if (state2.getBlock() != Blocks.ANVIL) {
                        bad = true;
                        break;
                    }
                    mine.add(pos);
                }
            }
        }
        if (bad) {
            return;
        }
        results.add(new AnvilResult(positions, mine, trap, player, playerPos, pressure, validPressure, fallingEntities, specialPressure));
    }
    
    private static boolean checkForFalling(final BlockPos pos, final List<Entity> entities) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos);
        for (final Entity entity : entities) {
            if (entity instanceof EntityFallingBlock && !entity.isDead && entity.getEntityBoundingBox().intersectsWith(bb)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean isTopSolid(final BlockPos pos, final Block block, final IBlockState base_state, final EnumFacing side, final IBlockAccess world) {
        if (base_state.isFullyOpaque() && side == EnumFacing.UP) {
            return true;
        }
        if (block instanceof BlockSlab) {
            final IBlockState state = block.getActualState(base_state, world, pos);
            return base_state.isFullBlock() || (state.getValue((IProperty)BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP && side == EnumFacing.UP) || (state.getValue((IProperty)BlockSlab.HALF) == BlockSlab.EnumBlockHalf.BOTTOM && side == EnumFacing.DOWN);
        }
        if (block instanceof BlockFarmland) {
            return side != EnumFacing.DOWN && side != EnumFacing.UP;
        }
        if (block instanceof BlockStairs) {
            final IBlockState state = block.getActualState(base_state, world, pos);
            final boolean flipped = state.getValue((IProperty)BlockStairs.HALF) == BlockStairs.EnumHalf.TOP;
            final BlockStairs.EnumShape shape = (BlockStairs.EnumShape)state.getValue((IProperty)BlockStairs.SHAPE);
            final EnumFacing facing = (EnumFacing)state.getValue((IProperty)BlockStairs.FACING);
            if (side == EnumFacing.UP) {
                return flipped;
            }
            if (side == EnumFacing.DOWN) {
                return !flipped;
            }
            if (facing == side) {
                return true;
            }
            if (flipped) {
                if (shape == BlockStairs.EnumShape.INNER_LEFT) {
                    return side == facing.rotateYCCW();
                }
                if (shape == BlockStairs.EnumShape.INNER_RIGHT) {
                    return side == facing.rotateY();
                }
            }
            else {
                if (shape == BlockStairs.EnumShape.INNER_LEFT) {
                    return side == facing.rotateY();
                }
                if (shape == BlockStairs.EnumShape.INNER_RIGHT) {
                    return side == facing.rotateYCCW();
                }
            }
            return false;
        }
        else {
            if (block instanceof BlockSnow) {
                final IBlockState state = block.getActualState(base_state, world, pos);
                return (int)state.getValue((IProperty)BlockSnow.LAYERS) >= 8;
            }
            return (block instanceof BlockHopper && side == EnumFacing.UP) || block instanceof BlockCompressedPowered || base_state.isFullyOpaque();
        }
    }
    
    static {
        ANVIL_BB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
    }
}
