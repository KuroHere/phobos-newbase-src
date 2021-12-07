// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.server;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.server.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ SPacketEntityStatus.class })
public interface ISPacketEntityStatus
{
    @Accessor("entityId")
    int getEntityId();
    
    @Accessor("logicOpcode")
    byte getLogicOpcode();
}
