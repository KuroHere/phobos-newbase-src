// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.config;

import me.earth.earthhack.api.config.*;
import me.earth.earthhack.impl.managers.*;
import me.earth.earthhack.api.register.*;
import me.earth.earthhack.api.hud.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.impl.*;
import me.earth.earthhack.impl.managers.config.helpers.*;
import java.io.*;
import java.util.*;
import me.earth.earthhack.impl.util.misc.io.*;

public class ConfigManager extends AbstractRegister<ConfigHelper<?>>
{
    public ConfigManager() {
        super(new LinkedHashMap());
        this.registered.put("macro", (T)new MacroConfigHelper(Managers.MACRO));
        this.registered.put("hud", (T)new HudConfigHelper(Managers.ELEMENTS));
        this.registered.put("friend", (T)new FriendConfigHelper());
        this.registered.put("enemy", (T)new EnemyConfigHelper());
        this.registered.put("bind", (T)new BindConfigHelper());
        this.registered.put("module", (T)new ModuleConfigHelper(Managers.MODULES));
    }
    
    public void saveAll() throws IOException {
        for (final ConfigHelper<?> helper : this.getRegistered()) {
            Earthhack.getLogger().info("Config saving: " + helper.getName());
            helper.save();
        }
        Earthhack.getLogger().info("Config saving: current");
        CurrentConfig.getInstance().save();
    }
    
    public void refreshAll() throws IOException {
        Earthhack.getLogger().info("Config refreshing: current");
        CurrentConfig.getInstance().refresh();
        for (final ConfigHelper<?> helper : this.getRegistered()) {
            Earthhack.getLogger().info("Config refreshing: " + helper.getName());
            helper.refresh();
            final String current = CurrentConfig.getInstance().get(helper);
            if (current == null) {
                CurrentConfig.getInstance().set(helper, "default");
            }
            helper.load(CurrentConfig.getInstance().get(helper));
        }
    }
    
    public void save(final ConfigHelper<?> helper, final String... args) throws IOException {
        Earthhack.getLogger().info("Config: saving " + helper.getName() + " " + Arrays.toString(args));
        this.execute(helper, ConfigHelper::save, ConfigHelper::save, args);
    }
    
    public void load(final ConfigHelper<?> helper, final String... args) throws IOException {
        Earthhack.getLogger().info("Config: loading " + helper.getName() + " " + Arrays.toString(args));
        this.execute(helper, h -> {}, ConfigHelper::load, args);
    }
    
    public void refresh(final ConfigHelper<?> helper, final String... args) throws IOException {
        Earthhack.getLogger().info("Config: refreshing " + helper.getName() + " " + Arrays.toString(args));
        this.execute(helper, ConfigHelper::refresh, ConfigHelper::refresh, args);
    }
    
    private void execute(final ConfigHelper<?> helper, final IOConsumer<ConfigHelper<?>> ifNoArgs, final BiIOConsumer<ConfigHelper<?>, String> consumer, final String... args) throws IOException {
        if (args == null || args.length == 0) {
            ifNoArgs.accept(helper);
            return;
        }
        for (final String arg : args) {
            consumer.accept(helper, arg);
        }
    }
}
