// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.util.minecraft.entity.module;

import me.earth.earthhack.api.module.*;
import me.earth.earthhack.api.setting.*;
import me.earth.earthhack.api.module.util.*;
import me.earth.earthhack.api.setting.settings.*;
import me.earth.earthhack.api.module.data.*;
import net.minecraft.entity.*;
import me.earth.earthhack.impl.core.ducks.entity.*;

public class EntityTypeModule extends Module
{
    public final Setting<Boolean> players;
    public final Setting<Boolean> monsters;
    public final Setting<Boolean> animals;
    public final Setting<Boolean> boss;
    public final Setting<Boolean> vehicles;
    public final Setting<Boolean> misc;
    public final Setting<Boolean> entities;
    
    public EntityTypeModule(final String name, final Category category) {
        super(name, category);
        this.players = this.register(new BooleanSetting("Players", true));
        this.monsters = this.register(new BooleanSetting("Monsters", false));
        this.animals = this.register(new BooleanSetting("Animals", false));
        this.boss = this.register(new BooleanSetting("Boss", false));
        this.vehicles = this.register(new BooleanSetting("Vehicles", false));
        this.misc = this.register(new BooleanSetting("Other", false));
        this.entities = this.register(new BooleanSetting("Entity", false));
        this.setData(new EntityTypeData<Object>(this));
    }
    
    public boolean isValid(final Entity entity) {
        if (entity == null) {
            return false;
        }
        switch (((IEntity)entity).getType()) {
            case Animal: {
                return this.animals.getValue();
            }
            case Monster: {
                return this.monsters.getValue();
            }
            case Player: {
                return this.players.getValue();
            }
            case Boss: {
                return this.boss.getValue();
            }
            case Vehicle: {
                return this.vehicles.getValue();
            }
            case Other: {
                return this.misc.getValue();
            }
            default: {
                return this.entities.getValue();
            }
        }
    }
}
