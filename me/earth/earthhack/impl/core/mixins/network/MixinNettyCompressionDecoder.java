// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.packets.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ NettyCompressionDecoder.class })
public abstract class MixinNettyCompressionDecoder
{
    private static final ModuleCache<Packets> PACKETS;
    
    @ModifyConstant(method = { "decode" }, constant = { @Constant(intValue = 2097152) })
    private int decodeHook(final int threshold) {
        return MixinNettyCompressionDecoder.PACKETS.returnIfPresent(Packets::isNoBookBanActive, false) ? Integer.MAX_VALUE : threshold;
    }
    
    static {
        PACKETS = Caches.getModule(Packets.class);
    }
}
