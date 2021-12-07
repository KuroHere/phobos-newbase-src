//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.core.mixins.gui;

import me.earth.earthhack.impl.core.ducks.gui.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.modules.misc.chat.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.settings.*;
import net.minecraft.client.*;
import net.minecraft.util.text.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import java.util.*;
import me.earth.earthhack.impl.util.animation.*;
import org.spongepowered.asm.mixin.injection.*;
import me.earth.earthhack.impl.modules.*;
import java.text.*;

@Mixin({ ChatLine.class })
public abstract class MixinChatLine implements IChatLine
{
    private static final ModuleCache<Chat> CHAT;
    private static final SettingCache<Boolean, BooleanSetting, Chat> TIME_STAMPS;
    private static final DateFormat FORMAT;
    private static final Minecraft MC;
    private String timeStamp;
    
    @Override
    public String getTimeStamp() {
        return this.timeStamp;
    }
    
    @Accessor("lineString")
    @Override
    public abstract void setComponent(final ITextComponent p0);
    
    @Inject(method = { "<init>" }, at = { @At("RETURN") })
    public void constructorHook(final int updateCounterCreatedIn, final ITextComponent lineStringIn, final int chatLineIDIn, final CallbackInfo ci) {
        this.timeStamp = "§+<" + MixinChatLine.FORMAT.format(new Date()) + "> " + "§r";
        String t = lineStringIn.getFormattedText();
        if (MixinChatLine.CHAT.isEnabled() && MixinChatLine.TIME_STAMPS.getValue()) {
            t = this.timeStamp + t;
        }
        MixinChatLine.CHAT.get().animationMap.put(ChatLine.class.cast(this), new TimeAnimation(MixinChatLine.CHAT.get().time.getValue(), -MixinChatLine.MC.fontRendererObj.getStringWidth(t), 0.0, false, AnimationMode.LINEAR));
    }
    
    static {
        CHAT = Caches.getModule(Chat.class);
        TIME_STAMPS = Caches.getSetting(Chat.class, BooleanSetting.class, "TimeStamps", false);
        FORMAT = new SimpleDateFormat("k:mm");
        MC = Minecraft.getMinecraft();
    }
}
