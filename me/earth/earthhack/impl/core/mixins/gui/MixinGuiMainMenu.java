//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import org.spongepowered.asm.mixin.*;
import me.earth.earthhack.impl.commands.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.gui.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiMainMenu.class })
public abstract class MixinGuiMainMenu extends GuiScreen
{
    private EarthhackButton earthhackButton;
    
    @Inject(method = { "initGui" }, at = { @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2, shift = At.Shift.AFTER, remap = false) })
    private void buttonHook(final CallbackInfo info) {
        int x = 2;
        int y = 0;
        int w = 2;
        for (final GuiButton button : this.buttonList) {
            if (button.id == 4) {
                x = button.xPosition;
                y = button.yPosition;
                w = button.width;
                break;
            }
        }
        this.earthhackButton = (EarthhackButton)this.addButton((GuiButton)new EarthhackButton(2500, x + w + 4, y));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") }, cancellable = true)
    private void actionPerformedHook(final GuiButton button, final CallbackInfo info) {
        if (button.id == this.earthhackButton.id) {
            this.earthhackButton.onClick(this, this.earthhackButton.id);
            info.cancel();
        }
    }
    
    @Inject(method = { "confirmClicked" }, at = { @At("HEAD") }, cancellable = true)
    public void confirmClickedHook(final boolean result, final int id, final CallbackInfo info) {
        if (id == this.earthhackButton.id) {
            this.mc.displayGuiScreen((GuiScreen)this);
            info.cancel();
        }
    }
}
