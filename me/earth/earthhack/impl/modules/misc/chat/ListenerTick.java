//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.chat;

import me.earth.earthhack.impl.event.listeners.*;
import me.earth.earthhack.impl.event.events.misc.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.util.text.*;
import net.minecraft.client.multiplayer.*;

final class ListenerTick extends ModuleListener<Chat, TickEvent>
{
    public ListenerTick(final Chat module) {
        super(module, (Class<? super Object>)TickEvent.class);
    }
    
    public void invoke(final TickEvent event) {
        if (event.isSafe() && ((Chat)this.module).autoQMain.getValue() && ((Chat)this.module).timer.passed(((Chat)this.module).qDelay.getValue())) {
            final ServerData data = ListenerTick.mc.getCurrentServerData();
            if (data != null && "2b2t.org".equalsIgnoreCase(data.serverIP) && ListenerTick.mc.player.dimension == 1 && ListenerTick.mc.player.getPosition().equals((Object)new Vec3i(0, 240, 0))) {
                ChatUtil.sendMessage("<" + ((Chat)this.module).getDisplayName() + "> Sending " + "§y" + ((Chat)this.module).message.getValue() + "§r" + "...");
                ListenerTick.mc.player.sendChatMessage((String)((Chat)this.module).message.getValue());
                ((Chat)this.module).timer.reset();
            }
        }
    }
}
