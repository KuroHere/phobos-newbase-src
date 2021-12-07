// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.util.network.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.network.*;

@Mixin({ EnumConnectionState.class })
public abstract class MixinEnumConnectionState
{
    @Inject(method = { "getFromPacket" }, at = { @At("HEAD") }, cancellable = true)
    private static void getFromPacketHook(final Packet<?> packet, final CallbackInfoReturnable<EnumConnectionState> cir) {
        if (packet instanceof CustomPacket) {
            cir.setReturnValue(((CustomPacket)packet).getState());
        }
    }
    
    @Inject(method = { "getPacketId" }, at = { @At("HEAD") }, cancellable = true)
    private void getPacketIdHook(final EnumPacketDirection dir, final Packet<?> packet, final CallbackInfoReturnable<Integer> cir) throws Exception {
        if (dir == EnumPacketDirection.SERVERBOUND && packet instanceof CustomPacket) {
            cir.setReturnValue(((CustomPacket)packet).getId());
        }
    }
}
