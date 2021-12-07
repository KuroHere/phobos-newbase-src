//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.chat;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.misc.chat.util.*;
import me.earth.earthhack.impl.event.events.render.*;
import net.minecraft.client.gui.*;
import me.earth.earthhack.impl.util.animation.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import java.util.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.misc.collections.*;
import me.earth.earthhack.api.setting.event.*;

public class Chat extends Module
{
    protected final Setting<Boolean> noScroll;
    protected final Setting<Boolean> timeStamps;
    public final Setting<Boolean> animated;
    public final Setting<Integer> time;
    protected final Setting<Boolean> autoQMain;
    protected final Setting<Integer> qDelay;
    protected final Setting<String> message;
    protected final Setting<LoggerMode> log;
    protected final Queue<ChatEvent.Send> events;
    public final Map<ChatLine, TimeAnimation> animationMap;
    protected final StopWatch timer;
    protected boolean cleared;
    
    public Chat() {
        super("Chat", Category.Misc);
        this.noScroll = this.register(new BooleanSetting("AntiScroll", true));
        this.timeStamps = this.register(new BooleanSetting("TimeStamps", false));
        this.animated = this.register(new BooleanSetting("Animated", false));
        this.time = this.register(new NumberSetting("AnimationTime", 200, 1, 500));
        this.autoQMain = this.register(new BooleanSetting("AutoQMain", false));
        this.qDelay = this.register(new NumberSetting("Q-Delay", 5000, 1, 10000));
        this.message = this.register(new StringSetting("Q-Message", "/queue main"));
        this.log = this.register(new EnumSetting("Log", LoggerMode.Normal));
        this.events = new ConcurrentLinkedQueue<ChatEvent.Send>();
        this.animationMap = new HashMap<ChatLine, TimeAnimation>();
        this.timer = new StopWatch();
        this.register(new BooleanSetting("Clean", false));
        this.register(new BooleanSetting("Infinite", false));
        this.listeners.add(new ListenerPacket(this));
        this.listeners.add(new ListenerGameLoop(this));
        this.listeners.add(new ListenerLogout(this));
        this.listeners.add(new ListenerChat(this));
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerChatLog(this));
        this.noScroll.addObserver(event -> {
            if (!event.getValue()) {
                Scheduler.getInstance().schedule(this::clearNoScroll);
            }
            return;
        });
        this.register(new BooleanSetting("Clear", false)).addObserver(e -> {
            e.setCancelled(true);
            if (Chat.mc.ingameGUI != null) {
                Chat.mc.ingameGUI.getChatGUI().clearChatMessages(true);
            }
            return;
        });
        this.setData(new ChatData(this));
    }
    
    public void onDisable() {
        this.clearNoScroll();
    }
    
    public void clearNoScroll() {
        if (Chat.mc.ingameGUI != null) {
            CollectionUtil.emptyQueue(this.events, ChatEvent.Send::invoke);
        }
        else {
            this.events.clear();
        }
        this.cleared = true;
    }
}
