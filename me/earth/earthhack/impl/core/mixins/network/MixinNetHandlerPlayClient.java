//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.network;

import me.earth.earthhack.impl.core.ducks.network.*;
import net.minecraft.client.network.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.misc.packets.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.network.play.server.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ NetHandlerPlayClient.class })
public abstract class MixinNetHandlerPlayClient implements INetHandlerPlayClient
{
    private static final ModuleCache<Packets> PACKETS;
    @Shadow
    @Final
    private NetworkManager netManager;
    
    @Accessor("doneLoadingTerrain")
    @Override
    public abstract boolean isDoneLoadingTerrain();
    
    @Accessor("doneLoadingTerrain")
    @Override
    public abstract void setDoneLoadingTerrain(final boolean p0);
    
    @Redirect(method = { "handleEntityTeleport" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setPositionAndRotationDirect(DDDFFIZ)V", ordinal = 0))
    private void setPositionAndRotationDirectHook(final Entity entity, final double x, final double y, final double z, final float yaw, final float pitch, final int posRotationIncrements, final boolean teleport) {
        if (posRotationIncrements == 0 && MixinNetHandlerPlayClient.PACKETS.returnIfPresent(Packets::areMiniTeleportsActive, false)) {
            entity.setPositionAndRotation(x, y, z, yaw, pitch);
        }
        else {
            entity.setPositionAndRotationDirect(x, y, z, yaw, pitch, posRotationIncrements, teleport);
        }
    }
    
    @Redirect(method = { "handleTeams" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/scoreboard/Scoreboard;removeTeam(Lnet/minecraft/scoreboard/ScorePlayerTeam;)V"))
    private void getScoreboardHook(final Scoreboard scoreboard, final ScorePlayerTeam playerTeam) {
        if (scoreboard != null && playerTeam != null) {
            scoreboard.removeTeam(playerTeam);
        }
    }
    
    @Inject(method = { "handleResourcePack" }, at = { @At("HEAD") }, cancellable = true)
    private void validateResourcePackHook(final SPacketResourcePackSend packetIn, final CallbackInfo ci) {
        if (packetIn.getURL() == null || packetIn.getHash() == null) {
            this.netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
            ci.cancel();
        }
    }
    
    @Inject(method = { "handlePlayerPosLook" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkManager;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 1, shift = At.Shift.BEFORE) })
    private void handlePlayerPosLookHook(final SPacketPlayerPosLook packetIn, final CallbackInfo ci) {
        Managers.ROTATION.setBlocking(true);
    }
    
    @Inject(method = { "handlePlayerPosLook" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkManager;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 1, shift = At.Shift.AFTER) })
    private void handlePlayerPosLookHookPost(final SPacketPlayerPosLook packetIn, final CallbackInfo ci) {
        Managers.ROTATION.setBlocking(false);
    }
    
    @Redirect(method = { "handleHeldItemChange" }, at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/InventoryPlayer;currentItem:I"))
    private void handleHeldItemChangeHook(final InventoryPlayer inventoryPlayer, int value) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> inventoryPlayer.currentItem = value);
    }
    
    static {
        PACKETS = Caches.getModule(Packets.class);
    }
}
