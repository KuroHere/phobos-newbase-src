//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.logger.*;
import org.spongepowered.asm.mixin.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.modules.misc.logger.util.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ NettyPacketEncoder.class })
public abstract class MixinNettyPacketEncoder
{
    private static final ModuleCache<Logger> LOGGER_MODULE;
    @Shadow
    @Final
    private EnumPacketDirection direction;
    
    @Inject(method = { "encode" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;writePacketData(Lnet/minecraft/network/PacketBuffer;)V", shift = At.Shift.AFTER) })
    private void encodeHook(final ChannelHandlerContext p_encode_1_, final Packet<?> p_encode_2_, final ByteBuf p_encode_3_, final CallbackInfo ci) {
        if (this.direction == EnumPacketDirection.SERVERBOUND && MixinNettyPacketEncoder.LOGGER_MODULE.isEnabled() && MixinNettyPacketEncoder.LOGGER_MODULE.get().getMode() == LoggerMode.Buffer) {
            if (p_encode_3_.readableBytes() != 0) {
                final int writerIndex = p_encode_3_.writerIndex();
                final int readerIndex = p_encode_3_.readerIndex();
                final PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
                try {
                    final int i = packetbuffer.readVarIntFromBuffer();
                    final Packet<?> packet = (Packet<?>)((EnumConnectionState)p_encode_1_.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getPacket(EnumPacketDirection.SERVERBOUND, i);
                    if (packet != null) {
                        packet.readPacketData(packetbuffer);
                        if (packetbuffer.readableBytes() > 0) {
                            Earthhack.getLogger().warn("Packet: " + packet.getClass().getName() + " : " + p_encode_2_.getClass().getName() + " has leftover bytes in the PacketBuffer!");
                        }
                        MixinNettyPacketEncoder.LOGGER_MODULE.get().logPacket(packet, "Originally: " + p_encode_2_.getClass().getName() + ", ", false);
                    }
                    else {
                        Earthhack.getLogger().warn("Packet was null for id: " + i);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                p_encode_3_.resetReaderIndex();
                if (p_encode_3_.readerIndex() != readerIndex || p_encode_3_.writerIndex() != writerIndex) {
                    Earthhack.getLogger().error("Indices are not matching for packet: " + p_encode_2_.getClass().getName() + "! ReaderIndex: " + readerIndex + ", now: " + p_encode_3_.readerIndex() + ", WriterIndex: " + writerIndex + ", now: " + p_encode_3_.writerIndex());
                }
            }
            else {
                Earthhack.getLogger().warn("Packet " + p_encode_2_.getClass().getName() + " has no readable bytes!");
            }
        }
    }
    
    static {
        LOGGER_MODULE = Caches.getModule(Logger.class);
    }
}
