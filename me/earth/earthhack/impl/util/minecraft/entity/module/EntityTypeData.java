// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.entity.module;

import me.earth.earthhack.api.module.data.*;
import me.earth.earthhack.api.setting.*;

public class EntityTypeData<T extends EntityTypeModule> extends DefaultData<T>
{
    public EntityTypeData(final T module) {
        super(module);
        this.register(module.players, "Targets Players.");
        this.register(module.monsters, "Targets Monsters.");
        this.register(module.animals, "Targets Animals.");
        this.register(module.boss, "Targets Boss Monsters.");
        this.register(module.animals, "Targets Vehicles.");
        this.register(module.misc, "Targets Fireballs, ShulkerBullets etc.");
        this.register(module.misc, "Targets Unknown Entities.");
    }
}
