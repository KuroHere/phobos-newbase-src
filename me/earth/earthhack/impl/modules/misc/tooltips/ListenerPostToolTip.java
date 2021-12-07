//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tooltips;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.storage.*;
import net.minecraft.client.renderer.*;

final class ListenerPostToolTip extends ModuleListener<ToolTips, ToolTipEvent.Post>
{
    public ListenerPostToolTip(final ToolTips module) {
        super(module, (Class<? super Object>)ToolTipEvent.Post.class);
    }
    
    public void invoke(final ToolTipEvent.Post event) {
        if (((ToolTips)this.module).maps.getValue() && !event.getStack().func_190926_b() && event.getStack().getItem() instanceof ItemMap) {
            final MapData mapData = Items.FILLED_MAP.getMapData(event.getStack(), (World)ListenerPostToolTip.mc.world);
            if (mapData != null) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                RenderHelper.disableStandardItemLighting();
                ListenerPostToolTip.mc.getTextureManager().bindTexture(ToolTips.MAP);
                final Tessellator instance = Tessellator.getInstance();
                final BufferBuilder buffer = instance.getBuffer();
                GlStateManager.translate((float)event.getX(), event.getY() - 67.5f - 5.0f, 0.0f);
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
                buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                buffer.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
                buffer.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
                buffer.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
                buffer.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
                instance.draw();
                ListenerPostToolTip.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, false);
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }
    }
}
