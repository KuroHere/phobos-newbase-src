// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.entityspeed;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class EntitySpeedData extends DefaultData<EntitySpeed>
{
    public EntitySpeedData(final EntitySpeed module) {
        super(module);
        this.register(module.speed, "The speed to move with.");
        this.register(module.noStuck, "Prevents you from getting stuck while riding.");
        this.register(module.resetStuck, "Makes you slower when using NoStuck.");
        this.register(module.stuckTime, "Time to be slowed down for after getting stuck.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Speed up the entity you are riding on.";
    }
}
