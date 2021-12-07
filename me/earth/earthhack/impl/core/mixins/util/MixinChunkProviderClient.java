// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.util;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ChunkProviderClient.class })
public abstract class MixinChunkProviderClient
{
    @Inject(method = { "unloadChunk" }, at = { @At("RETURN") })
    private void onUnloadChunkHook(final int x, final int z, final CallbackInfo ci) {
        Bus.EVENT_BUS.post(new UnloadChunkEvent());
    }
}
