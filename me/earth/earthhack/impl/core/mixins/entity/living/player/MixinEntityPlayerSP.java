//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living.player;

import me.earth.earthhack.impl.core.ducks.entity.*;
import net.minecraft.client.entity.*;
import me.earth.earthhack.impl.modules.player.spectate.*;
import me.earth.earthhack.impl.modules.movement.elytraflight.*;
import me.earth.earthhack.impl.modules.movement.autosprint.*;
import me.earth.earthhack.impl.modules.player.xcarry.*;
import me.earth.earthhack.impl.modules.misc.portals.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import me.earth.earthhack.impl.event.events.network.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.inventory.*;
import me.earth.earthhack.impl.modules.movement.elytraflight.mode.*;
import me.earth.earthhack.impl.modules.movement.autosprint.mode.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.event.events.movement.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ EntityPlayerSP.class })
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer implements IEntityPlayerSP
{
    private static final ModuleCache<Spectate> SPECTATE;
    private static final ModuleCache<ElytraFlight> ELYTRA_FLIGHT;
    private static final ModuleCache<AutoSprint> SPRINT;
    private static final ModuleCache<XCarry> XCARRY;
    private static final ModuleCache<Portals> PORTALS;
    private static final SettingCache<Boolean, BooleanSetting, Portals> CHAT;
    @Shadow
    public MovementInput movementInput;
    @Shadow
    @Final
    public NetHandlerPlayClient connection;
    private final Minecraft mc;
    private MotionUpdateEvent.Riding riding;
    private MotionUpdateEvent motionEvent;
    
    public MixinEntityPlayerSP() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @Accessor("lastReportedPosX")
    @Override
    public abstract double getLastReportedX();
    
    @Accessor("lastReportedPosY")
    @Override
    public abstract double getLastReportedY();
    
    @Accessor("lastReportedPosZ")
    @Override
    public abstract double getLastReportedZ();
    
    @Accessor("lastReportedYaw")
    @Override
    public abstract float getLastReportedYaw();
    
    @Accessor("lastReportedPitch")
    @Override
    public abstract float getLastReportedPitch();
    
    @Accessor("prevOnGround")
    @Override
    public abstract boolean getLastOnGround();
    
    @Accessor("lastReportedYaw")
    @Override
    public abstract void setLastReportedYaw(final float p0);
    
    @Accessor("lastReportedPitch")
    @Override
    public abstract void setLastReportedPitch(final float p0);
    
    @Accessor("positionUpdateTicks")
    @Override
    public abstract int getPositionUpdateTicks();
    
    @Accessor("horseJumpPower")
    @Override
    public abstract void setHorseJumpPower(final float p0);
    
    @Override
    public void superUpdate() {
        super.onUpdate();
    }
    
    @Override
    public void invokeUpdateWalkingPlayer() {
        this.onUpdateWalkingPlayer();
    }
    
    @Override
    public boolean isNoInterping() {
        return false;
    }
    
    @Shadow
    protected abstract void onUpdateWalkingPlayer();
    
    @Redirect(method = { "closeScreenAndDropStack" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;setItemStack(Lnet/minecraft/item/ItemStack;)V"))
    private void setItemStackHook(final InventoryPlayer inventory, final ItemStack stack) {
        if (!MixinEntityPlayerSP.XCARRY.isEnabled() || !(this.mc.currentScreen instanceof GuiInventory)) {
            inventory.setItemStack(stack);
        }
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "net/minecraft/client/entity/EntityPlayerSP.isElytraFlying()Z"))
    private boolean onLivingUpdateHook(final EntityPlayerSP player) {
        return (MixinEntityPlayerSP.ELYTRA_FLIGHT.isEnabled() && MixinEntityPlayerSP.ELYTRA_FLIGHT.get().getMode() == ElytraMode.Packet) || player.isElytraFlying();
    }
    
    @ModifyArg(method = { "setSprinting" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;setSprinting(Z)V"))
    private boolean setSprintingHook(final boolean sprinting) {
        return (MixinEntityPlayerSP.SPRINT.isEnabled() && AutoSprint.canSprintBetter() && MixinEntityPlayerSP.SPRINT.get().getMode() == SprintMode.Rage && MovementUtil.isMoving()) || sprinting;
    }
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "NEW", target = "net/minecraft/network/play/client/CPacketPlayer$Rotation", shift = At.Shift.BEFORE) }, cancellable = true)
    private void ridingHook_1(final CallbackInfo info) {
        this.riding = new MotionUpdateEvent.Riding(Stage.PRE, this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround, this.moveStrafing, this.field_191988_bg, this.movementInput.jump, this.movementInput.sneak);
        Bus.EVENT_BUS.post(this.riding);
        if (this.riding.isCancelled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "onUpdate" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;rotationYaw:F"))
    private float ridingHook_2(final EntityPlayerSP player) {
        return this.riding.getYaw();
    }
    
    @Redirect(method = { "onUpdate" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;rotationPitch:F"))
    private float ridingHook_3(final EntityPlayerSP player) {
        return this.riding.getPitch();
    }
    
    @Redirect(method = { "onUpdate" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;onGround:Z"))
    private boolean ridingHook_4(final EntityPlayerSP player) {
        return this.riding.isOnGround();
    }
    
    @Redirect(method = { "onUpdate" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;moveStrafing:F"))
    private float ridingHook_5(final EntityPlayerSP player) {
        return this.riding.getMoveStrafing();
    }
    
    @Redirect(method = { "onUpdate" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;moveForward:F"))
    private float ridingHook_6(final EntityPlayerSP player) {
        return this.riding.getMoveForward();
    }
    
    @Redirect(method = { "onUpdate" }, at = @At(value = "FIELD", target = "Lnet/minecraft/util/MovementInput;jump:Z"))
    private boolean ridingHook_7(final MovementInput input) {
        return this.riding.isOnGround();
    }
    
    @Redirect(method = { "onUpdate" }, at = @At(value = "FIELD", target = "Lnet/minecraft/util/MovementInput;sneak:Z"))
    private boolean ridingHook_8(final MovementInput input) {
        return this.riding.getSneak();
    }
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetHandlerPlayClient;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 2, shift = At.Shift.BY, by = 2) })
    private void ridingHook_9(final CallbackInfo info) {
        Bus.EVENT_BUS.post(new MotionUpdateEvent.Riding(Stage.POST, this.riding));
    }
    
    @Inject(method = { "onUpdate" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;onUpdate()V", shift = At.Shift.BEFORE) })
    private void onUpdateHook(final CallbackInfo info) {
        Bus.EVENT_BUS.post(new UpdateEvent());
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") }, cancellable = true)
    private void onUpdateWalkingPlayer_Head(final CallbackInfo callbackInfo) {
        this.motionEvent = new MotionUpdateEvent(Stage.PRE, this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround);
        Bus.EVENT_BUS.post(this.motionEvent);
        if (this.motionEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "net/minecraft/client/entity/EntityPlayerSP.posX:D"))
    private double posXHook(final EntityPlayerSP entityPlayerSP) {
        return this.motionEvent.getX();
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "net/minecraft/util/math/AxisAlignedBB.minY:D"))
    private double minYHook(final AxisAlignedBB axisAlignedBB) {
        return this.motionEvent.getY();
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "net/minecraft/client/entity/EntityPlayerSP.posZ:D"))
    private double posZHook(final EntityPlayerSP entityPlayerSP) {
        return this.motionEvent.getZ();
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F"))
    private float rotationYawHook(final EntityPlayerSP entityPlayerSP) {
        return this.motionEvent.getYaw();
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F"))
    private float rotationPitchHook(final EntityPlayerSP entityPlayerSP) {
        return this.motionEvent.getPitch();
    }
    
    @Redirect(method = { "onUpdateWalkingPlayer" }, at = @At(value = "FIELD", target = "net/minecraft/client/entity/EntityPlayerSP.onGround:Z"))
    private boolean onGroundHook(final EntityPlayerSP entityPlayerSP) {
        return this.motionEvent.isOnGround();
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") })
    private void onUpdateWalkingPlayer_Return(final CallbackInfo callbackInfo) {
        final MotionUpdateEvent event = new MotionUpdateEvent(Stage.POST, this.motionEvent);
        event.setCancelled(this.motionEvent.isCancelled());
        Bus.EVENT_BUS.postReversed(event, null);
    }
    
    @Inject(method = { "pushOutOfBlocks" }, at = { @At("HEAD") }, cancellable = true)
    private void pushOutOfBlocksHook(final CallbackInfoReturnable<Boolean> info) {
        final BlockPushEvent event = new BlockPushEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;doesGuiPauseGame()Z", ordinal = 0))
    public boolean doesGuiPauseGameHook(final GuiScreen guiScreen) {
        return (MixinEntityPlayerSP.PORTALS.isEnabled() && MixinEntityPlayerSP.CHAT.getValue()) || guiScreen.doesGuiPauseGame();
    }
    
    @Inject(method = { "isCurrentViewEntity" }, at = { @At("HEAD") }, cancellable = true)
    private void isCurrentViewEntityHook(final CallbackInfoReturnable<Boolean> cir) {
        if (!this.isSpectator() && MixinEntityPlayerSP.SPECTATE.isEnabled()) {
            cir.setReturnValue(true);
        }
    }
    
    static {
        SPECTATE = Caches.getModule(Spectate.class);
        ELYTRA_FLIGHT = Caches.getModule(ElytraFlight.class);
        SPRINT = Caches.getModule(AutoSprint.class);
        XCARRY = Caches.getModule(XCarry.class);
        PORTALS = Caches.getModule(Portals.class);
        CHAT = Caches.getSetting(Portals.class, BooleanSetting.class, "Chat", true);
    }
}
