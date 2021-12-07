//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.aimbot;

import me.earth.earthhack.impl.util.minecraft.entity.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.math.*;
import java.util.*;

public class AimBot extends EntityTypeModule
{
    protected final Setting<Boolean> silent;
    protected final Setting<Boolean> fov;
    protected final Setting<Integer> extrapolate;
    protected final Setting<Double> maxRange;
    protected Entity target;
    protected float yaw;
    protected float pitch;
    
    public AimBot() {
        super("AimBot", Category.Combat);
        this.silent = this.register(new BooleanSetting("Silent", true));
        this.fov = this.register(new BooleanSetting("Fov", false));
        this.extrapolate = this.register(new NumberSetting("Extrapolate", 0, 0, 10));
        this.maxRange = this.register(new NumberSetting("MaxRange", 100.0, 0.0, 500.0));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerGameLoop(this));
        this.unregister(this.vehicles);
        this.unregister(this.misc);
    }
    
    @Override
    public String getDisplayInfo() {
        return (this.target != null) ? this.target.getName() : null;
    }
    
    public Entity getTarget() {
        final List<Entity> entites = new LinkedList<Entity>();
        Entity closest = null;
        double closestAngle = 360.0;
        final double x = RotationUtil.getRotationPlayer().posX;
        final double y = RotationUtil.getRotationPlayer().posY;
        final double z = RotationUtil.getRotationPlayer().posZ;
        final float h = AimBot.mc.player.getEyeHeight();
        for (final Entity entity : AimBot.mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase && !entity.equals((Object)AimBot.mc.player) && !entity.equals((Object)RotationUtil.getRotationPlayer()) && EntityUtil.isValid(entity, this.maxRange.getValue()) && this.isValid(entity)) {
                if (!RayTraceUtil.canBeSeen(new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), x, y, z, h) && !RayTraceUtil.canBeSeen(new Vec3d(entity.posX, entity.posY + entity.getEyeHeight() / 2.0, entity.posZ), x, y, z, h) && !RayTraceUtil.canBeSeen(new Vec3d(entity.posX, entity.posY, entity.posZ), x, y, z, h)) {
                    continue;
                }
                final double angle = RotationUtil.getAngle(entity, 1.4);
                if (this.fov.getValue() && angle > AimBot.mc.gameSettings.fovSetting / 2.0f) {
                    continue;
                }
                if (angle >= closestAngle || (this.fov.getValue() && angle >= AimBot.mc.gameSettings.fovSetting / 2.0f)) {
                    continue;
                }
                closest = entity;
                closestAngle = angle;
            }
        }
        return closest;
    }
}
