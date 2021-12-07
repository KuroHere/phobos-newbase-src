// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.earth.earthhack.impl.commands.packet.arguments;

import net.minecraft.world.storage.*;
import me.earth.earthhack.impl.commands.packet.*;
import me.earth.earthhack.impl.commands.packet.exception.*;
import me.earth.earthhack.api.command.*;

public class MapDecorationArgument extends AbstractArgument<MapDecoration>
{
    private static final PacketArgument<MapDecoration.Type> TYPE;
    private static final PacketArgument<Byte> BYTE;
    
    public MapDecorationArgument() {
        super(MapDecoration.class);
    }
    
    @Override
    public MapDecoration fromString(final String argument) throws ArgParseException {
        final String[] split = argument.split(",");
        if (split.length != 4) {
            throw new ArgParseException("MapDecoration takes 4 arguments, but found: " + split.length);
        }
        final MapDecoration.Type type = MapDecorationArgument.TYPE.fromString(split[0]);
        final byte x = this.parseByte(split[0], "MapDecoration-X");
        final byte y = this.parseByte(split[1], "MapDecoration-Y");
        final byte rotation = this.parseByte(split[2], "MapDecoration-Rotation");
        return new MapDecoration(type, x, y, rotation);
    }
    
    @Override
    public PossibleInputs getPossibleInputs(final String argument) {
        PossibleInputs inputs = PossibleInputs.empty();
        if (argument == null || argument.isEmpty()) {
            return inputs.setRest("<MapDecoration:type,x,y,rotation");
        }
        final String[] split = argument.split(",");
        switch (split.length) {
            case 0: {
                return inputs.setRest("<MapDecoration:type,x,y,rotation");
            }
            case 1: {
                inputs = MapDecorationArgument.TYPE.getPossibleInputs(split[0]);
                return inputs.setCompletion(inputs.getCompletion() + ",").setRest("x,y,rotation");
            }
            case 2: {
                return inputs.setCompletion(",").setRest("y,rotation");
            }
            case 3: {
                return inputs.setCompletion(",").setRest("rotation");
            }
            default: {
                return inputs;
            }
        }
    }
    
    private byte parseByte(final String string, final String message) throws ArgParseException {
        try {
            return MapDecorationArgument.BYTE.fromString(string);
        }
        catch (ArgParseException e) {
            throw new ArgParseException("Couldn't parse " + message + " from " + string + "!");
        }
    }
    
    static {
        TYPE = new EnumArgument<MapDecoration.Type>(MapDecoration.Type.class);
        BYTE = new ByteArgument();
    }
}
