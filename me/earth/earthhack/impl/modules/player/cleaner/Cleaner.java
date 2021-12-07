//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.cleaner;

import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.impl.util.math.*;
import me.earth.earthhack.impl.modules.combat.autoarmor.util.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.impl.util.helpers.addable.*;
import me.earth.earthhack.impl.util.client.*;
import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.impl.util.thread.*;

public class Cleaner extends ItemAddingModule<Integer, RemovingInteger>
{
    protected final Setting<Integer> delay;
    protected final Setting<Boolean> rotate;
    protected final Setting<Boolean> prioHotbar;
    protected final Setting<Boolean> stack;
    protected final Setting<Boolean> inInventory;
    protected final Setting<Boolean> stackDrag;
    protected final Setting<Boolean> smartStack;
    protected final Setting<Boolean> xCarry;
    protected final Setting<Boolean> dragCarry;
    protected final Setting<Boolean> cleanInLoot;
    protected final Setting<Boolean> cleanWithFull;
    protected final Setting<Boolean> sizeCheck;
    protected final Setting<Integer> minXcarry;
    protected final Setting<Integer> xCarryStacks;
    protected final Setting<Integer> globalDelay;
    protected final StopWatch timer;
    protected WindowClick action;
    
    public Cleaner() {
        super("Cleaner", Category.Player, s -> new RemovingInteger(s, 0, 0, 36), s -> "How many stacks of " + s.getName() + " to allow. When List-Type is Whitelist the value doesn't matter, all other items will be dropped.");
        this.delay = this.register(new NumberSetting("Delay", 500, 0, 10000));
        this.rotate = this.register(new BooleanSetting("Rotate", false));
        this.prioHotbar = this.register(new BooleanSetting("PrioHotbar", true));
        this.stack = this.register(new BooleanSetting("Stack", false));
        this.inInventory = this.register(new BooleanSetting("InInventory", false));
        this.stackDrag = this.register(new BooleanSetting("StackDrag", true));
        this.smartStack = this.register(new BooleanSetting("SmartStack", false));
        this.xCarry = this.register(new BooleanSetting("XCarry", false));
        this.dragCarry = this.register(new BooleanSetting("DragXCarry", true));
        this.cleanInLoot = this.register(new BooleanSetting("CleanInLoot", true));
        this.cleanWithFull = this.register(new BooleanSetting("LootFullInvClean", true));
        this.sizeCheck = this.register(new BooleanSetting("SizeCheck", true));
        this.minXcarry = this.register(new NumberSetting("Min-XCarry", 5, 0, 36));
        this.xCarryStacks = this.register(new NumberSetting("XCarry-Stacks", 31, 0, 36));
        this.globalDelay = this.register(new NumberSetting("Global-Delay", 500, 0, 10000));
        this.timer = new StopWatch();
        super.listType.setValue(ListType.BlackList);
        this.listeners.add(new ListenerMotion(this));
        final SimpleData data = new SimpleData(this, "Cleans up your Inventory.");
        data.register(this.delay, "Delay between 2 actions.");
        data.register(this.rotate, "Rotates away when throwing away an item.");
        data.register(this.prioHotbar, "Prioritizes the Hotbar.");
        data.register(this.stack, "Stacks Stacks.");
        data.register(this.inInventory, "Manages your Inventory while you are in it.");
        data.register(this.stackDrag, "Stacks the DragSlot.");
        data.register(this.smartStack, "");
        data.register(this.xCarry, "Puts Items in your XCarry");
        data.register(this.minXcarry, "Minimum amount of stacks you need to have of an item for it to be put in your XCarry");
        data.register(this.xCarryStacks, "Minimum amount of Stacks that you need to have in your Inventory for XCarry to be active.");
        this.setData(data);
    }
    
    public void runAction() {
        if (this.action != null) {
            Locks.acquire(Locks.WINDOW_CLICK_LOCK, () -> {
                if (this.action.isValid()) {
                    this.action.runClick(Cleaner.mc.playerController);
                }
                return;
            });
            this.timer.reset();
            this.action = null;
        }
    }
    
    public StopWatch getTimer() {
        return this.timer;
    }
    
    public int getDelay() {
        return this.delay.getValue();
    }
}
