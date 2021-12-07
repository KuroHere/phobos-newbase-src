//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.blocks.states;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;

public class BlockStateHelper implements Globals, IBlockStateHelper
{
    private final Map<BlockPos, IBlockState> states;
    
    public BlockStateHelper() {
        this(new HashMap<BlockPos, IBlockState>());
    }
    
    public BlockStateHelper(final Map<BlockPos, IBlockState> stateMap) {
        this.states = stateMap;
    }
    
    public IBlockState getBlockState(final BlockPos pos) {
        final IBlockState state = this.states.get(pos);
        if (state == null) {
            return BlockStateHelper.mc.world.getBlockState(pos);
        }
        return state;
    }
    
    @Override
    public void addBlockState(final BlockPos pos, final IBlockState state) {
        this.states.putIfAbsent(pos.toImmutable(), state);
    }
    
    @Override
    public void delete(final BlockPos pos) {
        this.states.remove(pos);
    }
    
    @Override
    public void clearAllStates() {
        this.states.clear();
    }
    
    public TileEntity getTileEntity(final BlockPos pos) {
        return BlockStateHelper.mc.world.getTileEntity(pos);
    }
    
    public int getCombinedLight(final BlockPos pos, final int lightValue) {
        return BlockStateHelper.mc.world.getCombinedLight(pos, lightValue);
    }
    
    public boolean isAirBlock(final BlockPos pos) {
        return this.getBlockState(pos).getBlock().isAir(this.getBlockState(pos), (IBlockAccess)this, pos);
    }
    
    public Biome getBiome(final BlockPos pos) {
        return BlockStateHelper.mc.world.getBiome(pos);
    }
    
    public int getStrongPower(final BlockPos pos, final EnumFacing direction) {
        return this.getBlockState(pos).getStrongPower((IBlockAccess)this, pos, direction);
    }
    
    public WorldType getWorldType() {
        return BlockStateHelper.mc.world.getWorldType();
    }
    
    public boolean isSideSolid(final BlockPos pos, final EnumFacing side, final boolean _default) {
        if (!BlockStateHelper.mc.world.isValid(pos)) {
            return _default;
        }
        final Chunk chunk = BlockStateHelper.mc.world.getChunkFromBlockCoords(pos);
        if (chunk == null || chunk.isEmpty()) {
            return _default;
        }
        return this.getBlockState(pos).isSideSolid((IBlockAccess)this, pos, side);
    }
}
