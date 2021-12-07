// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.hud;

import me.earth.earthhack.api.hud.*;
import me.earth.earthhack.impl.managers.*;
import java.util.*;

public class ElementSnapPoint extends SnapPoint
{
    private final HudElement element;
    
    public ElementSnapPoint(final HudElement element, final Orientation orientation) {
        super(element.getX(), element.getY(), element.getWidth(), orientation);
        switch (orientation) {
            case TOP: {
                this.x = element.getX();
                this.y = element.getY();
                this.length = element.getWidth();
                break;
            }
            case BOTTOM: {
                this.x = element.getX();
                this.y = element.getY() + element.getHeight();
                this.length = element.getWidth();
                break;
            }
            case LEFT: {
                this.x = element.getX();
                this.y = element.getY();
                this.length = element.getHeight();
                break;
            }
            case RIGHT: {
                this.x = element.getX() + element.getWidth();
                this.y = element.getY();
                this.length = element.getHeight();
                break;
            }
        }
        this.element = element;
    }
    
    @Override
    public void update(final int mouseX, final int mouseY, final float partialTicks) {
        for (final HudElement hudElement : Managers.ELEMENTS.getRegistered()) {
            if (hudElement == this.element) {
                continue;
            }
            if (this.orientation == Orientation.LEFT && hudElement.getX() <= this.x + 4.0f && hudElement.getX() >= this.x - 4.0f) {
                if (hudElement.isDragging() || this.x == hudElement.getX()) {
                    continue;
                }
                hudElement.setX(this.x);
            }
            else if (this.orientation == Orientation.RIGHT && hudElement.getX() + hudElement.getWidth() <= this.x + 4.0f && hudElement.getX() + hudElement.getWidth() >= this.x - 4.0f) {
                if (hudElement.isDragging() || this.x == hudElement.getX() + hudElement.getWidth()) {
                    continue;
                }
                hudElement.setX(this.x - hudElement.getWidth());
            }
            else if (this.orientation == Orientation.TOP && hudElement.getY() <= this.y + 4.0f && hudElement.getY() >= this.y - 4.0f) {
                if (hudElement.isDragging() || this.y == hudElement.getY()) {
                    continue;
                }
                hudElement.setY(this.y);
            }
            else {
                if (this.orientation != Orientation.BOTTOM || hudElement.getY() + hudElement.getHeight() > this.y + 4.0f || hudElement.getY() + hudElement.getHeight() < this.y - 4.0f || hudElement.isDragging() || this.y == hudElement.getY() + hudElement.getHeight()) {
                    continue;
                }
                hudElement.setY(this.y - hudElement.getHeight());
            }
        }
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY, final float partialTicks) {
    }
    
    public HudElement getElement() {
        return this.element;
    }
}
