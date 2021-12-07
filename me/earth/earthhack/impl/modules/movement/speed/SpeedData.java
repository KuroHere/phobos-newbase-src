// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.speed;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class SpeedData extends DefaultData<Speed>
{
    public SpeedData(final Speed module) {
        super(module);
        this.register(module.mode, "-Instant always move at 20.5 km/h.\n-OldGround old OnGroundSpeed.\n-OnGround move quickly on flat surfaces.\nVanilla move quickly into all directions as specified by the Speed setting.");
        this.register(module.inWater, "Move quickly while in water.");
        this.register(module.speedSet, "Speed for Mode-Vanilla.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Movement hacks that make you go faster.";
    }
}
