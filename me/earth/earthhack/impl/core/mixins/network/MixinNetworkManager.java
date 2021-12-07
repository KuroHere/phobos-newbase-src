//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network;

import me.earth.earthhack.impl.core.ducks.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.logger.*;
import me.earth.earthhack.impl.modules.misc.packetdelay.*;
import org.spongepowered.asm.mixin.*;
import io.netty.util.concurrent.*;
import me.earth.earthhack.impl.modules.misc.logger.util.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.util.mcp.*;
import java.util.concurrent.*;
import org.spongepowered.asm.mixin.injection.*;
import javax.annotation.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import java.util.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.event.events.network.*;
import io.netty.channel.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ NetworkManager.class })
public abstract class MixinNetworkManager implements INetworkManager
{
    private static final ModuleCache<Logger> LOGGER_MODULE;
    private static final ModuleCache<PacketDelay> PACKET_DELAY;
    @Shadow
    @Final
    private static org.apache.logging.log4j.Logger LOGGER;
    @Shadow
    private Channel channel;
    @Shadow
    private INetHandler packetListener;
    
    @Shadow
    public abstract boolean isChannelOpen();
    
    @Shadow
    protected abstract void flushOutboundQueue();
    
    @Shadow
    protected abstract void dispatchPacket(final Packet<?> p0, final GenericFutureListener<? extends Future<? super Void>>[] p1);
    
    @Shadow
    public abstract void setConnectionState(final EnumConnectionState p0);
    
    @Shadow
    public abstract void sendPacket(final Packet<?> p0);
    
    @Override
    public Packet<?> sendPacketNoEvent(final Packet<?> packetIn) {
        return this.sendPacketNoEvent(packetIn, true);
    }
    
    @Override
    public Packet<?> sendPacketNoEvent(final Packet<?> packet, final boolean post) {
        if (MixinNetworkManager.LOGGER_MODULE.isEnabled() && MixinNetworkManager.LOGGER_MODULE.get().getMode() == LoggerMode.Normal) {
            MixinNetworkManager.LOGGER_MODULE.get().logPacket(packet, "Sending (No Event) Post: " + post + ", ", false);
        }
        final PacketEvent.NoEvent<?> event = new PacketEvent.NoEvent<Object>(packet, post);
        Bus.EVENT_BUS.post(event, packet.getClass());
        if (event.isCancelled()) {
            return packet;
        }
        if (this.isChannelOpen()) {
            this.flushOutboundQueue();
            if (post) {
                this.dispatchPacket(packet, null);
            }
            else {
                this.dispatchSilently(packet);
            }
            return packet;
        }
        return null;
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacketPre(final Packet<?> packet, final CallbackInfo info) {
        if (MixinNetworkManager.PACKET_DELAY.isEnabled() && !MixinNetworkManager.PACKET_DELAY.get().packets.contains(packet) && MixinNetworkManager.PACKET_DELAY.get().isPacketValid(MappingProvider.simpleName(packet.getClass()))) {
            info.cancel();
            MixinNetworkManager.PACKET_DELAY.get().service.schedule(() -> {
                MixinNetworkManager.PACKET_DELAY.get().packets.add((Packet<?>)packet);
                this.sendPacket((Packet<?>)packet);
                MixinNetworkManager.PACKET_DELAY.get().packets.remove(packet);
            }, MixinNetworkManager.PACKET_DELAY.get().getDelay(), TimeUnit.MILLISECONDS);
            return;
        }
        final PacketEvent.Send<?> event = new PacketEvent.Send<Object>(packet);
        Bus.EVENT_BUS.post(event, packet.getClass());
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "dispatchPacket" }, at = { @At("RETURN") })
    private void onSendPacketPost(final Packet<?> packetIn, @Nullable final GenericFutureListener<? extends Future<? super Void>>[] futureListeners, final CallbackInfo info) {
        final PacketEvent.Post<?> event = new PacketEvent.Post<Object>(packetIn);
        Bus.EVENT_BUS.post(event, packetIn.getClass());
    }
    
    @Inject(method = { "channelRead0" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V", shift = At.Shift.BEFORE) }, cancellable = true)
    private void onChannelRead(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo info) {
        final PacketEvent.Receive<?> event = new PacketEvent.Receive<Object>(packet);
        try {
            Bus.EVENT_BUS.post(event, packet.getClass());
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        if (event.isCancelled()) {
            info.cancel();
        }
        else if (!event.getPostEvents().isEmpty()) {
            try {
                packet.processPacket(this.packetListener);
            }
            catch (ThreadQuickExitException ex) {}
            for (final Runnable runnable : event.getPostEvents()) {
                Scheduler.getInstance().scheduleAsynchronously(runnable);
            }
            info.cancel();
        }
    }
    
    @Inject(method = { "closeChannel" }, at = { @At(value = "INVOKE", target = "Lio/netty/channel/Channel;isOpen()Z", remap = false) })
    private void onDisconnectHook(final ITextComponent component, final CallbackInfo info) {
        if (this.isChannelOpen()) {
            Bus.EVENT_BUS.post(new DisconnectEvent(component));
        }
    }
    
    private void dispatchSilently(final Packet<?> inPacket) {
        final EnumConnectionState enumconnectionstate = EnumConnectionState.getFromPacket((Packet)inPacket);
        final EnumConnectionState protocolConnectionState = (EnumConnectionState)this.channel.attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get();
        if (protocolConnectionState != enumconnectionstate) {
            MixinNetworkManager.LOGGER.debug("Disabled auto read");
            this.channel.config().setAutoRead(false);
        }
        if (this.channel.eventLoop().inEventLoop()) {
            if (enumconnectionstate != protocolConnectionState) {
                this.setConnectionState(enumconnectionstate);
            }
            final ChannelFuture channelfuture = this.channel.writeAndFlush((Object)inPacket);
            channelfuture.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        }
        else {
            this.channel.eventLoop().execute(() -> {
                if (enumconnectionstate != protocolConnectionState) {
                    this.setConnectionState(enumconnectionstate);
                }
                final ChannelFuture channelfuture2 = this.channel.writeAndFlush((Object)inPacket);
                channelfuture2.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
            });
        }
    }
    
    @Inject(method = { "exceptionCaught" }, at = { @At("RETURN") })
    private void onExceptionCaught(final ChannelHandlerContext p_exceptionCaught_1_, final Throwable p_exceptionCaught_2_, final CallbackInfo ci) {
        p_exceptionCaught_2_.printStackTrace();
        System.out.println("----------------------------------------------");
        Thread.dumpStack();
    }
    
    static {
        LOGGER_MODULE = Caches.getModule(Logger.class);
        PACKET_DELAY = Caches.getModule(PacketDelay.class);
    }
}
