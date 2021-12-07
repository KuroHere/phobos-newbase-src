//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.nametags;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.culling.*;
import java.util.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.core.ducks.*;
import javax.vecmath.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.math.*;

final class ListenerRender2D extends ModuleListener<Nametags, Render2DEvent>
{
    private int xOffset;
    private int maxEnchHeight;
    private boolean renderDurability;
    
    public ListenerRender2D(final Nametags module) {
        super(module, (Class<? super Object>)Render2DEvent.class);
    }
    
    public void invoke(final Render2DEvent event) {
        if (((Nametags)this.module).twoD.getValue()) {
            ((Nametags)this.module).updateNametags();
            Nametag.isRendering = true;
            final Entity renderEntity = RenderUtil.getEntity();
            final Frustum frustum = Interpolation.createFrustum(renderEntity);
            for (final Nametag nametag : ((Nametags)this.module).nametags) {
                if (!nametag.player.isDead && (!nametag.player.isInvisible() || ((Nametags)this.module).invisibles.getValue())) {
                    if (((Nametags)this.module).fov.getValue() && !frustum.isBoundingBoxInFrustum(nametag.player.getRenderBoundingBox())) {
                        continue;
                    }
                    this.renderNametag(nametag, nametag.player, event);
                }
            }
            Nametag.isRendering = false;
        }
    }
    
    private void renderNametag(final Nametag nametag, final EntityPlayer entity, final Render2DEvent event) {
        final double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ((IMinecraft)ListenerRender2D.mc).getTimer().field_194147_b;
        final double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ((IMinecraft)ListenerRender2D.mc).getTimer().field_194147_b;
        final double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ((IMinecraft)ListenerRender2D.mc).getTimer().field_194147_b;
        final AxisAlignedBB bb = entity.getEntityBoundingBox().addCoord(0.1, 0.1, 0.1);
        final Vector3d[] corners = { new Vector3d(posX + bb.minX - bb.maxX + entity.width / 2.0f, posY, posZ + bb.minZ - bb.maxZ + entity.width / 2.0f), new Vector3d(posX + bb.maxX - bb.minX - entity.width / 2.0f, posY, posZ + bb.minZ - bb.maxZ + entity.width / 2.0f), new Vector3d(posX + bb.minX - bb.maxX + entity.width / 2.0f, posY, posZ + bb.maxZ - bb.minZ - entity.width / 2.0f), new Vector3d(posX + bb.maxX - bb.minX - entity.width / 2.0f, posY, posZ + bb.maxZ - bb.minZ - entity.width / 2.0f), new Vector3d(posX + bb.minX - bb.maxX + entity.width / 2.0f, posY + bb.maxY - bb.minY, posZ + bb.minZ - bb.maxZ + entity.width / 2.0f), new Vector3d(posX + bb.maxX - bb.minX - entity.width / 2.0f, posY + bb.maxY - bb.minY, posZ + bb.minZ - bb.maxZ + entity.width / 2.0f), new Vector3d(posX + bb.minX - bb.maxX + entity.width / 2.0f, posY + bb.maxY - bb.minY, posZ + bb.maxZ - bb.minZ - entity.width / 2.0f), new Vector3d(posX + bb.maxX - bb.minX - entity.width / 2.0f, posY + bb.maxY - bb.minY, posZ + bb.maxZ - bb.minZ - entity.width / 2.0f) };
        final Vector4f transformed = new Vector4f(event.getResolution().getScaledWidth() * 2.0f, event.getResolution().getScaledHeight() * 2.0f, -1.0f, -1.0f);
        for (final Vector3d vec : corners) {
            final GLUProjection.Projection result = GLUProjection.getInstance().project(vec.x - ListenerRender2D.mc.getRenderManager().viewerPosX, vec.y - ListenerRender2D.mc.getRenderManager().viewerPosY, vec.z - ListenerRender2D.mc.getRenderManager().viewerPosZ, GLUProjection.ClampMode.NONE, true);
            transformed.setX((float)Math.min(transformed.getX(), result.getX()));
            transformed.setY((float)Math.min(transformed.getY(), result.getY()));
            transformed.setW((float)Math.max(transformed.getW(), result.getX()));
            transformed.setZ((float)Math.max(transformed.getZ(), result.getY()));
        }
        final float x1 = transformed.x;
        final float w1 = transformed.w - x1;
        final float y1 = transformed.y;
        final int nameWidth = nametag.nameWidth / 2;
        GlStateManager.pushMatrix();
        Managers.TEXT.drawStringWithShadow(nametag.nameString, x1 + w1 / 2.0f - nameWidth, y1 - 3.0f - ListenerRender2D.mc.fontRendererObj.FONT_HEIGHT, nametag.nameColor);
        this.xOffset = -nametag.stacks.size() * 8 - ((nametag.mainHand == null) ? 0 : 8);
        this.maxEnchHeight = nametag.maxEnchHeight;
        this.renderDurability = nametag.renderDura;
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        if (nametag.mainHand != null) {
            this.renderStackRenderer(nametag.mainHand, x1 + w1 / 2.0f, y1 - 3.0f, true);
        }
        for (final StackRenderer sr : nametag.stacks) {
            this.renderStackRenderer(sr, x1 + w1 / 2.0f, y1 - 3.0f, false);
        }
        GlStateManager.popMatrix();
    }
    
    private void renderStackRenderer(final StackRenderer sr, final float x, final float y, final boolean main) {
        int fontOffset = ((Nametags)this.module).getFontOffset(this.maxEnchHeight);
        if (((Nametags)this.module).armor.getValue()) {
            sr.renderStack2D((int)x + this.xOffset, (int)y + fontOffset, this.maxEnchHeight);
            fontOffset -= 32;
        }
        if (((Nametags)this.module).durability.getValue() && sr.isDamageable()) {
            sr.renderDurability(x + this.xOffset, y * 2.0f + fontOffset);
            fontOffset -= (int)Managers.TEXT.getStringHeight();
        }
        else if (this.renderDurability) {
            fontOffset -= (int)Managers.TEXT.getStringHeight();
        }
        if (((Nametags)this.module).itemStack.getValue() && main) {
            sr.renderText(x * 2.0f, y * 2.0f + fontOffset);
        }
        if (((Nametags)this.module).armor.getValue() || ((Nametags)this.module).durability.getValue() || sr.isDamageable()) {
            this.xOffset += 16;
        }
    }
}
