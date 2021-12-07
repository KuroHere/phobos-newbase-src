//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import me.earth.earthhack.impl.modules.misc.chat.*;
import me.earth.earthhack.impl.modules.client.media.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.gen.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import me.earth.earthhack.impl.core.ducks.gui.*;
import me.earth.earthhack.impl.util.animation.*;
import java.util.function.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.earth.earthhack.impl.commands.gui.*;
import me.earth.earthhack.impl.event.events.render.*;
import me.earth.earthhack.api.event.bus.instance.*;
import net.minecraft.client.gui.*;
import java.util.*;
import me.earth.earthhack.impl.core.ducks.util.*;
import me.earth.earthhack.impl.gui.chat.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.impl.modules.*;
import net.minecraft.util.text.*;

@Mixin({ GuiNewChat.class })
public abstract class MixinGuiNewChat implements IGuiNewChat
{
    private static final ModuleCache<Chat> CHAT;
    private static final ModuleCache<Media> MEDIA;
    private static final SettingCache<Boolean, BooleanSetting, Chat> TIME_STAMPS;
    private static final SettingCache<Boolean, BooleanSetting, Chat> CLEAN;
    private static final SettingCache<Boolean, BooleanSetting, Chat> INFINITE;
    private static final ITextComponent INSTEAD;
    private ChatLine currentLine;
    private ChatLine currentHover;
    private int deleteChatLineID;
    @Shadow
    @Final
    private List<ChatLine> chatLines;
    @Shadow
    @Final
    private List<ChatLine> drawnChatLines;
    @Shadow
    @Final
    private List<String> sentMessages;
    @Shadow
    private int scrollPos;
    @Shadow
    private boolean isScrolled;
    @Final
    @Shadow
    private Minecraft mc;
    private boolean first;
    
    public MixinGuiNewChat() {
        this.currentLine = null;
        this.currentHover = null;
        this.first = true;
    }
    
    @Shadow
    protected abstract void setChatLine(final ITextComponent p0, final int p1, final int p2, final boolean p3);
    
    @Shadow
    public abstract void deleteChatLine(final int p0);
    
    @Shadow
    public abstract int getChatWidth();
    
    @Shadow
    public abstract boolean getChatOpen();
    
    @Shadow
    public abstract float getChatScale();
    
    @Accessor("scrollPos")
    @Override
    public abstract int getScrollPos();
    
    @Accessor("scrollPos")
    @Override
    public abstract void setScrollPos(final int p0);
    
    @Accessor("isScrolled")
    @Override
    public abstract boolean getScrolled();
    
    @Accessor("isScrolled")
    @Override
    public abstract void setScrolled(final boolean p0);
    
    @Override
    public void invokeSetChatLine(final ITextComponent chatComponent, final int chatLineId, final int updateCounter, final boolean displayOnly) {
        this.setChatLine(chatComponent, chatLineId, updateCounter, displayOnly);
    }
    
    @Override
    public void invokeClearChat(final boolean sent) {
        this.drawnChatLines.clear();
        this.chatLines.clear();
        if (sent) {
            this.sentMessages.clear();
        }
    }
    
    @Override
    public boolean replace(final ITextComponent component, final int id, final boolean wrap, final boolean returnFirst) {
        boolean set = this.setLine(component, id, this.chatLines, wrap, returnFirst);
        set = (this.setLine(component, id, this.drawnChatLines, wrap, returnFirst) || set);
        return set;
    }
    
