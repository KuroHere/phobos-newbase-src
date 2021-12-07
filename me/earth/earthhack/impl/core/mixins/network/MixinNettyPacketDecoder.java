//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network;

import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.packets.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.text.*;
import java.io.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ NettyPacketDecoder.class })
public abstract class MixinNettyPacketDecoder
{
    private static final ModuleCache<Packets> PACKETS;
    
    @Redirect(method = { "decode" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;readPacketData(Lnet/minecraft/network/PacketBuffer;)V"))
    private void readPacketDataHook(final Packet<?> packet, final PacketBuffer buf) throws IOException {
        packet.readPacketData(buf);
        final int readable = buf.readableBytes();
        if (readable > 0 && MixinNettyPacketDecoder.PACKETS.returnIfPresent(Packets::isNoKickActive, false)) {
            ChatUtil.sendMessage("<Packets>§c (" + packet.getClass().getSimpleName() + ") was larger than expected, found " + readable + " bytes extra whilst reading packet.");
            buf.readerIndex(buf.writerIndex());
        }
    }
    
    static {
        PACKETS = Caches.getModule(Packets.class);
    }
}
