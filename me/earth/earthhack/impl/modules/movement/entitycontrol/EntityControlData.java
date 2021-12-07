// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.modules.movement.entitycontrol;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

final class EntityControlData extends DefaultData<EntityControl>
{
    public EntityControlData(final EntityControl module) {
        super(module);
        this.register(module.control, "Controls entities that you normally can't control, like lamas.");
        this.register(module.jumpHeight, "Modify the JumpHeight of horses.");
        this.register(module.noAI, "Removes the AI of entities your ride.");
    }
    
    @Override
    public int getColor() {
        return -1;
    }
    
    @Override
    public String getDescription() {
        return "Control entities you ride.";
    }
}
