//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.chat;

import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.gui.chat.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.core.ducks.gui.*;
import net.minecraft.util.text.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class WrapManager extends SubscriberImpl implements Globals
{
    private final Map<ChatLineReferenceMap, AbstractTextComponent> components;
    
    public WrapManager() {
        this.components = new ConcurrentHashMap<ChatLineReferenceMap, AbstractTextComponent>();
        this.listeners.add(new EventListener<TickEvent>(TickEvent.class) {
            @Override
            public void invoke(final TickEvent event) {
                WrapManager.this.onTick();
            }
        });
        this.listeners.add(new EventListener<DisconnectEvent>(DisconnectEvent.class) {
            @Override
            public void invoke(final DisconnectEvent event) {
                Globals.mc.addScheduledTask(() -> WrapManager.this.clear());
            }
        });
        this.listeners.add(new EventListener<WorldClientEvent.Load>(WorldClientEvent.Load.class) {
            @Override
            public void invoke(final WorldClientEvent.Load event) {
                Globals.mc.addScheduledTask(() -> WrapManager.this.clear());
            }
        });
    }
    
    private void clear() {
        if (WrapManager.mc.ingameGUI != null) {
            for (final Map.Entry<ChatLineReferenceMap, AbstractTextComponent> entry : this.components.entrySet()) {
                WrapManager.mc.ingameGUI.getChatGUI().deleteChatLine(entry.getKey().getId());
            }
        }
        this.components.clear();
    }
    
    private void onTick() {
        for (final Map.Entry<ChatLineReferenceMap, AbstractTextComponent> entry : this.components.entrySet()) {
            if (entry.getKey().isEmpty() || !entry.getValue().isWrapping() || WrapManager.mc.ingameGUI == null) {
                this.components.remove(entry.getKey());
            }
            else {
                ((IGuiNewChat)WrapManager.mc.ingameGUI.getChatGUI()).replace((ITextComponent)entry.getValue(), entry.getKey().getId(), true, false);
            }
        }
    }
    
    public void registerComponent(final AbstractTextComponent component, final ChatLine... references) {
        this.components.put(new ChatLineReferenceMap(references), component);
    }
    
    private static class ChatLineReferenceMap extends WeakHashMap<ChatLine, Boolean>
    {
        private int id;
        
        public ChatLineReferenceMap(final ChatLine... references) {
            this.id = -1;
            if (references != null) {
                for (final ChatLine line : references) {
                    if (line != null) {
                        super.put(line, true);
                        this.id = line.getChatLineID();
                    }
                }
            }
        }
        
        public int getId() {
            return this.id;
        }
        
        @Override
        public int hashCode() {
            return this.id;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof ChatLineReferenceMap && ((ChatLineReferenceMap)o).id == this.id;
        }
    }
}
