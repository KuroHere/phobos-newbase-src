// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.render;

import java.awt.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.render.*;

public class BlockESPBuilder
{
    private static final Color LIGHT_WHITE;
    private Supplier<Color> color;
    private Supplier<Color> outline;
    private Supplier<Float> width;
    private Function<AxisAlignedBB, AxisAlignedBB> interpolation;
    
    public BlockESPBuilder() {
        this.color = (() -> BlockESPBuilder.LIGHT_WHITE);
        this.outline = (() -> Color.white);
        this.width = (() -> 1.5f);
        this.interpolation = (Function<AxisAlignedBB, AxisAlignedBB>)(bb -> bb);
    }
    
    public BlockESPBuilder withColor(final Setting<Color> colorSetting) {
        return this.withColor(colorSetting::getValue);
    }
    
    public BlockESPBuilder withColor(final Supplier<Color> color) {
        this.color = color;
        return this;
    }
    
    public BlockESPBuilder withOutlineColor(final Setting<Color> outlineColor) {
        return this.withOutlineColor(outlineColor::getValue);
    }
    
    public BlockESPBuilder withOutlineColor(final Supplier<Color> outlineColor) {
        this.outline = outlineColor;
        return this;
    }
    
    public BlockESPBuilder withLineWidth(final Setting<Float> lineWidth) {
        return this.withLineWidth(lineWidth::getValue);
    }
    
    public BlockESPBuilder withLineWidth(final Supplier<Float> lineWidth) {
        this.width = lineWidth;
        return this;
    }
    
    public BlockESPBuilder withInterpolation(final Function<AxisAlignedBB, AxisAlignedBB> interpolation) {
        this.interpolation = interpolation;
        return this;
    }
    
    public IAxisESP build() {
        return bb -> RenderUtil.renderBox(this.interpolation.apply(bb), this.color.get(), this.outline.get(), this.width.get());
    }
    
    static {
        LIGHT_WHITE = new Color(255, 255, 255, 125);
    }
}
