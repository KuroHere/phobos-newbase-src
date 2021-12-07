//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft;

import net.minecraft.entity.player.*;
import me.earth.earthhack.api.util.interfaces.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

public class MotionTracker extends EntityPlayer implements Globals
{
    public EntityPlayer tracked;
    public MovementInput movementInput;
    public boolean safe;
    
    public MotionTracker(final World worldIn, final EntityPlayer from) {
        super(worldIn, new GameProfile(UUID.randomUUID(), "Motion-Tracker-" + from.getName()));
        this.tracked = from;
        this.setEntityId(from.getEntityId() * -1);
        this.updateFromTrackedEntity();
    }
    
    public MotionTracker(final World worldIn, final MotionTracker from) {
        this(worldIn, from.tracked);
        this.movementInput = from.movementInput;
        this.safe = from.safe;
    }
    
    private MotionTracker(final World worldIn) {
        super(worldIn, new GameProfile(UUID.randomUUID(), "Motion-Tracker"));
    }
    
    public void onUpdate() {
        this.updateFromTrackedEntity();
        super.onUpdate();
    }
    
    public void updateSilent() {
        super.onUpdate();
    }
    
    public void onLivingUpdate() {
        this.movementInput.updatePlayerMoveState();
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        axisalignedbb = this.getEntityBoundingBox();
        this.pushOutOfBlocks(this.posX - this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ + this.width * 0.35);
        this.pushOutOfBlocks(this.posX - this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ - this.width * 0.35);
        this.pushOutOfBlocks(this.posX + this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ - this.width * 0.35);
        this.pushOutOfBlocks(this.posX + this.width * 0.35, axisalignedbb.minY + 0.5, this.posZ + this.width * 0.35);
        super.onLivingUpdate();
    }
    
    public void updateFromTrackedEntity() {
        (this.movementInput = new MovementInputFromRemotePlayer(this.tracked)).updatePlayerMoveState();
        this.copyLocationAndAnglesFrom((Entity)this.tracked);
        this.setEntityBoundingBox(this.tracked.getEntityBoundingBox());
        this.motionX = this.tracked.motionX;
        this.motionY = this.tracked.motionY;
        this.motionZ = this.tracked.motionZ;
        this.onGround = this.tracked.onGround;
        this.prevPosX = this.tracked.prevPosX;
        this.prevPosY = this.tracked.prevPosY;
        this.prevPosZ = this.tracked.prevPosZ;
        this.isCollided = this.tracked.isCollided;
        this.isCollidedHorizontally = this.tracked.isCollidedHorizontally;
        this.isCollidedVertically = this.tracked.isCollidedVertically;
        this.field_191988_bg = this.tracked.field_191988_bg;
        this.moveStrafing = this.tracked.moveStrafing;
        this.moveForward = this.tracked.moveForward;
    }
    
    public boolean isSpectator() {
        return false;
    }
    
    public boolean isCreative() {
        return false;
    }
}
