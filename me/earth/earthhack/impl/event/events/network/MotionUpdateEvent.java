//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.network;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.event.events.*;
import net.minecraft.entity.*;

public class MotionUpdateEvent extends StageEvent implements Globals
{
    private double x;
    private double y;
    private double z;
    private float rotationYaw;
    private float rotationPitch;
    private boolean onGround;
    protected boolean modified;
    
    public MotionUpdateEvent(final Stage stage, final MotionUpdateEvent event) {
        this(stage, event.x, event.y, event.z, event.rotationYaw, event.rotationPitch, event.onGround);
    }
    
    public MotionUpdateEvent(final Stage stage, final double x, final double y, final double z, final float rotationYaw, final float rotationPitch, final boolean onGround) {
        super(stage);
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.onGround = onGround;
    }
    
    public boolean isModified() {
        return this.modified;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.modified = true;
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.modified = true;
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.modified = true;
        this.z = z;
    }
    
    public float getYaw() {
        return this.rotationYaw;
    }
    
    public void setYaw(final float rotationYaw) {
        this.modified = true;
        this.rotationYaw = rotationYaw;
    }
    
    public float getPitch() {
        return this.rotationPitch;
    }
    
    public void setPitch(final float rotationPitch) {
        this.modified = true;
        this.rotationPitch = rotationPitch;
    }
    
    public boolean isOnGround() {
        return this.onGround;
    }
    
    public void setOnGround(final boolean onGround) {
        this.modified = true;
        this.onGround = onGround;
    }
    
    public static class Riding extends MotionUpdateEvent
    {
        private float moveStrafing;
        private float moveForward;
        private boolean jump;
        private boolean sneak;
        
        public Riding(final Stage stage, final double x, final double y, final double z, final float rotationYaw, final float rotationPitch, final boolean onGround, final float moveStrafing, final float moveForward, final boolean jump, final boolean sneak) {
            super(stage, x, y, z, rotationYaw, rotationPitch, onGround);
            this.moveStrafing = moveStrafing;
            this.moveForward = moveForward;
            this.jump = jump;
            this.sneak = sneak;
        }
        
        public Riding(final Stage stage, final Riding event) {
            this(stage, event.getX(), event.getY(), event.getZ(), event.getYaw(), event.getPitch(), event.isOnGround(), event.moveStrafing, event.moveForward, event.jump, event.sneak);
        }
        
        public Entity getEntity() {
            return Riding.mc.player.getLowestRidingEntity();
        }
        
        public float getMoveStrafing() {
            return this.moveStrafing;
        }
        
        public void setMoveStrafing(final float moveStrafing) {
            this.modified = true;
            this.moveStrafing = moveStrafing;
        }
        
        public float getMoveForward() {
            return this.moveForward;
        }
        
        public void setMoveForward(final float moveForward) {
            this.modified = true;
            this.moveForward = moveForward;
        }
        
        public boolean getJump() {
            return this.jump;
        }
        
        public void setJump(final boolean jump) {
            this.modified = true;
            this.jump = jump;
        }
        
        public boolean getSneak() {
            return this.sneak;
        }
        
        public void setSneak(final boolean sneak) {
            this.modified = true;
            this.sneak = sneak;
        }
    }
}