    boolean setLine(final ITextComponent component, final int id, final List<ChatLine> list, final boolean wrap, final boolean returnFirst) {
        Stack<ITextComponent> wrapped = null;
        if (wrap) {
            final int max = MathHelper.floor(this.getChatWidth() / this.getChatScale());
            wrapped = new ConvenientStack<ITextComponent>(GuiUtilRenderComponents.splitText(component, max, this.mc.fontRendererObj, false, false));
        }
        int last = 0;
        final List<Integer> toRemove = new ArrayList<Integer>();
        int i;
        for (i = 0; i < list.size(); ++i) {
            final ChatLine line = list.get(i);
            if (line.getChatLineID() == id) {
                if (wrap) {
                    final ITextComponent itc = wrapped.pop();
                    if (itc != null) {
                        ((IChatLine)line).setComponent(itc);
                        last = i + 1;
                    }
                    else {
                        toRemove.add(i);
                    }
                }
                else {
                    ((IChatLine)line).setComponent(component);
                    if (returnFirst) {
                        return true;
                    }
                }
            }
        }
        if (toRemove.isEmpty()) {
            final boolean infinite = MixinGuiNewChat.INFINITE.getValue();
            while (infinite && wrap && !wrapped.empty()) {
                final ITextComponent itc2 = wrapped.pop();
                if (itc2 != null) {
                    final ChatLine newLine = new ChatLine(this.mc.ingameGUI.getUpdateCounter(), itc2, id);
                    MixinGuiNewChat.CHAT.get().animationMap.put(newLine, new TimeAnimation(MixinGuiNewChat.CHAT.get().time.getValue(), -Minecraft.getMinecraft().fontRendererObj.getStringWidth(newLine.getChatComponent().getFormattedText()), 0.0, false, AnimationMode.LINEAR));
                    list.add(last, newLine);
                    ++last;
                }
            }
        }
        else {
            toRemove.forEach(i -> {
                final ChatLine chatLine = list.set(i, null);
                return;
            });
            list.removeIf(Objects::isNull);
        }
        return false;
    }
    
