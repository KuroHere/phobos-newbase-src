//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.chams;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.render.esp.*;
import me.earth.earthhack.impl.modules.render.chams.mode.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import java.awt.*;

final class ListenerModelPost extends ModuleListener<Chams, ModelRenderEvent.Post>
{
    public ListenerModelPost(final Chams module) {
        super(module, (Class<? super Object>)ModelRenderEvent.Post.class);
    }
    
    public void invoke(final ModelRenderEvent.Post event) {
        if (!ESP.isRendering && ((Chams)this.module).mode.getValue() == ChamsMode.JelloTop) {
            final EntityLivingBase entity = event.getEntity();
            if (((Chams)this.module).isValid((Entity)entity)) {
                final Color color = ((Chams)this.module).getVisibleColor((Entity)event.getEntity());
                GL11.glPushMatrix();
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glLineWidth(1.5f);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2960);
                GL11.glEnable(10754);
                GL11.glDepthMask(false);
                GL11.glDisable(2929);
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
                this.render(event);
                GL11.glDepthMask(true);
                GL11.glEnable(2929);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
                GL11.glPopAttrib();
                GL11.glPopMatrix();
                event.setCancelled(true);
                this.render(event);
            }
        }
    }
    
    private void render(final ModelRenderEvent.Post event) {
        event.getModel().render((Entity)event.getEntity(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale());
    }
}
