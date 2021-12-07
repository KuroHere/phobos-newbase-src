// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.misc.announcer.util;

public class Announcement
{
    private final String name;
    private int amount;
    
    public Announcement(final String name, final int amount) {
        this.name = name;
        this.amount = amount;
    }
    
    public void setAmount(final int amount) {
        this.amount = amount;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public String getName() {
        return this.name;
    }
}
