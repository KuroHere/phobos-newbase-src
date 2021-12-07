//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.render.norender.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.instance.*;
import net.minecraft.client.renderer.texture.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.text.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import java.net.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.util.text.event.*;
import me.earth.earthhack.impl.core.ducks.util.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import me.earth.earthhack.impl.modules.*;

@Mixin({ GuiScreen.class })
public abstract class MixinGuiScreen extends Gui implements GuiYesNoCallback
{
    private static final ModuleCache<NoRender> NO_RENDER;
    private static final ResourceLocation BLACK_LOC;
    @Shadow
    @Final
    private static Logger LOGGER;
    @Shadow
    @Final
    private static Set<String> PROTOCOLS;
    @Shadow
    public Minecraft mc;
    @Shadow
    private URI clickedLinkURI;
    @Shadow
    public int width;
    @Shadow
    public int height;
    @Shadow
    protected FontRenderer fontRendererObj;
    
    @Shadow
    protected abstract void openWebLink(final URI p0);
    
    @Shadow
    protected abstract void setText(final String p0, final boolean p1);
    
    @Shadow
    public abstract void sendChatMessage(final String p0, final boolean p1);
    
    @Shadow
    public static boolean isShiftKeyDown() {
        throw new IllegalStateException("isShiftKeyDown was not shadowed!");
    }
    
