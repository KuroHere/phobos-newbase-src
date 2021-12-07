// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.item;

import org.spongepowered.asm.mixin.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ ItemFood.class })
public interface IITemFood
{
    @Accessor("potionId")
    PotionEffect getPotionId();
}
