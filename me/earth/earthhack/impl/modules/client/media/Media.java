//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.media;

import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.impl.modules.client.autoconfig.*;
import me.earth.earthhack.api.cache.*;
import me.earth.earthhack.impl.modules.client.pingbypass.*;
import me.earth.earthhack.api.setting.*;
import java.util.regex.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.commands.util.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.api.util.*;
import java.util.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.impl.modules.*;

public class Media extends RegisteringModule<String, RemovingString>
{
    protected static final ModuleCache<PingBypass> PING_BYPASS;
    protected final Setting<String> replacement;
    protected final Setting<Boolean> replaceCustom;
    protected final Map<String, String> cache;
    protected final Map<Setting<String>, Pattern> custom;
    protected Pattern pattern;
    protected Pattern pingBypass;
    protected boolean pingBypassEnabled;
    protected boolean send;
    
    public Media() {
        super("Media", Category.Client, "Add_Media", "name> <replace", s -> new RemovingString(s, s), s -> "Replaces on " + s.getName() + ".");
        this.replacement = this.register(new StringSetting("Replacement", "3arthqu4ke"));
        this.replaceCustom = this.register(new BooleanSetting("Custom", false));
        this.cache = new ConcurrentHashMap<String, String>();
        this.custom = new ConcurrentHashMap<Setting<String>, Pattern>();
        this.listeners.add(new ListenerClearChat(this));
        this.listeners.add(new ListenerTick(this));
        this.pattern = this.compile(Media.mc.getSession().getUsername());
        this.replacement.addObserver(event -> this.cache.clear());
        this.replaceCustom.addObserver(event -> this.cache.clear());
        this.register(new BooleanSetting("Reload", false)).addObserver(event -> this.reload());
        this.setData(new MediaData(this));
    }
    
    public void onEnable() {
        this.pingBypassEnabled = false;
        this.send = false;
    }
    
    @Override
    public void add(final String[] args) {
        if (args.length < 4) {
            ChatUtil.sendMessage("§cPlease specify a Replacement!");
            return;
        }
        final RemovingString setting = this.addSetting(args[2]);
        if (setting == null) {
            ChatUtil.sendMessage("§cA Replacement for §f" + args[2] + "§c" + " already exists!");
            return;
        }
        setting.fromString(CommandUtil.concatenate(args, 3));
    }
    
    @Override
    protected RemovingString addSetting(final String string) {
        final RemovingString setting = super.addSetting(string);
        if (setting != null) {
            this.custom.put(setting, this.compile(setting.getName()));
        }
        return setting;
    }
    
    @Override
    public Setting<?> unregister(final Setting<?> setting) {
        final Setting<?> s = super.unregister(setting);
        if (s != null) {
            this.custom.remove(s);
        }
        return s;
    }
    
    @Override
    public void del(final String string) {
        final Setting<?> setting = this.getSetting(string);
        if (setting != null) {
            this.custom.remove(setting);
        }
        super.del(string);
        this.cache.clear();
    }
    
    @Override
    public String getInput(final String input, final boolean add) {
        if (!add) {
            return super.getInput(input, false);
        }
        final String player = LookUpUtil.findNextPlayerName(input);
        if (player != null) {
            return TextUtil.substring(player, input.length());
        }
        return "";
    }
    
    @Override
    protected String formatString(final String string) {
        return string;
    }
    
    public void reload() {
        this.cache.clear();
        this.pattern = this.compile(Media.mc.getSession().getUsername());
    }
    
    public String convert(final String text) {
        if (!this.isEnabled() || text == null || this.pattern == null) {
            return text;
        }
        return this.cache.computeIfAbsent(text, v -> {
            String toAdd = text;
            if (this.replaceCustom.getValue()) {
                this.custom.entrySet().iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final Map.Entry<Setting<String>, Pattern> entry = iterator.next();
                    if (this.getSetting(entry.getKey().getName()) != null && this.getSettings().contains(entry.getKey())) {
                        toAdd = entry.getValue().matcher(toAdd).replaceAll(entry.getKey().getValue());
                    }
                }
            }
            if (Media.PING_BYPASS.isEnabled() && this.pingBypass != null) {
                toAdd = this.pingBypass.matcher(toAdd).replaceAll(this.replacement.getValue());
            }
            return this.pattern.matcher(toAdd).replaceAll(this.replacement.getValue());
        });
    }
    
    private Pattern compile(final String name) {
        this.cache.clear();
        if (name == null) {
            return null;
        }
        final StringBuilder regex = new StringBuilder("(?<!").append('§').append(")(");
        final char[] array = name.toCharArray();
        for (int i = 0; i < array.length; ++i) {
            final char c = array[i];
            regex.append(c);
            if (i != array.length - 1) {
                for (final TextColor textColor : TextColor.values()) {
                    if (textColor != TextColor.None) {
                        final String color = textColor.getColor();
                        regex.append("[").append(color).append("]").append("*");
                    }
                }
            }
        }
        return Pattern.compile(regex.append(")").toString());
    }
    
    public void setPingBypassName(final String name) {
        this.pingBypass = this.compile(name);
    }
    
    static {
        PING_BYPASS = Caches.getModule(PingBypass.class);
    }
}
