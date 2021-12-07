//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.frame;

import java.util.*;
import me.earth.earthhack.impl.gui.click.component.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.util.render.*;

public class Frame
{
    private final String label;
    private float posX;
    private float posY;
    private float lastPosX;
    private float lastPosY;
    private float width;
    private final float height;
    private boolean extended;
    private boolean dragging;
    private final ArrayList<Component> components;
    private int scrollY;
    
    public Frame(final String label, final float posX, final float posY, final float width, final float height) {
        this.components = new ArrayList<Component>();
        this.label = label;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }
    
    public void init() {
        this.components.forEach(Component::init);
    }
    
    public void moved(final float posX, final float posY) {
        this.components.forEach(component -> component.moved(posX, posY));
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (this.isDragging()) {
            this.setPosX(mouseX + this.getLastPosX());
            this.setPosY(mouseY + this.getLastPosY());
            this.getComponents().forEach(component -> component.moved(this.getPosX(), this.getPosY() + this.getScrollY()));
        }
        if (this.getPosX() < 0.0f) {
            this.setPosX(0.0f);
            this.getComponents().forEach(component -> component.moved(this.getPosX(), this.getPosY() + this.getScrollY()));
        }
        if (this.getPosX() + this.getWidth() > scaledResolution.getScaledWidth()) {
            this.setPosX(scaledResolution.getScaledWidth() - this.getWidth());
            this.getComponents().forEach(component -> component.moved(this.getPosX(), this.getPosY() + this.getScrollY()));
        }
        if (this.getPosY() < 0.0f) {
            this.setPosY(0.0f);
            this.getComponents().forEach(component -> component.moved(this.getPosX(), this.getPosY() + this.getScrollY()));
        }
        if (this.getPosY() + this.getHeight() > scaledResolution.getScaledHeight()) {
            this.setPosY(scaledResolution.getScaledHeight() - this.getHeight());
            this.getComponents().forEach(component -> component.moved(this.getPosX(), this.getPosY() + this.getScrollY()));
        }
    }
    
    public void keyTyped(final char character, final int keyCode) {
        if (this.isExtended()) {
            this.getComponents().forEach(component -> component.keyTyped(character, keyCode));
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final boolean hovered = RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());
        switch (mouseButton) {
            case 0: {
                if (hovered) {
                    this.setDragging(true);
                    this.setLastPosX(this.getPosX() - mouseX);
                    this.setLastPosY(this.getPosY() - mouseY);
                    break;
                }
                break;
            }
            case 1: {
                if (hovered) {
                    this.setExtended(!this.isExtended());
                    break;
                }
                break;
            }
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isDragging()) {
            this.setDragging(false);
        }
        if (this.isExtended()) {
            this.getComponents().forEach(component -> component.mouseReleased(mouseX, mouseY, mouseButton));
        }
    }
    
    public ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public float getPosX() {
        return this.posX;
    }
    
    public void setPosX(final float posX) {
        this.posX = posX;
    }
    
    public float getPosY() {
        return this.posY;
    }
    
    public void setPosY(final float posY) {
        this.posY = posY;
    }
    
    public float getLastPosX() {
        return this.lastPosX;
    }
    
    public void setLastPosX(final float lastPosX) {
        this.lastPosX = lastPosX;
    }
    
    public float getLastPosY() {
        return this.lastPosY;
    }
    
    public void setLastPosY(final float lastPosY) {
        this.lastPosY = lastPosY;
    }
    
    public boolean isExtended() {
        return this.extended;
    }
    
    public void setExtended(final boolean extended) {
        this.extended = extended;
    }
    
    public boolean isDragging() {
        return this.dragging;
    }
    
    public void setDragging(final boolean dragging) {
        this.dragging = dragging;
    }
    
    public int getScrollY() {
        return this.scrollY;
    }
    
    public void setScrollY(final int scrollY) {
        this.scrollY = scrollY;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
}
