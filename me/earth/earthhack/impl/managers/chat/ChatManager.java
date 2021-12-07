//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.chat;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.*;
import me.earth.earthhack.impl.util.misc.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.util.text.*;
import net.minecraft.util.text.*;
import me.earth.earthhack.impl.core.ducks.gui.*;

public class ChatManager extends SubscriberImpl implements Globals
{
    private final Map<Integer, Map<String, Integer>> message_ids;
    private final SkippingCounter counter;
    
    public ChatManager() {
        this.counter = new SkippingCounter(1337, i -> i != -1);
        this.message_ids = new ConcurrentHashMap<Integer, Map<String, Integer>>();
        this.listeners.add(new EventListener<DisconnectEvent>(DisconnectEvent.class) {
            @Override
            public void invoke(final DisconnectEvent event) {
                Globals.mc.addScheduledTask(() -> ChatManager.this.clear());
            }
        });
        this.listeners.add(new EventListener<WorldClientEvent.Load>(WorldClientEvent.Load.class) {
            @Override
            public void invoke(final WorldClientEvent.Load event) {
                Globals.mc.addScheduledTask(() -> ChatManager.this.clear());
            }
        });
    }
    
    public void clear() {
        if (ChatManager.mc.ingameGUI != null) {
            this.message_ids.values().forEach(m -> m.values().forEach(id -> ChatManager.mc.ingameGUI.getChatGUI().deleteChatLine((int)id)));
        }
        this.message_ids.clear();
        this.counter.reset();
    }
    
    public void sendDeleteMessageScheduled(final String message, final String uniqueWord, final int senderID) {
        final Integer id = this.message_ids.computeIfAbsent(Integer.valueOf(senderID), v -> new ConcurrentHashMap()).computeIfAbsent(uniqueWord, v -> this.counter.next());
        ChatManager.mc.addScheduledTask(() -> ChatUtil.sendMessage(message, id));
    }
    
    public void sendDeleteMessage(final String message, final String uniqueWord, final int senderID) {
        final Integer id = this.message_ids.computeIfAbsent(Integer.valueOf(senderID), v -> new ConcurrentHashMap()).computeIfAbsent(uniqueWord, v -> this.counter.next());
        ChatUtil.sendMessage(message, id);
    }
    
    public void deleteMessage(final String uniqueWord, final int senderID) {
        final Map<String, Integer> map = this.message_ids.get(senderID);
        if (map != null) {
            final Integer id = map.remove(uniqueWord);
            if (id != null) {
                ChatUtil.deleteMessage(id);
            }
        }
    }
    
    public void sendDeleteComponent(final ITextComponent component, final String uniqueWord, final int senderID) {
        final Integer id = this.message_ids.computeIfAbsent(Integer.valueOf(senderID), v -> new ConcurrentHashMap()).computeIfAbsent(uniqueWord, v -> this.counter.next());
        ChatUtil.sendComponent(component, id);
    }
    
    public int getId(final String uniqueWord, final int senderID) {
        final Map<String, Integer> map = this.message_ids.get(senderID);
        if (map != null) {
            final Integer id = map.get(uniqueWord);
            if (id != null) {
                return id;
            }
        }
        return -1;
    }
    
    public void replace(final ITextComponent component, final String uniqueWord, final int senderID, final boolean wrap, final boolean multiple, final boolean sendIfAbsent) {
        final Map<String, Integer> map = this.message_ids.get(senderID);
        if (map != null) {
            final Integer id = map.get(uniqueWord);
            if (id != null && ChatManager.mc.ingameGUI != null) {
                final IGuiNewChat gui = (IGuiNewChat)ChatManager.mc.ingameGUI.getChatGUI();
                if (gui.replace(component, senderID, wrap, !multiple)) {
                    return;
                }
            }
        }
        if (sendIfAbsent) {
            this.sendDeleteComponent(component, uniqueWord, senderID);
        }
    }
}
