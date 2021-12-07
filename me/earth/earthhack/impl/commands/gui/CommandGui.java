//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.gui;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.client.commands.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.modules.*;

public class CommandGui extends GuiScreen
{
    private static final SettingCache<Boolean, BooleanSetting, Commands> BACK;
    private static final ResourceLocation BLACK_PNG;
    private final CommandChatGui chat;
    private final GuiScreen parent;
    private final int id;
    
    public CommandGui(final GuiScreen parent, final int id) {
        this.chat = new CommandChatGui();
        this.parent = parent;
        this.id = id;
    }
    
    public void setText(final String text) {
        this.chat.setText(text);
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            super.keyTyped(typedChar, keyCode);
            return;
        }
        this.chat.keyTyped(typedChar, keyCode);
    }
    
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.chat.handleMouseInput();
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
        super.setWorldAndResolution(mc, width, height);
        this.buttonList.clear();
        this.buttonList.add(new ExitButton(0, this.width - 24, 5));
        this.chat.setWorldAndResolution(mc, width, height);
        this.chat.func_193975_a(true);
        this.chat.setText(Commands.getPrefix());
    }
    
    public void onGuiClosed() {
        this.chat.onGuiClosed();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution res = new ScaledResolution(this.mc);
        if (CommandGui.BACK.getValue()) {
            this.drawDefaultBackground();
        }
        else {
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            final Tessellator tessellator = Tessellator.getInstance();
            final BufferBuilder bufferbuilder = tessellator.getBuffer();
            this.mc.getTextureManager().bindTexture(CommandGui.BLACK_PNG);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferbuilder.pos(0.0, (double)this.height, 0.0).tex(0.0, (double)(this.height / 32.0f + 0.0f)).color(64, 64, 64, 255).endVertex();
            bufferbuilder.pos((double)this.width, (double)this.height, 0.0).tex((double)(this.width / 32.0f), (double)(this.height / 32.0f + 0.0f)).color(64, 64, 64, 255).endVertex();
            bufferbuilder.pos((double)this.width, 0.0, 0.0).tex((double)(this.width / 32.0f), 0.0).color(64, 64, 64, 255).endVertex();
            bufferbuilder.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(64, 64, 64, 255).endVertex();
            tessellator.draw();
        }
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, (float)(res.getScaledHeight() - 48), 0.0f);
        this.mc.ingameGUI.getChatGUI().drawChat(this.mc.ingameGUI.getUpdateCounter());
        GlStateManager.popMatrix();
        this.chat.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {
            this.parent.confirmClicked(false, this.id);
        }
    }
    
    static {
        BACK = Caches.getSetting(Commands.class, BooleanSetting.class, "BackgroundGui", false);
        BLACK_PNG = new ResourceLocation("earthhack:textures/gui/black.png");
    }
}
