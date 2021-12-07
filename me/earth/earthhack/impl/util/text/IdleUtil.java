// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.text;

import me.earth.earthhack.impl.util.math.*;

public class IdleUtil
{
    private static final StopWatch dotTimer;
    private static final StopWatch undTimer;
    private static String dots;
    
    public static String getDots() {
        if (IdleUtil.dotTimer.passed(500L)) {
            IdleUtil.dots += ".";
            IdleUtil.dotTimer.reset();
        }
        if (IdleUtil.dots.length() > 3) {
            IdleUtil.dots = "";
        }
        return IdleUtil.dots;
    }
    
    public static String getUnderscore() {
        if (!IdleUtil.undTimer.passed(500L)) {
            return "_";
        }
        if (IdleUtil.undTimer.passed(1000L)) {
            IdleUtil.undTimer.reset();
        }
        return "";
    }
    
    static {
        dotTimer = new StopWatch();
        undTimer = new StopWatch();
        IdleUtil.dots = "";
    }
}
