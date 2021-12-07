//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.api.hud;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.misc.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.gui.hud.*;
import net.minecraft.util.math.*;
import java.awt.*;
import me.earth.earthhack.impl.util.render.*;

public abstract class HudElement extends Module
{
    private final Setting<Float> x;
    private final Setting<Float> y;
    private final Setting<Float> scale;
    private float z;
    private float width;
    private float height;
    private final boolean scalable;
    private boolean scaling;
    private GuiUtil.Edge currentEdge;
    private boolean dragging;
    private float draggingX;
    private float draggingY;
    private float originalWidth;
    private float originalHeight;
    private float stretchingWidth;
    private float stretchingHeight;
    private float stretchingX;
    private float stretchingY;
    private float stretchingX2;
    private float stretchingY2;
    private boolean shouldScale;
    
    public HudElement(final String name, final boolean scalable) {
        super(name, Category.Client);
        this.x = this.register(new NumberSetting("X", 2.0f, -2000.0f, 2000.0f));
        this.y = this.register(new NumberSetting("Y", 2.0f, -2000.0f, 2000.0f));
        this.scale = this.register(new NumberSetting("Scale", 1.0f, 0.1f, 10.0f));
        this.z = 0.0f;
        this.width = 100.0f;
        this.height = 100.0f;
        this.scaling = false;
        this.scalable = scalable;
        this.stretchingWidth = this.width;
        this.stretchingHeight = this.height;
        this.originalWidth = this.width;
        this.originalHeight = this.height;
    }
    
    public HudElement(final String name, final boolean scalable, final float x, final float y, final float width, final float height) {
        super(name, Category.Client);
        this.x = this.register(new NumberSetting("X", 2.0f, -2000.0f, 2000.0f));
        this.y = this.register(new NumberSetting("Y", 2.0f, -2000.0f, 2000.0f));
        this.scale = this.register(new NumberSetting("Scale", 1.0f, 0.1f, 10.0f));
        this.z = 0.0f;
        this.width = 100.0f;
        this.height = 100.0f;
        this.scaling = false;
        this.scalable = scalable;
        this.x.setValue(x);
        this.y.setValue(y);
        this.width = width;
        this.height = height;
        this.stretchingWidth = width;
        this.stretchingHeight = height;
        this.originalWidth = width;
        this.originalHeight = height;
    }
    
    public void onEnable() {
        if (HudEditorGui.getInstance() != null) {
            HudEditorGui.getInstance().onToggle();
        }
    }
    
    public void onDisable() {
        if (HudEditorGui.getInstance() != null) {
            HudEditorGui.getInstance().onToggle();
        }
    }
    
    public void guiUpdate(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.dragging) {
            this.setX(mouseX - this.draggingX);
            this.setY(mouseY - this.draggingY);
        }
        if (this.scalable) {
            if (this.scaling && this.currentEdge != null) {
                if ((mouseX < mouseY && mouseX < this.stretchingX) || (mouseY < mouseX && mouseY < this.stretchingY)) {
                    this.scale.setValue(Math.min(this.getHeight(), this.stretchingHeight + (mouseY - this.stretchingY)) * Math.min(this.getWidth(), this.stretchingWidth + (mouseX - this.stretchingX)) / (this.getHeight() * this.getWidth()));
                }
                else if ((mouseX > mouseY && mouseX > this.stretchingX) || (mouseY > mouseX && mouseY > this.stretchingY)) {
                    this.scale.setValue(Math.max(this.getHeight(), this.stretchingHeight + (mouseY - this.stretchingY)) * Math.max(this.getWidth(), this.stretchingWidth + (mouseX - this.stretchingX)) / (this.getHeight() * this.getWidth()));
                }
                this.scale.setValue(MathHelper.clamp((float)this.scale.getValue(), 0.1f, 10.0f));
                this.setWidth(this.getWidth() * this.scale.getValue());
                this.setHeight(this.getHeight() * this.scale.getValue());
            }
            if (this.shouldScale) {
                this.shouldScale = false;
                this.scale.setValue(MathHelper.clamp((float)this.scale.getValue(), 0.1f, 10.0f));
                this.setWidth(this.originalWidth * this.scale.getValue());
                this.setHeight(this.originalHeight * this.scale.getValue());
            }
        }
    }
    
    public void guiDraw(final int mouseX, final int mouseY, final float partialTicks) {
        Render2DUtil.drawRect(this.x.getValue(), this.y.getValue(), this.x.getValue() + this.width, this.y.getValue() + this.height, new Color(40, 40, 40, 255).getRGB());
    }
    
    public void guiKeyPressed(final char eventChar, final int key) {
    }
    
    public void guiMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (GuiUtil.isHovered(this, mouseX, mouseY)) {
            this.dragging = true;
        }
        this.currentEdge = GuiUtil.getHoveredEdge(this, mouseX, mouseY, 5);
        if (GuiUtil.isHovered(this, mouseX, mouseY)) {
            if (this.currentEdge != null && this.scalable) {
                this.scaling = true;
                this.dragging = false;
                this.stretchingWidth = this.getWidth();
                this.stretchingHeight = this.getHeight();
                this.stretchingX = (float)mouseX;
                this.stretchingY = (float)mouseY;
                this.stretchingX2 = this.getX() + this.getWidth();
                this.stretchingY2 = this.getY() + this.getHeight();
            }
            else {
                this.dragging = true;
                this.scaling = false;
                this.draggingX = mouseX - this.getX();
                this.draggingY = mouseY - this.getY();
            }
        }
    }
    
    public void guiMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        this.dragging = false;
        this.scaling = false;
    }
    
    public void hudUpdate(final float partialTicks) {
        if (this.shouldScale) {
            this.shouldScale = false;
            this.scale.setValue(MathHelper.clamp((float)this.scale.getValue(), 0.1f, 10.0f));
            this.setWidth(this.originalWidth * this.scale.getValue());
            this.setHeight(this.originalHeight * this.scale.getValue());
        }
    }
    
    public abstract void hudDraw(final float p0);
    
    public float getX() {
        return this.x.getValue();
    }
    
    public void setX(final float x) {
        this.x.setValue(x);
    }
    
    public float getY() {
        return this.y.getValue();
    }
    
    public void setY(final float y) {
        this.y.setValue(y);
    }
    
    public float getZ() {
        return this.z;
    }
    
    public void setZ(final float z) {
        this.z = z;
    }
    
    public float getScale() {
        return this.scale.getValue();
    }
    
    public void setScale(final float scale) {
        this.scale.setValue(scale);
    }
    
    public boolean isScalable() {
        return this.scalable;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public float getDraggingX() {
        return this.draggingX;
    }
    
    public void setDraggingX(final float draggingX) {
        this.draggingX = draggingX;
    }
    
    public float getDraggingY() {
        return this.draggingY;
    }
    
    public void setDraggingY(final float draggingY) {
        this.draggingY = draggingY;
    }
    
    public boolean isDragging() {
        return this.dragging;
    }
    
    public void setDragging(final boolean dragging) {
        this.dragging = dragging;
    }
}
