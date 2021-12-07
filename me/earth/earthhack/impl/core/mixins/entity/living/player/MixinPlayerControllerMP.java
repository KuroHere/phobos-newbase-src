//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.entity.living.player;

import me.earth.earthhack.impl.modules.misc.tpssync.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;
import me.earth.earthhack.api.event.bus.instance.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.block.state.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ PlayerControllerMP.class })
public abstract class MixinPlayerControllerMP implements IPlayerControllerMP
{
    private static final ModuleCache<TpsSync> TPS_SYNC;
    private static final SettingCache<Boolean, BooleanSetting, TpsSync> MINE;
    @Shadow
    private float curBlockDamageMP;
    @Shadow
    private int blockHitDelay;
    
    @Shadow
    protected abstract void syncCurrentPlayItem();
    
    @Invoker("syncCurrentPlayItem")
    @Override
    public abstract void syncItem();
    
    @Accessor("currentPlayerItem")
    @Override
    public abstract int getItem();
    
    @Accessor("blockHitDelay")
    @Override
    public abstract void setBlockHitDelay(final int p0);
    
    @Accessor("blockHitDelay")
    @Override
    public abstract int getBlockHitDelay();
    
    @Accessor("curBlockDamageMP")
    @Override
    public abstract float getCurBlockDamageMP();
    
    @Accessor("curBlockDamageMP")
    @Override
    public abstract void setCurBlockDamageMP(final float p0);
    
    @Accessor("isHittingBlock")
    @Override
    public abstract boolean getIsHittingBlock();
    
    @Accessor("isHittingBlock")
    @Override
    public abstract void setIsHittingBlock(final boolean p0);
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void clickBlockHook(final BlockPos pos, final EnumFacing facing, final CallbackInfoReturnable<Boolean> info) {
        final ClickBlockEvent event = new ClickBlockEvent(pos, facing);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "resetBlockRemoving" }, at = { @At("HEAD") }, cancellable = true)
    public void resetBlockRemovingHook(final CallbackInfo info) {
        final ResetBlockEvent event = new ResetBlockEvent();
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "processRightClick" }, at = { @At("HEAD") }, cancellable = true)
    private void processClickHook(final EntityPlayer player, final World worldIn, final EnumHand hand, final CallbackInfoReturnable<EnumActionResult> cir) {
        final RightClickItemEvent event = new RightClickItemEvent(player, worldIn, hand);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            cir.setReturnValue(EnumActionResult.PASS);
        }
    }
    
    @Inject(method = { "processRightClickBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void clickBlockHook(final EntityPlayerSP player, final WorldClient worldIn, final BlockPos pos, final EnumFacing direction, final Vec3d vec, final EnumHand hand, final CallbackInfoReturnable<EnumActionResult> info) {
        final ClickBlockEvent.Right event = new ClickBlockEvent.Right(pos, direction, vec, hand);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    private void onPlayerDamageBlock(final BlockPos pos, final EnumFacing facing, final CallbackInfoReturnable<Boolean> info) {
        final DamageBlockEvent event = new DamageBlockEvent(pos, facing, this.curBlockDamageMP, this.blockHitDelay);
        Bus.EVENT_BUS.post(event);
        this.curBlockDamageMP = event.getDamage();
        this.blockHitDelay = event.getDelay();
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "onPlayerDamageBlock" }, at = @At(value = "NEW", target = "net/minecraft/network/play/client/CPacketPlayerDigging"))
    private CPacketPlayerDigging cPacketPlayerDiggingInitHook(final CPacketPlayerDigging.Action actionIn, final BlockPos posIn, final EnumFacing facingIn) {
        final CPacketPlayerDigging packet = new CPacketPlayerDigging(actionIn, posIn, facingIn);
        if (actionIn == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
            ((ICPacketPlayerDigging)packet).setClientSideBreaking(true);
        }
        return packet;
    }
    
    @Redirect(method = { "onPlayerDamageBlock" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"))
    public float getPlayerRelativeBlockHardnessHook(final IBlockState state, final EntityPlayer player, final World worldIn, final BlockPos pos) {
        return state.getPlayerRelativeBlockHardness(player, worldIn, pos) * ((MixinPlayerControllerMP.TPS_SYNC.isEnabled() && MixinPlayerControllerMP.MINE.getValue()) ? (1.0f / Managers.TPS.getFactor()) : 1.0f);
    }
    
    @Redirect(method = { "updateController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;syncCurrentPlayItem()V"))
    private void syncCurrentPlayItemHook(final PlayerControllerMP playerControllerMP) {
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, this::syncCurrentPlayItem);
    }
    
    static {
        TPS_SYNC = Caches.getModule(TpsSync.class);
        MINE = Caches.getSetting(TpsSync.class, BooleanSetting.class, "Mine", false);
    }
}
