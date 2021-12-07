// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.combat.autotrap.util;

import net.minecraft.util.math.*;

public class Trap
{
    public static final Vec3i[] OFFSETS;
    public static final Vec3i[] NO_STEP;
    public static final Vec3i[] LEGS;
    public static final Vec3i[] PLATFORM;
    public static final Vec3i[] NO_DROP;
    public static final Vec3i[] TOP;
    public static final Vec3i[] NO_SCAFFOLD;
    public static final Vec3i[] NO_SCAFFOLD_P;
    
    static {
        OFFSETS = new Vec3i[] { new Vec3i(1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(-1, 0, 0), new Vec3i(0, 0, -1) };
        NO_STEP = new Vec3i[] { new Vec3i(1, 1, 0), new Vec3i(0, 1, 1), new Vec3i(-1, 1, 0), new Vec3i(0, 1, -1) };
        LEGS = new Vec3i[] { new Vec3i(1, -1, 0), new Vec3i(0, -1, 1), new Vec3i(-1, -1, 0), new Vec3i(0, -1, -1) };
        PLATFORM = new Vec3i[] { new Vec3i(1, -2, 0), new Vec3i(0, -2, 1), new Vec3i(-1, -2, 0), new Vec3i(0, -2, -1) };
        NO_DROP = new Vec3i[] { new Vec3i(0, -3, 0) };
        TOP = new Vec3i[] { new Vec3i(0, 1, 0) };
        NO_SCAFFOLD = new Vec3i[] { new Vec3i(0, 2, 0) };
        NO_SCAFFOLD_P = new Vec3i[] { new Vec3i(0, 3, 0) };
    }
}
