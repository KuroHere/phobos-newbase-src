// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.antipackets;

import me.earth.earthhack.api.module.*;
import net.minecraft.network.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.mcp.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.modules.misc.antipackets.util.*;
import me.earth.earthhack.impl.gui.visibility.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.observable.*;
import me.earth.earthhack.api.setting.event.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.*;

public class AntiPackets extends Module
{
    private final Map<Class<? extends Packet<?>>, BooleanSetting> client;
    private final Map<Class<? extends Packet<?>>, BooleanSetting> server;
    private final Setting<Boolean> unknown;
    private int settings;
    
    public AntiPackets() {
        super("AntiPackets", Category.Misc);
        this.client = new HashMap<Class<? extends Packet<?>>, BooleanSetting>();
        this.server = new HashMap<Class<? extends Packet<?>>, BooleanSetting>();
        this.unknown = new BooleanSetting("Unknown", false);
        this.listeners.add(new ListenerCPacket(this));
        this.listeners.add(new ListenerSPacket(this));
        final AntiPacketData data = new AntiPacketData(this);
        this.setData(data);
        BooleanSetting s = null;
        for (final Class<? extends Packet<?>> clazz : PacketUtil.getAllPackets()) {
            if (clazz.getName().contains("$")) {
                continue;
            }
            final String simpleName = MappingProvider.simpleName(clazz);
            final boolean side = simpleName.startsWith("S");
            this.getMap(side).put(clazz, new BooleanSetting(this.formatPacketName(simpleName), false));
            for (final Class<?> inner : clazz.getDeclaredClasses()) {
                if (inner.getSuperclass() == clazz) {
                    final String sin = MappingProvider.simpleName(inner);
                    s = new BooleanSetting(this.formatPacketName(simpleName) + "-" + sin, false);
                    this.getMap(side).put((Class<? extends Packet<?>>)inner, s);
                }
            }
        }
        final EnumSetting<Page> pageEnumSetting = this.register(new EnumSetting<Page>("Page", Page.CPackets));
        this.client.values().forEach(s -> Visibilities.VISIBILITY_MANAGER.registerVisibility(s, () -> pageEnumSetting.getValue() == Page.CPackets));
        this.server.values().forEach(s -> Visibilities.VISIBILITY_MANAGER.registerVisibility(s, () -> pageEnumSetting.getValue() == Page.SPackets));
        final Iterable<BooleanSetting> sortedC = this.sorted(this.client.values());
        final Iterable<BooleanSetting> sortedS = this.sorted(this.server.values());
        this.registerSettings(sortedC, data);
        this.registerSettings(sortedS, data);
        final Setting<Integer> sPacketPages = NumberPageBuilder.autoPage(this, "SPackets", 8, sortedS).withConversion(Visibilities::andComposer).reapplyConversion().setPagePositionAfter("Page").register(Visibilities.VISIBILITY_MANAGER).registerPageSetting().getPageSetting();
        final Setting<Integer> cPacketPages = NumberPageBuilder.autoPage(this, "CPackets", 8, sortedC).withConversion(Visibilities::andComposer).reapplyConversion().setPagePositionAfter("Page").register(Visibilities.VISIBILITY_MANAGER).registerPageSetting().getPageSetting();
        Visibilities.VISIBILITY_MANAGER.registerVisibility(sPacketPages, () -> pageEnumSetting.getValue() == Page.SPackets);
        Visibilities.VISIBILITY_MANAGER.registerVisibility(cPacketPages, () -> pageEnumSetting.getValue() == Page.CPackets);
        final Function<Setting<Boolean>, Observer<SettingEvent<Boolean>>> f = (Function<Setting<Boolean>, Observer<SettingEvent<Boolean>>>)(s -> e -> {
            if (!e.getValue().equals(s.getValue())) {
                if (e.getValue()) {
                    ++this.settings;
                }
                else {
                    --this.settings;
                }
            }
            return;
        });
        this.register(this.unknown).addObserver((Observer<? super Object>)f.apply(this.unknown));
        this.client.values().forEach(s -> s.addObserver(f.apply(s)));
        this.server.values().forEach(s -> s.addObserver(f.apply(s)));
        data.register(this.unknown.getName(), "Cancels unknown packets.");
    }
    
    @Override
    public String getDisplayInfo() {
        return this.settings + "";
    }
    
    private Iterable<BooleanSetting> sorted(final Collection<BooleanSetting> settings) {
        return settings.stream().sorted(Comparator.comparing((Function<? super BooleanSetting, ? extends Comparable>)Setting::getName)).collect((Collector<? super BooleanSetting, ?, Iterable<BooleanSetting>>)Collectors.toList());
    }
    
    private void registerSettings(final Iterable<BooleanSetting> settings, final AntiPacketData data) {
        for (final BooleanSetting s : settings) {
            this.register(s);
            data.register(s.getName(), "Cancels " + s.getName() + " packets.");
        }
    }
    
    protected void onPacket(final PacketEvent<?> event, final boolean receive) {
        final BooleanSetting s = receive ? this.server.get(event.getPacket().getClass()) : this.client.get(event.getPacket().getClass());
        if (s == null) {
            Earthhack.getLogger().info("Unknown packet: " + event.getPacket().getClass().getName());
            if (this.unknown.getValue()) {
                event.setCancelled(true);
            }
        }
        else if (s.getValue()) {
            event.setCancelled(true);
        }
    }
    
    private String formatPacketName(final String name) {
        if (name.startsWith("SPacket") || name.startsWith("CPacket")) {
            return name.charAt(0) + name.substring(7);
        }
        return name;
    }
    
    private Map<Class<? extends Packet<?>>, BooleanSetting> getMap(final boolean side) {
        return side ? this.server : this.client;
    }
}
