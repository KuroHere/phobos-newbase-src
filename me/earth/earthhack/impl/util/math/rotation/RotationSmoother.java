//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math.rotation;

import me.earth.earthhack.impl.managers.minecraft.movement.*;
import net.minecraft.entity.*;

public class RotationSmoother
{
    private final RotationManager manager;
    private int rotationTicks;
    private boolean rotating;
    
    public RotationSmoother(final RotationManager manager) {
        this.manager = manager;
    }
    
    public float[] getRotations(final Entity from, final Entity entity, final double height, final double maxAngle) {
        return this.getRotations(entity, from.posX, from.posY, from.posZ, from.getEyeHeight(), height, maxAngle);
    }
    
    public float[] getRotations(final Entity entity, final double fromX, final double fromY, final double fromZ, final float eyeHeight, final double height, final double maxAngle) {
        final float[] rotations = RotationUtil.getRotations(entity.posX, entity.posY + entity.getEyeHeight() * height, entity.posZ, fromX, fromY, fromZ, eyeHeight);
        return this.smoothen(rotations, maxAngle);
    }
    
    public float[] smoothen(final float[] rotations, final double maxAngle) {
        final float[] server = { this.manager.getServerYaw(), this.manager.getServerPitch() };
        return this.smoothen(server, rotations, maxAngle);
    }
    
    public float[] smoothen(final float[] server, final float[] rotations, final double maxAngle) {
        if (maxAngle >= 180.0 || maxAngle <= 0.0 || RotationUtil.angle(server, rotations) <= maxAngle) {
            this.rotating = false;
            return rotations;
        }
        this.rotationTicks = 0;
        this.rotating = true;
        return RotationUtil.faceSmoothly(server[0], server[1], rotations[0], rotations[1], maxAngle, maxAngle);
    }
    
    public void incrementRotationTicks() {
        ++this.rotationTicks;
    }
    
    public int getRotationTicks() {
        return this.rotationTicks;
    }
    
    public boolean isRotating() {
        return this.rotating;
    }
}
