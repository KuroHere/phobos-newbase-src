//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.skeleton;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.combat.killaura.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.managers.*;
import java.awt.*;
import me.earth.earthhack.impl.util.render.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.modules.*;

final class ListenerRender extends ModuleListener<Skeleton, Render3DEvent>
{
    private static final ModuleCache<KillAura> KILL_AURA;
    
    public ListenerRender(final Skeleton module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        final boolean lightning = GL11.glIsEnabled(2896);
        final boolean blend = GL11.glIsEnabled(3042);
        final boolean texture = GL11.glIsEnabled(3553);
        final boolean depth = GL11.glIsEnabled(2929);
        final boolean lineSmooth = GL11.glIsEnabled(2848);
        if (lightning) {
            GL11.glDisable(2896);
        }
        if (!blend) {
            GL11.glEnable(3042);
        }
        GL11.glLineWidth(1.0f);
        if (texture) {
            GL11.glDisable(3553);
        }
        if (depth) {
            GL11.glDisable(2929);
        }
        if (!lineSmooth) {
            GL11.glEnable(2848);
        }
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GL11.glHint(3154, 4354);
        GlStateManager.depthMask(false);
        final List<EntityPlayer> playerEntities = ListenerRender.mc.world.playerEntities;
        final Entity renderEntity = RenderUtil.getEntity();
        ((Skeleton)this.module).rotations.keySet().removeIf(player -> player == null || !playerEntities.contains(player) || player.equals((Object)renderEntity) || player.isPlayerSleeping() || EntityUtil.isDead((Entity)player));
        playerEntities.forEach(player -> {
            final float[][] rotations = ((Skeleton)this.module).rotations.get(player);
            if (rotations != null) {
                GlStateManager.pushMatrix();
                if (Managers.FRIENDS.contains(player.getName())) {
                    final Color friendClr = ((Skeleton)this.module).friendColor.getValue();
                    GlStateManager.color(friendClr.getRed() / 255.0f, friendClr.getGreen() / 255.0f, friendClr.getBlue() / 255.0f, friendClr.getAlpha() / 255.0f);
                }
                else {
                    final EntityPlayer autoCrystal = Managers.TARGET.getAutoCrystal();
                    final Entity killAuraTarget = ListenerRender.KILL_AURA.returnIfPresent(KillAura::getTarget, null);
                    if (player.equals((Object)autoCrystal) || player.equals((Object)killAuraTarget)) {
                        final Color targetClr = ((Skeleton)this.module).targetColor.getValue();
                        GlStateManager.color(targetClr.getRed() / 255.0f, targetClr.getGreen() / 255.0f, targetClr.getBlue() / 255.0f, targetClr.getAlpha() / 255.0f);
                    }
                    else {
                        final Color clr = ((Skeleton)this.module).color.getValue();
                        GlStateManager.color(clr.getRed() / 255.0f, clr.getGreen() / 255.0f, clr.getBlue() / 255.0f, clr.getAlpha() / 255.0f);
                    }
                }
                final Vec3d interpolation = Interpolation.interpolateEntity((Entity)player);
                final double pX = interpolation.xCoord;
                final double pY = interpolation.yCoord;
                final double pZ = interpolation.zCoord;
                GlStateManager.translate(pX, pY, pZ);
                GlStateManager.rotate(-player.renderYawOffset, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0, 0.0, player.isSneaking() ? -0.235 : 0.0);
                final float sneak = player.isSneaking() ? 0.6f : 0.75f;
                GlStateManager.pushMatrix();
                GlStateManager.translate(-0.125, (double)sneak, 0.0);
                if (rotations[3][0] != 0.0f) {
                    GlStateManager.rotate(rotations[3][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                if (rotations[3][1] != 0.0f) {
                    GlStateManager.rotate(rotations[3][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (rotations[3][2] != 0.0f) {
                    GlStateManager.rotate(rotations[3][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                GlStateManager.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, (double)(-sneak), 0.0);
                GlStateManager.glEnd();
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.125, (double)sneak, 0.0);
                if (rotations[4][0] != 0.0f) {
                    GlStateManager.rotate(rotations[4][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                if (rotations[4][1] != 0.0f) {
                    GlStateManager.rotate(rotations[4][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (rotations[4][2] != 0.0f) {
                    GlStateManager.rotate(rotations[4][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                GlStateManager.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, (double)(-sneak), 0.0);
                GlStateManager.glEnd();
                GlStateManager.popMatrix();
                GlStateManager.translate(0.0, 0.0, player.isSneaking() ? 0.25 : 0.0);
                GlStateManager.pushMatrix();
                double sneakOffset = 0.0;
                if (player.isSneaking()) {
                    sneakOffset = -0.05;
                }
                GlStateManager.translate(0.0, sneakOffset, player.isSneaking() ? -0.01725 : 0.0);
                GlStateManager.pushMatrix();
                GlStateManager.translate(-0.375, sneak + 0.55, 0.0);
                if (rotations[1][0] != 0.0f) {
                    GlStateManager.rotate(rotations[1][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                if (rotations[1][1] != 0.0f) {
                    GlStateManager.rotate(rotations[1][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (rotations[1][2] != 0.0f) {
                    GlStateManager.rotate(-rotations[1][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                GlStateManager.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, -0.5, 0.0);
                GlStateManager.glEnd();
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.375, sneak + 0.55, 0.0);
                if (rotations[2][0] != 0.0f) {
                    GlStateManager.rotate(rotations[2][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                if (rotations[2][1] != 0.0f) {
                    GlStateManager.rotate(rotations[2][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (rotations[2][2] != 0.0f) {
                    GlStateManager.rotate(-rotations[2][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                GlStateManager.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, -0.5, 0.0);
                GlStateManager.glEnd();
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0, sneak + 0.55, 0.0);
                if (rotations[0][0] != 0.0f) {
                    GlStateManager.rotate(rotations[0][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                GlStateManager.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, 0.3, 0.0);
                GlStateManager.glEnd();
                GlStateManager.popMatrix();
                GlStateManager.popMatrix();
                GlStateManager.rotate(player.isSneaking() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
                if (player.isSneaking()) {
                    sneakOffset = -0.16175;
                }
                GlStateManager.translate(0.0, sneakOffset, player.isSneaking() ? -0.48025 : 0.0);
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0, (double)sneak, 0.0);
                GlStateManager.glBegin(3);
                GL11.glVertex3d(-0.125, 0.0, 0.0);
                GL11.glVertex3d(0.125, 0.0, 0.0);
                GlStateManager.glEnd();
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0, (double)sneak, 0.0);
                GlStateManager.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, 0.55, 0.0);
                GlStateManager.glEnd();
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0, sneak + 0.55, 0.0);
                GlStateManager.glBegin(3);
                GL11.glVertex3d(-0.375, 0.0, 0.0);
                GL11.glVertex3d(0.375, 0.0, 0.0);
                GlStateManager.glEnd();
                GlStateManager.popMatrix();
                GlStateManager.popMatrix();
            }
            return;
        });
        GlStateManager.depthMask(true);
        if (!lineSmooth) {
            GL11.glDisable(2848);
        }
        if (depth) {
            GL11.glEnable(2929);
        }
        if (texture) {
            GL11.glEnable(3553);
        }
        if (!blend) {
            GL11.glDisable(3042);
        }
        if (lightning) {
            GL11.glEnable(2896);
        }
    }
    
    static {
        KILL_AURA = Caches.getModule(KillAura.class);
    }
}
