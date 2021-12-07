// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.forge.mixins.minecraftforge;

import org.spongepowered.asm.mixin.*;
import net.minecraftforge.fml.client.config.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.render.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { GuiUtils.class }, remap = false)
public abstract class MixinGuiUtils
{
    @Redirect(method = { "drawHoveringText(Lnet/minecraft/item/ItemStack;Ljava/util/List;IIIIILnet/minecraft/client/gui/FontRenderer;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/eventhandler/EventBus;post(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z", ordinal = 3))
    private static boolean postTextHook(final EventBus eventBus, final Event event) {
        final RenderTooltipEvent.PostText e = (RenderTooltipEvent.PostText)event;
        Bus.EVENT_BUS.post(new ToolTipEvent.Post(e.getStack(), e.getX(), e.getY()));
        return eventBus.post(event);
    }
}
