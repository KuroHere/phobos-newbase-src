//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.arrows;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.potion.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.math.position.*;
import net.minecraft.util.math.*;

final class ListenerMotion extends ModuleListener<Arrows, MotionUpdateEvent>
{
    private PotionType lastType;
    private long lastDown;
    
    public ListenerMotion(final Arrows module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        final EnumHand hand = InventoryUtil.getHand((Item)Items.BOW);
        ItemStack arrow;
        if (!((Arrows)this.module).shoot.getValue() || ListenerMotion.mc.player.isCreative() || ListenerMotion.mc.currentScreen != null || hand == null || (arrow = ((Arrows)this.module).findArrow()).func_190926_b() || this.blocked()) {
            return;
        }
        final boolean cycle = ((Arrows)this.module).cycle.getValue();
        if (((Arrows)this.module).badStack(arrow) || (((Arrows)this.module).fast && cycle)) {
            if (!cycle) {
                return;
            }
            ((Arrows)this.module).cycle(false, true);
            ((Arrows)this.module).fast = false;
            arrow = ((Arrows)this.module).findArrow();
            if (((Arrows)this.module).badStack(arrow)) {
                return;
            }
        }
        if (event.getStage() == Stage.PRE) {
            if (ListenerMotion.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                this.lastDown = System.currentTimeMillis();
            }
            else if (System.currentTimeMillis() - this.lastDown > 100L) {
                return;
            }
            final EntityPlayer player = RotationUtil.getRotationPlayer();
            if (player.motionX != 0.0 || player.motionZ != 0.0) {
                final Vec3d vec3d = player.getPositionVector().addVector(player.motionX, player.motionY + player.getEyeHeight(), player.motionZ);
                final float[] rotations = RotationUtil.getRotations(vec3d);
                event.setYaw(rotations[0]);
                event.setPitch(rotations[1]);
            }
            else {
                event.setPitch(-90.0f);
            }
        }
        else if (((Arrows)this.module).autoRelease.getValue() && !ListenerMotion.mc.player.getActiveItemStack().func_190926_b()) {
            PotionType type = PotionUtils.getPotionFromItem(arrow);
            if (arrow.getItem() instanceof ItemSpectralArrow) {
                type = Arrows.SPECTRAL;
            }
            if (this.lastType == type && !((Arrows)this.module).timer.passed(((Arrows)this.module).shootDelay.getValue())) {
                return;
            }
            this.lastType = type;
            final float ticks = ListenerMotion.mc.player.getHeldItem(hand).getMaxItemUseDuration() - ListenerMotion.mc.player.getItemInUseCount() - (((Arrows)this.module).tpsSync.getValue() ? (20.0f - Managers.TPS.getTps()) : 0.0f);
            if (ticks >= ((Arrows)this.module).releaseTicks.getValue() && ticks <= ((Arrows)this.module).maxTicks.getValue()) {
                Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> ListenerMotion.mc.playerController.onStoppedUsingItem((EntityPlayer)ListenerMotion.mc.player));
                ((Arrows)this.module).fast = (((Arrows)this.module).preCycle.getValue() && cycle);
                ((Arrows)this.module).timer.reset();
            }
        }
    }
    
    private boolean blocked() {
        final BlockPos pos = PositionUtil.getPosition();
        return ListenerMotion.mc.world.getBlockState(pos.up()).getMaterial().blocksMovement() || ListenerMotion.mc.world.getBlockState(pos.up(2)).getMaterial().blocksMovement();
    }
}
