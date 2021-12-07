// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.noafk;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;

public class NoAFK extends Module
{
    private static final String DEFAULT = "I'm AFK! This message was brought to you by 3arthh4ck.";
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> swing;
    protected final Setting<Boolean> sneak;
    protected final Setting<Boolean> autoReply;
    protected final Setting<String> message;
    protected final Setting<String> indicator;
    protected final Setting<String> reply;
    protected final Setting<TextColor> color;
    protected final StopWatch swing_timer;
    protected final StopWatch sneak_timer;
    protected boolean sneaking;
    
    public NoAFK() {
        super("NoAFK", Category.Misc);
        this.rotate = this.register(new BooleanSetting("Rotate", true));
        this.swing = this.register(new BooleanSetting("Swing", true));
        this.sneak = this.register(new BooleanSetting("Sneak", true));
        this.autoReply = this.register(new BooleanSetting("AutoReply", false));
        this.message = this.register(new StringSetting("Message", "I'm AFK! This message was brought to you by 3arthh4ck."));
        this.indicator = this.register(new StringSetting("Indicator", " whispers: "));
        this.reply = this.register(new StringSetting("Reply", "/r "));
        this.color = this.register(new EnumSetting("Color", TextColor.LightPurple));
        this.swing_timer = new StopWatch();
        this.sneak_timer = new StopWatch();
        this.listeners.add(new ListenerGameLoop(this));
        this.listeners.add(new ListenerChat(this));
        this.listeners.add(new ListenerInput(this));
        this.setData(new NoAFKData(this));
    }
}
