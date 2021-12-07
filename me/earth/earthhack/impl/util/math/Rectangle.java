// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math;

public class Rectangle
{
    private double x;
    private double y;
    private double width;
    private double height;
    
    public Rectangle(final double x, final double y, final double width, final double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getWidth() {
        return this.width;
    }
    
    public void setWidth(final double width) {
        this.width = width;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public void setHeight(final double height) {
        this.height = height;
    }
    
    @Override
    public String toString() {
        return "Rectangle: {" + this.x + ", " + this.y + ", " + (this.x + this.width) + ", " + (this.y + this.height) + "}";
    }
}
