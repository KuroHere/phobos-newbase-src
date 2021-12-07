//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import net.minecraft.entity.*;
import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import net.minecraft.entity.player.*;
import me.earth.earthhack.impl.util.minecraft.entity.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.core.ducks.entity.*;

public abstract class AbstractEntityArgument<T extends Entity> extends AbstractArgument<T> implements Globals
{
    protected final Class<T> directType;
    
    public AbstractEntityArgument(final Class<T> type) {
        super(type);
        this.directType = type;
    }
    
    @Override
    public T fromString(final String argument) throws ArgParseException {
        if (AbstractEntityArgument.mc.world == null) {
            throw new ArgParseException("Minecraft.world was null!");
        }
        Entity entity = null;
        if (EntityPlayer.class.isAssignableFrom(this.type)) {
            if ("$closest".equalsIgnoreCase(argument)) {
                entity = (Entity)EntityUtil.getClosestEnemy();
            }
            else {
                entity = (Entity)AbstractEntityArgument.mc.world.getPlayerEntityByName(argument);
            }
        }
        if (entity == null) {
            try {
                final int id = (int)Long.parseLong(argument);
                entity = AbstractEntityArgument.mc.world.getEntityByID(id);
                if (entity == null) {
                    entity = new DummyEntity((World)AbstractEntityArgument.mc.world);
                    entity.setEntityId(id);
                    ((IEntity)entity).setDummy(true);
                }
            }
            catch (Exception e) {
                throw new ArgParseException("Couldn't parse Entity from name or id!");
            }
        }
        return (T)entity;
    }
    
    public static <E extends Entity> E getEntity(final String argument, final Class<E> type) {
        Entity entity = null;
        if (type.isAssignableFrom(EntityPlayer.class)) {
            if (argument.equalsIgnoreCase("$closest")) {
                entity = (Entity)EntityUtil.getClosestEnemy();
            }
            else {
                entity = (Entity)AbstractEntityArgument.mc.world.getPlayerEntityByName(argument);
            }
        }
        if (entity == null) {
            try {
                final int id = (int)Long.parseLong(argument);
                entity = AbstractEntityArgument.mc.world.getEntityByID(id);
                if (!type.isInstance(entity)) {
                    return null;
                }
            }
            catch (Exception ex) {}
        }
        return (E)entity;
    }
}
