//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.esp;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.render.esp.mode.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.culling.*;
import java.util.*;
import net.minecraft.util.math.*;

final class ListenerRender extends ModuleListener<ESP, Render3DEvent>
{
    public ListenerRender(final ESP module) {
        super(module, (Class<? super Object>)Render3DEvent.class, Integer.MIN_VALUE);
    }
    
    public void invoke(final Render3DEvent event) {
        if (((ESP)this.module).mode.getValue() == EspMode.Outline && ((ESP)this.module).storage.getValue()) {
            ((ESP)this.module).drawTileEntities();
        }
        if (((ESP)this.module).items.getValue()) {
            final boolean fancyGraphics = ListenerRender.mc.gameSettings.fancyGraphics;
            ListenerRender.mc.gameSettings.fancyGraphics = false;
            ESP.isRendering = true;
            final float gammaSetting = ListenerRender.mc.gameSettings.gammaSetting;
            ListenerRender.mc.gameSettings.gammaSetting = 100.0f;
            final Entity renderEntity = RenderUtil.getEntity();
            final Frustum frustum = Interpolation.createFrustum(renderEntity);
            for (final Entity entity : ListenerRender.mc.world.loadedEntityList) {
                if (entity instanceof EntityItem) {
                    if (entity.isDead) {
                        continue;
                    }
                    final AxisAlignedBB bb = entity.getEntityBoundingBox();
                    if (!frustum.isBoundingBoxInFrustum(bb)) {
                        continue;
                    }
                    GL11.glPushMatrix();
                    final Vec3d i = Interpolation.interpolateEntity(entity);
                    RenderUtil.drawNametag(((EntityItem)entity).getEntityItem().getDisplayName(), i.xCoord, i.yCoord, i.zCoord, ((ESP)this.module).scale.getValue(), -1, false);
                    RenderUtil.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glPopMatrix();
                }
            }
            ESP.isRendering = false;
            ListenerRender.mc.gameSettings.gammaSetting = gammaSetting;
            ListenerRender.mc.gameSettings.fancyGraphics = fancyGraphics;
        }
    }
}
