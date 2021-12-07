//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.tracker;

import me.earth.earthhack.impl.util.helpers.disabling.*;
import me.earth.earthhack.impl.util.helpers.command.*;
import me.earth.earthhack.api.setting.*;
import java.util.*;
import net.minecraft.util.math.*;
import java.util.concurrent.atomic.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import io.netty.util.internal.*;
import me.earth.earthhack.api.event.bus.instance.*;
import me.earth.earthhack.api.event.bus.api.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.managers.thread.scheduler.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.util.text.*;

public class Tracker extends DisablingModule implements CustomCommandModule
{
    protected final Setting<Boolean> autoEnable;
    protected final Setting<Boolean> only1v1;
    protected final Set<BlockPos> placed;
    protected final StopWatch timer;
    protected final AtomicInteger awaitingExp;
    protected final AtomicInteger crystals;
    protected final AtomicInteger exp;
    protected EntityPlayer trackedPlayer;
    protected boolean awaiting;
    protected int crystalStacks;
    protected int expStacks;
    
    public Tracker() {
        super("Tracker", Category.Misc);
        this.autoEnable = this.register(new BooleanSetting("Auto-Enable", false));
        this.only1v1 = this.register(new BooleanSetting("1v1-Only", true));
        this.placed = (Set<BlockPos>)new ConcurrentSet();
        this.timer = new StopWatch();
        this.awaitingExp = new AtomicInteger();
        this.crystals = new AtomicInteger();
        this.exp = new AtomicInteger();
        this.listeners.add(new ListenerSpawnObject(this));
        this.listeners.add(new ListenerUseItem(this));
        this.listeners.add(new ListenerUseItemOnBlock(this));
        Bus.EVENT_BUS.register(new ListenerChat(this));
        Bus.EVENT_BUS.register(new ListenerTick(this));
        final SimpleData data = new SimpleData(this, "Tracks the items players use. Only recommended in a 1v1!");
        data.register(this.autoEnable, "Enables automatically when a duel starts.");
        data.register(this.only1v1, "Automatically disables when there's more than one player in render distance.");
        this.setData(data);
    }
    
    @Override
    protected void onEnable() {
        this.awaiting = false;
        this.trackedPlayer = null;
        this.awaitingExp.set(0);
        this.crystals.set(0);
        this.exp.set(0);
        this.crystalStacks = 0;
        this.expStacks = 0;
    }
    
    @Override
    public String getDisplayInfo() {
        return (this.trackedPlayer == null) ? null : this.trackedPlayer.getName();
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length == 1 && this.isEnabled()) {
            final EntityPlayer tracked = this.trackedPlayer;
            if (tracked != null) {
                Scheduler.getInstance().schedule(() -> Scheduler.getInstance().schedule(() -> {
                    final int c = this.crystals.get();
                    final int e = this.exp.get();
                    final StringBuilder builder = new StringBuilder().append(tracked.getName()).append("§d").append(" has used ").append("§f").append(c).append("§d").append(" (").append("§f");
                    if (c % 64 == 0) {
                        builder.append(c / 64);
                    }
                    else {
                        builder.append(MathUtil.round(c / 64.0, 1));
                    }
                    builder.append("§d").append(") crystals and ").append("§f").append(e).append("§d").append(" (").append("§f");
                    if (e % 64 == 0) {
                        builder.append(e / 64);
                    }
                    else {
                        builder.append(MathUtil.round(e / 64.0, 1));
                    }
                    builder.append("§d").append(") bottles of experience.");
                    ChatUtil.sendMessage(builder.toString());
                }));
            }
        }
        return false;
    }
}
