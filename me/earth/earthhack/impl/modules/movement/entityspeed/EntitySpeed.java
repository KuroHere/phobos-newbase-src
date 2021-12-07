//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.entityspeed;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.util.*;

public class EntitySpeed extends Module
{
    protected final Setting<Float> speed;
    protected final Setting<Boolean> noStuck;
    protected final Setting<Boolean> resetStuck;
    protected final Setting<Integer> stuckTime;
    protected final StopWatch stuckTimer;
    protected final StopWatch jumpTimer;
    protected List<BlockPos> positions;
    
    public EntitySpeed() {
        super("EntitySpeed", Category.Movement);
        this.speed = this.register(new NumberSetting("Speed", 3.8f, 0.1f, 10.0f));
        this.noStuck = this.register(new BooleanSetting("NoStuck", false));
        this.resetStuck = this.register(new BooleanSetting("Reset-Stuck", false));
        this.stuckTime = this.register(new NumberSetting("Stuck-Time", 10000, 0, 10000));
        this.stuckTimer = new StopWatch();
        this.jumpTimer = new StopWatch();
        this.positions = new ArrayList<BlockPos>();
        this.listeners.add(new ListenerTick(this));
        this.setData(new EntitySpeedData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.speed.getValue() + "";
    }
    
    public static void strafe(final double speed) {
        final MovementInput input = EntitySpeed.mc.player.movementInput;
        double forward = input.field_192832_b;
        double strafe = input.moveStrafe;
        float yaw = EntitySpeed.mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            EntitySpeed.mc.player.getRidingEntity().motionX = 0.0;
            EntitySpeed.mc.player.getRidingEntity().motionZ = 0.0;
            return;
        }
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45.0f : 45.0f);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45.0f : -45.0f);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            }
            else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        EntitySpeed.mc.player.getRidingEntity().motionX = forward * speed * cos + strafe * speed * sin;
        EntitySpeed.mc.player.getRidingEntity().motionZ = forward * speed * sin - strafe * speed * cos;
    }
}
