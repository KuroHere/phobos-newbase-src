// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.projectile.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EntityArrow.class })
public interface IEntityArrow
{
    @Accessor("inGround")
    boolean isInGround();
}
