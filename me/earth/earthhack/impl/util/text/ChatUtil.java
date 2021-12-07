//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.text;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.*;
import net.minecraft.util.text.*;
import java.util.function.*;
import net.minecraft.client.gui.*;

public class ChatUtil implements Globals
{
    private static final Random RND;
    
    public static void sendMessage(final String message) {
        sendMessage(message, 0);
    }
    
    public static void sendMessage(final String message, final int id) {
        sendComponent((ITextComponent)new TextComponentString((message == null) ? "null" : message), id);
    }
    
    public static void deleteMessage(final int id) {
        applyIfPresent(g -> g.deleteChatLine(id));
    }
    
    public static void sendComponent(final ITextComponent component) {
        sendComponent(component, 0);
    }
    
    public static void sendComponent(final ITextComponent c, final int id) {
        applyIfPresent(g -> g.printChatMessageWithOptionalDeletion(c, id));
    }
    
    public static void applyIfPresent(final Consumer<GuiNewChat> consumer) {
        final GuiNewChat chat = getChatGui();
        if (chat != null) {
            consumer.accept(chat);
        }
    }
    
    public static GuiNewChat getChatGui() {
        if (ChatUtil.mc.ingameGUI != null) {
            return ChatUtil.mc.ingameGUI.getChatGUI();
        }
        return null;
    }
    
    public static void sendMessageScheduled(final String message) {
        ChatUtil.mc.addScheduledTask(() -> sendMessage(message));
    }
    
    public static String generateRandomHexSuffix(final int places) {
        return "[" + Integer.toHexString((ChatUtil.RND.nextInt() + 11) * ChatUtil.RND.nextInt()).substring(0, places) + "]";
    }
    
    static {
        RND = new Random();
    }
}
