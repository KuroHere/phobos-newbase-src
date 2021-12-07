// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.cleaner;

import me.earth.earthhack.api.setting.*;

public class ItemToDrop extends SlotCount
{
    private final Setting<Integer> setting;
    private int stacks;
    
    public ItemToDrop(final Setting<Integer> setting) {
        super(Integer.MAX_VALUE, 0);
        this.setting = setting;
    }
    
    public void addSlot(final int slot, final int count) {
        ++this.stacks;
        if (count < this.getCount()) {
            this.setSlot(slot);
            this.setCount(count);
        }
    }
    
    public boolean shouldDrop() {
        return this.setting == null || this.setting.getValue() < this.stacks;
    }
}
