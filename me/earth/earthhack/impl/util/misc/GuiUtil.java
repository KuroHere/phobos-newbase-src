// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.misc;

import me.earth.earthhack.impl.gui.hud.*;
import me.earth.earthhack.api.hud.*;

public class GuiUtil
{
    public static boolean isHovered(final int x, final int y, final int width, final int height, final int mouseX, final int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height;
    }
    
    public static boolean isHovered(final float x, final float y, final float width, final float height, final float mouseX, final float mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height;
    }
    
    public static boolean isHovered(final AbstractGuiElement element, final int mouseX, final int mouseY) {
        return isHovered(element.getX(), element.getY(), element.getWidth(), element.getHeight(), (float)mouseX, (float)mouseY);
    }
    
    public static boolean isHovered(final HudElement element, final int mouseX, final int mouseY) {
        return isHovered(element.getX(), element.getY(), element.getWidth(), element.getHeight(), (float)mouseX, (float)mouseY);
    }
    
    public static float reCheckSliderRange(final float value, final float min, final float max) {
        return Math.min(Math.max(value, min), max);
    }
    
    public static double roundSliderForConfig(final double val) {
        return Double.parseDouble(String.format("%.2f", val));
    }
    
    public static String roundSlider(final float f) {
        return String.format("%.2f", f);
    }
    
    public static float roundSliderStep(final float input, final float step) {
        return Math.round(input / step) * step;
    }
    
    public static boolean isHoveredOnEdge(final int x, final int y, final int width, final int height, final int mouseX, final int mouseY, final int edge) {
        return isHovered(x, y, width, height, mouseX, mouseY) && (mouseX < x + edge || mouseX > x + width - edge || mouseY < y + edge || mouseY > y + height - edge);
    }
    
    public static Edge getHoveredEdge(final int x, final int y, final int width, final int height, final int mouseX, final int mouseY, final int edge) {
        if (isHovered(x, y, edge, edge, mouseX, mouseY)) {
            return Edge.TOP_LEFT;
        }
        if (isHovered(x, y + height - edge, edge, edge, mouseX, mouseY)) {
            return Edge.BOTTOM_LEFT;
        }
        if (isHovered(x + width - edge, y, edge, edge, mouseX, mouseY)) {
            return Edge.TOP_RIGHT;
        }
        if (isHovered(x + width - edge, y + height - edge, edge, edge, mouseX, mouseY)) {
            return Edge.BOTTOM_RIGHT;
        }
        if (isHovered(x, y, edge, height, mouseX, mouseY)) {
            return Edge.LEFT;
        }
        if (isHovered(x + width - edge, y, edge, height, mouseX, mouseY)) {
            return Edge.RIGHT;
        }
        if (isHovered(x, y + edge, width, edge, mouseX, mouseY)) {
            return Edge.TOP;
        }
        if (isHovered(x, y + height - edge, width, edge, mouseX, mouseY)) {
            return Edge.BOTTOM;
        }
        return null;
    }
    
    public static Edge getHoveredEdgeNoTop(final int x, final int y, final int width, final int height, final int mouseX, final int mouseY, final int edge) {
        if (!isHovered(x, y, edge, edge, mouseX, mouseY)) {
            if (isHovered(x, y + height - edge, edge, edge, mouseX, mouseY)) {
                return Edge.BOTTOM_LEFT;
            }
            if (!isHovered(x + width - edge, y, edge, edge, mouseX, mouseY)) {
                if (isHovered(x + width - edge, y + height - edge, edge, edge, mouseX, mouseY)) {
                    return Edge.BOTTOM_RIGHT;
                }
                if (isHovered(x, y, edge, height, mouseX, mouseY)) {
                    return Edge.LEFT;
                }
                if (isHovered(x + width - edge, y, edge, height, mouseX, mouseY)) {
                    return Edge.RIGHT;
                }
                if (!isHovered(x, y + edge, width, edge, mouseX, mouseY)) {
                    if (isHovered(x, y + height - edge, width, edge, mouseX, mouseY)) {
                        return Edge.BOTTOM;
                    }
                }
            }
        }
        return null;
    }
    
    public static Edge getHoveredEdgeNoTop(final AbstractGuiElement element, final int mouseX, final int mouseY, final int edge) {
        return getHoveredEdgeNoTop((int)element.getX(), (int)element.getY(), (int)element.getWidth(), (int)element.getHeight(), mouseX, mouseY, edge);
    }
    
    public static Edge getHoveredEdge(final AbstractGuiElement element, final int mouseX, final int mouseY, final int edge) {
        return getHoveredEdge((int)element.getX(), (int)element.getY(), (int)element.getWidth(), (int)element.getHeight(), mouseX, mouseY, edge);
    }
    
    public static Edge getHoveredEdge(final HudElement element, final int mouseX, final int mouseY, final int edge) {
        return getHoveredEdge((int)element.getX(), (int)element.getY(), (int)element.getWidth(), (int)element.getHeight(), mouseX, mouseY, edge);
    }
    
    public enum Edge
    {
        RIGHT, 
        TOP, 
        LEFT, 
        BOTTOM, 
        TOP_RIGHT, 
        BOTTOM_RIGHT, 
        TOP_LEFT, 
        BOTTOM_LEFT;
    }
}
