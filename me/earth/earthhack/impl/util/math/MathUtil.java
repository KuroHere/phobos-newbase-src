//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.math;

import java.math.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.*;

public class MathUtil
{
    public static int clamp(final int num, final int min, final int max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static float clamp(final float num, final float min, final float max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static double clamp(final double num, final double min, final double max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static long clamp(final long num, final long min, final long max) {
        return (num < min) ? min : Math.min(num, max);
    }
    
    public static BigDecimal clamp(final BigDecimal num, final BigDecimal min, final BigDecimal max) {
        return smallerThan(num, min) ? min : (biggerThan(num, max) ? max : num);
    }
    
    public static boolean biggerThan(final BigDecimal bigger, final BigDecimal than) {
        return bigger.compareTo(than) > 0;
    }
    
    public static boolean equal(final BigDecimal bd1, final BigDecimal bd2) {
        return bd1.compareTo(bd2) == 0;
    }
    
    public static boolean smallerThan(final BigDecimal smaller, final BigDecimal than) {
        return smaller.compareTo(than) < 0;
    }
    
    public static long squareToLong(final int i) {
        return i * (long)i;
    }
    
    public static int square(final int i) {
        return i * i;
    }
    
    public static float square(final float i) {
        return i * i;
    }
    
    public static double square(final double i) {
        return i * i;
    }
    
    public static double simplePow(final double number, final int power) {
        if (power == 0) {
            return 1.0;
        }
        if (power < 0) {
            return 1.0 / simplePow(number, power * -1);
        }
        double result = number;
        for (int i = 1; i < power; ++i) {
            result *= number;
        }
        return result;
    }
    
    public static double round(final double value, final int places) {
        return (places < 0) ? value : new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static float round(final float value, final int places) {
        return (places < 0) ? value : new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).floatValue();
    }
    
    public static float round(final float value, final int places, final float min, final float max) {
        return MathHelper.clamp((places < 0) ? value : new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).floatValue(), min, max);
    }
    
    public static float rad(final float angle) {
        return (float)(angle * 3.141592653589793 / 180.0);
    }
    
    public static double degree(final double angle) {
        return angle / 3.141592653589793 * 180.0;
    }
    
    public static double angle(final Vec3d vec3d, final Vec3d other) {
        final double lengthSq = vec3d.lengthVector() * other.lengthVector();
        if (lengthSq < 1.0E-4) {
            return 0.0;
        }
        final double dot = vec3d.dotProduct(other);
        final double arg = dot / lengthSq;
        if (arg > 1.0) {
            return 0.0;
        }
        if (arg < -1.0) {
            return 180.0;
        }
        return Math.acos(arg) * 180.0 / 3.141592653589793;
    }
    
    public static Vec3d fromTo(final Vec3d from, final Vec3d to) {
        return fromTo(from.xCoord, from.yCoord, from.zCoord, to);
    }
    
    public static Vec3d fromTo(final Vec3d from, final double x, final double y, final double z) {
        return fromTo(from.xCoord, from.yCoord, from.zCoord, x, y, z);
    }
    
    public static Vec3d fromTo(final double x, final double y, final double z, final Vec3d to) {
        return fromTo(x, y, z, to.xCoord, to.yCoord, to.zCoord);
    }
    
    public static Vec3d fromTo(final double x, final double y, final double z, final double x2, final double y2, final double z2) {
        return new Vec3d(x2 - x, y2 - y, z2 - z);
    }
    
    public static double distance2D(final Vec3d from, final Vec3d to) {
        final double x = to.xCoord - from.xCoord;
        final double z = to.zCoord - from.zCoord;
        return Math.sqrt(x * x + z * z);
    }
    
    public static EnumFacing[] getRotated(final EnumFacing facing) {
        switch (facing) {
            case NORTH:
            case SOUTH: {
                return new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST };
            }
            case WEST:
            case EAST: {
                return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.SOUTH };
            }
        }
        return new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };
    }
    
    public static int getRandomInRange(final int min, final int max) {
        return min + new Random().nextInt(max - min);
    }
    
    public static double getRandomInRange(final double min, final double max) {
        final Random random = new Random();
        final double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        double shifted = scaled + min;
        if (shifted > max) {
            shifted = max;
        }
        return shifted;
    }
    
    public static float getRandomInRange(final float min, final float max) {
        final Random random = new Random();
        final float range = max - min;
        float scaled = random.nextFloat() * range;
        if (scaled > max) {
            scaled = max;
        }
        float shifted = scaled + min;
        if (shifted > max) {
            shifted = max;
        }
        return shifted;
    }
}
