//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\aesthetical\Documents\Development\Tools\Minecraft-Clients\1.12.2 mappings"!

// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.entity.*;
import me.earth.earthhack.api.util.interfaces.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.impl.commands.packet.util.*;
import net.minecraft.world.*;
import me.earth.earthhack.api.command.*;
import me.earth.earthhack.api.util.*;
import me.earth.earthhack.impl.util.thread.*;

public class EntityArgument extends AbstractArgument<Entity> implements Globals
{
    public EntityArgument() {
        super(Entity.class);
    }
    
    @Override
    public Entity fromString(final String arg) throws ArgParseException {
        if (EntityArgument.mc.world == null) {
            throw new ArgParseException("Minecraft.world was null!");
        }
        Entity entity = AbstractEntityArgument.getEntity(arg, Entity.class);
        if (entity == null) {
            int id = -1337;
            try {
                id = (int)Long.parseLong(arg);
            }
            catch (Exception ex) {}
            entity = new DummyEntity((World)EntityArgument.mc.world);
            entity.setEntityId(id);
        }
        return entity;
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        if (argument == null || argument.isEmpty()) {
            return new PossibleInputs("", "<" + this.getSimpleName() + ">");
        }
        final PossibleInputs inputs = PossibleInputs.empty();
        if (TextUtil.startsWith("$closest", argument)) {
            return inputs.setCompletion(TextUtil.substring("$closest", argument.length()));
        }
        final String name = LookUpUtil.findNextPlayerName(argument);
        if (name == null) {
            return inputs;
        }
        return inputs.setCompletion(TextUtil.substring(name, argument.length()));
    }
}
