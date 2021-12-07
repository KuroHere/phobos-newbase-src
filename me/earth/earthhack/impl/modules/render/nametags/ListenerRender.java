//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.nametags;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.util.math.rotation.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.impl.managers.*;

final class ListenerRender extends ModuleListener<Nametags, Render3DEvent>
{
    private int xOffset;
    private int maxEnchHeight;
    private boolean renderDurability;
    
    public ListenerRender(final Nametags module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (!((Nametags)this.module).twoD.getValue()) {
            ((Nametags)this.module).updateNametags();
            final Vec3d interp = Interpolation.interpolateEntity(RenderUtil.getEntity());
            Nametag.isRendering = true;
            for (final Nametag nametag : ((Nametags)this.module).nametags) {
                if (!nametag.player.isDead && (!nametag.player.isInvisible() || ((Nametags)this.module).invisibles.getValue())) {
                    if (((Nametags)this.module).fov.getValue() && !RotationUtil.inFov((Entity)nametag.player)) {
                        continue;
                    }
                    final Vec3d i = Interpolation.interpolateEntity((Entity)nametag.player);
                    this.renderNametag(nametag, nametag.player, i.xCoord, i.yCoord, i.zCoord, interp);
                }
            }
            Nametag.isRendering = false;
            if (((Nametags)this.module).debug.getValue()) {
                final Entity renderEntity = RenderUtil.getEntity();
                final Frustum frustum = Interpolation.createFrustum(renderEntity);
                for (final Entity entity : ListenerRender.mc.world.loadedEntityList) {
                    if (entity != null && !EntityUtil.isDead(entity) && !(entity instanceof EntityPlayer) && (!entity.isInvisible() || ((Nametags)this.module).invisibles.getValue())) {
                        if (((Nametags)this.module).fov.getValue() && !frustum.isBoundingBoxInFrustum(entity.getRenderBoundingBox())) {
                            continue;
                        }
                        final Vec3d j = Interpolation.interpolateEntity(entity);
                        RenderUtil.drawNametag(entity.getEntityId() + "", j.xCoord, j.yCoord, j.zCoord, ((Nametags)this.module).scale.getValue(), -1, false);
                    }
                }
            }
        }
    }
    
    private void renderNametag(final Nametag nametag, final EntityPlayer player, final double x, double y, final double z, final Vec3d mcPlayerInterpolation) {
        final double yOffset = y + (player.isSneaking() ? 0.5 : 0.7);
        final double xDist = mcPlayerInterpolation.xCoord - x;
        final double yDist = mcPlayerInterpolation.yCoord - y;
        final double zDist = mcPlayerInterpolation.zCoord - z;
        y = MathHelper.sqrt(xDist * xDist + yDist * yDist + zDist * zDist);
        final int nameWidth = nametag.nameWidth / 2;
        double scaling = 0.0018 + ((Nametags)this.module).scale.getValue() * y;
        if (y <= 8.0) {
            scaling = 0.0245;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)yOffset + 1.4f, (float)z);
        GlStateManager.rotate(-ListenerRender.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        final float xRot = (ListenerRender.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f;
        GlStateManager.rotate(ListenerRender.mc.getRenderManager().playerViewX, xRot, 0.0f, 0.0f);
        GlStateManager.scale(-scaling, -scaling, scaling);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        RenderUtil.prepare((float)(-nameWidth - 1), -Managers.TEXT.getStringHeight(), (float)(nameWidth + 2), 1.0f, 1.8f, 1426064384, 855638016);
        GlStateManager.disableBlend();
        Managers.TEXT.drawStringWithShadow(nametag.nameString, (float)(-nameWidth), -(Managers.TEXT.getStringHeight() - 1.0f), nametag.nameColor);
        this.xOffset = -nametag.stacks.size() * 8 - ((nametag.mainHand == null) ? 0 : 8);
        this.maxEnchHeight = nametag.maxEnchHeight;
        this.renderDurability = nametag.renderDura;
        GlStateManager.pushMatrix();
        if (nametag.mainHand != null) {
            this.renderStackRenderer(nametag.mainHand, true);
        }
        for (final StackRenderer sr : nametag.stacks) {
            this.renderStackRenderer(sr, false);
        }
        GlStateManager.popMatrix();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    private void renderStackRenderer(final StackRenderer sr, final boolean main) {
        int fontOffset = ((Nametags)this.module).getFontOffset(this.maxEnchHeight);
        if (((Nametags)this.module).armor.getValue()) {
            sr.renderStack(this.xOffset, fontOffset, this.maxEnchHeight);
            fontOffset -= 32;
        }
        if (((Nametags)this.module).durability.getValue() && sr.isDamageable()) {
            sr.renderDurability((float)this.xOffset, (float)fontOffset);
            fontOffset -= (int)Managers.TEXT.getStringHeight();
        }
        else if (this.renderDurability) {
            fontOffset -= (int)Managers.TEXT.getStringHeight();
        }
        if (((Nametags)this.module).itemStack.getValue() && main) {
            sr.renderText(fontOffset);
        }
        if (((Nametags)this.module).armor.getValue() || ((Nametags)this.module).durability.getValue() || sr.isDamageable()) {
            this.xOffset += 16;
        }
    }
}
