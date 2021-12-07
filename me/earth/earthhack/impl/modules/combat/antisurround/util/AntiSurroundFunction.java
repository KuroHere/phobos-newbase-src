// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.antisurround.util;

import net.minecraft.util.math.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

@FunctionalInterface
public interface AntiSurroundFunction
{
    void accept(final BlockPos p0, final BlockPos p1, final BlockPos p2, final EnumFacing p3, final int p4, final MineSlots p5, final int p6, final Entity p7, final EntityPlayer p8, final boolean p9);
}
