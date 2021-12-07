//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.settingspoof;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.network.play.client.*;
import me.earth.earthhack.impl.util.network.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.util.*;
import me.earth.earthhack.api.setting.event.*;

public class SettingSpoof extends Module
{
    protected final Setting<Boolean> spoofLanguage;
    protected final Setting<String> language;
    protected final Setting<Boolean> spoofRender;
    protected final Setting<Integer> renderDist;
    protected final Setting<Boolean> spoofColors;
    protected final Setting<Boolean> chatColors;
    protected final Setting<Boolean> spoofChat;
    protected final Setting<ChatVisibilityTranslator> chat;
    protected final Setting<Boolean> spoofModel;
    protected final Setting<Integer> model;
    protected final Setting<Boolean> spoofHand;
    protected final Setting<HandTranslator> hand;
    protected final Setting<Boolean> send;
    
    public SettingSpoof() {
        super("SettingSpoof", Category.Misc);
        this.spoofLanguage = this.register(new BooleanSetting("Spoof-Language", false));
        this.language = this.register(new StringSetting("Language", "en_us"));
        this.spoofRender = this.register(new BooleanSetting("Spoof-RenderDistance", false));
        this.renderDist = this.register(new NumberSetting("RenderDistance", 32, -1, 128));
        this.spoofColors = this.register(new BooleanSetting("Spoof-ChatColors", false));
        this.chatColors = this.register(new BooleanSetting("ChatColors", true));
        this.spoofChat = this.register(new BooleanSetting("Spoof-Chat", false));
        this.chat = this.register(new EnumSetting("Chat", ChatVisibilityTranslator.Full));
        this.spoofModel = this.register(new BooleanSetting("Spoof-Model", false));
        this.model = this.register(new NumberSetting("Model", 0, 0, 64));
        this.spoofHand = this.register(new BooleanSetting("Spoof-Hand", false));
        this.hand = this.register(new EnumSetting("Hand", HandTranslator.Right));
        this.send = this.register(new BooleanSetting("Send", true));
        this.listeners.add(new ListenerSettings(this));
        this.send.addObserver(e -> {
            e.setValue(true);
            this.sendPacket();
        });
    }
    
    public void sendPacket() {
        if (SettingSpoof.mc.player != null) {
            final String lang = this.getLanguage(SettingSpoof.mc.gameSettings.language);
            final int render = this.getRenderDistance(SettingSpoof.mc.gameSettings.renderDistanceChunks);
            final EntityPlayer.EnumChatVisibility vis = this.getVisibility(SettingSpoof.mc.gameSettings.chatVisibility);
            final boolean chatColors = this.getChatColors(SettingSpoof.mc.gameSettings.chatColours);
            int mask = 0;
            for (final EnumPlayerModelParts enumplayermodelparts : SettingSpoof.mc.gameSettings.getModelParts()) {
                mask |= enumplayermodelparts.getPartMask();
            }
            final int modelParts = this.getModelParts(mask);
            final EnumHandSide handSide = this.getHandSide(SettingSpoof.mc.gameSettings.mainHand);
            NetworkUtil.sendPacketNoEvent((Packet<?>)new CPacketClientSettings(lang, render, vis, chatColors, modelParts, handSide));
        }
    }
    
    public String getLanguage(final String languageIn) {
        return this.spoofLanguage.getValue() ? this.language.getValue() : languageIn;
    }
    
    public int getRenderDistance(final int renderDistIn) {
        return this.spoofRender.getValue() ? this.renderDist.getValue() : renderDistIn;
    }
    
    public EntityPlayer.EnumChatVisibility getVisibility(final EntityPlayer.EnumChatVisibility enumChatVisibilityIn) {
        return this.spoofChat.getValue() ? this.chat.getValue().getVisibility() : enumChatVisibilityIn;
    }
    
    public boolean getChatColors(final boolean chatColorsIn) {
        return this.spoofChat.getValue() ? this.chatColors.getValue() : chatColorsIn;
    }
    
    public int getModelParts(final int modelPartsIn) {
        return this.spoofModel.getValue() ? this.model.getValue() : modelPartsIn;
    }
    
    public EnumHandSide getHandSide(final EnumHandSide enumHandSideIn) {
        return this.spoofHand.getValue() ? this.hand.getValue().getHandSide() : enumHandSideIn;
    }
}
