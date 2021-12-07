//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.clickgui;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.gui.click.*;

public class ClickGui extends Module
{
    public final Setting<Color> color;
    public final Setting<Boolean> catEars;
    public final Setting<Boolean> blur;
    public final Setting<Integer> blurAmount;
    public final Setting<Integer> blurSize;
    public final Setting<String> open;
    public final Setting<String> close;
    public final Setting<Boolean> white;
    public final Setting<Boolean> description;
    public final Setting<Integer> descriptionWidth;
    protected boolean fromEvent;
    protected GuiScreen screen;
    
    public ClickGui() {
        super("ClickGui", Category.Client);
        this.color = this.register(new ColorSetting("Color", new Color(0, 80, 255)));
        this.catEars = this.register(new BooleanSetting("CatEars", false));
        this.blur = this.register(new BooleanSetting("Blur", false));
        this.blurAmount = this.register(new NumberSetting("Blur-Amount", 8, 1, 20));
        this.blurSize = this.register(new NumberSetting("Blur-Size", 3, 1, 20));
        this.open = this.register(new StringSetting("Open", "+"));
        this.close = this.register(new StringSetting("Close", "-"));
        this.white = this.register(new BooleanSetting("White-Settings", true));
        this.description = this.register(new BooleanSetting("Description", true));
        this.descriptionWidth = this.register(new NumberSetting("Description-Width", 240, 100, 1000));
        this.listeners.add(new ListenerScreen(this));
        this.setData(new SimpleData(this, "Beautiful ClickGui by OHare"));
    }
    
    @Override
    protected void onEnable() {
        this.screen = ClickGui.mc.currentScreen;
        final Click gui = new Click();
        gui.init();
        gui.onGuiOpened();
        ClickGui.mc.displayGuiScreen((GuiScreen)gui);
    }
    
    @Override
    protected void onDisable() {
        if (!this.fromEvent) {
            ClickGui.mc.displayGuiScreen(this.screen);
        }
        this.fromEvent = false;
    }
}
