// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.replenish;

import me.earth.earthhack.api.setting.*;
import java.util.*;
import net.minecraft.item.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import java.util.concurrent.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;

public class Replenish extends RemovingItemAddingModule
{
    protected final Setting<Integer> threshold;
    protected final Setting<Integer> minSize;
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> putBack;
    protected final Setting<Boolean> replenishInLoot;
    protected final List<ItemStack> hotbar;
    protected final StopWatch timer;
    
    public Replenish() {
        super("Replenish", Category.Player, s -> "Black/Whitelists " + s.getName() + " from getting replenished.");
        this.threshold = this.register(new NumberSetting("Threshold", 10, 0, 64));
        this.minSize = this.register(new NumberSetting("MinSize", 54, 0, 64));
        this.delay = this.register(new NumberSetting("Delay", 50, 0, 500));
        this.putBack = this.register(new BooleanSetting("PutBack", true));
        this.replenishInLoot = this.register(new BooleanSetting("ReplenishInLoot", true));
        this.hotbar = new CopyOnWriteArrayList<ItemStack>();
        this.timer = new StopWatch();
        super.listType.setValue(ListType.BlackList);
        this.listeners.add(new ListenerTick(this));
        this.listeners.add(new ListenerLogout(this));
        this.listeners.add(new ListenerDeath(this));
        this.listeners.add(new ListenerWorldClient(this));
        final SimpleData data = new SimpleData(this, "Makes you never run out of items in your hotbar.");
        data.register(this.threshold, "If a stack in your hotbar reaches this threshold it will be replenished.");
        data.register(this.delay, "Delay between each time items are moved around in your Inventory. Low delays might cause Inventory desync.");
        data.register(this.putBack, "If this setting isn't enabled some items might end up in your dragslot.");
        this.setData(data);
        this.clear();
    }
    
    @Override
    protected void onEnable() {
        this.clear();
    }
    
    public void clear() {
        if (this.hotbar.isEmpty()) {
            for (int i = 0; i < 9; ++i) {
                this.hotbar.add(ItemStack.field_190927_a);
            }
        }
        else {
            for (int i = 0; i < 9; ++i) {
                this.hotbar.set(i, ItemStack.field_190927_a);
            }
        }
    }
}
