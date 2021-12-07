//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.submodules.sautototem;

import me.earth.earthhack.impl.gui.module.impl.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.init.*;
import me.earth.earthhack.impl.util.minecraft.*;

public class ServerAutoTotem extends SimpleSubModule<PingBypass>
{
    private int count;
    
    public ServerAutoTotem(final PingBypass pingBypass) {
        super(pingBypass, "S-AutoTotem", Category.Client);
        this.count = 0;
        this.register(new NumberSetting("Health", 14.5f, 0.0f, 36.0f));
        this.register(new NumberSetting("SafeHealth", 3.5f, 0.0f, 36.0f));
        this.register(new BooleanSetting("XCarry", false));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerSetSlot(this));
        this.setData(new ServerAutoTotemData(this));
    }
    
    @Override
    public String getDisplayInfo() {
        return Integer.toString(this.count);
    }
    
    protected void onTick() {
        if (ServerAutoTotem.mc.player != null) {
            this.count = InventoryUtil.getCount(Items.field_190929_cY);
        }
    }
}
