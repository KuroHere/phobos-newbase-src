// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autocrystal.util;

public class TimeStamp
{
    private final long timeStamp;
    private boolean valid;
    
    public TimeStamp() {
        this.valid = true;
        this.timeStamp = System.currentTimeMillis();
    }
    
    public long getTimeStamp() {
        return this.timeStamp;
    }
    
    public boolean isValid() {
        return this.valid;
    }
    
    public void setValid(final boolean valid) {
        this.valid = valid;
    }
}
