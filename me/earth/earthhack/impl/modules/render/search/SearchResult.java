// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.search;

import net.minecraft.util.math.*;
import java.awt.*;

public class SearchResult
{
    private final BlockPos pos;
    private final AxisAlignedBB bb;
    private final Color color;
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;
    
    public SearchResult(final BlockPos pos, final AxisAlignedBB bb, final float red, final float green, final float blue, final float alpha) {
        this.pos = pos;
        this.bb = bb;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.color = new Color(red, green, blue, alpha);
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public AxisAlignedBB getBb() {
        return this.bb;
    }
    
    public float getRed() {
        return this.red;
    }
    
    public float getGreen() {
        return this.green;
    }
    
    public float getBlue() {
        return this.blue;
    }
    
    public float getAlpha() {
        return this.alpha;
    }
    
    public Color getColor() {
        return this.color;
    }
}
