//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.blocks.states;

import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public enum SimpleBlockStateHelper implements Globals, IBlockStateHelper
{
    INSTANCE;
    
    @Override
    public void addBlockState(final BlockPos pos, final IBlockState state) {
    }
    
    @Override
    public void delete(final BlockPos pos) {
    }
    
    @Override
    public void clearAllStates() {
    }
    
    public TileEntity getTileEntity(final BlockPos pos) {
        return SimpleBlockStateHelper.mc.world.getTileEntity(pos);
    }
    
    public int getCombinedLight(final BlockPos pos, final int lightValue) {
        return SimpleBlockStateHelper.mc.world.getCombinedLight(pos, lightValue);
    }
    
    public IBlockState getBlockState(final BlockPos pos) {
        return SimpleBlockStateHelper.mc.world.getBlockState(pos);
    }
    
    public boolean isAirBlock(final BlockPos pos) {
        return SimpleBlockStateHelper.mc.world.isAirBlock(pos);
    }
    
    public Biome getBiome(final BlockPos pos) {
        return SimpleBlockStateHelper.mc.world.getBiome(pos);
    }
    
    public int getStrongPower(final BlockPos pos, final EnumFacing direction) {
        return SimpleBlockStateHelper.mc.world.getStrongPower(pos, direction);
    }
    
    public WorldType getWorldType() {
        return SimpleBlockStateHelper.mc.world.getWorldType();
    }
    
    public boolean isSideSolid(final BlockPos pos, final EnumFacing side, final boolean _default) {
        return SimpleBlockStateHelper.mc.world.isSideSolid(pos, side, _default);
    }
}
