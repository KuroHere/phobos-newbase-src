//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.packetfly.util;

import net.minecraft.util.math.*;
import java.util.*;

public enum Type
{
    Down {
        @Override
        public Vec3d createOutOfBounds(final Vec3d vec3d, final int invalid) {
            return vec3d.addVector(0.0, (double)(-invalid), 0.0);
        }
    }, 
    Up {
        @Override
        public Vec3d createOutOfBounds(final Vec3d vec3d, final int invalid) {
            return vec3d.addVector(0.0, (double)invalid, 0.0);
        }
    }, 
    Preserve {
        private final Random random;
        
        {
            this.random = new Random();
        }
        
        private int randomInt() {
            final int result = this.random.nextInt(29000000);
            if (this.random.nextBoolean()) {
                return result;
            }
            return -result;
        }
        
        @Override
        public Vec3d createOutOfBounds(final Vec3d vec3d, final int invalid) {
            return vec3d.addVector((double)this.randomInt(), 0.0, (double)this.randomInt());
        }
    }, 
    Switch {
        private final Random random;
        
        {
            this.random = new Random();
        }
        
        @Override
        public Vec3d createOutOfBounds(final Vec3d vec3d, final int invalid) {
            final boolean down = this.random.nextBoolean();
            return down ? vec3d.addVector(0.0, (double)(-invalid), 0.0) : vec3d.addVector(0.0, (double)invalid, 0.0);
        }
    }, 
    X {
        @Override
        public Vec3d createOutOfBounds(final Vec3d vec3d, final int invalid) {
            return vec3d.addVector((double)invalid, 0.0, 0.0);
        }
    }, 
    Z {
        @Override
        public Vec3d createOutOfBounds(final Vec3d vec3d, final int invalid) {
            return vec3d.addVector(0.0, 0.0, (double)invalid);
        }
    }, 
    XZ {
        @Override
        public Vec3d createOutOfBounds(final Vec3d vec3d, final int invalid) {
            return vec3d.addVector((double)invalid, 0.0, (double)invalid);
        }
    };
    
    public abstract Vec3d createOutOfBounds(final Vec3d p0, final int p1);
}
