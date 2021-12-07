// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.commands;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.impl.modules.*;

public class Commands extends Module
{
    private static final SettingCache<String, StringSetting, Commands> PREFIX;
    protected final Setting<Boolean> prefixBind;
    protected char prefixChar;
    
    public Commands() {
        super("Commands", Category.Client);
        this.prefixBind = this.register(new BooleanSetting("PrefixBind", false));
        this.prefixChar = '+';
        final StringSetting prefix = this.register(new StringSetting("Prefix", "+"));
        this.register(new BooleanSetting("BackgroundGui", false));
        prefix.addObserver(s -> {
            if (!s.isCancelled()) {
                if (s.getValue().length() == 1) {
                    this.prefixChar = s.getValue().charAt(0);
                }
                else {
                    this.prefixChar = '\0';
                }
            }
            return;
        });
        Commands.PREFIX.setContainer(this);
        Commands.PREFIX.set(prefix);
        Bus.EVENT_BUS.register(new KeyboardListener(this));
        this.setData(new CommandData(this));
    }
    
    public static void setPrefix(final String prefix) {
        Commands.PREFIX.computeIfPresent(s -> s.setValue(prefix));
    }
    
    public static String getPrefix() {
        if (!Commands.PREFIX.isPresent()) {
            return "+";
        }
        return Commands.PREFIX.getValue();
    }
    
    static {
        PREFIX = Caches.getSetting(Commands.class, StringSetting.class, "Prefix", "+");
    }
}
