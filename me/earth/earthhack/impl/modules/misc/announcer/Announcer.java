// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.modules.misc.announcer.util.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import me.earth.earthhack.api.module.data.*;
import com.google.common.collect.*;
import me.earth.earthhack.impl.util.misc.*;
import java.util.*;
import me.earth.earthhack.impl.util.text.*;

public class Announcer extends Module
{
    private static final Random RANDOM;
    protected final Setting<Double> delay;
    protected final Setting<Boolean> distance;
    protected final Setting<Boolean> mine;
    protected final Setting<Boolean> place;
    protected final Setting<Boolean> eat;
    protected final Setting<Boolean> join;
    protected final Setting<Boolean> leave;
    protected final Setting<Boolean> totems;
    protected final Setting<Boolean> autoEZ;
    protected final Setting<Boolean> miss;
    protected final Setting<Boolean> friends;
    protected final Setting<Boolean> antiKick;
    protected final Setting<Boolean> green;
    protected final Setting<Boolean> refresh;
    protected final Setting<Boolean> random;
    protected final Setting<Double> minDist;
    protected final Map<AnnouncementType, Announcement> announcements;
    protected final Map<AnnouncementType, List<String>> messages;
    protected final Set<AnnouncementType> types;
    protected final Set<EntityPlayer> targets;
    protected final StopWatch timer;
    double travelled;
    protected final Map<Integer, EntityPlayer> arrowMap;
    
    public Announcer() {
        super("Announcer", Category.Misc);
        this.delay = this.register(new NumberSetting("Delay", 5.0, 0.0, 60.0));
        this.distance = this.register(new BooleanSetting("Distance", true));
        this.mine = this.register(new BooleanSetting("Mine", true));
        this.place = this.register(new BooleanSetting("Place", true));
        this.eat = this.register(new BooleanSetting("Eat", true));
        this.join = this.register(new BooleanSetting("Join", true));
        this.leave = this.register(new BooleanSetting("Leave", true));
        this.totems = this.register(new BooleanSetting("Totems", true));
        this.autoEZ = this.register(new BooleanSetting("AutoEZ", true));
        this.miss = this.register(new BooleanSetting("ArrowMiss", false));
        this.friends = this.register(new BooleanSetting("Friends", false));
        this.antiKick = this.register(new BooleanSetting("AntiKick", false));
        this.green = this.register(new BooleanSetting("GreenText", false));
        this.refresh = this.register(new BooleanSetting("Refresh", false));
        this.random = this.register(new BooleanSetting("Random", false));
        this.minDist = this.register(new NumberSetting("MinDistance", 10.0, 1.0, 100.0));
        this.announcements = new ConcurrentHashMap<AnnouncementType, Announcement>();
        this.messages = new ConcurrentHashMap<AnnouncementType, List<String>>();
        this.types = new HashSet<AnnouncementType>();
        this.targets = new HashSet<EntityPlayer>();
        this.timer = new StopWatch();
        this.arrowMap = new ConcurrentHashMap<Integer, EntityPlayer>();
        this.listeners.add(new ListenerDigging(this));
        this.listeners.add(new ListenerDeath(this));
        this.listeners.add(new ListenerJoin(this));
        this.listeners.add(new ListenerLeave(this));
        this.listeners.add(new ListenerPlace(this));
        this.listeners.add(new ListenerTotems(this));
        this.listeners.add(new ListenerMotion(this));
        this.listeners.add(new ListenerDisconnect(this));
        this.listeners.add(new ListenerWorldClient(this));
        this.listeners.add(new ListenerEat(this));
        this.listeners.add(new ListenerSpawn(this));
        this.setData(new AnnouncerData(this));
    }
    
    @Override
    protected void onEnable() {
        this.reset();
    }
    
    @Override
    protected void onLoad() {
        this.loadFiles();
    }
    
    public void reset() {
        this.travelled = 0.0;
        this.announcements.clear();
        this.types.clear();
        this.targets.clear();
    }
    
    public void loadFiles() {
        this.reset();
        this.messages.clear();
        for (final AnnouncementType type : AnnouncementType.values()) {
            final List<String> list = FileUtil.readFile(type.getFile(), true, Lists.newArrayList((Object[])new String[] { type.getDefaultMessage() }));
            this.messages.put(type, list);
        }
    }
    
    String getNextMessage() {
        for (final Map.Entry<AnnouncementType, Announcement> entry : this.announcements.entrySet()) {
            if (entry != null && entry.getValue() != null && entry.getKey() != null && entry.getKey() != AnnouncementType.Distance && !this.types.contains(entry.getKey())) {
                if (!this.shouldAnnounce(entry.getKey())) {
                    continue;
                }
                final Announcement announcement = entry.getValue();
                this.types.add(entry.getKey());
                this.announcements.remove(entry.getKey());
                return this.convert(entry.getKey(), announcement);
            }
        }
        if (!this.types.isEmpty()) {
            this.types.clear();
            return this.getNextMessage();
        }
        if (this.distance.getValue()) {
            final int dist = (int)this.travelled;
            if (dist > this.minDist.getValue()) {
                this.travelled = 0.0;
                return this.convert(AnnouncementType.Distance, new Announcement("Block", dist));
            }
        }
        return null;
    }
    
    Announcement addWordAndIncrement(final AnnouncementType type, final String word) {
        Announcement announcement = this.announcements.get(type);
        if (announcement != null && announcement.getName().equals(word)) {
            announcement.setAmount(announcement.getAmount() + 1);
            return announcement;
        }
        announcement = new Announcement(word, 1);
        this.announcements.put(type, announcement);
        return announcement;
    }
    
    private String convert(final AnnouncementType type, final Announcement announcement) {
        final List<String> list = this.messages.get(type);
        String text = null;
        if (list != null && !list.isEmpty()) {
            if (this.random.getValue()) {
                text = list.get(Announcer.RANDOM.nextInt(list.size()));
            }
            else {
                text = list.get(0);
            }
        }
        if (text == null) {
            text = type.getDefaultMessage();
        }
        return (this.green.getValue() ? ">" : "") + text.replace("<NUMBER>", Integer.toString(announcement.getAmount())).replace("<NAME>", announcement.getName()) + (this.antiKick.getValue() ? (" " + ChatUtil.generateRandomHexSuffix(2)) : "");
    }
    
    private boolean shouldAnnounce(final AnnouncementType type) {
        switch (type) {
            case Distance: {
                return this.distance.getValue();
            }
            case Mine: {
                return this.mine.getValue();
            }
            case Place: {
                return this.place.getValue();
            }
            case Eat: {
                return this.eat.getValue();
            }
            case Join: {
                return this.join.getValue();
            }
            case Leave: {
                return this.leave.getValue();
            }
            case Totems: {
                return this.totems.getValue();
            }
            case Death: {
                return this.autoEZ.getValue();
            }
            case Miss: {
                return this.miss.getValue();
            }
            default: {
                return false;
            }
        }
    }
    
    static {
        RANDOM = new Random();
    }
}
