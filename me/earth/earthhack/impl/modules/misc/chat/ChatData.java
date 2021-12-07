// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.chat;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class ChatData extends DefaultData<Chat>
{
    public ChatData(final Chat module) {
        super(module);
        this.register(module.noScroll, "Won't scroll the chat when its scrolled and you receive new messages.");
        this.register(module.timeStamps, "Displays timestamps in chat.");
        this.register("Clean", "Doesn't render the rectangle around/behind the chat.");
        this.register("Infinite", "Never delete received messages.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Tweaks for the chat.";
    }
}
