//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.render.esp;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.impl.modules.render.esp.mode.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import java.awt.*;

final class ListenerModel extends ModuleListener<ESP, ModelRenderEvent.Pre>
{
    public ListenerModel(final ESP module) {
        super(module, (Class<? super Object>)ModelRenderEvent.Pre.class);
    }
    
    public void invoke(final ModelRenderEvent.Pre event) {
        if (((ESP)this.module).mode.getValue() == EspMode.Outline) {
            if (!((ESP)this.module).isValid((Entity)event.getEntity())) {
                return;
            }
            this.render(event);
            final Color clr = ((ESP)this.module).getEntityColor((Entity)event.getEntity());
            ((ESP)this.module).renderOne(((ESP)this.module).lineWidth.getValue());
            this.render(event);
            GlStateManager.glLineWidth((float)((ESP)this.module).lineWidth.getValue());
            ((ESP)this.module).renderTwo();
            this.render(event);
            GlStateManager.glLineWidth((float)((ESP)this.module).lineWidth.getValue());
            ((ESP)this.module).renderThree();
            ((ESP)this.module).renderFour(clr);
            this.render(event);
            GlStateManager.glLineWidth((float)((ESP)this.module).lineWidth.getValue());
            ((ESP)this.module).renderFive();
            event.setCancelled(true);
        }
    }
    
    private void render(final ModelRenderEvent.Pre event) {
        event.getModel().render((Entity)event.getEntity(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScale());
    }
}
