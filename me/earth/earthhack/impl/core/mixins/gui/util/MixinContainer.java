// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui.util;

import me.earth.earthhack.impl.core.ducks.util.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ Container.class })
public abstract class MixinContainer implements IContainer
{
    @Accessor("transactionID")
    @Override
    public abstract void setTransactionID(final short p0);
    
    @Accessor("transactionID")
    @Override
    public abstract short getTransactionID();
}
