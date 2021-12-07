//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.hud;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;
import me.earth.earthhack.impl.modules.client.hud.modes.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.hud.*;
import java.util.*;

final class ListenerRender extends ModuleListener<HUD, Render2DEvent>
{
    public ListenerRender(final HUD module) {
        super(module, (Class<? super Object>)Render2DEvent.class);
    }
    
    public void invoke(final Render2DEvent event) {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (((HUD)this.module).animations.getValue()) {
            final float ySpeed = 22.0f / (Minecraft.getDebugFPS() >> 2);
            if (ListenerRender.mc.ingameGUI.getChatGUI().getChatOpen()) {
                if (((HUD)this.module).animationY != 14.0f) {
                    if (((HUD)this.module).animationY > 14.0f) {
                        ((HUD)this.module).animationY = Math.max(((HUD)this.module).animationY - ySpeed, 14.0f);
                    }
                    else if (((HUD)this.module).animationY < 14.0f) {
                        ((HUD)this.module).animationY = Math.min(((HUD)this.module).animationY + ySpeed, 14.0f);
                    }
                }
            }
            else if (((HUD)this.module).animationY != 0.0f) {
                if (((HUD)this.module).animationY > 0.0f) {
                    ((HUD)this.module).animationY = Math.max(((HUD)this.module).animationY - ySpeed, 0.0f);
                }
                else if (((HUD)this.module).animationY < 0.0f) {
                    ((HUD)this.module).animationY = Math.min(((HUD)this.module).animationY + ySpeed, 0.0f);
                }
            }
        }
        else if (ListenerRender.mc.ingameGUI.getChatGUI().getChatOpen()) {
            ((HUD)this.module).animationY = 14.0f;
        }
        else {
            ((HUD)this.module).animationY = 0.0f;
        }
        if (((HUD)this.module).renderMode.getValue() == RenderMode.Normal) {
            ((HUD)this.module).renderLogo();
            ((HUD)this.module).renderModules();
        }
        else if (((HUD)this.module).renderMode.getValue() == RenderMode.Editor) {
            for (final HudElement element : Managers.ELEMENTS.getRegistered()) {
                if (ListenerRender.mc.currentScreen == null) {
                    element.hudUpdate(ListenerRender.mc.getRenderPartialTicks());
                    if (!element.isEnabled()) {
                        continue;
                    }
                    element.hudDraw(ListenerRender.mc.getRenderPartialTicks());
                }
            }
        }
        else {
            ((HUD)this.module).renderLogo();
            ((HUD)this.module).renderModules();
            for (final HudElement element : Managers.ELEMENTS.getRegistered()) {
                if (ListenerRender.mc.currentScreen == null) {
                    element.hudUpdate(ListenerRender.mc.getRenderPartialTicks());
                    if (!element.isEnabled()) {
                        continue;
                    }
                    element.hudDraw(ListenerRender.mc.getRenderPartialTicks());
                }
            }
        }
        GL11.glPopMatrix();
    }
}