    @Inject(method = { "drawChat" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatLine;getUpdatedCounter()I") }, locals = LocalCapture.CAPTURE_FAILSOFT)
    public void drawChatHook(final int updateCounter, final CallbackInfo ci, final int i, final int j, final float f, final boolean flag, final float f1, final int k, final int l, final int il, final ChatLine chatLine) {
        this.currentLine = chatLine;
    }
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    public int drawStringWithShadowHook(final FontRenderer renderer, final String text, final float x, final float y, final int color) {
        TimeAnimation animation = null;
        if (this.currentLine != null) {
            if (MixinGuiNewChat.CHAT.get().animationMap.containsKey(this.currentLine)) {
                animation = MixinGuiNewChat.CHAT.get().animationMap.get(this.currentLine);
            }
            if (animation != null) {
                animation.add(this.mc.getRenderPartialTicks());
            }
        }
        final String s = MixinGuiNewChat.MEDIA.returnIfPresent(m -> m.convert(text), text);
        if (MixinGuiNewChat.CHAT.isEnabled() && MixinGuiNewChat.TIME_STAMPS.getValue() && this.currentLine != null) {
            final String t = ((IChatLine)this.currentLine).getTimeStamp() + s;
            return renderer.drawStringWithShadow(t, (float)(x + ((animation != null && MixinGuiNewChat.CHAT.get().animated.getValue()) ? animation.getCurrent() : 0.0)), y, color);
        }
        return renderer.drawStringWithShadow(s, (float)(x + ((animation != null && MixinGuiNewChat.CHAT.get().animated.getValue()) ? animation.getCurrent() : 0.0)), y, color);
    }
    
    @Inject(method = { "getChatOpen" }, at = { @At("HEAD") }, cancellable = true)
    public void getChatOpenHook(final CallbackInfoReturnable<Boolean> info) {
        if (this.mc.currentScreen instanceof CommandGui) {
            info.setReturnValue(true);
        }
    }
    
    @Inject(method = { "clearChatMessages" }, at = { @At("HEAD") }, cancellable = true)
    public void clearChatMessagesHook(final boolean sent, final CallbackInfo info) {
        final ChatEvent.Clear event = new ChatEvent.Clear(this, sent);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V", ordinal = 0))
    private void drawRectHook(final int left, final int top, final int right, final int bottom, final int c) {
        if (MixinGuiNewChat.CHAT.isEnabled() && MixinGuiNewChat.CLEAN.getValue()) {
            return;
        }
        Gui.drawRect(left, top, right + ((MixinGuiNewChat.CHAT.isEnabled() && MixinGuiNewChat.TIME_STAMPS.getValue()) ? 40 : 0), bottom, c);
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 0, remap = false))
    public int drawnChatLinesSize(final List<ChatLine> list) {
        return this.getChatSize(list);
    }
    
    @Inject(method = { "getChatHeight" }, at = { @At("HEAD") }, cancellable = true)
    private void getChatHeightHook(final CallbackInfoReturnable<Integer> info) {
        if (this.mc.currentScreen instanceof CommandGui) {
            info.setReturnValue(500);
        }
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 2, remap = false))
    public int chatLinesSize(final List<ChatLine> list) {
        return this.getChatSize(list);
    }
    
    @Inject(method = { "printChatMessageWithOptionalDeletion" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;setChatLine(Lnet/minecraft/util/text/ITextComponent;IIZ)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void loggerHook(final ITextComponent chatComponent, final int chatLineId, final CallbackInfo ci) {
        final ChatEvent.Log event = new ChatEvent.Log(this);
        Bus.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "printChatMessageWithOptionalDeletion" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;setChatLine(Lnet/minecraft/util/text/ITextComponent;IIZ)V"))
    public void setChatLineHook(final GuiNewChat gui, final ITextComponent chatComponent, final int chatLineId, final int updateCounter, final boolean displayOnly) {
        final ChatEvent.Send event = new ChatEvent.Send(this, chatComponent, chatLineId, updateCounter, displayOnly);
        Bus.EVENT_BUS.post(event);
        if (!event.isCancelled()) {
            this.setChatLine(event.getChatComponent(), event.getChatLineId(), event.getUpdateCounter(), event.isDisplayOnly());
        }
    }
    
    @Inject(method = { "getChatComponent" }, at = { @At("HEAD") })
    public void getChatComponentHook(final int mouseX, final int mouseY, final CallbackInfoReturnable<ITextComponent> info) {
        this.first = true;
    }
    
    @Redirect(method = { "getChatComponent" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatLine;getChatComponent()Lnet/minecraft/util/text/ITextComponent;"))
    private ITextComponent getHook(final ChatLine chatLine) {
        this.currentHover = chatLine;
        return chatLine.getChatComponent();
    }
    
    @Redirect(method = { "getChatComponent" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;getChatWidth()I"))
    private int getChatComponentChatWidthHook(final GuiNewChat gnc) {
        return (MixinGuiNewChat.CHAT.isEnabled() && MixinGuiNewChat.TIME_STAMPS.getValue()) ? (gnc.getChatWidth() + 40) : gnc.getChatWidth();
    }
    
    @Redirect(method = { "getChatComponent" }, at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;", remap = false))
    private Object getChatComponentInstanceOfHook(final Iterator<ITextComponent> iterator) {
        final ITextComponent component = iterator.next();
        if (component instanceof IHoverable && !((IHoverable)component).canBeHovered()) {
            return MixinGuiNewChat.INSTEAD;
        }
        return component;
    }
    
    @Redirect(method = { "getChatComponent" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;getStringWidth(Ljava/lang/String;)I"))
    public int getStringWidthHook(final FontRenderer renderer, final String text) {
        final String s = MixinGuiNewChat.MEDIA.returnIfPresent(m -> m.convert(text), text);
        if (MixinGuiNewChat.CHAT.isEnabled() && MixinGuiNewChat.TIME_STAMPS.getValue() && this.first && this.currentHover != null) {
            final String t = ((IChatLine)this.currentHover).getTimeStamp() + s;
            this.first = false;
            return renderer.getStringWidth(t);
        }
        return renderer.getStringWidth(text);
    }
    
    @Redirect(method = { "setChatLine" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;scroll(I)V"))
    public void scrollHook(final GuiNewChat gui, final int amount) {
    }
    
    @Inject(method = { "setChatLine" }, at = { @At("HEAD") }, cancellable = true)
    public void setChatLineHookHead(final ITextComponent chatComponent, int id, final int updateCounter, final boolean displayOnly, final CallbackInfo info) {
        if (chatComponent instanceof AbstractTextComponent) {
            if (id != 0) {
                this.deleteChatLine(id);
            }
            else {
                id = -1;
            }
            final AbstractTextComponent component = (AbstractTextComponent)chatComponent;
            if (component.isWrapping()) {
                final int max = MathHelper.floor(this.getChatWidth() / this.getChatScale());
                final List<ITextComponent> list = GuiUtilRenderComponents.splitText((ITextComponent)component, max, this.mc.fontRendererObj, false, false);
                final boolean chatOpen = this.getChatOpen();
                final ChatLine[] references = new ChatLine[list.size()];
                for (int i = 0; i < list.size(); ++i) {
                    final ITextComponent itc = list.get(i);
                    if (chatOpen && this.scrollPos > 0) {
                        this.isScrolled = true;
                    }
                    final ChatLine line = new ChatLine(updateCounter, itc, id);
                    MixinGuiNewChat.CHAT.get().animationMap.put(line, new TimeAnimation(MixinGuiNewChat.CHAT.get().time.getValue(), -Minecraft.getMinecraft().fontRendererObj.getStringWidth(line.getChatComponent().getFormattedText()), 0.0, false, AnimationMode.LINEAR));
                    this.drawnChatLines.add(0, line);
                    references[i] = line;
                }
                Managers.WRAP.registerComponent(component, references);
            }
            else {
                final ChatLine newLine = new ChatLine(updateCounter, chatComponent, id);
                MixinGuiNewChat.CHAT.get().animationMap.put(newLine, new TimeAnimation(MixinGuiNewChat.CHAT.get().time.getValue(), -Minecraft.getMinecraft().fontRendererObj.getStringWidth(newLine.getChatComponent().getFormattedText()), 0.0, false, AnimationMode.LINEAR));
                this.drawnChatLines.add(0, newLine);
            }
            info.cancel();
        }
    }
    
    private int getChatSize(final List<ChatLine> list) {
        return ((MixinGuiNewChat.CHAT.isEnabled() && MixinGuiNewChat.INFINITE.getValue()) || this.mc.currentScreen instanceof CommandGui) ? -2147483647 : list.size();
    }
    
    @Inject(method = { "deleteChatLine" }, at = { @At("HEAD") })
    private void deleteChatLineHook(final int id, final CallbackInfo info) {
        this.deleteChatLineID = id;
    }
    
    @Redirect(method = { "deleteChatLine" }, at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;", remap = false))
    private Iterator<ChatLine> iteratorHook(final List<ChatLine> list) {
        if (list == null) {
            return null;
        }
        return list.iterator();
    }
    
    @Redirect(method = { "deleteChatLine" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatLine;getChatLineID()I"))
    private int getChatLineIDHook(final ChatLine chatLine) {
        if (chatLine == null) {
            return this.deleteChatLineID + 1;
        }
        return chatLine.getChatLineID();
    }
    
    @Redirect(method = { "deleteChatLine" }, at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z", remap = false))
    private boolean hasNextHook(final Iterator<ChatLine> iterator) {
        return iterator != null && iterator.hasNext();
    }
    
    static {
        CHAT = Caches.getModule(Chat.class);
        MEDIA = Caches.getModule(Media.class);
        TIME_STAMPS = Caches.getSetting(Chat.class, BooleanSetting.class, "TimeStamps", false);
        CLEAN = Caches.getSetting(Chat.class, BooleanSetting.class, "Clean", false);
        INFINITE = Caches.getSetting(Chat.class, BooleanSetting.class, "Infinite", false);
        INSTEAD = (ITextComponent)new TextComponentKeybind("");
    }
}
