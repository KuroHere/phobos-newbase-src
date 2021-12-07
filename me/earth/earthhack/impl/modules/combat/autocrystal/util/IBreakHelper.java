// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.util;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public interface IBreakHelper<T extends CrystalData>
{
    BreakData<T> newData(final Collection<T> p0);
    
    BreakData<T> getData(final Collection<T> p0, final List<Entity> p1, final List<EntityPlayer> p2, final List<EntityPlayer> p3);
}
