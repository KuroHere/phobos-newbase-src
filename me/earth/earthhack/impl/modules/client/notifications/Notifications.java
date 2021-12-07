//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.notifications;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.impl.event.events.client.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.gui.visibility.*;
import me.earth.earthhack.impl.managers.*;
import java.util.*;
import net.minecraft.entity.*;
import me.earth.earthhack.api.setting.event.*;

public class Notifications extends Module
{
    protected final Setting<Boolean> totems;
    protected final Setting<TextColor> totemColor;
    protected final Setting<TextColor> totemAmountColor;
    protected final Setting<TextColor> totemPlayerColor;
    protected final Setting<Boolean> modules;
    protected final Setting<Boolean> configure;
    protected final Setting<Category> categories;
    protected final Map<Module, Setting<Boolean>> announceMap;
    
    public Notifications() {
        super("Notifications", Category.Client);
        this.totems = this.register(new BooleanSetting("TotemPops", true));
        this.totemColor = this.register(new EnumSetting("Totem-Color", TextColor.None));
        this.totemAmountColor = this.register(new EnumSetting("Amount-Color", TextColor.None));
        this.totemPlayerColor = this.register(new EnumSetting("Player-Color", TextColor.None));
        this.modules = this.register(new BooleanSetting("Modules", true));
        this.configure = this.register(new BooleanSetting("Show-Modules", true));
        this.categories = this.register(new EnumSetting("Categories", Category.Combat));
        this.announceMap = new HashMap<Module, Setting<Boolean>>();
        this.listeners.add(new ListenerTotems(this));
        this.listeners.add(new ListenerDeath(this));
        this.setData(new NotificationData(this));
        Bus.EVENT_BUS.register(new EventListener<PostInitEvent>(PostInitEvent.class) {
            @Override
            public void invoke(final PostInitEvent event) {
                Notifications.this.createSettings();
            }
        });
    }
    
    private void createSettings() {
        this.announceMap.clear();
        Visibilities.VISIBILITY_MANAGER.registerVisibility(this.categories, this.configure::getValue);
        for (final Module module : Managers.MODULES.getRegistered()) {
            final Setting<Boolean> enabled = module.getSetting("Enabled", BooleanSetting.class);
            if (enabled == null) {
                continue;
            }
            enabled.addObserver(event -> {
                if (this.isEnabled() && !event.isCancelled() && this.modules.getValue() && this.announceMap.get(module).getValue()) {
                    this.onToggleModule((Module)event.getSetting().getContainer(), event.getValue());
                }
                return;
            });
            String name = module.getName();
            if (this.getSetting(name) != null) {
                name = "Show" + name;
            }
            final Setting<Boolean> setting = this.register(new BooleanSetting(name, false));
            this.announceMap.put(module, setting);
            Visibilities.VISIBILITY_MANAGER.registerVisibility(setting, () -> this.configure.getValue() && this.categories.getValue() == module.getCategory());
            this.getData().settingDescriptions().put(setting, "Announce Toggling of " + name + "?");
        }
    }
    
    protected void onToggleModule(final Module module, final boolean enabled) {
        final Setting<Boolean> setting = this.announceMap.get(module);
        if (setting != null && setting.getValue()) {
            final String message = "§l" + module.getDisplayName() + (enabled ? "§a" : "§c") + (enabled ? " enabled." : " disabled.");
            Notifications.mc.addScheduledTask(() -> Managers.CHAT.sendDeleteMessage(message, module.getName(), 2000));
        }
    }
    
    public void onPop(final Entity player, final int totemPops) {
        if (this.isEnabled() && this.totems.getValue()) {
            final String message = this.totemPlayerColor.getValue().getColor() + player.getName() + this.totemColor.getValue().getColor() + " popped " + this.totemAmountColor.getValue().getColor() + totemPops + this.totemColor.getValue().getColor() + " totem" + ((totemPops == 1) ? "." : "s.");
            Managers.CHAT.sendDeleteMessage(message, player.getName(), 1000);
        }
    }
    
    public void onDeath(final Entity player, final int totemPops) {
        if (this.isEnabled() && this.totems.getValue()) {
            final String message = this.totemPlayerColor.getValue().getColor() + player.getName() + this.totemColor.getValue().getColor() + " died after popping " + this.totemAmountColor.getValue().getColor() + totemPops + this.totemColor.getValue().getColor() + " totem" + ((totemPops == 1) ? "." : "s.");
            Managers.CHAT.sendDeleteMessage(message, player.getName(), 1000);
        }
    }
}