    @Inject(method = { "renderToolTip" }, at = { @At("HEAD") }, cancellable = true)
    public void renderToolTipHook(final ItemStack stack, final int x, final int y, final CallbackInfo info) {
        final ToolTipEvent event = new ToolTipEvent(stack, x, y);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "drawBackground" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
    private void bindTextureHook(final TextureManager textureManager, final ResourceLocation resource) {
        if (MixinGuiScreen.NO_RENDER.get().isEnabled() && MixinGuiScreen.NO_RENDER.get().defaultBackGround.getValue()) {
            textureManager.bindTexture(MixinGuiScreen.BLACK_LOC);
            return;
        }
        textureManager.bindTexture(MixinGuiScreen.OPTIONS_BACKGROUND);
    }
    
    @Inject(method = { "handleComponentClick" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;sendChatMessage(Ljava/lang/String;Z)V", shift = At.Shift.BEFORE) }, cancellable = true)
    public void handleComponentClick(final ITextComponent component, final CallbackInfoReturnable<Boolean> info) {
        final IClickEvent event = (IClickEvent)component.getStyle().getClickEvent();
        if (event != null && event.getRunnable() != null) {
            event.getRunnable().run();
            info.setReturnValue(true);
        }
    }
    
    protected boolean handleClick(final ITextComponent component, final int button) {
        if (component == null) {
            return false;
        }
        final IStyle style = (IStyle)component.getStyle();
        ClickEvent event = null;
        if (button == 1) {
            event = style.getRightClickEvent();
        }
        else if (button == 2) {
            event = style.getMiddleClickEvent();
        }
        if (isShiftKeyDown()) {
            String insertion = null;
            if (button == 1) {
                insertion = style.getRightInsertion();
            }
            else if (button == 2) {
                insertion = style.getMiddleInsertion();
            }
            if (insertion != null) {
                this.setText(insertion, false);
            }
        }
        else if (event != null) {
            if (event.getAction() == ClickEvent.Action.OPEN_URL) {
                if (!this.mc.gameSettings.chatLinks) {
                    return false;
                }
                try {
                    final URI uri = new URI(event.getValue());
                    final String s = uri.getScheme();
                    if (s == null) {
                        throw new URISyntaxException(event.getValue(), "Missing protocol");
                    }
                    if (!MixinGuiScreen.PROTOCOLS.contains(s.toLowerCase(Locale.ROOT))) {
                        throw new URISyntaxException(event.getValue(), "Unsupported protocol: " + s.toLowerCase(Locale.ROOT));
                    }
                    if (this.mc.gameSettings.chatLinksPrompt) {
                        this.clickedLinkURI = uri;
                        this.mc.displayGuiScreen((GuiScreen)new GuiConfirmOpenLink((GuiYesNoCallback)this, event.getValue(), 31102009, false));
                    }
                    else {
                        this.openWebLink(uri);
                    }
                }
                catch (URISyntaxException urisyntaxexception) {
                    MixinGuiScreen.LOGGER.error("Can't open url for {}", (Object)event, (Object)urisyntaxexception);
                }
            }
            else if (event.getAction() == ClickEvent.Action.OPEN_FILE) {
                final URI uri2 = new File(event.getValue()).toURI();
                this.openWebLink(uri2);
            }
            else if (event.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
                this.setText(event.getValue(), true);
            }
            else if (event.getAction() == ClickEvent.Action.RUN_COMMAND) {
                if (((IClickEvent)event).getRunnable() != null) {
                    ((IClickEvent)event).getRunnable().run();
                    return true;
                }
                this.sendChatMessage(event.getValue(), false);
            }
            else {
                MixinGuiScreen.LOGGER.error("Don't know how to handle {}", (Object)event);
            }
            return true;
        }
        return false;
    }
    
    @Inject(method = { "handleComponentHover" }, at = { @At("HEAD") }, cancellable = true)
    private void handleComponentHoverHook(final ITextComponent component, final int x, final int y, final CallbackInfo info) {
        if (component != null && component.getStyle().getHoverEvent() != null) {
            final HoverEvent event = component.getStyle().getHoverEvent();
            if (event.getAction() == HoverEvent.Action.SHOW_TEXT && !((IHoverEvent)event).hasOffset()) {
                this.drawHoveringTextShadow(this.fontRendererObj.listFormattedStringToWidth(event.getValue().getFormattedText(), Math.max(this.width / 2, 200)), x, y, this.width, this.height, -1, this.fontRendererObj);
                GlStateManager.disableLighting();
                info.cancel();
            }
        }
    }
    
    private void drawHoveringTextShadow(List<String> textLines, final int mouseX, final int mouseY, final int screenWidth, final int screenHeight, final int maxTextWidth, final FontRenderer font) {
        if (!textLines.isEmpty()) {
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int tttW = 0;
            for (final String textLine : textLines) {
                final int textLineWidth = font.getStringWidth(textLine);
                if (textLineWidth > tttW) {
                    tttW = textLineWidth;
                }
            }
            boolean needsWrap = false;
            int ttX = mouseX + 12;
            if (ttX + tttW + 4 > screenWidth) {
                ttX = mouseX - 16 - tttW;
                if (ttX < 4) {
                    if (mouseX > screenWidth / 2) {
                        tttW = mouseX - 12 - 8;
                    }
                    else {
                        tttW = screenWidth - 16 - mouseX;
                    }
                    needsWrap = true;
                }
            }
            if (maxTextWidth > 0 && tttW > maxTextWidth) {
                tttW = maxTextWidth;
                needsWrap = true;
            }
            if (needsWrap) {
                int wrappedTooltipWidth = 0;
                final List<String> wrappedTextLines = new ArrayList<String>();
                for (final String textLine2 : textLines) {
                    final List<String> wrappedLine = font.listFormattedStringToWidth(textLine2, tttW);
                    for (final String line : wrappedLine) {
                        final int lineWidth = font.getStringWidth(line);
                        if (lineWidth > wrappedTooltipWidth) {
                            wrappedTooltipWidth = lineWidth;
                        }
                        wrappedTextLines.add(line);
                    }
                }
                tttW = wrappedTooltipWidth;
                textLines = wrappedTextLines;
                if (mouseX > screenWidth / 2) {
                    ttX = mouseX - 16 - tttW;
                }
                else {
                    ttX = mouseX + 12;
                }
            }
            int ttY = mouseY - 12;
            int ttH = 8;
            if (textLines.size() > 1) {
                ttH += (textLines.size() - 1) * 10;
            }
            if (ttY < 4) {
                ttY = 4;
            }
            else if (ttY + ttH + 4 > screenHeight) {
                ttY = screenHeight - ttH - 4;
            }
            final int zLevel = 300;
            final int bgc = -267386864;
            final int bgcs = 1347420415;
            final int bgce = (bgcs & 0xFEFEFE) >> 1 | (bgcs & 0xFF000000);
            drawGradientRectForge(300, ttX - 3, ttY - 4, ttX + tttW + 3, ttY - 3, bgc, bgc);
            drawGradientRectForge(300, ttX - 3, ttY + ttH + 3, ttX + tttW + 3, ttY + ttH + 4, bgc, bgc);
            drawGradientRectForge(300, ttX - 3, ttY - 3, ttX + tttW + 3, ttY + ttH + 3, bgc, bgc);
            drawGradientRectForge(300, ttX - 4, ttY - 3, ttX - 3, ttY + ttH + 3, bgc, bgc);
            drawGradientRectForge(300, ttX + tttW + 3, ttY - 3, ttX + tttW + 4, ttY + ttH + 3, bgc, bgc);
            drawGradientRectForge(300, ttX - 3, ttY - 3 + 1, ttX - 3 + 1, ttY + ttH + 3 - 1, bgcs, bgce);
            drawGradientRectForge(300, ttX + tttW + 2, ttY - 3 + 1, ttX + tttW + 3, ttY + ttH + 3 - 1, bgcs, bgce);
            drawGradientRectForge(300, ttX - 3, ttY - 3, ttX + tttW + 3, ttY - 3 + 1, bgcs, bgcs);
            drawGradientRectForge(300, ttX - 3, ttY + ttH + 2, ttX + tttW + 3, ttY + ttH + 3, bgce, bgce);
            for (final String line2 : textLines) {
                font.drawStringWithShadow(line2, (float)ttX, (float)ttY, -1);
                ttY += 10;
            }
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableRescaleNormal();
        }
    }
    
    private static void drawGradientRectForge(final int zLevel, final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        final float startAlpha = (startColor >> 24 & 0xFF) / 255.0f;
        final float startRed = (startColor >> 16 & 0xFF) / 255.0f;
        final float startGreen = (startColor >> 8 & 0xFF) / 255.0f;
        final float startBlue = (startColor & 0xFF) / 255.0f;
        final float endAlpha = (endColor >> 24 & 0xFF) / 255.0f;
        final float endRed = (endColor >> 16 & 0xFF) / 255.0f;
        final float endGreen = (endColor >> 8 & 0xFF) / 255.0f;
        final float endBlue = (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos((double)right, (double)top, (double)zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos((double)left, (double)top, (double)zLevel).color(startRed, startGreen, startBlue, startAlpha).endVertex();
        buffer.pos((double)left, (double)bottom, (double)zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        buffer.pos((double)right, (double)bottom, (double)zLevel).color(endRed, endGreen, endBlue, endAlpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    static {
        NO_RENDER = Caches.getModule(NoRender.class);
        BLACK_LOC = new ResourceLocation("earthhack:textures/gui/black.png");
    }
}
