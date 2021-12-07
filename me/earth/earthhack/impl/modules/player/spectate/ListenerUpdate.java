//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.spectate;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.util.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

final class ListenerUpdate extends ModuleListener<Spectate, UpdateEvent>
{
    public ListenerUpdate(final Spectate module) {
        super(module, (Class<? super Object>)UpdateEvent.class);
    }
    
    public void invoke(final UpdateEvent event) {
        if (ListenerUpdate.mc.player.movementInput instanceof MovementInputFromOptions) {
            ListenerUpdate.mc.player.movementInput = new MovementInput();
        }
        final EntityPlayerNoInterp render = ((Spectate)this.module).render;
        final EntityPlayerNoInterp fake = ((Spectate)this.module).fakePlayer;
        fake.setPositionAndRotationDirect(ListenerUpdate.mc.player.posX, ListenerUpdate.mc.player.posY, ListenerUpdate.mc.player.posZ, ListenerUpdate.mc.player.rotationYaw, ListenerUpdate.mc.player.rotationPitch, 3, false);
        fake.setAbsorptionAmount(ListenerUpdate.mc.player.getAbsorptionAmount());
        fake.setHealth(ListenerUpdate.mc.player.getHealth());
        fake.hurtTime = ListenerUpdate.mc.player.hurtTime;
        fake.maxHurtTime = ListenerUpdate.mc.player.maxHurtTime;
        fake.attackedAtYaw = ListenerUpdate.mc.player.attackedAtYaw;
        render.noClip = true;
        render.setAbsorptionAmount(ListenerUpdate.mc.player.getAbsorptionAmount());
        render.setHealth(ListenerUpdate.mc.player.getHealth());
        render.setAir(ListenerUpdate.mc.player.getAir());
        render.getFoodStats().setFoodLevel(ListenerUpdate.mc.player.getFoodStats().getFoodLevel());
        render.getFoodStats().setFoodSaturationLevel(ListenerUpdate.mc.player.getFoodStats().getSaturationLevel());
        render.setVelocity(0.0, 0.0, 0.0);
        render.setPrimaryHand(ListenerUpdate.mc.player.getPrimaryHand());
        render.hurtTime = ListenerUpdate.mc.player.hurtTime;
        render.maxHurtTime = ListenerUpdate.mc.player.maxHurtTime;
        render.attackedAtYaw = ListenerUpdate.mc.player.attackedAtYaw;
        final EntityPlayerNoInterp entityPlayerNoInterp = render;
        entityPlayerNoInterp.rotationYaw %= 360.0f;
        final EntityPlayerNoInterp entityPlayerNoInterp2 = render;
        entityPlayerNoInterp2.rotationPitch %= 360.0f;
        render.prevRotationYaw = render.rotationYaw;
        render.prevRotationPitch = render.rotationPitch;
        render.prevRotationYawHead = render.rotationYawHead;
        while (render.rotationYaw - render.prevRotationYaw < -180.0f) {
            final EntityPlayerNoInterp entityPlayerNoInterp3 = render;
            entityPlayerNoInterp3.prevRotationYaw -= 360.0f;
        }
        while (render.rotationYaw - render.prevRotationYaw >= 180.0f) {
            final EntityPlayerNoInterp entityPlayerNoInterp4 = render;
            entityPlayerNoInterp4.prevRotationYaw += 360.0f;
        }
        while (render.rotationPitch - render.prevRotationPitch < -180.0f) {
            final EntityPlayerNoInterp entityPlayerNoInterp5 = render;
            entityPlayerNoInterp5.prevRotationPitch -= 360.0f;
        }
        while (render.rotationPitch - render.prevRotationPitch >= 180.0f) {
            final EntityPlayerNoInterp entityPlayerNoInterp6 = render;
            entityPlayerNoInterp6.prevRotationPitch += 360.0f;
        }
        while (render.rotationYawHead - render.prevRotationYawHead < -180.0f) {
            final EntityPlayerNoInterp entityPlayerNoInterp7 = render;
            entityPlayerNoInterp7.prevRotationYawHead -= 360.0f;
        }
        while (render.rotationYawHead - render.prevRotationYawHead >= 180.0f) {
            final EntityPlayerNoInterp entityPlayerNoInterp8 = render;
            entityPlayerNoInterp8.prevRotationYawHead += 360.0f;
        }
        render.lastTickPosX = render.posX;
        render.lastTickPosY = render.posY;
        render.lastTickPosZ = render.posZ;
        render.prevPosX = render.posX;
        render.prevPosY = render.posY;
        render.prevPosZ = render.posZ;
        ((Spectate)this.module).input.updatePlayerMoveState();
        final double[] dir = MovementUtil.strafe((Entity)render, ((Spectate)this.module).input, 0.5);
        if (((Spectate)this.module).input.moveStrafe != 0.0f || ((Spectate)this.module).input.field_192832_b != 0.0f) {
            render.motionX = dir[0];
            render.motionZ = dir[1];
        }
        else {
            render.motionX = 0.0;
            render.motionZ = 0.0;
        }
        if (((Spectate)this.module).input.jump) {
            final EntityPlayerNoInterp entityPlayerNoInterp9 = render;
            entityPlayerNoInterp9.motionY += 0.5;
        }
        if (((Spectate)this.module).input.sneak) {
            final EntityPlayerNoInterp entityPlayerNoInterp10 = render;
            entityPlayerNoInterp10.motionY -= 0.5;
        }
        render.setEntityBoundingBox(render.getEntityBoundingBox().offset(render.motionX, render.motionY, render.motionZ));
        render.resetPositionToBB();
        render.chunkCoordX = MathHelper.floor(render.posX / 16.0);
        render.chunkCoordZ = MathHelper.floor(render.posZ / 16.0);
    }
}
