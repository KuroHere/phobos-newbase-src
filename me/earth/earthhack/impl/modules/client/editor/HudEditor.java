//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.editor;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.gui.hud.*;
import me.earth.earthhack.api.module.util.*;
import net.minecraft.client.gui.*;

public class HudEditor extends Module
{
    private final HudEditorGui GUI;
    
    public HudEditor() {
        super("HudEditor", Category.Client);
        this.GUI = new HudEditorGui();
    }
    
    public void onEnable() {
        if (HudEditor.mc.currentScreen == null) {
            HudEditor.mc.displayGuiScreen((GuiScreen)this.GUI);
        }
    }
}
