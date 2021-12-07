//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import me.earth.earthhack.impl.core.ducks.gui.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.*;
import org.lwjgl.input.*;
import net.minecraft.util.text.*;

@Mixin({ GuiChat.class })
public abstract class MixinGuiChat extends MixinGuiScreen implements IGuiChat
{
    @Shadow
    protected GuiTextField inputField;
    
    @Override
    public void accessSetText(final String text, final boolean shouldOverwrite) {
        if (shouldOverwrite) {
            this.inputField.setText(text);
        }
        else {
            this.inputField.writeText(text);
        }
    }
    
    @Inject(method = { "drawScreen(IIF)V" }, at = { @At("HEAD") })
    public void drawScreenHook(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo callbackInfo) {
        Managers.COMMANDS.renderCommandGui(this.inputField.getText(), this.inputField.xPosition, this.inputField.yPosition);
    }
    
    @Redirect(method = { "keyTyped" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/TabCompleter;complete()V"))
    protected void completerHook(final TabCompleter completer) {
        if (Managers.COMMANDS.onTabComplete(this.inputField)) {
            completer.complete();
        }
    }
    
    @Inject(method = { "mouseClicked" }, at = { @At("HEAD") }, cancellable = true)
    protected void mouseClickedHook(final int mouseX, final int mouseY, final int mouseButton, final CallbackInfo info) {
        if (mouseButton == 1 || mouseButton == 2) {
            final ITextComponent tc = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
            if (this.handleClick(tc, mouseButton)) {
                info.cancel();
            }
        }
    }
}
