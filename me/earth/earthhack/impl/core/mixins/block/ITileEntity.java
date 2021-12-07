// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.block;

import org.spongepowered.asm.mixin.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ TileEntity.class })
public interface ITileEntity
{
    @Accessor("blockType")
    Block getBlockType();
    
    @Accessor("blockType")
    void setBlockType(final Block p0);
}
