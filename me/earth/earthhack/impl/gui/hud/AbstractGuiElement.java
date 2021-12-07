//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.hud;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public abstract class AbstractGuiElement implements ICoordinate
{
    private float x;
    private float y;
    private float width;
    private float height;
    private String name;
    protected Minecraft mc;
    
    public AbstractGuiElement(final String name) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
        this.x = 0.0f;
        this.y = 0.0f;
        this.width = 300.0f;
        this.height = (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
    }
    
    public AbstractGuiElement(final String name, final float width, final float height) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
        this.width = width;
        this.height = height;
    }
    
    public AbstractGuiElement(final String name, final float x, final float y, final float width, final float height) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDisplayName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setDisplayName(final String displayName) {
        this.name = displayName;
    }
    
    @Override
    public float getX() {
        return this.x;
    }
    
    @Override
    public void setX(final float x) {
        this.x = x;
    }
    
    @Override
    public float getY() {
        return this.y;
    }
    
    @Override
    public void setY(final float y) {
        this.y = y;
    }
    
    @Override
    public float getWidth() {
        return this.width;
    }
    
    @Override
    public void setWidth(final float width) {
        this.width = width;
    }
    
    @Override
    public float getHeight() {
        return this.height;
    }
    
    @Override
    public void setHeight(final float height) {
        this.height = height;
    }
}
