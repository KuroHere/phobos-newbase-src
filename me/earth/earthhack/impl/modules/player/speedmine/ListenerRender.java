//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.speedmine;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.util.minecraft.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.modules.player.speedmine.mode.*;
import org.lwjgl.opengl.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.util.math.*;

final class ListenerRender extends ModuleListener<Speedmine, Render3DEvent>
{
    public ListenerRender(final Speedmine module) {
        super(module, (Class<? super Object>)Render3DEvent.class);
    }
    
    public void invoke(final Render3DEvent event) {
        if (!PlayerUtil.isCreative((EntityPlayer)ListenerRender.mc.player) && ((Speedmine)this.module).esp.getValue() != ESPMode.None && ((Speedmine)this.module).bb != null) {
            GL11.glPushMatrix();
            GL11.glPushAttrib(1048575);
            final float max = Math.min(((Speedmine)this.module).maxDamage, 1.0f);
            final AxisAlignedBB bb = Interpolation.interpolateAxis(((Speedmine)this.module).bb);
            ((Speedmine)this.module).esp.getValue().drawEsp((Speedmine)this.module, bb, max);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
    }
}
