// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.audio;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.client.audio.*;

@Mixin({ SoundHandler.class })
public interface ISoundHandler
{
    @Accessor("sndManager")
    SoundManager getManager();
    
    @Accessor("soundRegistry")
    SoundRegistry getRegistry();
}
