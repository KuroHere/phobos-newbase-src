// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.helpers.render;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import java.awt.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.util.math.*;

public class BlockESPModule extends ColorModule
{
    public final ColorSetting outline;
    public final Setting<Float> lineWidth;
    public final Setting<Float> height;
    protected IAxisESP esp;
    
    public BlockESPModule(final String name, final Category category) {
        super(name, category);
        this.outline = this.register(new ColorSetting("Outline", new Color(255, 255, 255, 240)));
        this.lineWidth = this.register(new NumberSetting("LineWidth", 1.5f, 0.0f, 10.0f));
        this.height = this.register(new NumberSetting("ESP-Height", 1.0f, -1.0f, 1.0f));
        this.esp = new BlockESPBuilder().withColor(this.color).withOutlineColor(this.outline).withLineWidth(this.lineWidth).build();
        super.color.setValue(new Color(255, 255, 255, 76));
    }
    
    public void renderPos(final BlockPos pos) {
        this.esp.render(Interpolation.interpolatePos(pos, this.height.getValue()));
    }
    
    public void renderAxis(final AxisAlignedBB bb) {
        this.esp.render(Interpolation.interpolateAxis(bb));
    }
    
    public void renderInterpAxis(final AxisAlignedBB bb) {
        this.esp.render(bb);
    }
}
