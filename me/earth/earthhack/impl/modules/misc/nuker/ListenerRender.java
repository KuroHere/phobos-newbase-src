//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.nuker;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.util.render.*;
import java.awt.*;
import java.util.*;
import net.minecraft.util.math.*;

final class ListenerRender extends ModuleListener<Nuker, Render3DEvent>
{
    public ListenerRender(final Nuker module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (!((Nuker)this.module).render.getValue() || !((Nuker)this.module).nuke.getValue()) {
            return;
        }
        final RayTraceResult result = ListenerRender.mc.objectMouseOver;
        if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            final Set<BlockPos> positions = ((Nuker)this.module).getSelection(result.getBlockPos());
            if (!positions.isEmpty()) {
                GL11.glPushMatrix();
                GL11.glPushAttrib(1048575);
            }
            for (final BlockPos pos : positions) {
                final AxisAlignedBB bb = Interpolation.interpolatePos(pos, 1.0f);
                RenderUtil.startRender();
                RenderUtil.drawBox(bb, ((Nuker)this.module).color.getValue());
                RenderUtil.endRender();
            }
            if (!positions.isEmpty()) {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
        }
    }
}
