//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.killaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.combat.killaura.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.thread.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.ai.attributes.*;
import me.earth.earthhack.impl.core.ducks.entity.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;

final class ListenerMotion extends ModuleListener<KillAura, MotionUpdateEvent>
{
    public ListenerMotion(final KillAura module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() == Stage.PRE) {
            pre((KillAura)this.module, event, true);
        }
        else {
            post((KillAura)this.module);
        }
    }
    
    public static void pre(final KillAura module, final MotionUpdateEvent event, final boolean teleports) {
        module.isAttacking = false;
        if (!module.shouldAttack()) {
            return;
        }
        if (!module.whileEating.getValue() && ListenerMotion.mc.player.getActiveItemStack().getItem() instanceof ItemFood) {
            return;
        }
        module.target = module.findTarget();
        if (module.target == null) {
            return;
        }
        module.slot = -1;
        if (module.autoSwitch.getValue() != AuraSwitch.None) {
            module.slot = findSlot();
        }
        final boolean passed = passedDelay(module, module.slot);
        float[] rotations = null;
        module.isTeleporting = false;
        if (module.rotate.getValue()) {
            rotations = module.rotationSmoother.getRotations((Entity)RotationUtil.getRotationPlayer(), module.target, module.height.getValue(), module.soft.getValue());
        }
        if (passed && !module.isTeleporting && module.isInRange(Managers.POSITION.getVec(), module.target) && (module.efficient.getValue() || !module.isInRange(ListenerMotion.mc.player.getPositionVector(), module.target)) && (!module.rotate.getValue() || (RotationUtil.isLegit(module.target, new Entity[0]) && (module.rotationTicks.getValue() <= 1 || module.rotationSmoother.getRotationTicks() >= module.rotationTicks.getValue())))) {
            module.eff = Managers.POSITION.getVec();
            attack(module, module.target, module.slot);
            module.eff = null;
            module.timer.reset((long)(1000.0 / module.cps.getValue()));
            if (rotations != null && module.stay.getValue()) {
                event.setYaw(rotations[0]);
                event.setPitch(rotations[1]);
            }
            return;
        }
        if (module.rotate.getValue() && !module.stay.getValue() && !passed) {
            return;
        }
        if (rotations != null) {
            event.setYaw(rotations[0]);
            event.setPitch(rotations[1]);
            if (module.rotationSmoother.isRotating()) {
                return;
            }
        }
        module.isAttacking = passed;
        if (module.isAttacking) {
            module.timer.reset((long)(1000.0 / module.cps.getValue()));
        }
        if (teleports && module.isAttacking && (module.movingTeleport.getValue() || MovementUtil.noMovementKeys())) {
            final boolean canSee = ListenerMotion.mc.player.canEntityBeSeen(module.target);
            final double tp = module.teleportRange.getValue();
            final double tpSq = MathUtil.square(tp);
            final double distSq = ListenerMotion.mc.player.getDistanceSqToEntity(module.target);
            final double dist = Math.sqrt(distSq);
            switch (module.auraTeleport.getValue()) {
                case Smart: {
                    if (((canSee || distSq < 9.0) && distSq < 36.0) || (dist - tp >= 3.0 && (!canSee || dist - tp >= 6.0))) {
                        break;
                    }
                    final Vec3d vec = module.target.getPositionVector();
                    final Vec3d own = ListenerMotion.mc.player.getPositionVector();
                    if (!module.yTeleport.getValue() && vec.yCoord != own.yCoord) {
                        final Vec3d noY = new Vec3d(vec.xCoord, own.yCoord, vec.zCoord);
                        final double cSq = (canSee ? 36.0 : 9.0) - 5.0E-4;
                        final double aSq = noY.squareDistanceTo(vec);
                        final double b = Math.sqrt(cSq - aSq);
                        final Vec3d dir = own.subtract(noY).normalize();
                        final Vec3d result = noY.add(dir.scale(b));
                        if (result.distanceTo(own) <= tp && module.isInRange(result, module.target)) {
                            teleport(module, result, event);
                        }
                        return;
                    }
                    final Vec3d dir2 = own.subtract(vec).normalize();
                    final Vec3d result2 = vec.add(dir2.scale((canSee ? 6.0 : 3.0) - 0.005));
                    teleport(module, result2, event);
                    break;
                }
                case Full: {
                    if (distSq <= tpSq) {
                        teleport(module, module.target.getPositionVector(), event);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public static void post(final KillAura module) {
        if (module.target == null || !module.isAttacking) {
            return;
        }
        attack(module, module.target, module.slot);
    }
    
    private static void teleport(final KillAura module, final Vec3d to, final MotionUpdateEvent event) {
        module.isTeleporting = true;
        module.pos = to;
        event.setX(to.xCoord);
        event.setY(to.yCoord);
        event.setZ(to.zCoord);
    }
    
    private static void attack(final KillAura module, final Entity entity, final int slot) {
        if (module.rotate.getValue() && !module.rotationSmoother.isRotating() && module.rotationSmoother.getRotationTicks() < module.rotationTicks.getValue()) {
            module.rotationSmoother.incrementRotationTicks();
            return;
        }
        if (!module.isInRange(Managers.POSITION.getVec(), module.target)) {
            return;
        }
        final boolean stopSneak = module.stopSneak.getValue() && Managers.ACTION.isSneaking();
        final boolean stopSprint = module.stopSprint.getValue() && ListenerMotion.mc.player.isSprinting();
        final boolean stopShield = module.stopShield.getValue() && ListenerMotion.mc.player.isActiveItemStackBlocking();
        if (stopSneak) {
            ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerMotion.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (stopSprint) {
            ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerMotion.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
        if (stopShield) {
            module.releaseShield();
        }
        Locks.acquire(Locks.PLACE_SWITCH_LOCK, () -> {
            final int last = ListenerMotion.mc.player.inventory.currentItem;
            if (slot != -1) {
                InventoryUtil.switchTo(slot);
            }
            module.ourCrit = true;
            ListenerMotion.mc.playerController.attackEntity((EntityPlayer)ListenerMotion.mc.player, entity);
            module.ourCrit = false;
            module.swing.getValue().swing(EnumHand.MAIN_HAND);
            if (module.autoSwitch.getValue() != AuraSwitch.Keep && slot != -1) {
                InventoryUtil.switchTo(last);
            }
            return;
        });
        if (stopSneak) {
            ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerMotion.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        if (stopSprint) {
            ListenerMotion.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ListenerMotion.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
        if (stopShield) {
            module.useShield();
        }
    }
    
    private static boolean passedDelay(final KillAura module, final int slot) {
        if (!module.delay.getValue() || (module.t2k.getValue() && DamageUtil.isSharper(ListenerMotion.mc.player.getHeldItemMainhand(), 1000))) {
            return module.cps.getValue() >= 20.0f || module.timer.passed((long)(1000.0 / module.cps.getValue()));
        }
        final float tps = module.tps.getValue() ? (20.0f - Managers.TPS.getTps()) : 0.0f;
        if (slot == -1) {
            return ListenerMotion.mc.player.getCooledAttackStrength(0.5f - tps) >= 1.0f;
        }
        final ItemStack stack = ListenerMotion.mc.player.inventory.getStackInSlot(slot);
        double value = ListenerMotion.mc.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue();
        final Multimap<String, AttributeModifier> map = (Multimap<String, AttributeModifier>)stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
        final Collection<AttributeModifier> modifiers = map.get((Object)SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName());
        if (modifiers != null) {
            for (final AttributeModifier modifier : modifiers) {
                value += modifier.getAmount();
            }
        }
        final float swing = ((IEntityLivingBase)ListenerMotion.mc.player).getTicksSinceLastSwing() + 0.5f - tps;
        final float cooldown = (float)(1.0 / value * 20.0);
        return MathHelper.clamp(swing / cooldown, 0.0f, 1.0f) >= 1.0f;
    }
    
    private static int findSlot() {
        int slot = -1;
        int bestSharp = -1;
        for (int i = 8; i > -1; --i) {
            final ItemStack stack = ListenerMotion.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemAxe) {
                final int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
                if (level > bestSharp) {
                    bestSharp = level;
                    slot = i;
                }
            }
        }
        return slot;
    }
}
