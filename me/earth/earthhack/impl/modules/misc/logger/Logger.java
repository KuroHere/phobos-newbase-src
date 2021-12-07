//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.logger;

import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.impl.util.helpers.addable.setting.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.misc.logger.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.network.*;
import me.earth.earthhack.impl.util.mcp.*;
import java.util.function.*;
import java.util.stream.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.util.*;
import java.util.*;
import net.minecraft.network.*;
import me.earth.earthhack.impl.util.text.*;
import me.earth.earthhack.impl.*;
import java.lang.reflect.*;

public class Logger extends RegisteringModule<Boolean, SimpleRemovingSetting>
{
    protected final Setting<LoggerMode> mode;
    protected final Setting<Boolean> incoming;
    protected final Setting<Boolean> outgoing;
    protected final Setting<Boolean> info;
    protected final Setting<Boolean> chat;
    protected final Setting<Boolean> deobfuscate;
    protected final Setting<Boolean> stackTrace;
    protected final Setting<Boolean> statics;
    protected final Setting<Boolean> filter;
    protected final List<String> packetNames;
    protected boolean cancel;
    
    public Logger() {
        super("Logger", Category.Misc, "Add_Packet", "packet", SimpleRemovingSetting::new, s -> "Filter " + s.getName() + " packets.");
        this.mode = this.register(new EnumSetting("Mode", LoggerMode.Normal));
        this.incoming = this.register(new BooleanSetting("Incoming", true));
        this.outgoing = this.register(new BooleanSetting("Outgoing", true));
        this.info = this.register(new BooleanSetting("Info", true));
        this.chat = this.register(new BooleanSetting("Chat", false));
        this.deobfuscate = this.register(new BooleanSetting("Deobfuscate", true));
        this.stackTrace = this.register(new BooleanSetting("StackTrace", false));
        this.statics = this.register(new BooleanSetting("Static", false));
        this.filter = this.registerBefore(new BooleanSetting("Filter", false), this.listType);
        this.packetNames = PacketUtil.getAllPackets().stream().map((Function<? super Object, ?>)MappingProvider::simpleName).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        this.listeners.add(new ListenerChatLog(this));
        this.listeners.add(new ListenerReceive(this));
        this.listeners.add(new ListenerSend(this));
        this.setData(new LoggerData(this));
    }
    
    @Override
    protected void onEnable() {
        this.cancel = false;
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
    
    public void logPacket(final Packet<?> packet, final String message, final boolean cancelled) {
        final String simpleName = MappingProvider.simpleName(packet.getClass());
        if (this.filter.getValue() && !this.isValid(simpleName)) {
            return;
        }
        final StringBuilder outPut = new StringBuilder(message).append(simpleName).append(", cancelled : ").append(cancelled).append("\n");
        if (this.info.getValue()) {
            try {
                for (Class<?> clazz = packet.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                    for (final Field field : clazz.getDeclaredFields()) {
                        if (field != null) {
                            if (!Modifier.isStatic(field.getModifiers()) || this.statics.getValue()) {
                                field.setAccessible(true);
                                outPut.append("     ").append(this.getName(clazz, field)).append(" : ").append(field.get(packet)).append("\n");
                            }
                        }
                    }
                }
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        final String s = outPut.toString();
        if (this.chat.getValue()) {
            Logger.mc.addScheduledTask(() -> {
                this.cancel = true;
                try {
                    ChatUtil.sendMessage(s);
                }
                finally {
                    this.cancel = false;
                }
                return;
            });
        }
        Earthhack.getLogger().info(s);
        if (this.stackTrace.getValue()) {
            Thread.dumpStack();
        }
    }
    
    private String getName(final Class<?> c, final Field field) {
        if (this.deobfuscate.getValue()) {
            final String name = MappingProvider.field(c, field.getName());
            if (name != null) {
                return name;
            }
        }
        return field.getName();
    }
    
    public LoggerMode getMode() {
        return this.mode.getValue();
    }
}
