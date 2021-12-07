//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.customfont;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.client.customfont.mode.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import java.util.*;
import me.earth.earthhack.impl.gui.chat.components.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.gui.chat.clickevents.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import me.earth.earthhack.impl.managers.*;
import net.minecraft.util.text.*;
import java.awt.*;
import me.earth.earthhack.api.setting.event.*;

public class FontMod extends Module
{
    protected final Setting<String> fontName;
    protected final Setting<FontStyle> fontStyle;
    protected final Setting<Integer> fontSize;
    protected final Setting<Boolean> antiAlias;
    protected final Setting<Boolean> metrics;
    protected final Setting<Boolean> shadow;
    protected final Setting<Boolean> showFonts;
    protected final List<String> fonts;
    
    public FontMod() {
        super("CustomFont", Category.Client);
        this.fontName = this.register(new StringSetting("Font", "Verdana"));
        this.fontStyle = this.register(new EnumSetting("FontStyle", FontStyle.Plain));
        this.fontSize = this.register(new NumberSetting("FontSize", 18, 15, 25));
        this.antiAlias = this.register(new BooleanSetting("AntiAlias", true));
        this.metrics = this.register(new BooleanSetting("Metrics", true));
        this.shadow = this.register(new BooleanSetting("Shadow", true));
        this.showFonts = this.register(new BooleanSetting("Fonts", false));
        Collections.addAll(this.fonts = new ArrayList<String>(), GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        this.registerObservers();
        this.setData(new FontData(this));
    }
    
    private void registerObservers() {
        for (final Setting<?> setting : this.getSettings()) {
            if (setting.equals(this.showFonts)) {
                setting.addObserver(event -> {
                    event.setCancelled(true);
                    this.sendFonts();
                });
            }
            else {
                setting.addObserver(e -> Scheduler.getInstance().schedule(this::setFont));
            }
        }
    }
    
    public void sendFonts() {
        final SimpleComponent component = new SimpleComponent("Available Fonts: ");
        component.setWrap(true);
        for (int i = 0; i < this.fonts.size(); ++i) {
            final String font = this.fonts.get(i);
            if (font != null) {
                final int finalI = i;
                component.appendSibling(new SuppliedComponent(() -> (font.equals(this.fontName.getValue()) ? "§a" : "§c") + font + ((finalI == this.fonts.size() - 1) ? "" : ", ")).setStyle(new Style().setClickEvent((ClickEvent)new SmartClickEvent(ClickEvent.Action.RUN_COMMAND) {
                    @Override
                    public String getValue() {
                        return Commands.getPrefix() + "CustomFont Font \"" + font + "\"";
                    }
                })));
            }
        }
        Managers.CHAT.sendDeleteComponent((ITextComponent)component, "Fonts", 2000);
    }
    
    private void setFont() {
        Managers.TEXT.setFontRenderer(new Font(this.fontName.getValue(), this.fontStyle.getValue().getFontStyle(), this.fontSize.getValue()), this.antiAlias.getValue(), this.metrics.getValue());
    }
}
