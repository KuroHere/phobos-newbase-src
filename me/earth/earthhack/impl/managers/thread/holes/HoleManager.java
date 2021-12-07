//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.holes;

import me.earth.earthhack.api.util.interfaces.*;
import java.util.concurrent.atomic.*;
import net.minecraft.util.math.*;
import me.earth.earthhack.impl.event.events.misc.*;
import me.earth.earthhack.api.event.bus.*;
import me.earth.earthhack.impl.event.events.network.*;
import me.earth.earthhack.impl.managers.*;
import java.util.function.*;
import me.earth.earthhack.impl.util.thread.*;
import java.util.*;

public class HoleManager extends SubscriberImpl implements Globals, IHoleManager
{
    private static final HoleObserver EMPTY;
    private final AtomicBoolean finished;
    private final Set<HoleObserver> observers;
    private List<BlockPos> safe;
    private List<BlockPos> unsafe;
    private List<BlockPos> longHoles;
    private List<BlockPos> bigHoles;
    
    public HoleManager() {
        this.finished = new AtomicBoolean(true);
        this.observers = new HashSet<HoleObserver>();
        this.safe = Collections.emptyList();
        this.unsafe = Collections.emptyList();
        this.longHoles = Collections.emptyList();
        this.bigHoles = Collections.emptyList();
        this.listeners.add(new EventListener<TickEvent>(TickEvent.class) {
            @Override
            public void invoke(final TickEvent event) {
                HoleManager.this.runTick();
            }
        });
        this.listeners.add(new EventListener<WorldClientEvent.Load>(WorldClientEvent.Load.class) {
            @Override
            public void invoke(final WorldClientEvent.Load event) {
                synchronized (Managers.HOLES) {
                    HoleManager.this.safe = (List<BlockPos>)Collections.emptyList();
                    HoleManager.this.unsafe = (List<BlockPos>)Collections.emptyList();
                    HoleManager.this.longHoles = (List<BlockPos>)Collections.emptyList();
                    HoleManager.this.bigHoles = (List<BlockPos>)Collections.emptyList();
                }
            }
        });
    }
    
    public List<BlockPos> getSafe() {
        return this.safe;
    }
    
    public List<BlockPos> getUnsafe() {
        return this.unsafe;
    }
    
    public List<BlockPos> getLongHoles() {
        return this.longHoles;
    }
    
    public List<BlockPos> getBigHoles() {
        return this.bigHoles;
    }
    
    private void runTick() {
        if (HoleManager.mc.player != null && HoleManager.mc.world != null && this.finished.get() && !this.observers.isEmpty()) {
            final double maxRange = this.getMaxRange();
            if (maxRange == 0.0) {
                return;
            }
            final int safes = this.observers.stream().max(Comparator.comparing((Function<? super HoleObserver, ? extends Comparable>)HoleObserver::getSafeHoles)).orElse(HoleManager.EMPTY).getSafeHoles();
            final int unsafes = this.observers.stream().max(Comparator.comparing((Function<? super HoleObserver, ? extends Comparable>)HoleObserver::getUnsafeHoles)).orElse(HoleManager.EMPTY).getSafeHoles();
            final int longs = this.observers.stream().max(Comparator.comparing((Function<? super HoleObserver, ? extends Comparable>)HoleObserver::get2x1Holes)).orElse(HoleManager.EMPTY).getUnsafeHoles();
            final int bigs = this.observers.stream().max(Comparator.comparing((Function<? super HoleObserver, ? extends Comparable>)HoleObserver::get2x2Holes)).orElse(HoleManager.EMPTY).getUnsafeHoles();
            if (safes != 0 || unsafes != 0 || longs != 0 || bigs != 0) {
                this.finished.set(false);
                this.calc(maxRange, safes, unsafes, longs, bigs);
            }
        }
    }
    
    protected void calc(final double maxRange, final int safes, final int unsafes, final int longs, final int bigs) {
        Managers.THREAD.submit(new HoleRunnable(this, maxRange, safes, unsafes, longs, bigs));
    }
    
    @Override
    public void setSafe(final List<BlockPos> safe) {
        this.safe = safe;
    }
    
    @Override
    public void setUnsafe(final List<BlockPos> unsafe) {
        this.unsafe = unsafe;
    }
    
    @Override
    public void setLongHoles(final List<BlockPos> longHoles) {
        this.longHoles = longHoles;
    }
    
    @Override
    public void setBigHoles(final List<BlockPos> bigHoles) {
        this.bigHoles = bigHoles;
    }
    
    @Override
    public void setFinished() {
        this.finished.set(true);
        synchronized (this) {
            this.notifyAll();
        }
    }
    
    public boolean register(final HoleObserver observer) {
        this.observers.add(observer);
        if (this.observers.size() == 1) {
            this.runTick();
            return true;
        }
        return false;
    }
    
    public void unregister(final HoleObserver observer) {
        this.observers.remove(observer);
    }
    
    public boolean isFinished() {
        return this.finished.get();
    }
    
    public double getMaxRange() {
        if (this.observers.isEmpty()) {
            return 0.0;
        }
        try {
            return Collections.max((Collection<? extends HoleObserver>)this.observers).getRange();
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    
    static {
        EMPTY = new EmptyHoleObserver();
    }
}
