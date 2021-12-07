//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.scheduler;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.core.ducks.*;
import me.earth.earthhack.impl.util.misc.collections.*;

public class Scheduler extends SubscriberImpl implements Globals
{
    private static final Scheduler INSTANCE;
    private final Queue<Runnable> scheduled;
    private final Queue<Runnable> toSchedule;
    private boolean executing;
    private int gameLoop;
    
    private Scheduler() {
        this.scheduled = new LinkedList<Runnable>();
        this.toSchedule = new LinkedList<Runnable>();
        this.listeners.add(new EventListener<GameLoopEvent>(GameLoopEvent.class, Integer.MAX_VALUE) {
            @Override
            public void invoke(final GameLoopEvent event) {
                Scheduler.this.gameLoop = ((IMinecraft)Globals.mc).getGameLoop();
                Scheduler.this.executing = true;
                CollectionUtil.emptyQueue(Scheduler.this.scheduled, Runnable::run);
                Scheduler.this.executing = false;
                CollectionUtil.emptyQueue(Scheduler.this.toSchedule, Scheduler.this.scheduled::add);
            }
        });
    }
    
    public static Scheduler getInstance() {
        return Scheduler.INSTANCE;
    }
    
    public void schedule(final Runnable runnable) {
        this.schedule(runnable, true);
    }
    
    public void scheduleAsynchronously(final Runnable runnable) {
        Scheduler.mc.addScheduledTask(() -> this.schedule(runnable, false));
    }
    
    public void schedule(final Runnable runnable, final boolean checkGameLoop) {
        if (Scheduler.mc.isCallingFromMinecraftThread()) {
            if (this.executing || (checkGameLoop && this.gameLoop != ((IMinecraft)Scheduler.mc).getGameLoop())) {
                this.toSchedule.add(runnable);
            }
            else {
                this.scheduled.add(runnable);
            }
        }
        else {
            Scheduler.mc.addScheduledTask(runnable);
        }
    }
    
    static {
        INSTANCE = new Scheduler();
    }
}
