// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network.server;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.packets.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ SPacketResourcePackSend.class })
public abstract class MixinSPacketResourcePack
{
    private static final ModuleCache<Packets> PACKETS;
    
    @Inject(method = { "readPacketData" }, at = { @At("HEAD") }, cancellable = true)
    private void readPacketDataHook(final PacketBuffer buf, final CallbackInfo ci) {
        if (MixinSPacketResourcePack.PACKETS.returnIfPresent(Packets::areCCResourcesActive, false)) {
            buf.readerIndex(buf.writerIndex());
            ci.cancel();
        }
    }
    
    static {
        PACKETS = Caches.getModule(Packets.class);
    }
}
