// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.forge.mixins.network;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.network.handshake.*;
import me.earth.earthhack.impl.*;
import net.minecraftforge.fml.common.network.internal.*;
import me.earth.earthhack.forge.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ NetHandlerPlayClient.class })
public abstract class MixinNethandlerPlayClient
{
    @Redirect(method = { "handleJoinGame" }, at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/network/handshake/NetworkDispatcher;get(Lnet/minecraft/network/NetworkManager;)Lnet/minecraftforge/fml/common/network/handshake/NetworkDispatcher;", remap = false))
    private NetworkDispatcher networkDispatcherHook(final NetworkManager manager) {
        NetworkDispatcher dispatcher = NetworkDispatcher.get(manager);
        if (dispatcher == null) {
            Earthhack.getLogger().warn("NetworkDispatcher Disconnect avoided!");
            try {
                FMLNetworkHandler.fmlClientHandshake(manager);
                dispatcher = NetworkDispatcher.get(manager);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            if (dispatcher == null) {
                dispatcher = new ReplaceNetworkDispatcher(manager);
            }
        }
        return dispatcher;
    }
}
