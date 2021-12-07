// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living.player;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.datasync.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EntityPlayer.class })
public interface IEntityPlayer
{
    @Accessor("ABSORPTION")
    default DataParameter<Float> getAbsorption() {
        throw new IllegalStateException("ABSORPTION accessor wasn't shadowed.");
    }
}
