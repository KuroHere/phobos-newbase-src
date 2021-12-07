// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine.mode;

import me.earth.earthhack.impl.modules.player.speedmine.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.render.*;
import java.awt.*;

public enum ESPMode
{
    None {
        @Override
        public void drawEsp(final Speedmine module, final AxisAlignedBB bb, final float damage) {
        }
    }, 
    Outline {
        @Override
        public void drawEsp(final Speedmine module, final AxisAlignedBB bb, final float damage) {
            RenderUtil.startRender();
            final float red = 255.0f - 255.0f * damage;
            final float green = 255.0f * damage;
            RenderUtil.drawOutline(bb, 1.5f, new Color((int)red, (int)green, 0, module.getOutlineAlpha()));
            RenderUtil.endRender();
        }
    }, 
    Block {
        @Override
        public void drawEsp(final Speedmine module, final AxisAlignedBB bb, final float damage) {
            RenderUtil.startRender();
            final float red = 255.0f - 255.0f * damage;
            final float green = 255.0f * damage;
            RenderUtil.drawBox(bb, new Color((int)red, (int)green, 0, module.getBlockAlpha()));
            RenderUtil.endRender();
        }
    }, 
    Box {
        @Override
        public void drawEsp(final Speedmine module, final AxisAlignedBB bb, final float damage) {
            ESPMode$4.Outline.drawEsp(module, bb, damage);
            ESPMode$4.Block.drawEsp(module, bb, damage);
        }
    };
    
    public abstract void drawEsp(final Speedmine p0, final AxisAlignedBB p1, final float p2);
}
