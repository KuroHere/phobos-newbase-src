//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.click;

import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import net.minecraft.util.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.clickgui.*;
import me.earth.earthhack.impl.gui.click.frame.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.impl.gui.click.frame.impl.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.vertex.*;
import me.earth.earthhack.impl.util.render.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import me.earth.earthhack.impl.gui.click.component.*;
import java.util.*;
import me.earth.earthhack.impl.gui.click.component.impl.*;
import java.awt.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.impl.modules.*;

public class Click extends GuiScreen
{
    private static final SettingCache<Boolean, BooleanSetting, Commands> BACK;
    private static final ResourceLocation BLACK_PNG;
    private static final ModuleCache<ClickGui> CLICK_GUI;
    public static DescriptionFrame descriptionFrame;
    private final ArrayList<Frame> frames;
    private boolean oldVal;
    private boolean attached;
    private int emptyTicks;
    
    public Click() {
        this.frames = new ArrayList<Frame>();
        this.oldVal = false;
        this.attached = false;
        this.emptyTicks = 0;
    }
    
    public void init() {
        if (!this.attached) {
            Click.CLICK_GUI.get().descriptionWidth.addObserver(e -> Click.descriptionFrame.setWidth(e.getValue()));
            this.attached = true;
        }
        this.getFrames().clear();
        int x = Click.CLICK_GUI.get().catEars.getValue() ? 14 : 2;
        int y = Click.CLICK_GUI.get().catEars.getValue() ? 14 : 2;
        for (final Category moduleCategory : Category.values()) {
            this.getFrames().add(new CategoryFrame(moduleCategory, (float)x, (float)y, 110.0f, 16.0f));
            if (x + 220 >= new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth()) {
                x = (Click.CLICK_GUI.get().catEars.getValue() ? 14 : 2);
                y += (Click.CLICK_GUI.get().catEars.getValue() ? 32 : 20);
            }
            else {
                x += (Click.CLICK_GUI.get().catEars.getValue() ? 132 : 112);
            }
        }
        Click.descriptionFrame = new DescriptionFrame((float)x, (float)y, Click.CLICK_GUI.get().descriptionWidth.getValue(), 16.0f);
        this.getFrames().add(Click.descriptionFrame);
        this.getFrames().forEach(Frame::init);
        this.oldVal = Click.CLICK_GUI.get().catEars.getValue();
    }
    
    public void onResize(final Minecraft mcIn, final int w, final int h) {
        super.onResize(mcIn, w, h);
        this.init();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (this.mc.world == null) {
            if (Click.BACK.getValue()) {
                this.drawDefaultBackground();
            }
            else {
                GlStateManager.disableLighting();
                GlStateManager.disableFog();
                final Tessellator tessellator = Tessellator.getInstance();
                final BufferBuilder bufferbuilder = tessellator.getBuffer();
                this.mc.getTextureManager().bindTexture(Click.BLACK_PNG);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferbuilder.pos(0.0, (double)this.height, 0.0).tex(0.0, (double)(this.height / 32.0f + 0.0f)).color(64, 64, 64, 255).endVertex();
                bufferbuilder.pos((double)this.width, (double)this.height, 0.0).tex((double)(this.width / 32.0f), (double)(this.height / 32.0f + 0.0f)).color(64, 64, 64, 255).endVertex();
                bufferbuilder.pos((double)this.width, 0.0, 0.0).tex((double)(this.width / 32.0f), 0.0).color(64, 64, 64, 255).endVertex();
                bufferbuilder.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(64, 64, 64, 255).endVertex();
                tessellator.draw();
            }
        }
        if (this.oldVal != Click.CLICK_GUI.get().catEars.getValue()) {
            this.init();
            this.oldVal = Click.CLICK_GUI.get().catEars.getValue();
        }
        if (Click.CLICK_GUI.get().blur.getValue()) {
            final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            Render2DUtil.drawBlurryRect(0.0f, 0.0f, (float)scaledResolution.getScaledWidth(), (float)scaledResolution.getScaledHeight(), Click.CLICK_GUI.get().blurAmount.getValue(), Click.CLICK_GUI.get().blurSize.getValue());
        }
        this.getFrames().forEach(frame -> frame.drawScreen(mouseX, mouseY, partialTicks));
    }
    
    protected void keyTyped(final char character, final int keyCode) throws IOException {
        super.keyTyped(character, keyCode);
        this.getFrames().forEach(frame -> frame.keyTyped(character, keyCode));
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.getFrames().forEach(frame -> frame.mouseClicked(mouseX, mouseY, mouseButton));
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        this.getFrames().forEach(frame -> frame.mouseReleased(mouseX, mouseY, mouseButton));
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        this.getFrames().forEach(frame -> {
            frame.getComponents().iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final Component comp = iterator.next();
                if (comp instanceof ModuleComponent) {
                    final ModuleComponent moduleComponent = (ModuleComponent)comp;
                    moduleComponent.getComponents().iterator();
                    final Iterator iterator2;
                    while (iterator2.hasNext()) {
                        final Component component = iterator2.next();
                        if (component instanceof KeybindComponent) {
                            final KeybindComponent keybindComponent = (KeybindComponent)component;
                            keybindComponent.setBinding(false);
                        }
                        if (component instanceof StringComponent) {
                            final StringComponent stringComponent = (StringComponent)component;
                            stringComponent.setListening(false);
                        }
                    }
                }
            }
        });
    }
    
    public void onGuiOpened() {
        this.getFrames().forEach(frame -> {
            frame.getComponents().iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final Component comp = iterator.next();
                if (comp instanceof ModuleComponent) {
                    final ModuleComponent moduleComponent = (ModuleComponent)comp;
                    moduleComponent.getComponents().iterator();
                    final Iterator iterator2;
                    while (iterator2.hasNext()) {
                        final Component component = iterator2.next();
                        if (component instanceof ColorComponent) {
                            final ColorComponent colorComponent = (ColorComponent)component;
                            final float[] hsb = Color.RGBtoHSB(colorComponent.getColorSetting().getRed(), colorComponent.getColorSetting().getGreen(), colorComponent.getColorSetting().getBlue(), null);
                            colorComponent.setHue(hsb[0]);
                            colorComponent.setSaturation(hsb[1]);
                            colorComponent.setBrightness(hsb[2]);
                            colorComponent.setAlpha(colorComponent.getColorSetting().getAlpha() / 255.0f);
                        }
                    }
                }
            }
        });
    }
    
    public ArrayList<Frame> getFrames() {
        return this.frames;
    }
    
    static {
        BACK = Caches.getSetting(Commands.class, BooleanSetting.class, "BackgroundGui", false);
        BLACK_PNG = new ResourceLocation("earthhack:textures/gui/black.png");
        CLICK_GUI = Caches.getModule(ClickGui.class);
        Click.descriptionFrame = new DescriptionFrame(0.0f, 0.0f, 200.0f, 16.0f);
    }
}
