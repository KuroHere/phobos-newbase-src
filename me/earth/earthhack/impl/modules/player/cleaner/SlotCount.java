// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.player.cleaner;

public class SlotCount
{
    private int count;
    private int slot;
    
    public SlotCount(final int count, final int slot) {
        this.count = count;
        this.slot = slot;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
    
    public int getSlot() {
        return this.slot;
    }
    
    public void setSlot(final int slot) {
        this.slot = slot;
    }
}
