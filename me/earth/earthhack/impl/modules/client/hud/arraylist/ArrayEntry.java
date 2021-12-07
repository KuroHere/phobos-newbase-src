//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.hud.arraylist;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.hud.*;
import me.earth.earthhack.impl.util.math.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.util.client.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.modules.*;
import me.earth.earthhack.api.module.util.*;

public class ArrayEntry implements Globals
{
    private Module module;
    private float x;
    private float startX;
    private boolean atDesired;
    private static final ModuleCache<HUD> HUD_MODULE_CACHE;
    private final StopWatch stopWatch;
    
    public ArrayEntry(final Module module) {
        this.stopWatch = new StopWatch();
        this.module = module;
        final float n = (float)new ScaledResolution(ArrayEntry.mc).getScaledWidth();
        this.startX = n;
        this.x = n;
        this.stopWatch.reset();
    }
    
    public void drawArrayEntry(final float desiredX, final float desiredY) {
        final float textWidth = (float)HUD.RENDERER.getStringWidth(ModuleUtil.getHudName(this.getModule()));
        final float xSpeed = textWidth / (Minecraft.getDebugFPS() >> 2);
        GlStateManager.pushMatrix();
        GL11.glEnable(3089);
        RenderUtil.scissor(desiredX - textWidth - 1.0f, desiredY - 1.0f, desiredX + 1.0f, desiredY + HUD.RENDERER.getStringHeight() + 3.0f);
        ArrayEntry.HUD_MODULE_CACHE.get().renderText(ModuleUtil.getHudName(this.getModule()), this.getX(), desiredY);
        GL11.glDisable(3089);
        GlStateManager.popMatrix();
        if (Caches.getModule(this.getModule().getClass()).get().isEnabled() && Caches.getModule(this.getModule().getClass()).get().isHidden() != Hidden.Hidden) {
            if (this.stopWatch.passed(1000L)) {
                this.setX(desiredX - textWidth);
                this.setAtDesired(true);
            }
            else if (this.isAtDesired()) {
                this.setX(desiredX - textWidth);
            }
            else if (!this.isDone(desiredX)) {
                if (this.getX() != desiredX - textWidth) {
                    this.setX(Math.max(this.getX() - xSpeed, desiredX - textWidth));
                }
            }
            else {
                this.setAtDesired(true);
            }
        }
        else {
            if (!this.shouldDelete()) {
                this.setX(this.getX() + xSpeed);
            }
            else {
                ArrayEntry.HUD_MODULE_CACHE.get().getRemoveEntries().put(this.module, this);
            }
            this.setAtDesired(false);
            this.stopWatch.reset();
        }
    }
    
    private boolean isDone(final float desiredX) {
        final float textWidth = (float)HUD.RENDERER.getStringWidth(ModuleUtil.getHudName(this.getModule()));
        return this.getX() <= desiredX - textWidth;
    }
    
    private boolean shouldDelete() {
        return this.getX() > this.getStartX();
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(final Module module) {
        this.module = module;
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public boolean isAtDesired() {
        return this.atDesired;
    }
    
    public void setAtDesired(final boolean atDesired) {
        this.atDesired = atDesired;
    }
    
    public float getStartX() {
        return this.startX;
    }
    
    public void setStartX(final float startX) {
        this.startX = startX;
    }
    
    static {
        HUD_MODULE_CACHE = Caches.getModule(HUD.class);
    }
}
