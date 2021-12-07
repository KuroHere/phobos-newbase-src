//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.automine.util;

import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;

public class Constellation implements IConstellation
{
    private final EntityPlayer player;
    private final BlockPos playerPos;
    private final IBlockState state;
    private final IBlockState playerState;
    private final BlockPos pos;
    
    public Constellation(final IBlockAccess world, final EntityPlayer player, final BlockPos pos, final BlockPos playerPos, final IBlockState state) {
        this.player = player;
        this.pos = pos;
        this.playerPos = playerPos;
        this.playerState = world.getBlockState(playerPos);
        this.state = state;
    }
    
    @Override
    public boolean isAffected(final BlockPos pos, final IBlockState state) {
        return this.pos.equals((Object)pos) && !this.state.equals(state);
    }
    
    @Override
    public boolean isValid(final IBlockAccess world, final boolean checkPlayerState) {
        return PositionUtil.getPosition((Entity)this.player).equals((Object)this.playerPos) && world.getBlockState(this.pos).equals(this.state) && (!checkPlayerState || world.getBlockState(this.playerPos).equals(this.playerState));
    }
}
