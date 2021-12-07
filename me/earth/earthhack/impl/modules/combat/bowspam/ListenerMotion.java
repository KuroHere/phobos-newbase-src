//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.bowspam;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import net.minecraft.item.*;

final class ListenerMotion extends ModuleListener<BowSpam, MotionUpdateEvent>
{
    private float lastTimer;
    
    public ListenerMotion(final BowSpam module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
        this.lastTimer = -1.0f;
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.POST) {
            final ItemStack stack = this.getStack();
            if (((BowSpam)this.module).rape.getValue()) {
                if (ListenerMotion.mc.player.onGround) {
                    if (stack != null && !ListenerMotion.mc.player.getActiveItemStack().func_190926_b() && ListenerMotion.mc.player.getItemInUseCount() > 0) {
                        Managers.TIMER.setTimer(6.0f);
                        if (stack.getMaxItemUseDuration() - ListenerMotion.mc.player.getItemInUseCount() > ((BowSpam)this.module).delay.getValue() * 6) {
                            Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> ListenerMotion.mc.playerController.onStoppedUsingItem((EntityPlayer)ListenerMotion.mc.player));
                        }
                    }
                    else {
                        if (this.lastTimer > 0.0f && Managers.TIMER.getSpeed() != this.lastTimer) {
                            Managers.TIMER.setTimer(this.lastTimer);
                        }
                        this.lastTimer = Managers.TIMER.getSpeed();
                    }
                }
            }
            else {
                if (this.lastTimer > 0.0f && Managers.TIMER.getSpeed() != this.lastTimer) {
                    Managers.TIMER.setTimer(this.lastTimer);
                    this.lastTimer = 1.0f;
                }
                if (stack != null && !ListenerMotion.mc.player.getActiveItemStack().func_190926_b() && stack.getMaxItemUseDuration() - ListenerMotion.mc.player.getItemInUseCount() - (((BowSpam)this.module).tpsSync.getValue() ? (20.0f - Managers.TPS.getTps()) : 0.0f) >= ((BowSpam)this.module).delay.getValue()) {
                    if (((BowSpam)this.module).bowBomb.getValue()) {
                        NetworkUtil.sendPacketNoEvent((Packet<?>)new CPacketPlayer.PositionRotation(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY - 0.0624, ListenerMotion.mc.player.posZ, ListenerMotion.mc.player.rotationYaw, ListenerMotion.mc.player.rotationPitch, false));
                        NetworkUtil.sendPacketNoEvent((Packet<?>)new CPacketPlayer.PositionRotation(ListenerMotion.mc.player.posX, ListenerMotion.mc.player.posY - 999.0, ListenerMotion.mc.player.posZ, ListenerMotion.mc.player.rotationYaw, ListenerMotion.mc.player.rotationPitch, true));
                    }
                    Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> ListenerMotion.mc.playerController.onStoppedUsingItem((EntityPlayer)ListenerMotion.mc.player));
                }
            }
        }
    }
    
    private ItemStack getStack() {
        final ItemStack mainHand = ListenerMotion.mc.player.getHeldItemMainhand();
        if (mainHand.getItem() instanceof ItemBow) {
            return mainHand;
        }
        final ItemStack offHand = ListenerMotion.mc.player.getHeldItemOffhand();
        if (offHand.getItem() instanceof ItemBow) {
            return offHand;
        }
        return null;
    }
}
