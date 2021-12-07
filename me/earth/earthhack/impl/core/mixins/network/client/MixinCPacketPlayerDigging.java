// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.client;

import me.earth.earthhack.impl.core.ducks.network.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ CPacketPlayerDigging.class })
public abstract class MixinCPacketPlayerDigging implements ICPacketPlayerDigging
{
    @Unique
    private boolean clientSideBreaking;
    
    @Unique
    @Override
    public void setClientSideBreaking(final boolean breaking) {
        this.clientSideBreaking = breaking;
    }
    
    @Unique
    @Override
    public boolean isClientSideBreaking() {
        return this.clientSideBreaking;
    }
}
