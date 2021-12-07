// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.rpc;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.discord.*;

public class RPC extends Module
{
    public final Setting<String> state;
    public final Setting<String> details;
    public final Setting<String> largeImageKey;
    public final Setting<String> smallImageKey;
    public final Setting<Boolean> customDetails;
    public final Setting<Boolean> showIP;
    public final Setting<Boolean> froggers;
    
    public RPC() {
        super("RPC", Category.Misc);
        this.state = this.register(new StringSetting("State", "3arfH4ck :3"));
        this.details = this.register(new StringSetting("Details", "3arfH4ck :3"));
        this.largeImageKey = this.register(new StringSetting("LargeImageKey", "earthhack"));
        this.smallImageKey = this.register(new StringSetting("SmallImageKey", "Da greatest"));
        this.customDetails = this.register(new BooleanSetting("CustomDetails", false));
        this.showIP = this.register(new BooleanSetting("ShowIP", false));
        this.froggers = this.register(new BooleanSetting("Froggers", false));
    }
    
    @Override
    protected void onEnable() {
        DiscordPresence.start();
    }
    
    @Override
    protected void onDisable() {
        DiscordPresence.stop();
    }
}
