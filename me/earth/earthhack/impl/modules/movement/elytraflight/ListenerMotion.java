//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.elytraflight;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.network.*;
import java.util.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.modules.movement.elytraflight.mode.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.core.ducks.util.*;
import net.minecraft.item.*;

final class ListenerMotion extends ModuleListener<ElytraFlight, MotionUpdateEvent>
{
    private static final Random RANDOM;
    private static float previousTimerVal;
    
    public ListenerMotion(final ElytraFlight module) {
        super(module, (Class<? super Object>)MotionUpdateEvent.class);
    }
    
    public void invoke(final MotionUpdateEvent event) {
        if (event.getStage() != Stage.PRE) {
            return;
        }
        final ItemStack stack = ListenerMotion.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (stack.getItem() != Items.ELYTRA || !ItemElytra.isBroken(stack)) {
            return;
        }
        if (ListenerMotion.mc.player.isElytraFlying() && ((((ElytraFlight)this.module).noWater.getValue() && ListenerMotion.mc.player.isInWater()) || (((ElytraFlight)this.module).noGround.getValue() && ListenerMotion.mc.player.onGround))) {
            ((ElytraFlight)this.module).sendFallPacket();
            return;
        }
        if (((ElytraFlight)this.module).mode.getValue() == ElytraMode.Packet) {
            boolean falling = false;
            if (((ElytraFlight)this.module).infDura.getValue() || !ListenerMotion.mc.player.isElytraFlying()) {
                ((ElytraFlight)this.module).sendFallPacket();
                falling = true;
            }
            if (((ElytraFlight)this.module).ncp.getValue() && !((ElytraFlight)this.module).lag && (Math.abs(event.getX()) >= 0.05 || Math.abs(event.getZ()) >= 0.05)) {
                final double y = 1.0E-8 + 1.0E-8 * (1.0 + ListenerMotion.RANDOM.nextInt(1 + (ListenerMotion.RANDOM.nextBoolean() ? ListenerMotion.RANDOM.nextInt(34) : ListenerMotion.RANDOM.nextInt(43))));
                if (ListenerMotion.mc.player.onGround || ListenerMotion.mc.player.ticksExisted % 2 == 0) {
                    event.setY(event.getY() + y);
                    return;
                }
                event.setY(event.getY() - y);
                return;
            }
            else if (falling) {
                return;
            }
        }
        if (((ElytraFlight)this.module).autoStart.getValue() && ListenerMotion.mc.gameSettings.keyBindJump.isKeyDown() && !ListenerMotion.mc.player.isElytraFlying() && ListenerMotion.mc.player.motionY < 0.0) {
            if (ListenerMotion.previousTimerVal == -1.0f) {
                ListenerMotion.previousTimerVal = Managers.TIMER.getSpeed();
            }
            Managers.TIMER.setTimer(0.17f);
            if (((ElytraFlight)this.module).timer.passed(10L)) {
                ((IKeyBinding)ListenerMotion.mc.gameSettings.keyBindJump).setPressed(true);
                ((ElytraFlight)this.module).sendFallPacket();
                ((ElytraFlight)this.module).timer.reset();
            }
            else {
                ((IKeyBinding)ListenerMotion.mc.gameSettings.keyBindJump).setPressed(false);
            }
            return;
        }
        if (ListenerMotion.previousTimerVal != -1.0f) {
            Managers.TIMER.setTimer(ListenerMotion.previousTimerVal);
            ListenerMotion.previousTimerVal = -1.0f;
        }
        if (((ElytraFlight)this.module).infDura.getValue() && ListenerMotion.mc.player.isElytraFlying()) {
            ((ElytraFlight)this.module).sendFallPacket();
        }
    }
    
    static {
        RANDOM = new Random();
        ListenerMotion.previousTimerVal = -1.0f;
    }
}
