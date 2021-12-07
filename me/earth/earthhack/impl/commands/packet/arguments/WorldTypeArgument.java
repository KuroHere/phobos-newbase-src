// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import me.earth.earthhack.impl.commands.packet.*;
import net.minecraft.world.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import java.lang.reflect.*;
import me.earth.earthhack.api.command.*;

public class WorldTypeArgument extends AbstractArgument<WorldType>
{
    public WorldTypeArgument() {
        super(WorldType.class);
    }
    
    @Override
    public WorldType fromString(final String argument) throws ArgParseException {
        final String[] split = argument.split(",");
        if (split.length < 2 || split.length > 3) {
            throw new ArgParseException("Expected 2-3 arguments for WorldType, but found " + split.length + "!");
        }
        final int id = (int)ArgParseException.tryLong(split[0], "WorldType-ID");
        int version = 0;
        if (split.length == 3) {
            version = (int)ArgParseException.tryLong(split[2], "Version");
        }
        try {
            final Constructor<WorldType> ctr = WorldType.class.getDeclaredConstructor(Integer.TYPE, String.class, Integer.TYPE);
            return ctr.newInstance(id, split[1], version);
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            final ReflectiveOperationException ex2;
            final ReflectiveOperationException e = ex2;
            e.printStackTrace();
            throw new ArgParseException("This definitely shouldn't happen, contact the dev!");
        }
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        final PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty()) {
            return inputs.setRest("<WorldType:id,name,version>");
        }
        final String[] split = argument.split(",");
        switch (split.length) {
            case 0: {
                return inputs.setRest("<WorldType:id,name,version>");
            }
            case 1: {
                return inputs.setCompletion(",").setRest("name,version");
            }
            case 2: {
                return inputs.setCompletion(",").setRest("version");
            }
            default: {
                return inputs;
            }
        }
    }
}
