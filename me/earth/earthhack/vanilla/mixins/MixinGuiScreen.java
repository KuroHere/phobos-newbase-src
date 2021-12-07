//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.vanilla.mixins;

import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.core.mixins.gui.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.inventory.*;

@Mixin({ GuiScreen.class })
public abstract class MixinGuiScreen
{
    @Shadow
    public Minecraft mc;
    private int toolTipX;
    private int toolTipY;
    
    @Inject(method = { "drawHoveringText(Ljava/util/List;II)V" }, at = { @At("HEAD") })
    private void drawHoveringTextHookHead(final List<String> textLines, final int x, final int y, final CallbackInfo ci) {
        this.toolTipX = -1;
        this.toolTipY = -1;
    }
    
    @Redirect(method = { "drawHoveringText(Ljava/util/List;II)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    private int drawHoveringTextHook(final FontRenderer fontRenderer, final String text, final float x, final float y, final int color) {
        if (this.toolTipY == -1) {
            this.toolTipY = (int)(y + 1.0f);
            this.toolTipX = (int)x;
        }
        return fontRenderer.drawStringWithShadow(text, x, y, color);
    }
    
    @Inject(method = { "drawHoveringText(Ljava/util/List;II)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;enableLighting()V", shift = At.Shift.BEFORE) })
    private void drawHoveringTextHook(final List<String> textLines, final int x, final int y, final CallbackInfo ci) {
        if (this.toolTipY == -1 || !(this instanceof IGuiContainer)) {
            return;
        }
        final Slot slot = ((IGuiContainer)this).getHoveredSlot();
        if (slot != null) {
            Bus.EVENT_BUS.post(new ToolTipEvent.Post(slot.getStack(), this.toolTipX, this.toolTipY));
        }
    }
}
