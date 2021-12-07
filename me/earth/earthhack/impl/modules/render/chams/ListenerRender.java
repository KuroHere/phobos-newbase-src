//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.chams;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.entity.*;
import java.util.*;
import org.lwjgl.opengl.*;

public class ListenerRender extends ModuleListener<Chams, Render3DEvent>
{
    public ListenerRender(final Chams module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
    }
    
    private void renderEntities(final double renderPosX, final double renderPosY, final double renderPosZ) {
        for (final Entity e : ListenerRender.mc.world.loadedEntityList) {
            if (!((Chams)this.module).isValid(e)) {
                continue;
            }
            if (e == ListenerRender.mc.getRenderViewEntity()) {
                continue;
            }
            if (e.ticksExisted == 0) {
                e.lastTickPosX = e.posX;
                e.lastTickPosY = e.posY;
                e.lastTickPosZ = e.posZ;
            }
            final double d0 = e.lastTickPosX + (e.posX - e.lastTickPosX) * ListenerRender.mc.getRenderPartialTicks();
            final double d2 = e.lastTickPosY + (e.posY - e.lastTickPosY) * ListenerRender.mc.getRenderPartialTicks();
            final double d3 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * ListenerRender.mc.getRenderPartialTicks();
            final double f = e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * ListenerRender.mc.getRenderPartialTicks();
            ListenerRender.mc.getRenderManager().doRenderEntity(e, d0 - renderPosX, d2 - renderPosY, d3 - renderPosZ, (float)f, ListenerRender.mc.getRenderPartialTicks(), true);
        }
    }
    
    public static void drawCompleteImage(final float posX, final float posY, final float width, final float height) {
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(width, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(width, 0.0f, 0.0f);
        GL11.glEnd();
    }
}
