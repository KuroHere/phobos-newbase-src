//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.anvilaura;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.util.math.*;
import java.awt.*;
import me.earth.earthhack.impl.util.render.*;
import me.earth.earthhack.impl.modules.combat.anvilaura.modes.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.util.math.*;
import java.util.*;

final class ListenerRender extends ModuleListener<AnvilAura, Render3DEvent>
{
    public ListenerRender(final AnvilAura module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        final AxisAlignedBB mineBB = ((AnvilAura)this.module).mineBB;
        if (((AnvilAura)this.module).mineESP.getValue() && mineBB != null) {
            final long time = ((AnvilAura)this.module).mineTimer.getTime();
            final double factor = MathUtil.clamp(1.0 - time / 1000.0, 0.0, 1.0);
            if (factor != 0.0) {
                Color b = ((AnvilAura)this.module).box.getValue();
                Color c = ((AnvilAura)this.module).outline.getValue();
                b = new Color(b.getRed(), b.getGreen(), b.getBlue(), (int)(b.getAlpha() * factor));
                c = new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(c.getAlpha() * factor));
                final AxisAlignedBB ib = Interpolation.interpolateAxis(mineBB);
                RenderUtil.renderBox(ib, b, c, ((AnvilAura)this.module).lineWidth.getValue());
            }
        }
        if (((AnvilAura)this.module).mode.getValue() == AnvilMode.Render && (!((AnvilAura)this.module).holdingAnvil.getValue() || InventoryUtil.isHolding(Blocks.ANVIL))) {
            for (final AxisAlignedBB bb : ((AnvilAura)this.module).renderBBs) {
                final AxisAlignedBB ib2 = Interpolation.interpolateAxis(bb);
                RenderUtil.renderBox(ib2, ((AnvilAura)this.module).box.getValue(), ((AnvilAura)this.module).outline.getValue(), ((AnvilAura)this.module).lineWidth.getValue());
            }
        }
    }
}
