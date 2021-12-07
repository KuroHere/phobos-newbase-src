// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click.component;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.clickgui.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.gui.click.*;
import me.earth.earthhack.impl.modules.*;

public class Component
{
    private final String label;
    private float posX;
    private float posY;
    private float finishedX;
    private float finishedY;
    private float offsetX;
    private float offsetY;
    private float lastPosX;
    private float lastPosY;
    private float width;
    private float height;
    private boolean extended;
    private boolean dragging;
    private String description;
    private static final ModuleCache<ClickGui> CLICK_GUI;
    
    public Component(final String label, final float posX, final float posY, final float offsetX, final float offsetY, final float width, final float height) {
        this.label = label;
        this.posX = posX;
        this.posY = posY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.finishedX = posX + offsetX;
        this.finishedY = posY + offsetY;
    }
    
    public void init() {
    }
    
    public void moved(final float posX, final float posY) {
        this.setPosX(posX);
        this.setPosY(posY);
        this.setFinishedX(this.getPosX() + this.getOffsetX());
        this.setFinishedY(this.getPosY() + this.getOffsetY());
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (RenderUtil.mouseWithinBounds(mouseX, mouseY, this.getFinishedX() + 5.0f, this.getFinishedY() + 1.0f, this.getWidth() - 10.0f, this.getHeight() - 2.0f)) {
            Click.descriptionFrame.setDescription(this.getDescription());
        }
    }
    
    public void keyTyped(final char character, final int keyCode) {
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public float getFinishedX() {
        return this.finishedX;
    }
    
    public void setFinishedX(final float finishedX) {
        this.finishedX = finishedX;
    }
    
    public float getFinishedY() {
        return this.finishedY;
    }
    
    public void setFinishedY(final float finishedY) {
        this.finishedY = finishedY;
    }
    
    public float getOffsetX() {
        return this.offsetX;
    }
    
    public void setOffsetX(final float offsetX) {
        this.offsetX = offsetX;
    }
    
    public float getOffsetY() {
        return this.offsetY;
    }
    
    public void setOffsetY(final float offsetY) {
        this.offsetY = offsetY;
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
    
    public static ModuleCache<ClickGui> getClickGui() {
        return Component.CLICK_GUI;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    static {
        CLICK_GUI = Caches.getModule(ClickGui.class);
    }
}
