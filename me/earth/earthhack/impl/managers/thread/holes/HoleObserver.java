// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.managers.thread.holes;

public interface HoleObserver extends Comparable<HoleObserver>
{
    double getRange();
    
    int getSafeHoles();
    
    int getUnsafeHoles();
    
    int get2x1Holes();
    
    int get2x2Holes();
    
    default int compareTo(final HoleObserver o) {
        return Double.compare(this.getRange(), o.getRange());
    }
}
