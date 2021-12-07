//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.client.pingbypass.serializer.setting;

import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.modules.client.pingbypass.serializer.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.observable.*;
import me.earth.earthhack.api.setting.event.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.network.*;
import java.util.*;
import net.minecraft.network.*;

public class SettingSerializer extends SubscriberImpl implements Globals, Serializer<Setting<?>>
{
    private static final Set<String> UNSERIALIZABLE;
    private final Set<Module> modules;
    private final Set<Setting<?>> settings;
    private final Set<Setting<?>> changed;
    
    public SettingSerializer(final Module... modules) {
        this.modules = new HashSet<Module>();
        this.settings = new HashSet<Setting<?>>();
        this.changed = new LinkedHashSet<Setting<?>>();
        this.init(new ListenerSetting(this), modules);
        this.listeners.add(new ListenerDisconnect(this));
        this.listeners.add(new ListenerTick(this));
    }
    
    private void init(final Observer observer, final Module... modules) {
        this.modules.addAll(Arrays.asList(modules));
        this.modules.forEach(module -> {
            if (module != null) {
                module.getSettings().forEach(setting -> {
                    if (this.isSettingSerializable(setting)) {
                        setting.addObserver(observer);
                        this.settings.add(setting);
                    }
                });
            }
            return;
        });
        this.clear();
    }
    
    public void onSettingChange(final SettingEvent<?> event) {
        final Setting<?> setting = event.getSetting();
        Scheduler.getInstance().schedule(() -> this.changed.add(setting));
    }
    
    protected void onTick() {
        if (SettingSerializer.mc.player != null && SettingSerializer.mc.getConnection() != null && !this.changed.isEmpty()) {
            final Setting<?> setting = this.pollSetting();
            if (setting != null) {
                this.serializeAndSend(setting);
            }
        }
    }
    
    public void clear() {
        synchronized (this.changed) {
            this.changed.clear();
            this.changed.addAll(this.settings);
        }
    }
    
    private Setting<?> pollSetting() {
        if (!this.changed.isEmpty()) {
            final Setting<?> setting = this.changed.iterator().next();
            this.changed.remove(setting);
            return setting;
        }
        return null;
    }
    
    @Override
    public void serializeAndSend(final Setting<?> setting) {
        String name = null;
        if (setting.getContainer() instanceof Displayable) {
            name = ((Nameable)setting.getContainer()).getName();
        }
        final String command = "@Server" + name + " " + setting.getName() + " " + setting.getValue().toString();
        Earthhack.getLogger().info(command);
        final CPacketChatMessage packet = new CPacketChatMessage(command);
        Objects.requireNonNull(SettingSerializer.mc.getConnection()).sendPacket((Packet)packet);
    }
    
    private boolean isSettingSerializable(final Setting<?> setting) {
        return !SettingSerializer.UNSERIALIZABLE.contains(setting.getName());
    }
    
    static {
        (UNSERIALIZABLE = new HashSet<String>()).add("Bind");
        SettingSerializer.UNSERIALIZABLE.add("Hidden");
        SettingSerializer.UNSERIALIZABLE.add("Name");
        SettingSerializer.UNSERIALIZABLE.add("IP");
        SettingSerializer.UNSERIALIZABLE.add("Port");
        SettingSerializer.UNSERIALIZABLE.add("Pings");
        SettingSerializer.UNSERIALIZABLE.add("Toggle");
    }
}
