// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.packetdelay;

import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import me.earth.earthhack.api.setting.*;
import net.minecraft.network.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.util.thread.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.mcp.*;
import java.util.function.*;
import java.util.stream.*;
import me.earth.earthhack.api.util.*;
import java.util.*;

public class PacketDelay extends RegisteringModule<Boolean, SimpleRemovingSetting>
{
    protected final Setting<Integer> delay;
    public final Set<Packet<?>> packets;
    public final ScheduledExecutorService service;
    public Integer lastDelay;
    protected final List<String> packetNames;
    
    public PacketDelay() {
        super("PacketDelay", Category.Misc, "Add_Packet", "packet", SimpleRemovingSetting::new, s -> "Filter " + s.getName() + " packets.");
        this.delay = this.register(new NumberSetting("Delay", 0, 0, 5000));
        this.packets = Collections.newSetFromMap(new ConcurrentHashMap<Packet<?>, Boolean>());
        this.service = ThreadUtil.newDaemonScheduledExecutor("Packet-Delay");
        this.lastDelay = null;
        this.packetNames = PacketUtil.getAllPackets().stream().map((Function<? super Object, ?>)MappingProvider::simpleName).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    @Override
    public String getInput(final String input, final boolean add) {
        if (!add) {
            return super.getInput(input, false);
        }
        final String packet = this.getPacketStartingWith(input);
        if (packet != null) {
            return TextUtil.substring(packet, input.length());
        }
        return "";
    }
    
    private String getPacketStartingWith(final String input) {
        for (final String packet : this.packetNames) {
            if (TextUtil.startsWith(packet, input)) {
                return packet;
            }
        }
        return null;
    }
    
    @Override
    protected void onEnable() {
        this.lastDelay = 0;
    }
    
    @Override
    protected void onDisable() {
        this.lastDelay = 0;
    }
    
    public int getDelay() {
        return this.delay.getValue();
    }
    
    public boolean isPacketValid(final String packet) {
        return this.isValid(packet);
    }
}
