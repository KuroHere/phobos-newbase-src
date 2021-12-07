// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.util.*;

public class Calculation extends AbstractCalculation<CrystalData> implements Globals
{
    public Calculation(final AutoCrystal module, final List<Entity> entities, final List<EntityPlayer> players, final BlockPos... blackList) {
        super(module, entities, players, blackList);
    }
    
    public Calculation(final AutoCrystal module, final List<Entity> entities, final List<EntityPlayer> players, final boolean breakOnly, final boolean noBreak, final BlockPos... blackList) {
        super(module, entities, players, breakOnly, noBreak, blackList);
    }
    
    @Override
    protected IBreakHelper<CrystalData> getBreakHelper() {
        return this.module.breakHelper;
    }
}
