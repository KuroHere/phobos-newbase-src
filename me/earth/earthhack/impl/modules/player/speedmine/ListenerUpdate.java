//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.impl.modules.misc.nuker.*;
import me.earth.earthhack.impl.modules.combat.autocrystal.*;
import me.earth.earthhack.impl.modules.combat.anvilaura.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.entity.*;
import java.util.*;
import me.earth.earthhack.impl.core.ducks.network.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.minecraft.blocks.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.blocks.mine.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.init.*;
import me.earth.earthhack.api.util.bind.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerUpdate extends ModuleListener<Speedmine, UpdateEvent>
{
    private static final ModuleCache<Nuker> NUKER;
    private static final ModuleCache<AutoCrystal> AUTOCRYSTAL;
    private static final ModuleCache<AnvilAura> ANVIL_AURA;
    private static final SettingCache<Boolean, BooleanSetting, Nuker> NUKE;
    
    public ListenerUpdate(final Speedmine module) {
        super(module, (Class<? super Object>)UpdateEvent.class, -10);
    }
    
    private EntityPlayer getPlacePlayer(final BlockPos pos) {
        for (final EntityPlayer player : ListenerUpdate.mc.world.playerEntities) {
            if (!Managers.FRIENDS.contains(player)) {
                if (player == ListenerUpdate.mc.player) {
                    continue;
                }
                final BlockPos playerPos = PositionUtil.getPosition((Entity)player);
                for (final EnumFacing facing : EnumFacing.HORIZONTALS) {
                    if (playerPos.offset(facing).equals((Object)pos)) {
                        return player;
                    }
                }
                if (playerPos.offset(EnumFacing.UP).offset(EnumFacing.UP).equals((Object)pos)) {
                    return player;
                }
                continue;
            }
        }
        return null;
    }
    
    public void invoke(final UpdateEvent event) {
        ((Speedmine)this.module).checkReset();
        if (PlayerUtil.isCreative((EntityPlayer)ListenerUpdate.mc.player) || (ListenerUpdate.NUKER.isEnabled() && ListenerUpdate.NUKE.getValue()) || (ListenerUpdate.ANVIL_AURA.isEnabled() && ListenerUpdate.ANVIL_AURA.get().isMining())) {
            return;
        }
        ((IPlayerControllerMP)ListenerUpdate.mc.playerController).setBlockHitDelay(0);
        if (!((Speedmine)this.module).multiTask.getValue() && (((Speedmine)this.module).noReset.getValue() || ((Speedmine)this.module).mode.getValue() == MineMode.Reset) && ListenerUpdate.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            ((IPlayerControllerMP)ListenerUpdate.mc.playerController).setIsHittingBlock(false);
        }
        if (((Speedmine)this.module).pos != null) {
            if ((((Speedmine)this.module).mode.getValue() == MineMode.Smart || ((Speedmine)this.module).mode.getValue() == MineMode.Instant || ((Speedmine)this.module).mode.getValue() == MineMode.Civ) && ListenerUpdate.mc.player.getDistanceSq(((Speedmine)this.module).pos) > MathUtil.square(((Speedmine)this.module).range.getValue())) {
                ((Speedmine)this.module).abortCurrentPos();
                return;
            }
            if (((Speedmine)this.module).mode.getValue() == MineMode.Civ && ((Speedmine)this.module).facing != null && !BlockUtil.isAir(((Speedmine)this.module).pos) && !((Speedmine)this.module).isPausing() && ((Speedmine)this.module).delayTimer.passed(((Speedmine)this.module).realDelay.getValue())) {
                ArmUtil.swingPacket(EnumHand.MAIN_HAND);
                ((Speedmine)this.module).sendStopDestroy(((Speedmine)this.module).pos, ((Speedmine)this.module).facing, false);
            }
            ((Speedmine)this.module).maxDamage = 0.0f;
            for (int i = 0; i < 9; ++i) {
                final ItemStack stack = ListenerUpdate.mc.player.inventory.getStackInSlot(i);
                ((Speedmine)this.module).damages[i] = MathUtil.clamp(((Speedmine)this.module).damages[i] + MineUtil.getDamage(stack, ((Speedmine)this.module).pos, ((Speedmine)this.module).onGround.getValue()), 0.0f, Float.MAX_VALUE);
                if (((Speedmine)this.module).damages[i] > ((Speedmine)this.module).maxDamage) {
                    ((Speedmine)this.module).maxDamage = ((Speedmine)this.module).damages[i];
                }
            }
            if (((Speedmine)this.module).normal.getValue()) {
                int fastSlot = -1;
                for (int j = 0; j < ((Speedmine)this.module).damages.length && (((Speedmine)this.module).damages[j] < ((Speedmine)this.module).limit.getValue() || (fastSlot = j) != ListenerUpdate.mc.player.inventory.currentItem); ++j) {}
                if ((((Speedmine)this.module).damages[ListenerUpdate.mc.player.inventory.currentItem] >= ((Speedmine)this.module).limit.getValue() || (((Speedmine)this.module).swap.getValue() && fastSlot != -1)) && (!((Speedmine)this.module).checkPacket.getValue() || !((Speedmine)this.module).sentPacket)) {
                    int lastSlot = -1;
                    if (((Speedmine)this.module).swap.getValue()) {
                        lastSlot = ListenerUpdate.mc.player.inventory.currentItem;
                        InventoryUtil.switchTo(fastSlot);
                    }
                    final boolean toAir = ((Speedmine)this.module).toAir.getValue();
                    InventoryUtil.syncItem();
                    if (((Speedmine)this.module).sendStopDestroy(((Speedmine)this.module).pos, ((Speedmine)this.module).facing, toAir)) {
                        ((Speedmine)this.module).postSend(toAir);
                    }
                    if (lastSlot != -1) {
                        InventoryUtil.switchTo(lastSlot);
                    }
                }
                return;
            }
            final int pickSlot = InventoryUtil.findHotbarItem(Items.DIAMOND_PICKAXE, new Item[0]);
            if (((Speedmine)this.module).damages[ListenerUpdate.mc.player.inventory.currentItem] >= ((Speedmine)this.module).limit.getValue() || (pickSlot >= 0 && ((Speedmine)this.module).damages[pickSlot] >= ((Speedmine)this.module).limit.getValue() && !((Speedmine)this.module).pausing && ((Speedmine)this.module).breakBind.getValue().getKey() == -1)) {
                final int lastSlot = ListenerUpdate.mc.player.inventory.currentItem;
                final EntityPlayer placeTarg = this.getPlacePlayer(((Speedmine)this.module).pos);
                if (placeTarg != null) {
                    final BlockPos p = PlayerUtil.getBestPlace(((Speedmine)this.module).pos, placeTarg);
                    if (((Speedmine)this.module).placeCrystal.getValue() && ListenerUpdate.AUTOCRYSTAL.isEnabled() && p != null && BlockUtil.canPlaceCrystal(p, false, false)) {
                        final RayTraceResult result = new RayTraceResult(new Vec3d(0.5, 1.0, 0.5), EnumFacing.UP, p);
                        if (ListenerUpdate.mc.player.getHeldItemOffhand() != ItemStack.field_190927_a && ListenerUpdate.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                            final CPacketPlayerTryUseItemOnBlock place = new CPacketPlayerTryUseItemOnBlock(p, result.sideHit, EnumHand.OFF_HAND, (float)result.hitVec.xCoord, (float)result.hitVec.yCoord, (float)result.hitVec.zCoord);
                            final CPacketAnimation animation = new CPacketAnimation(EnumHand.OFF_HAND);
                            InventoryUtil.syncItem();
                            ListenerUpdate.mc.player.connection.sendPacket((Packet)place);
                            ListenerUpdate.mc.player.connection.sendPacket((Packet)animation);
                        }
                        else {
                            final int crystalSlot = InventoryUtil.findHotbarItem(Items.END_CRYSTAL, new Item[0]);
                            if (crystalSlot != -1) {
                                InventoryUtil.switchTo(crystalSlot);
                                final CPacketPlayerTryUseItemOnBlock place2 = new CPacketPlayerTryUseItemOnBlock(p, result.sideHit, EnumHand.MAIN_HAND, (float)result.hitVec.xCoord, (float)result.hitVec.yCoord, (float)result.hitVec.zCoord);
                                final CPacketAnimation animation2 = new CPacketAnimation(EnumHand.MAIN_HAND);
                                ListenerUpdate.mc.player.connection.sendPacket((Packet)place2);
                                ListenerUpdate.mc.player.connection.sendPacket((Packet)animation2);
                                InventoryUtil.switchTo(lastSlot);
                            }
                        }
                    }
                }
                if (((Speedmine)this.module).swap.getValue()) {
                    InventoryUtil.switchTo(pickSlot);
                }
                NetworkUtil.sendPacketNoEvent((Packet<?>)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, ((Speedmine)this.module).pos, ((Speedmine)this.module).facing), false);
                if (((Speedmine)this.module).swap.getValue()) {
                    InventoryUtil.switchTo(lastSlot);
                }
                if (((Speedmine)this.module).toAir.getValue()) {
                    ListenerUpdate.mc.playerController.onPlayerDestroyBlock(((Speedmine)this.module).pos);
                }
                ((Speedmine)this.module).onSendPacket();
            }
        }
    }
    
    static {
        NUKER = Caches.getModule(Nuker.class);
        AUTOCRYSTAL = Caches.getModule(AutoCrystal.class);
        ANVIL_AURA = Caches.getModule(AnvilAura.class);
        NUKE = Caches.getSetting(Nuker.class, BooleanSetting.class, "Nuke", false);
    }
}
