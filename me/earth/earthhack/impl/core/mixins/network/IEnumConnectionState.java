// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network;

import org.spongepowered.asm.mixin.*;
import java.util.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EnumConnectionState.class })
public interface IEnumConnectionState
{
    @Accessor("STATES_BY_CLASS")
    Map<Class<? extends Packet<?>>, EnumConnectionState> getStatesByClass();
}
