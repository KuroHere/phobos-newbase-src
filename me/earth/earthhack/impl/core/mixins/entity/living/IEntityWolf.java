// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EntityWolf.class })
public interface IEntityWolf
{
    @Accessor("isWet")
    boolean getIsWet();
    
    @Accessor("isWet")
    void setIsWet(final boolean p0);
}
