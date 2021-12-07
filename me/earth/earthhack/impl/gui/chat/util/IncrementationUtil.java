// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.gui.chat.util;

import org.lwjgl.input.*;
import java.math.*;
import me.earth.earthhack.impl.util.math.*;

public class IncrementationUtil
{
    public static final int MAX = 157;
    public static final int FASTER = 29;
    public static final int FAST = 56;
    
    public static String crD(final String d, final String min, final String max, final boolean de) {
        if (!Keyboard.isKeyDown(157)) {
            final BigDecimal v = new BigDecimal(d);
            final BigDecimal n = new BigDecimal(min);
            final BigDecimal m = new BigDecimal(max);
            BigDecimal incr;
            if (Keyboard.isKeyDown(29)) {
                if (Keyboard.isKeyDown(56)) {
                    final BigDecimal diff = m.subtract(n);
                    incr = diff.divide(new BigDecimal(de ? "-10" : "10"), RoundingMode.FLOOR);
                }
                else {
                    incr = new BigDecimal(de ? "-1.0" : "1.0");
                }
            }
            else if (Keyboard.isKeyDown(56)) {
                incr = new BigDecimal(de ? "-10.0" : "10.0");
            }
            else {
                incr = new BigDecimal(de ? "-0.1" : "0.1");
            }
            return MathUtil.clamp(v.add(incr), n, m).toString();
        }
        if (de) {
            return min;
        }
        return max;
    }
    
    public static double crF(final double d, final double min, final double max, final boolean de) {
        if (!Keyboard.isKeyDown(157)) {
            final BigDecimal v = new BigDecimal(d);
            final BigDecimal n = new BigDecimal(min);
            final BigDecimal m = new BigDecimal(max);
            BigDecimal incr;
            if (Keyboard.isKeyDown(29)) {
                if (Keyboard.isKeyDown(56)) {
                    final BigDecimal diff = m.subtract(n);
                    incr = diff.divide(new BigDecimal(de ? "-10" : "10"), RoundingMode.FLOOR);
                }
                else {
                    incr = new BigDecimal(de ? "-1.0" : "1.0");
                }
            }
            else if (Keyboard.isKeyDown(56)) {
                incr = new BigDecimal(de ? "-10.0" : "10.0");
            }
            else {
                incr = new BigDecimal(de ? "-0.1" : "0.1");
            }
            return MathUtil.clamp(v.add(incr), n, m).doubleValue();
        }
        if (de) {
            return min;
        }
        return max;
    }
    
    public static long crL(final long l, final long min, final long max, final boolean de) {
        if (!Keyboard.isKeyDown(157)) {
            long incr;
            if (Keyboard.isKeyDown(29)) {
                final long diff = max - min;
                if (Keyboard.isKeyDown(56)) {
                    incr = diff / 10L;
                }
                else {
                    incr = diff / 5L;
                }
            }
            else if (Keyboard.isKeyDown(56)) {
                incr = 10L;
            }
            else {
                incr = 1L;
            }
            return MathUtil.clamp(l + (de ? (-incr) : incr), min, max);
        }
        if (de) {
            return min;
        }
        return max;
    }
}
