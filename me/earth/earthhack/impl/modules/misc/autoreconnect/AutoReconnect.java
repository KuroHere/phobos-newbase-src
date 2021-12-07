//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.autoreconnect;

import me.earth.earthhack.api.module.*;
import net.minecraft.client.multiplayer.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.core.mixins.gui.util.*;
import me.earth.earthhack.impl.modules.misc.autoreconnect.util.*;
import net.minecraft.client.gui.*;

public class AutoReconnect extends Module
{
    private ServerData serverData;
    protected final Setting<Integer> delay;
    
    public AutoReconnect() {
        super("AutoReconnect", Category.Misc);
        this.delay = this.register(new NumberSetting("Delay", 5, 1, 60));
        this.listeners.add(new ListenerScreen(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.setData(new AutoReconnectData(this));
    }
    
    protected void setServerData() {
        final ServerData data = AutoReconnect.mc.getCurrentServerData();
        if (data != null) {
            this.serverData = data;
        }
    }
    
    protected void onGuiDisconnected(final GuiDisconnected guiDisconnected) {
        this.setServerData();
        AutoReconnect.mc.displayGuiScreen((GuiScreen)new ReconnectScreen((IGuiDisconnected)guiDisconnected, this.serverData, this.delay.getValue() * 1000));
    }
}
