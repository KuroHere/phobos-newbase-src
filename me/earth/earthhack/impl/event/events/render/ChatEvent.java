// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.event.events.render;

import me.earth.earthhack.api.event.events.*;
import me.earth.earthhack.impl.core.ducks.gui.*;
import net.minecraft.util.text.*;

public abstract class ChatEvent extends Event
{
    protected final IGuiNewChat gui;
    
    public ChatEvent(final IGuiNewChat gui) {
        this.gui = gui;
    }
    
    public abstract void invoke();
    
    public static class Clear extends ChatEvent
    {
        private boolean sent;
        
        public Clear(final IGuiNewChat gui, final boolean sent) {
            super(gui);
        }
        
        @Override
        public void invoke() {
            this.gui.invokeClearChat(this.sent);
        }
        
        public boolean clearsSent() {
            return this.sent;
        }
        
        public void setSent(final boolean sent) {
            this.sent = sent;
        }
    }
    
    public static class Log extends ChatEvent
    {
        public Log(final IGuiNewChat gui) {
            super(gui);
        }
        
        @Override
        public void invoke() {
        }
    }
    
    public static class Send extends ChatEvent
    {
        private ITextComponent chatComponent;
        private int chatLineId;
        private int updateCounter;
        private boolean displayOnly;
        
        public Send(final IGuiNewChat gui, final ITextComponent chatComponent, final int chatLineId, final int updateCounter, final boolean displayOnly) {
            super(gui);
            this.chatComponent = chatComponent;
            this.chatLineId = chatLineId;
            this.updateCounter = updateCounter;
            this.displayOnly = displayOnly;
        }
        
        @Override
        public void invoke() {
            this.gui.invokeSetChatLine(this.chatComponent, this.chatLineId, this.updateCounter, this.displayOnly);
        }
        
        public ITextComponent getChatComponent() {
            return this.chatComponent;
        }
        
        public void setChatComponent(final ITextComponent chatComponent) {
            this.chatComponent = chatComponent;
        }
        
        public int getChatLineId() {
            return this.chatLineId;
        }
        
        public void setChatLineId(final int chatLineId) {
            this.chatLineId = chatLineId;
        }
        
        public int getUpdateCounter() {
            return this.updateCounter;
        }
        
        public void setUpdateCounter(final int updateCounter) {
            this.updateCounter = updateCounter;
        }
        
        public boolean isDisplayOnly() {
            return this.displayOnly;
        }
        
        public void setDisplayOnly(final boolean displayOnly) {
            this.displayOnly = displayOnly;
        }
    }
}
